package sch.ldg.aimysterygame.unityAPI.dto.gameData;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class GameDataDTO {
    private String title;
    private String background;
    private List<RoomDTO> map;
    private List<SuspectDTO> suspects;
    private List<ClueDTO> clues;
    private PublicViewDTO publicView;
}
