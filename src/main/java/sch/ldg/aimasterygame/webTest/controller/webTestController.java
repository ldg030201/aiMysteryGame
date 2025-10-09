package sch.ldg.aimasterygame.webTest.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class webTestController {
    @GetMapping("/web")
    public String webTest(Model model) {
        model.addAttribute("showMsg", "1234Test");
        return "test";
    }
}
