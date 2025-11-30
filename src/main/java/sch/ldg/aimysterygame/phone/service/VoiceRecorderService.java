package sch.ldg.aimysterygame.phone.service;

import org.springframework.stereotype.Service;
import sch.ldg.aimysterygame.ai.dto.GameStateDTO;
import sch.ldg.aimysterygame.ai.service.GameStateStoreService;
import sch.ldg.aimysterygame.phone.entity.VoiceRecordInfo;
import sch.ldg.aimysterygame.phone.entity.VoiceRecordNpc;
import sch.ldg.aimysterygame.phone.repository.VoiceRecordInfoRepository;
import sch.ldg.aimysterygame.phone.repository.VoiceRecordNpcRepository;
import sch.ldg.aimysterygame.unityAPI.dto.gameData.SuspectDTO;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class VoiceRecorderService {
    private final VoiceRecordNpcRepository voiceRecorderNpc;
    private final VoiceRecordInfoRepository voiceRecorderInfo;
    private final GameStateStoreService gameStateStoreService;

    public VoiceRecorderService(VoiceRecordNpcRepository voiceRecorderNpc, VoiceRecordInfoRepository voiceRecorderInfoRepository, GameStateStoreService gameStateStoreService) {
        this.voiceRecorderNpc = voiceRecorderNpc;
        this.voiceRecorderInfo = voiceRecorderInfoRepository;
        this.gameStateStoreService = gameStateStoreService;
    }

    public VoiceRecordNpc findByNpcIdAndUserId(String npcId, String userId) {
        VoiceRecordNpc npc = voiceRecorderNpc.findByNpcIdAndUserId(npcId, userId);

        if (npc == null) {
            String npcName = gameStateStoreService.getNpcNameByNpcId(userId, npcId);
            npc = VoiceRecordNpc.builder()
                    .userId(userId)
                    .npcId(npcId)
                    .npcName(npcName)
                    .createDateTime(LocalDateTime.now())
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

    public List<VoiceRecordNpc> findRecordNpcByUserId(String userId) {
        return voiceRecorderNpc.findByUserId(userId);
    }

    public List<VoiceRecordInfo> findRecordInfoByVrnIdx(VoiceRecordNpc npc) {
        return voiceRecorderInfo.findByVrnInfo(npc);
    }
}
