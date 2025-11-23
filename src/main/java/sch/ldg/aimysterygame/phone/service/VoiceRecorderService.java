package sch.ldg.aimysterygame.phone.service;

import org.springframework.stereotype.Service;
import sch.ldg.aimysterygame.phone.entity.VoiceRecordInfo;
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

    public VoiceRecordNpc findByNpcIdAndUserId(String npcId, String userId) {
        VoiceRecordNpc npc = voiceRecorderNpc.findByNpcIdAndUserId(npcId, userId);

        if (npc == null) {
            npc = VoiceRecordNpc.builder()
                    .userId(userId)
                    .npcId(npcId)
                    .build();

            return createVoiceRecordNpc(npc);
        }

        return npc;
    }

    public VoiceRecordNpc createVoiceRecordNpc(VoiceRecordNpc npc) {
        return voiceRecorderNpc.save(npc);
    }

    public void createVoiceRecordInfo(VoiceRecordInfo userRecord) {
        voiceRecorderInfo.save(userRecord);
    }
}
