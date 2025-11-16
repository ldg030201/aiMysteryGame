package sch.ldg.aimysterygame.phone.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Comment;

import java.time.LocalDateTime;

@Comment("녹음 npc 목록")
@Entity
@Table(name = "voice_record_npc")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class VoiceRecordNpc {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Comment("녹음 npc 목록 idx")
    private Integer vrnIdx;

    @Comment("사용자 id")
    private String userId;

    @Comment("npc id")
    private String npcId;

    @Comment("npc명")
    private String npcName;

    @Comment("시작 일시 (생성일시)")
    private LocalDateTime createDateTime;
}
