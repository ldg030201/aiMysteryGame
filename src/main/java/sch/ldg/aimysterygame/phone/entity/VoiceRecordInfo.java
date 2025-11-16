package sch.ldg.aimysterygame.phone.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Comment;

import java.time.LocalDateTime;

@Comment("녹음 정보")
@Entity
@Table(name = "voice_record_info")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class VoiceRecordInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Comment("녹음 정보 idx")
    private Integer vriIdx;

    @ManyToOne
    @JoinColumn(name = "vrnIdx")
    @Comment("녹음 npc 목록 idx")
    private VoiceRecordNpc vrnInfo;

    @Comment("대화내용")
    private String contents;

    @Comment("사용자 대화 여부")
    private Boolean isUser;

    @Comment("대화 했던 일시")
    private LocalDateTime createDateTime;
}
