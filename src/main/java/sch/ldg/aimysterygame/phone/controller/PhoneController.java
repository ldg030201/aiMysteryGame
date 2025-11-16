package sch.ldg.aimysterygame.phone.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = "/phone")
public class PhoneController {
    @GetMapping(value = "/qr")
    public String qr() {
        return "phone/qr";
    }

    @GetMapping(value = "/main")
    public String phoneMain() {
        return "phone/main";
    }

    @GetMapping(value = "/voice-recorder")
    public String phoneVoiceRecorder() {
        return "phone/voice-recorder";
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
