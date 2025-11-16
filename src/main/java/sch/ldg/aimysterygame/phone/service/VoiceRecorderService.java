package sch.ldg.aimysterygame.phone.service;

import org.springframework.stereotype.Service;
import sch.ldg.aimysterygame.phone.entity.VoiceRecordNpc;
import sch.ldg.aimysterygame.phone.repository.VoiceRecordInfoRepository;
import sch.ldg.aimysterygame.phone.repository.VoiceRecordNpcRepository;

@Service
public class VoiceRecorderService {
    private final VoiceRecordNpcRepository voiceRecorderNpc;
    private final VoiceRecordInfoRepository voiceRecorderInfo;

    public VoiceRecorderService(VoiceRecordNpcRepository voiceRecorderNpc, VoiceRecordInfoRepository voiceRecorderInfoRepository) {
        this.voiceRecorderNpc = voiceRecorderNpc;
        this.voiceRecorderInfo = voiceRecorderInfoRepository;
    }

    public Integer findVrnIdxByNpcIdAndUserId(String npcId, String userId) {
        VoiceRecordNpc vrnInfo = voiceRecorderNpc.findVrnIdxByNpcIdAndUserId(npcId, userId);
        if (vrnInfo == null) {
            VoiceRecordNpc npc = VoiceRecordNpc.builder()
                    .userId(userId)
                    .npcId(npcId)
                    .build();

            vrnInfo = createVoiceRecordNpc(npc);
        }

        return vrnInfo.getVrnIdx();
    }

    public VoiceRecordNpc createVoiceRecordNpc(VoiceRecordNpc npc) {
        return voiceRecorderNpc.save(npc);
    }
}
