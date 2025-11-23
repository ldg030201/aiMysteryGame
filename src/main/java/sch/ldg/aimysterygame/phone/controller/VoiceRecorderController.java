package sch.ldg.aimysterygame.phone.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import sch.ldg.aimysterygame.phone.entity.VoiceRecordNpc;
import sch.ldg.aimysterygame.phone.service.VoiceRecorderService;

import java.util.List;

@Controller
public class VoiceRecorderController {
    private final VoiceRecorderService voiceRecorderService;

    public VoiceRecorderController(VoiceRecorderService voiceRecorderService) {
        this.voiceRecorderService = voiceRecorderService;
    }

    @GetMapping(value = "/phone/voice-recorder")
    public String phoneVoiceRecorder(HttpServletRequest request, Model model) {
        String userId = request.getParameter("userId");
        List<VoiceRecordNpc> recordNpcList = voiceRecorderService.findRecordNpcByUserId(userId);
        model.addAttribute("recordNpcList", recordNpcList);

        return "phone/voice-recorder";
    }
}
