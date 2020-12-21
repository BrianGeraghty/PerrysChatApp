package com.example.PerrysChatApp.Repositories;

import com.example.PerrysChatApp.Models.Message;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.Collection;

public interface MessageRepository extends CrudRepository<Message, Long> {

    @Query(
            value = "SELECT * FROM messages m WHERE m.conversation_id = ?1",
            nativeQuery = true)
    Collection<Message> retrieveAllConversationMessages(Long id);
}
