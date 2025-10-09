package sch.ldg.aimasterygame.restTest;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import sch.ldg.aimasterygame.restTest.entity.TestEntity;
import sch.ldg.aimasterygame.restTest.service.TestService;

import java.util.List;

@RestController
public class RestTestController {
    private final TestService testService;

    public RestTestController(TestService testService) {
        this.testService = testService;
    }

    @GetMapping("/test")
    public String test() {
        return "test";
    }

    @GetMapping("/test-db")
    public List<TestEntity> testDb() {
        return testService.getAllTests();
    }
}
