package sch.ldg.aimysterygame.ai.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class VerdictResponseDTO {
    private boolean correct;    // 정답 여부
    private String killerName;  // 실제 범인 이름
    private String caseSummary; // 사건의 전말(동기 + 수법 + 진행 요약)
}
