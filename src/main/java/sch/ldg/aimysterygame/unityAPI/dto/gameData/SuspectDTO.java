package sch.ldg.aimysterygame.unityAPI.dto.gameData;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class SuspectDTO {
    private String id;
    private String name;
    private Integer age;
    private String job;
    private String role;
    private List<RelationshipDTO> relationships;
    private AlibiDTO alibi;
}
