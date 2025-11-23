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
            GameStateDTO stateDto = gameStateStoreService.getStateByUser(userId);
            String npcName = stateDto.getGameData().getSuspects().stream()
                    .filter(dto -> npcId.equals(dto.getId()))
                    .map(SuspectDTO::getName)
                    .findFirst()
                    .orElse("");

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
}
