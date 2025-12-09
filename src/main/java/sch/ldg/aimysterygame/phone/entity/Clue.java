package sch.ldg.aimysterygame.phone.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Comment;

import java.time.LocalDateTime;

@Comment("단서 정보")
@Entity
@Table(name = "clue")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Clue {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Comment("단서 정보 idx")
    private Integer clueId;

    @Comment("사용자 id")
    private String userId;

    @Comment("단서 이미지 및 unity id")
    private String clueImgId;

    @Comment("단서 획득 일시")
    private LocalDateTime createDateTime;
}
