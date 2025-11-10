package sch.ldg.aimysterygame.unityAPI.dto.gameData;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class TimeDTO {
    private String t;
    private String location;
    private List<String> who;
    private String event;
}
