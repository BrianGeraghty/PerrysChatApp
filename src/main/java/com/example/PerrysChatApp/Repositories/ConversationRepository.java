package com.example.PerrysChatApp.Repositories;

import com.example.PerrysChatApp.Models.Conversation;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.Collection;

public interface ConversationRepository extends CrudRepository<Conversation, Long> {

    @Query(
            value = "SELECT * FROM conversations c WHERE c.participant_one_id = ?1 OR c.participant_two_id = ?1",
            nativeQuery = true)
    Collection<Conversation> findAllUsersConversations(Long id);

    @Query(
            value = "SELECT * FROM conversations c WHERE (c.participant_one_id = ?1 AND c.participant_two_id = ?2) " +
                    "OR (c.participant_one_id = ?2 AND c.participant_two_id = ?1)",
            nativeQuery = true)
    Conversation findExistingConversation(Long participantOne, Long participantTwo);

}
