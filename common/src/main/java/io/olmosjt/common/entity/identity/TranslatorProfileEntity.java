package io.olmosjt.common.entity.identity;

import io.olmosjt.common.enums.TranslatorBadge;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "translator_profiles", schema = "identity")
@Getter @Setter @Builder
@NoArgsConstructor @AllArgsConstructor
public class TranslatorProfileEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "user_id", nullable = false, unique = true)
    private UUID userId;

    @Column(name = "display_name", nullable = false)
    private String displayName;

    @Column(columnDefinition = "text")
    private String bio;

    @Column(name = "avatar_url")
    private String avatarUrl;

    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "translator_badge_type")
    private TranslatorBadge badge;

    @Column(name = "total_projects")
    @Builder.Default
    private Integer totalProjects = 0;

    @Column(name = "total_followers")
    @Builder.Default
    private Integer totalFollowers = 0;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private Instant createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private Instant updatedAt;
}
