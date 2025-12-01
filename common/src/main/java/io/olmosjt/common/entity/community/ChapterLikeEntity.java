package io.olmosjt.common.entity.community;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.io.Serializable;
import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "chapter_likes", schema = "community")
@Getter @Setter @Builder
@NoArgsConstructor
@AllArgsConstructor
@IdClass(ChapterLikeEntity.ChapterLikeId.class)
public class ChapterLikeEntity {
    @Id
    @Column(name = "user_id", nullable = false)
    private UUID userId;

    @Id
    @Column(name = "chapter_id", nullable = false)
    private UUID chapterId;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private Instant createdAt;

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @EqualsAndHashCode
    public static class ChapterLikeId implements Serializable {
        private UUID userId;
        private UUID chapterId;
    }
}
