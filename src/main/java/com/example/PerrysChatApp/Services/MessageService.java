package com.example.PerrysChatApp.Services;

import com.example.PerrysChatApp.Exceptions.InvalidEditException;
import com.example.PerrysChatApp.Exceptions.InvalidMessageException;
import com.example.PerrysChatApp.Exceptions.MessageNotFoundException;
import com.example.PerrysChatApp.Models.Conversation;
import com.example.PerrysChatApp.Models.Message;
import com.example.PerrysChatApp.Repositories.ConversationRepository;
import com.example.PerrysChatApp.Repositories.MessageRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
public class MessageService {

    private static final Logger messagingServiceLogger = LoggerFactory.getLogger(MessageService.class);

    @Autowired
    private MessageRepository messageRepository;

    @Autowired
    private ConversationRepository conversationRepository;

    @Autowired
    private ConversationService conversationService;

    public Message saveMessage(Message message) {
        messagingServiceLogger.info("Entering MessageService.saveMessage()");
        String messageText = message.getMessageText();
        Long sender = message.getSenderId();
        Long recipient = message.getRecipientId();
        Long conversationId = message.getConversationId();

        if (messageText == null || messageText.isBlank()) {
            messagingServiceLogger.warn("Message text was blank");
            throw new InvalidMessageException("Message cannot be blank");
        }

        if (conversationId == null && (sender == null || recipient == null)) {
            messagingServiceLogger.warn("Missing sender or recipient or conversationId");
            throw new InvalidMessageException("Missing sender or recipient or conversationId");
        }

        if (conversationId == null) {
            messagingServiceLogger.warn("checking for existing conversation");
            messagingServiceLogger.info("sender: " + sender + " recipient: " + recipient);
            // check if conversation already exists
            Conversation existingConversation = conversationRepository.
                    findExistingConversation(sender, recipient);

            if (existingConversation == null) {
                messagingServiceLogger.info("conversation did not exist, attempting to create it");
                conversationId = conversationService.saveConversation(new Conversation(sender, recipient)).getId();
            } else {
                conversationId = existingConversation.getId();
            }
        }

        Message savedMessage = messageRepository.save(new Message(conversationId, sender, recipient, messageText));
        messagingServiceLogger.info("Leaving MessageService.saveMessage()");
        return savedMessage;
    }

    public Message editMessageText(Message message) {
        messagingServiceLogger.info("Entering MessageService.editMessageText()");
        Message existingMessage = messageRepository.findById(message.getId()).get();

        if (existingMessage == null) throw new MessageNotFoundException(String.valueOf(message.getId()));

        if (existingMessage.getSenderId() == message.getSenderId()) {
            existingMessage.setMessageText(message.getMessageText());
        } else {
            messagingServiceLogger.error("Message edit attempted by message recipient.");
            throw new InvalidEditException("User " + message.getSenderId() + " attempted to edit a message created by "
                    + message.getRecipientId());
        }

        messagingServiceLogger.info("Exiting MessageService.editMessageText()");
        return messageRepository.save(existingMessage);
    }

    public ResponseEntity<String> deleteMessage(Long messageId) {
        messagingServiceLogger.info("Entering MessageService.deleteMessage()");
        messageRepository.deleteById(messageId);
        messagingServiceLogger.info("Exiting MessageService.deleteMessage()");
        return ResponseEntity.ok().body("Message Deleted");
    }

    public Message getMessageById(Long messageId) {
        messagingServiceLogger.info("Entering MessageService.getMessageById()");
        // This returns a JSON or XML with the message
        Optional<Message> message = messageRepository.findById(messageId);
        return message.isPresent() ? message.get() : null;
    }

    public Iterable<Message> getAllMessages() {
        messagingServiceLogger.info("Entering MessageService.getAllMessages()");
        // This returns a JSON or XML with the messages
        return messageRepository.findAll();
    }

}
