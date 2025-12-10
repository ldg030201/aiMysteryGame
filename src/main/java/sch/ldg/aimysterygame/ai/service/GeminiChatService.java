package sch.ldg.aimysterygame.ai.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.genai.Client;
import com.google.genai.errors.ClientException;
import com.google.genai.types.CachedContent;
import com.google.genai.types.Content;
import com.google.genai.types.CreateCachedContentConfig;
import com.google.genai.types.GenerateContentConfig;
import com.google.genai.types.GenerateContentResponse;
import com.google.genai.types.Part;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import sch.ldg.aimysterygame.ai.dto.GameStateDTO;
import sch.ldg.aimysterygame.ai.dto.UserRequestDTO;
import sch.ldg.aimysterygame.ai.dto.VerdictResponseDTO;
import sch.ldg.aimysterygame.phone.entity.VoiceRecordInfo;
import sch.ldg.aimysterygame.phone.entity.VoiceRecordNpc;
import sch.ldg.aimysterygame.phone.service.MemoService;
import sch.ldg.aimysterygame.phone.service.VoiceRecorderService;
import sch.ldg.aimysterygame.unityAPI.dto.gameData.GameDataDTO;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class GeminiChatService {
    private final Client genai;

    private final Set<String> primedUsers = ConcurrentHashMap.newKeySet();

    @Value("${app.ai.system-prompt}")
    private String systemPrompt;

    private static final String MODEL = "gemini-2.5-pro";
    private static final float TEMPERATURE = 0.3f;

    private static final int MAX_RECENT_MESSAGES = 20; //전체는 20번 (사용자 + 시스템 요청)

    private static final ObjectMapper MAPPER = new ObjectMapper();

    private static final Gson GSON = new Gson();

    private final VoiceRecorderService voiceRecorderService;
    private final GameStateStoreService gameStateStoreService;
    private final MemoService memoService;

    public GeminiChatService(Client genai, VoiceRecorderService voiceRecorderService, GameStateStoreService gameStateStoreService, MemoService memoService) {
        this.genai = genai;
        this.voiceRecorderService = voiceRecorderService;
        this.gameStateStoreService = gameStateStoreService;
        this.memoService = memoService;
    }

    //설정단계
    public GameDataDTO startGame(UserRequestDTO dto) {
        if (dto == null) throw new IllegalArgumentException("UserRequestDTO가 null입니다.");
        String userId = safeTrim(dto.getUserId());
        if (userId == null || userId.isEmpty()) throw new IllegalArgumentException("userId는 필수입니다.");

        dto.setMode("SETUP");

        GameStateDTO st = gameStateStoreService.getStateByUser(dto.getUserId());

        if (st.getCacheName() == null) {
            st.setCacheName(createCacheForUser(userId));
        }

        Map<String, Object> meta = new LinkedHashMap<>();
        meta.put("mode", dto.getMode());
        meta.put("source", "gameSystem");
        meta.put("userId", dto.getUserId());

        Content userMsg = Content.builder()
                .role("user")
                .parts(
                        Part.builder().text("[META]\n" + toJson(meta)).build(),
                        Part.builder().text(
                                "지금은 [셋업 단계]입니다. 외부 입력 없이 전부 스스로 정하고 시나리오를 생성하세요. " +
                                        "출력은 '오직 JSON 객체 하나'만. 코드블록/백틱/설명 텍스트 금지."
                        ).build()
                )
                .build();

        GenerateContentConfig.Builder cfgBuilder = GenerateContentConfig.builder().temperature(TEMPERATURE);
        if (isNonBlank(st.getCacheName())) {
            cfgBuilder.cachedContent(st.getCacheName());
        } else {
            cfgBuilder.systemInstruction(
                    Content.builder()
                            .role("system")
                            .parts(Part.builder().text(systemPrompt).build())
                            .build()
            );
        }
        try {
            GenerateContentConfig.class
                    .getMethod("responseMimeType", String.class)
                    .invoke(cfgBuilder, "application/json");
        } catch (Exception ignore) {}

        List<Content> payload = List.of(userMsg);

        GenerateContentResponse resp = genai.models.generateContent(MODEL, payload, cfgBuilder.build());
        String raw = resp.text();
        String json = extractJsonObject(raw);
        if (json == null) {
            String head = raw == null ? "null" : raw.substring(0, Math.min(300, raw.length()));
            throw new RuntimeException("셋업 응답에서 JSON을 추출하지 못했습니다. 응답 head: " + head);
        }

        GameDataDTO gameData = GSON.fromJson(json, GameDataDTO.class);
        st.setGameData(gameData);
        st.setSetupComplete(true);

        st.setTrueKillerId(null);
        st.setTrueKillerName(null);
        st.setTrueCaseSummary(null);

        st.getHistory().clear();
        primedUsers.remove(userId);

        //npc 메모장 저장
        memoService.saveNpcMemo(userId, gameData.getSuspects());

        return gameData;
    }

    //플레이 단계
    public String talk(UserRequestDTO dto) {
        if (dto == null) throw new IllegalArgumentException("UserRequestDTO가 null입니다.");
        String userId = safeTrim(dto.getUserId());
        if (userId == null || userId.isEmpty()) throw new IllegalArgumentException("userId는 필수입니다.");

        dto.setMode("TALK");

        GameStateDTO st = gameStateStoreService.getStateByUser(dto.getUserId());
        if (st == null || !st.isSetupComplete() || st.getGameData() == null) {
            return "게임이 아직 준비되지 않았어요. 먼저 /startGame 을 호출해 주세요.";
        }

        List<Content> payload = new ArrayList<>();

        if (!primedUsers.contains(userId)) {
            Content gameDataContext = Content.builder()
                    .role("user")
                    .parts(Part.builder().text("[GAME_DATA]\n" + toJson(st.getGameData())).build())
                    .build();
            payload.add(gameDataContext);
            primedUsers.add(userId);
        }

        for (Content c : st.getHistory()) {
            if (!"system".equalsIgnoreCase(c.role().orElse(""))) {
                payload.add(c);
            }
        }

        String responseRules =
                "[RESPONSE_RULES]\n" +
                        "- 자연어 대화만. 선택지/리스트/JSON/코드블록/백틱 금지.\n" +
                        "- NPC 1인칭 화법, 1~3문장 이내로 답하고 보통 질문으로 마무리.\n" +
                        "- 스포일러/정답 암시 금지(플레이어가 단서를 요구할 때만 간접 힌트).";

        Map<String, Object> userPayload = new LinkedHashMap<>();
        userPayload.put("mode", dto.getMode());
        userPayload.put("npcId", dto.getNpcId());
        userPayload.put("playerInput", dto.getPlayerInput());
        userPayload.put("knownClues", dto.getKnownClues());

        Map<String, Object> meta = new LinkedHashMap<>();
        meta.put("mode", dto.getMode());
        meta.put("source", "player");
        meta.put("userId", dto.getUserId());

        Content userMsg = Content.builder()
                .role("user")
                .parts(
                        Part.builder().text("[META]\n" + toJson(meta)).build(),
                        Part.builder().text(responseRules).build(),
                        Part.builder().text("[PLAYER_INPUT]\n" + toJson(userPayload)).build()
                )
                .build();

        payload.add(userMsg);

        GenerateContentConfig.Builder cfgBuilder = GenerateContentConfig.builder().temperature(TEMPERATURE);
        if (isNonBlank(st.getCacheName())) {
            cfgBuilder.cachedContent(st.getCacheName());
        } else {
            cfgBuilder.systemInstruction(
                    Content.builder()
                            .role("system")
                            .parts(Part.builder().text(systemPrompt).build())
                            .build()
            );
        }
        try {
            GenerateContentConfig.class
                    .getMethod("responseMimeType", String.class)
                    .invoke(cfgBuilder, "text/plain");
        } catch (Exception ignore) {}

        GenerateContentResponse resp = genai.models.generateContent(MODEL, payload, cfgBuilder.build());
        String answerRaw = resp.text();
        String answer = stripCodeFences(answerRaw).trim(); // 혹시 붙은 백틱 제거

        st.getHistory().addLast(userMsg);
        st.getHistory().addLast(Content.builder().role("model").parts(Part.builder().text(answer).build()).build());
        trimToMax(st.getHistory(), MAX_RECENT_MESSAGES);

        VoiceRecordNpc voiceRecordNpc = voiceRecorderService.findByNpcIdAndUserId(dto.getNpcId(), dto.getUserId());

        //유저 대화 삽입
        VoiceRecordInfo userRecord = VoiceRecordInfo.builder()
                .isUser(true)
                .vrnInfo(voiceRecordNpc)
                .contents(dto.getPlayerInput())
                .createDateTime(LocalDateTime.now())
                .build();
        voiceRecorderService.createVoiceRecordInfo(userRecord);

        //AI 답변
        VoiceRecordInfo aiRecord = VoiceRecordInfo.builder()
                .isUser(false)
                .vrnInfo(voiceRecordNpc)
                .contents(answer)
                .createDateTime(LocalDateTime.now())
                .build();
        voiceRecorderService.createVoiceRecordInfo(aiRecord);

        return answer;
    }

    //정답 판정
    public VerdictResponseDTO checkAnswer(UserRequestDTO dto) {
        if (dto == null) throw new IllegalArgumentException("UserRequestDTO가 null입니다.");
        String userId = safeTrim(dto.getUserId());
        if (userId == null || userId.isEmpty()) {
            throw new IllegalArgumentException("userId는 필수입니다.");
        }

        dto.setMode("VERDICT");

        GameStateDTO st = gameStateStoreService.getStateByUser(dto.getUserId());
        if (st == null || !st.isSetupComplete() || st.getGameData() == null) {
            throw new IllegalStateException("게임이 아직 준비되지 않았어요. 먼저 /startGame 을 호출해 주세요.");
        }

        ensureTruthComputed(userId, st);

        boolean correct = st.getTrueKillerId() != null
                && dto.getKillerId() != null
                && st.getTrueKillerId().equals(dto.getKillerId());

        VerdictResponseDTO result = new VerdictResponseDTO();
        result.setCorrect(correct);
        result.setKillerName(st.getTrueKillerName() == null ? "" : st.getTrueKillerName());
        result.setCaseSummary(st.getTrueCaseSummary() == null ? "" : st.getTrueCaseSummary());

        return result;
    }

    private void ensureTruthComputed(String userId, GameStateDTO st) {
        if (st.getTrueKillerId() != null) {
            return; // 이미 계산됨
        }

        String rules =
                "[CANONICAL_TRUTH]\n" +
                        "- 아래 GAME_DATA는 이미 완성된 추리 게임 시나리오 전체 JSON입니다.\n" +
                        "- 이 시나리오를 기준으로 실제 범인을 '정확히 한 명' 선택하세요.\n" +
                        "- 플레이어의 선택은 아직 고려하지 마세요(여기서는 정답만 정의합니다).\n" +
                        "- 출력은 오직 JSON 객체 하나만, 코드블록/백틱/추가 설명 텍스트 금지.\n" +
                        "- JSON 스키마:\n" +
                        "  {\"trueKillerId\": \"npc001 처럼 용의자 ID\", " +
                        "   \"killerName\": \"실제 범인 이름\", " +
                        "   \"caseSummary\": \"사건의 전말(동기+수법+주요 흐름, 스포일러 허용)\"}";

        Map<String, Object> meta = new LinkedHashMap<>();
        meta.put("mode", "CANONICAL_VERDICT");
        meta.put("source", "system-truth");
        meta.put("userId", userId);

        Content userMsg = Content.builder()
                .role("user")
                .parts(
                        Part.builder().text("[META]\n" + toJson(meta)).build(),
                        Part.builder().text(rules).build(),
                        Part.builder().text("[GAME_DATA]\n" + toJson(st.getGameData())).build()
                )
                .build();

        List<Content> payload = List.of(userMsg);

        GenerateContentConfig.Builder cfgBuilder = GenerateContentConfig.builder()
                .temperature(TEMPERATURE);
        if (isNonBlank(st.getCacheName())) {
            cfgBuilder.cachedContent(st.getCacheName());
        } else {
            cfgBuilder.systemInstruction(
                    Content.builder()
                            .role("system")
                            .parts(Part.builder().text(systemPrompt).build())
                            .build()
            );
        }
        try {
            GenerateContentConfig.class
                    .getMethod("responseMimeType", String.class)
                    .invoke(cfgBuilder, "application/json");
        } catch (Exception ignore) {}

        try {
            GenerateContentResponse resp = genai.models.generateContent(MODEL, payload, cfgBuilder.build());
            String raw = resp.text();
            String json = extractJsonObject(raw);
            if (json != null) {
                Map<String, Object> m = GSON.fromJson(json, Map.class);
                Object tk = m.get("trueKillerId");
                Object kn = m.get("killerName");
                Object cs = m.get("caseSummary");

                if (tk != null) st.setTrueKillerId(String.valueOf(tk));
                if (kn != null) st.setTrueKillerName(String.valueOf(kn));
                if (cs != null) st.setTrueCaseSummary(String.valueOf(cs));
            }
        } catch (Exception e) {
            // 실패 시 truth는 null로 남고, 나중에 checkAnswer에서 correct=false + 빈 설명이 나가게 됨
        }

        if (st.getTrueKillerId() == null) {
            st.setTrueKillerId("");
        }
        if (st.getTrueKillerName() == null) {
            st.setTrueKillerName("");
        }
        if (st.getTrueCaseSummary() == null) {
            st.setTrueCaseSummary("");
        }
    }

    private void trimToMax(Deque<Content> deque, int max) {
        while (deque.size() > max) deque.removeFirst();
    }

    private String toJson(Object o) {
        try {
            return MAPPER.writeValueAsString(o);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    private String extractJsonObject(String raw) {
        if (raw == null) return null;
        String s = raw.trim();

        if (s.startsWith("```")) {
            int firstNewLine = s.indexOf('\n');
            if (firstNewLine > 0) s = s.substring(firstNewLine + 1).trim();
            int lastFence = s.lastIndexOf("```");
            if (lastFence >= 0) s = s.substring(0, lastFence).trim();
        }
        int start = s.indexOf('{');
        if (start < 0) return null;

        int level = 0;
        boolean inString = false, esc = false;
        for (int i = start; i < s.length(); i++) {
            char c = s.charAt(i);
            if (inString) {
                if (esc) esc = false;
                else if (c == '\\') esc = true;
                else if (c == '"') inString = false;
            } else {
                if (c == '"') inString = true;
                else if (c == '{') level++;
                else if (c == '}') {
                    level--;
                    if (level == 0) return s.substring(start, i + 1).trim();
                }
            }
        }
        return null;
    }

    private String stripCodeFences(String s) {
        if (s == null) return null;
        String t = s.trim();
        if (t.startsWith("```")) {
            int firstNl = t.indexOf('\n');
            if (firstNl > 0) t = t.substring(firstNl + 1);
            int lastFence = t.lastIndexOf("```");
            if (lastFence >= 0) t = t.substring(0, lastFence);
        }
        while (t.startsWith("`")) t = t.substring(1);
        while (t.endsWith("`")) t = t.substring(0, t.length() - 1);
        return t.trim();
    }

    private String safeTrim(String s) {
        return s == null ? null : s.trim();
    }

    private boolean isNonBlank(String s) {
        return s != null && !s.isBlank();
    }

    private String createCacheForUser(String userId) {
        try {
            Content system = Content.builder()
                    .role("system")
                    .parts(Part.builder().text(systemPrompt).build())
                    .build();

            CachedContent created = genai.caches.create(
                    MODEL,
                    CreateCachedContentConfig.builder()
                            .systemInstruction(system)
                            .ttl(Duration.ofHours(12))
                            .displayName("game-" + userId)
                            .build()
            );

            return created.name().orElseThrow(() -> new IllegalArgumentException("캐시 name을 받지 못함"));
        } catch (ClientException e) {
            return null; //캐시 양이 작아서 오류 나는 경우 처리
        }
    }
}
