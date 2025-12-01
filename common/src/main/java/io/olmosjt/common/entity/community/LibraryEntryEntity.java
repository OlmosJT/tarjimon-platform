package io.olmosjt.common.entity.community;

import io.olmosjt.common.enums.ReadingStatus;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.io.Serializable;
import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "library_entries", schema = "community",
        indexes = {
                @Index(name = "idx_library_user", columnList = "user_id")
        }
)
@Getter @Setter @Builder
@NoArgsConstructor
@AllArgsConstructor
@IdClass(LibraryEntryEntity.LibraryEntryId.class)
public class LibraryEntryEntity {

    @Id
    @Column(name = "user_id", nullable = false)
    private UUID userId;

    @Id
    @Column(name = "project_id", nullable = false)
    private UUID projectId;

    @Column(name = "last_read_chapter_id")
    private UUID lastReadChapterId;

    @Enumerated(EnumType.STRING)
    @Column(name = "reading_status", nullable = false, length = 20)
    @Builder.Default
    private ReadingStatus readingStatus = ReadingStatus.READING;

    @CreationTimestamp
    @Column(name = "added_at", updatable = false)
    private Instant addedAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private Instant updatedAt;

    @Getter @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @EqualsAndHashCode
    public static class LibraryEntryId implements Serializable {
        private UUID userId;
        private UUID projectId;
    }

}
