package io.olmosjt.common.entity.content;

import io.olmosjt.common.enums.ProjectStatus;
import io.olmosjt.common.enums.ProjectType;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.type.SqlTypes;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "projects", schema = "content")
@Getter @Setter @Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProjectEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "translator_id", nullable = false)
    private UUID translatorId;

    @Column(nullable = false, length = 255)
    private String title;

    @Column(name = "original_author_name", length = 255)
    private String originalAuthorName;

    @Column(name = "original_language", length = 50, nullable = false)
    private String originalLanguage;

    @Column(name = "target_language", length = 50, nullable = false)
    @Builder.Default
    private String targetLanguage = "uz";

    @Column(columnDefinition = "text")
    private String description;

    @Column(name = "author_description", columnDefinition = "text")
    private String authorDescription;

    @Column(name = "cover_image_url", length = 255)
    private String coverImageUrl;

    @Enumerated(EnumType.STRING)
    @Column(name = "project_type", nullable = false, columnDefinition = "project_type_enum")
    private ProjectType projectType;

    @Column(length = 100)
    private String genre;


    @JdbcTypeCode(SqlTypes.ARRAY)
    @Column(name = "tags", columnDefinition = "text[]")
    private String[] tags;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, columnDefinition = "project_status_enum")
    @Builder.Default
    private ProjectStatus status = ProjectStatus.ONGOING;

    @Column(name = "total_chapters", nullable = false)
    @Builder.Default
    private Integer totalChapters = 0;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private Instant createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private Instant updatedAt;
}
