package sch.ldg.aimysterygame.unityAPI.dto.gameData;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ClueDTO {
    private String id;
    private String name;
    private String type;
    private String foundAt;
    private String description;
    private String relevance;
    private Boolean tamperable;
}
