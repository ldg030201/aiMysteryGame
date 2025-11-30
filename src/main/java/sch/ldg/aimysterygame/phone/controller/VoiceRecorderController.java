package sch.ldg.aimysterygame.phone.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import sch.ldg.aimysterygame.phone.entity.VoiceRecordInfo;
import sch.ldg.aimysterygame.phone.entity.VoiceRecordNpc;
import sch.ldg.aimysterygame.phone.service.VoiceRecorderService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class VoiceRecorderController {
    private final VoiceRecorderService voiceRecorderService;

    public VoiceRecorderController(VoiceRecorderService voiceRecorderService) {
        this.voiceRecorderService = voiceRecorderService;
    }

    @GetMapping(value = "/phone/voice-recorder")
    public String phoneVoiceRecorder(HttpServletRequest request, Model model) {
        String userId = request.getParameter("userId");

        //npc 목록 가져오기
        List<VoiceRecordNpc> recordNpcList = voiceRecorderService.findRecordNpcByUserId(userId);
        model.addAttribute("recordNpcList", recordNpcList);

        //채팅 목록 가져오기 (모든 npc)
        Map<Integer, List<VoiceRecordInfo>> recordInfoMap = new HashMap<>();
        for (VoiceRecordNpc recordNpc : recordNpcList) {
            Integer vrnIdx = recordNpc.getVrnIdx();
            List<VoiceRecordInfo> recordInfoList = voiceRecorderService.findRecordInfoByVrnIdx(recordNpc);
            recordInfoMap.put(vrnIdx, recordInfoList);
        }
        model.addAttribute("recordInfoMap", recordInfoMap);

        return "phone/voice-recorder";
    }
}
