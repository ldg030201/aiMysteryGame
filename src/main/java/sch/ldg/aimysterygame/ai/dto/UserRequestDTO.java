package sch.ldg.aimysterygame.ai.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class UserRequestDTO {
    public String userId;
    public String playerInput;
    public String npcId;
    public List<String> knownClues;
    public String mode;          // "SETUP", "TALK", "VERDICT"
    public String killerId;
    public String killerReason;
}
