package sch.ldg.aimysterygame.unityAPI.dto.gameData;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class GameDataDTO {
    private String title;
    private String background;
    private MapDTO map;
    private List<SuspectDTO> suspects;
    private List<ClueDTO> clues;
    private List<TimeDTO> timeline;
    private PublicViewDTO publicView;
    private AnswerKeyDTO answerKey;
    private ReportCenterDTO reportCenter;
}
