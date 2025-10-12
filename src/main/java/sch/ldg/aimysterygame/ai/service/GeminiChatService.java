package sch.ldg.aimysterygame.ai.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.genai.Client;
import com.google.genai.errors.ClientException;
import com.google.genai.types.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import sch.ldg.aimysterygame.ai.dto.UserRequestDTO;

import java.time.Duration;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class GeminiChatService {
    private final Client genai;

    private final Map<String, String> cacheIdByUser = new ConcurrentHashMap<>();
    private final Map<String, Deque<Content>> recentByUser = new ConcurrentHashMap<>();

    @Value("${app.ai.system-prompt}")
    private String systemPrompt;

    private static final String MODEL = "gemini-2.0-flash";
    private static final float TEMPERATURE = 0.3f;

    private static final int MAX_RECENT_MESSAGES = 20; //전체는 20번 (사용자 + 시스템 요청)

    public GeminiChatService(Client genai) {
        this.genai = genai;
    }

    public String chat(UserRequestDTO dto) throws JsonProcessingException {
        String userId = dto.getScenarioId();
        ObjectMapper mapper = new ObjectMapper();
        String prompt = mapper.writeValueAsString(dto); //json 으로 변환

        //시스템 prompt 캐시처리
        String cacheName = cacheIdByUser.computeIfAbsent(userId, this::createCacheForUser);

        //사용자 큐
        Deque<Content> deque = recentByUser.computeIfAbsent(userId, k -> new ArrayDeque<>());

        Content userMsg = Content.builder()
                .role("user")
                .parts(Part.builder().text(prompt).build())
                .build();

        List<Content> payload = new ArrayList<>(deque);
        payload.add(userMsg);

        GenerateContentConfig.Builder cfgBuilder = GenerateContentConfig.builder().temperature(TEMPERATURE);
        if (cacheName != null && !cacheName.isBlank()) {
            cfgBuilder.cachedContent(cacheName);
        } else {
            cfgBuilder.systemInstruction(
                Content.builder()
                    .parts(Part.builder().text(prompt).build())
                    .build()
            );
        }

        GenerateContentConfig cfg = cfgBuilder.build();

        GenerateContentResponse resp = genai.models.generateContent(MODEL, payload, cfg); //값 보내기
        String answer = resp.text(); //받은 답

        deque.addLast(userMsg);
        deque.addLast(Content.builder()
                .role("model")
                .parts(Part.builder().text(answer).build())
                .build());

        trimToMax(deque, MAX_RECENT_MESSAGES); //턴 짜르기

        return answer;
    }

    private void trimToMax(Deque<Content> deque, int max) {
        while (deque.size() > max) deque.removeFirst();
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
