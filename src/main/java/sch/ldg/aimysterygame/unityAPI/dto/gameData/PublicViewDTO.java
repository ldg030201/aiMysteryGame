package sch.ldg.aimysterygame.unityAPI.dto.gameData;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class PublicViewDTO {
    private String overview;
    private List<SuspectSummary> suspectSummaries;
}
