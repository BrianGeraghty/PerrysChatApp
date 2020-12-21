package com.example.PerrysChatApp.Repositories;

import com.example.PerrysChatApp.Models.LikedMessage;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface LikedMessageRepository extends CrudRepository<LikedMessage, Long> {
    @Query(
            value = "SELECT COUNT(*) FROM liked_messages l WHERE l.message_id = ?1 AND l.user_id = ?2",
            nativeQuery = true)
    Integer likeAlreadyExists(Long messageId, Long userId);
}
