package sch.ldg.aimysterygame.ai.dto;

import com.google.genai.types.Content;
import lombok.Getter;
import lombok.Setter;
import sch.ldg.aimysterygame.unityAPI.dto.gameData.GameDataDTO;

import java.util.ArrayDeque;
import java.util.Deque;

@Getter
@Setter
public class GameStateDTO {
    private String cacheName;
    private Deque<Content> history = new ArrayDeque<>();
    private GameDataDTO gameData;
    private boolean setupComplete;

    private boolean contextPrimed;
}
