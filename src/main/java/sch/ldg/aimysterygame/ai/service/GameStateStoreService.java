package sch.ldg.aimysterygame.ai.service;

import org.springframework.stereotype.Service;
import sch.ldg.aimysterygame.ai.dto.GameStateDTO;

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
}
