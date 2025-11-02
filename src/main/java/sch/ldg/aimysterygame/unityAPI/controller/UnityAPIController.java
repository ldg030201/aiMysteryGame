package sch.ldg.aimysterygame.unityAPI.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sch.ldg.aimysterygame.ai.dto.UserRequestDTO;
import sch.ldg.aimysterygame.ai.service.GeminiChatService;

import java.util.Map;

@RestController
@RequestMapping(("/chat"))
public class UnityAPIController {
    private final GeminiChatService geminiChatService;

    public UnityAPIController(GeminiChatService geminiChatService) {
        this.geminiChatService = geminiChatService;
    }

    //게임 시작 (게임 셋업)
    @PostMapping("/startGame")
    public ResponseEntity<Map<String, Object>> startGame(@RequestBody UserRequestDTO dto) {
        return ResponseEntity.ok(geminiChatService.startGame(dto));
    }

    //npc와 대화 구현
    @PostMapping("/talk")
    public ResponseEntity<String> talk(@RequestBody UserRequestDTO dto) {
        return ResponseEntity.ok(geminiChatService.talk(dto));
    }
}
