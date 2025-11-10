package sch.ldg.aimysterygame.unityAPI.dto.gameData;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class AnswerKeyDTO {
    private String killerId;
    private String motive;
    private String method;
    private List<FalseStatementDTO> falseStatements;
    private List<String> truthGates;
    private List<RedHerringDTO> redHerrings;
    private Integer requiredCluesToConvict;
}
