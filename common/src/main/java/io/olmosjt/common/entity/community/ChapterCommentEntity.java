package io.olmosjt.common.entity.community;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "chapter_comments", schema = "community",
        indexes = {
                @Index(name = "idx_comments_chapter", columnList = "chapter_id")
        }
)
@Getter @Setter @Builder
@NoArgsConstructor
@AllArgsConstructor
public class ChapterCommentEntity {
        @Id
        @GeneratedValue(strategy = GenerationType.UUID)
        private UUID id;

        @Column(name = "chapter_id", nullable = false)
        private UUID chapterId;

        @Column(name = "user_id", nullable = false)
        private UUID userId;

        @Column(name = "parent_comment_id")
        private UUID parentCommentId;

        @Column(nullable = false, columnDefinition = "text")
        private String content;

        @Column(name = "is_spoiler", nullable = false)
        @Builder.Default
        private boolean isSpoiler = false;

        @CreationTimestamp
        @Column(name = "created_at", updatable = false)
        private Instant createdAt;

        @UpdateTimestamp
        @Column(name = "updated_at")
        private Instant updatedAt;
}
