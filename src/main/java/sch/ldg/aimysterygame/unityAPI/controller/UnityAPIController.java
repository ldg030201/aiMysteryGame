package sch.ldg.aimysterygame.unityAPI.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sch.ldg.aimysterygame.ai.dto.UserRequestDTO;
import sch.ldg.aimysterygame.ai.dto.VerdictResponseDTO;
import sch.ldg.aimysterygame.ai.service.GeminiChatService;
import sch.ldg.aimysterygame.unityAPI.dto.gameData.GameDataDTO;

@RestController
@RequestMapping(("/chat"))
@Slf4j
public class UnityAPIController {
    private final GeminiChatService geminiChatService;

    public UnityAPIController(GeminiChatService geminiChatService) {
        this.geminiChatService = geminiChatService;
    }

    //게임 시작 (게임 셋업)
    @PostMapping("/startGame")
    public ResponseEntity<GameDataDTO> startGame(@RequestBody UserRequestDTO dto) {
        log.debug("setUp 시작");
        return ResponseEntity.ok(geminiChatService.startGame(dto));
    }

    //npc와 대화 구현
    @PostMapping("/talk")
    public ResponseEntity<String> talk(@RequestBody UserRequestDTO dto) {
        return ResponseEntity.ok(geminiChatService.talk(dto));
    }

    //범인 맞추기
    @PostMapping("/checkAnswer")
    public ResponseEntity<VerdictResponseDTO> checkAnswer(@RequestBody UserRequestDTO dto) {
        VerdictResponseDTO res = geminiChatService.checkAnswer(dto);
        return ResponseEntity.ok(res);
    }
}
