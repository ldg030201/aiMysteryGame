package sch.ldg.aimysterygame.phone.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Comment;

import java.time.LocalDateTime;

@Comment("메모 정보")
@Entity
@Table(name = "memo")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Memo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Comment("메모 정보 idx")
    private Integer memoId;

    @Comment("사용자 id")
    private String userId;

    @Comment("메모 제목")
    private String title;

    @Comment("메모내용")
    private String memo;

    @Comment("최종 수정일시 일시")
    private LocalDateTime updateDateTime;
}
