package sch.ldg.aimasterygame.restTest;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import sch.ldg.aimasterygame.restTest.dto.PromptDTO;
import sch.ldg.aimasterygame.restTest.entity.TestEntity;
import sch.ldg.aimasterygame.restTest.service.TestService;

import java.util.List;

@RestController
public class RestTestController {
    private final TestService testService;
    private final ChatClient chatClient; //spring ai를 통한 채팅

    public RestTestController(TestService testService, ChatClient.Builder chatClient) {
        this.testService = testService;
        this.chatClient = chatClient.build();
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
}
