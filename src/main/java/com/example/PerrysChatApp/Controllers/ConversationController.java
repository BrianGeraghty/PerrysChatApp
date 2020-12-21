package com.example.PerrysChatApp.Controllers;

import com.example.PerrysChatApp.Exceptions.ConversationNotFoundException;
import com.example.PerrysChatApp.Exceptions.DuplicateConversationException;
import com.example.PerrysChatApp.Models.Conversation;
import com.example.PerrysChatApp.Models.Message;
import com.example.PerrysChatApp.Services.ConversationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping(path="/api/v1/conversations")
public class ConversationController {
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private ConversationService conversationService;

    @PostMapping(path="")
    public @ResponseBody
    ResponseEntity createConversation(@RequestBody Conversation conversation) {
        logger.debug("Entered ConversationController.createConversation()");
        try {
            return ResponseEntity.status(201).body(conversationService.saveConversation(conversation));
        } catch (DataIntegrityViolationException e) {
            logger.error(e.getMessage());
           throw e;
        }
    }

    @GetMapping(path="")
    public @ResponseBody Iterable<Conversation> getAllConversations() {
        logger.debug("Entered ConversationController.getAllConversations()");
        return conversationService.findAllConversations();
    }

    @GetMapping(path="/{conversationId}")
    public @ResponseBody
    Conversation getConversation(@PathVariable Long conversationId) {
        logger.debug("Entered ConversationController.getConversation()");
        Conversation conversation = conversationService.getConversationById(conversationId);
        if (conversation == null) throw new ConversationNotFoundException(String.valueOf(conversationId));

        return conversation;
    }

    @GetMapping(path="/{conversationId}/messages")
    public @ResponseBody Iterable<Message> getAllConversationMessages(@PathVariable Long conversationId) {
        logger.debug("Entered ConversationController.getAllConversationMessages()");
        return conversationService.getAllMessagesFromConversation(conversationId);
    }

    @GetMapping(path="/filter")
    public @ResponseBody
    ResponseEntity<Iterable<Conversation>> getAllUserConversations(@RequestParam Long userId) {
        logger.debug("Entered ConversationController.getAllUserConversations()");
        return ResponseEntity.ok(conversationService.getAllConversationsForUser(userId));
    }
}
