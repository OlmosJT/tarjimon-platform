package io.olmosjt.auth.domain.repository;

import io.olmosjt.common.entity.identity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<UserEntity, UUID> {
    Optional<UserEntity> findByEmail(String email);
    Optional<UserEntity> findByGoogleSub(String googleSub);
    boolean existsByEmail(String email);
}
