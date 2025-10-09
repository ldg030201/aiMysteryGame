package sch.ldg.aimasterygame.restTest;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RestTestController {
    @GetMapping("/test")
    public String test(){
        return "test";
    }
}
