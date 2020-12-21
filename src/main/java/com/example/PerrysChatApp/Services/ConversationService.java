package com.example.PerrysChatApp.Services;

import com.example.PerrysChatApp.Exceptions.DuplicateConversationException;
import com.example.PerrysChatApp.Models.Conversation;
import com.example.PerrysChatApp.Models.Message;
import com.example.PerrysChatApp.Repositories.ConversationRepository;
import com.example.PerrysChatApp.Repositories.MessageRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ConversationService {
    private static final Logger logger = LoggerFactory.getLogger(ConversationService.class);

    @Autowired
    private ConversationRepository conversationRepository;

    @Autowired
    private MessageRepository messageRepository;

    public Conversation saveConversation(Conversation conversation) {
        logger.debug("Entered ConversationService.saveConversation()");
        Conversation savedConversation = null;
        if (conversationRepository.findExistingConversation(conversation.getParticipantOneId(),
                conversation.getParticipantTwoId()) == null) {
            savedConversation = conversationRepository.save(conversation);
        } else {
            throw new DuplicateConversationException("Conversation between " + conversation.getParticipantOneId() + " and "
                    + conversation.getParticipantTwoId() + " has already been created.");
        }

        return savedConversation;
    }

    public Iterable<Conversation> findAllConversations() {
        logger.debug("Entered ConversationService.findAllConversations()");
        return conversationRepository.findAll();
    }

    public Conversation getConversationById(Long conversationId) {
        logger.debug("Entered ConversationService.getConversationById()");
        Optional<Conversation> conversation = conversationRepository.findById(conversationId);
        return conversation.isPresent() ? conversation.get() : null;
    }

    public Iterable<Conversation> getAllConversationsForUser(Long userId) {
        logger.debug("Entered ConversationService.getAllConversationsForUser()");
        // This returns a JSON or XML with the conversations for a specific user
        return conversationRepository.findAllUsersConversations(userId);
    }

    public Iterable<Message> getAllMessagesFromConversation(Long conversationId) {
        logger.debug("Entered ConversationService.getAllMessagesFromConversation()");
        return messageRepository.retrieveAllConversationMessages(conversationId);
    }
}
