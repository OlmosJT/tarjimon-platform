package io.olmosjt.common.entity.content;

import io.olmosjt.common.enums.ChapterStatus;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "chapters", schema = "content", uniqueConstraints = {
                @UniqueConstraint(name = "uk_project_sequence", columnNames = {"project_id", "sequence_number"})
        }
)
@Getter @Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ChapterEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "project_id", nullable = false)
    private UUID projectId;

    @Column(name = "sequence_number", nullable = false)
    private Integer sequenceNumber;

    @Column(nullable = false, length = 255)
    private String title;

    @Column(name = "google_doc_id", length = 255)
    private String googleDocId;

    @Column(name = "google_doc_url", length = 500)
    private String googleDocUrl;

    @Column(name = "content_body", columnDefinition = "text")
    private String contentBody;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, columnDefinition = "chapter_status_enum")
    @Builder.Default
    private ChapterStatus status = ChapterStatus.CREATED;

    @Column(name = "published_at")
    private Instant publishedAt;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private Instant createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private Instant updatedAt;
}
