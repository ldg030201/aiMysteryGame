package sch.ldg.aimysterygame.unityAPI.dto.gameData;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class RoomDTO {
    private String id;
    private String name;
    private String description;
    private List<RoomDTO> connection;
}
