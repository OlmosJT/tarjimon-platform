package io.olmosjt.common.entity.identity;

import io.olmosjt.common.enums.TranslatorApplicationStatus;
import io.olmosjt.common.enums.TranslatorBadge;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.type.SqlTypes;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "translator_applications", schema = "identity")
@Getter @Setter @Builder
@NoArgsConstructor
@AllArgsConstructor
public class TranslatorApplicationEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "user_id", nullable = false)
    private UUID userId;

    @Column(name = "full_name", nullable = false)
    private String fullName;

    private String profession;

    @Enumerated(EnumType.STRING)
    @Column(name = "badge_request", nullable = false, columnDefinition = "translator_badge_type")
    private TranslatorBadge badgeRequest;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "known_languages", columnDefinition = "jsonb")
    private List<String> knownLanguages;

    @Column(name = "test_submission_text", columnDefinition = "text")
    private String testSubmissionText;

    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "t_application_status_type")
    @Builder.Default
    private TranslatorApplicationStatus status = TranslatorApplicationStatus.PENDING;

    @Column(name = "rejection_reason", columnDefinition = "text")
    private String rejectionReason;

    @Column(name = "reviewed_at")
    private Instant reviewedAt;

    @Column(name = "reviewed_by")
    private UUID reviewedBy;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private Instant createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private Instant updatedAt;
}
