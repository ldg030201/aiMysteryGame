package sch.ldg.aimysterygame.phone.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = "/phone")
public class PhoneController {
    @GetMapping(value = "/qr")
    public String qr(HttpServletRequest request, Model model) {
        String userId = request.getParameter("userId");
        model.addAttribute("userId", userId);

        return "phone/qr";
    }

    @GetMapping(value = "/main")
    public String phoneMain(HttpServletRequest request, Model model) {
        String userId = request.getParameter("userId");
        model.addAttribute("userId", userId);

        return "phone/main";
    }

    @GetMapping(value = "/memo")
    public String phoneMemo() {
        return "phone/memo";
    }

    @GetMapping(value = "/gallery")
    public String phoneGallery() {
        return "phone/gallery";
    }
}
