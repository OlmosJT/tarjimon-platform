package io.olmosjt.common.entity.community;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.io.Serializable;
import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "user_follows", schema = "community"
)
@Getter @Setter @Builder
@NoArgsConstructor
@AllArgsConstructor
@IdClass(UserFollowEntity.UserFollowId.class)
public class UserFollowEntity {
    @Id
    @Column(name = "follower_id", nullable = false)
    private UUID followerId;

    @Id
    @Column(name = "followed_id", nullable = false)
    private UUID followedId;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private Instant createdAt;


    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @EqualsAndHashCode
    public static class UserFollowId implements Serializable {
        private UUID followerId;
        private UUID followedId;
    }

}
