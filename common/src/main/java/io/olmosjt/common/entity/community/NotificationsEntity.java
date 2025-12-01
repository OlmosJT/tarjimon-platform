package io.olmosjt.common.entity.community;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "notifications", schema = "community",
        indexes = {
                @Index(name = "idx_notifications_recipient", columnList = "recipient_id")
        }
)
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NotificationsEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "recipient_id", nullable = false)
    private UUID recipientId;

    @Column(name = "actor_id")
    private UUID actorId;

    @Column(name = "type", nullable = false, length = 50)
    private String type;

    @Column(name = "entity_id")
    private UUID entityId;

    @Column(name = "is_read", nullable = false)
    @Builder.Default
    private boolean isRead = false;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private Instant createdAt;
}
