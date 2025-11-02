package sch.ldg.aimysterygame.ai.dto;

import com.google.genai.types.Content;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Map;

@Getter
@Setter
public class GameStateDTO {
    private String cacheName;
    private Deque<Content> history = new ArrayDeque<>();
    private Map<String, Object> gameData;
    private boolean setupComplete;

    private boolean contextPrimed;
}
