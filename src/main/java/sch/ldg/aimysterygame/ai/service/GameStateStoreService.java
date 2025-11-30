package sch.ldg.aimysterygame.ai.service;

import org.springframework.stereotype.Service;
import sch.ldg.aimysterygame.ai.dto.GameStateDTO;
import sch.ldg.aimysterygame.unityAPI.dto.gameData.SuspectDTO;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class GameStateStoreService {
    private final Map<String, GameStateDTO> stateByUser = new ConcurrentHashMap<>();

    public GameStateDTO getStateByUser(String userId) {
        GameStateDTO dto =  stateByUser.get(userId);

        if (dto == null) {
            setStateByUser(userId, new GameStateDTO());
        }

        return stateByUser.get(userId);
    }

    public void setStateByUser(String userId, GameStateDTO state) {
        stateByUser.put(userId, state);
    }

    public String getNpcNameByNpcId(String userId, String npcId) {
        GameStateDTO gameState = getStateByUser(userId);
        return gameState.getGameData().getSuspects().stream()
                .filter(dto -> npcId.equals(dto.getId()))
                .map(SuspectDTO::getName)
                .findFirst()
                .orElse("이름없음");
    }
}
