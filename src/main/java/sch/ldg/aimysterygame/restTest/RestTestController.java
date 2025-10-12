package sch.ldg.aimysterygame.restTest;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import sch.ldg.aimysterygame.ai.dto.UserRequestDTO;
import sch.ldg.aimysterygame.ai.service.GeminiChatService;
import sch.ldg.aimysterygame.restTest.dto.PromptDTO;
import sch.ldg.aimysterygame.restTest.entity.TestEntity;
import sch.ldg.aimysterygame.restTest.service.TestService;

import java.util.List;

@RestController

public class RestTestController {
    private final TestService testService;
    private final ChatClient chatClient; //spring ai를 통한 채팅
    private final GeminiChatService geminiChatService;

    public RestTestController(TestService testService, ChatClient.Builder chatClient, GeminiChatService geminiChatService) {
        this.testService = testService;
        this.chatClient = chatClient.build();
        this.geminiChatService = geminiChatService;
    }

    @GetMapping("/test")
    public String test() {
        return "test";
    }

    @GetMapping("/test-db")
    public List<TestEntity> testDb() {
        return testService.getAllTests();
    }

    @PostMapping("/test-ai")
    public String testAi(@RequestBody PromptDTO promptDTO) {
        return chatClient.prompt(promptDTO.getPrompt()).call().content();
    }

    @PostMapping("/chat")
    public String chat(@RequestBody UserRequestDTO dto) throws JsonProcessingException {
        return geminiChatService.chat(dto);
    }
}
