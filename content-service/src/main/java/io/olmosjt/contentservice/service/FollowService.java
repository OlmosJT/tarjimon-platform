package io.olmosjt.contentservice.service;

import io.olmosjt.common.dto.FollowersDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface FollowService {

    Page<FollowersDto.Translator> getFollowersWhoAreTranslators(UUID currentUserId, Pageable pageable);

    Page<FollowersDto.Reader> getFollowersWhoAreReaders(UUID currentUserId, Pageable pageable);

    boolean followUser(UUID currentUserId, UUID targetUserId);
    boolean unfollowUser(UUID currentUserId, UUID targetUserId);

}
