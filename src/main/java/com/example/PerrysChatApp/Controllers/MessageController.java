package com.example.PerrysChatApp.Controllers;

import com.example.PerrysChatApp.Exceptions.*;
import com.example.PerrysChatApp.Models.Message;
import com.example.PerrysChatApp.Services.MessageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping(path="/api/v1/messages")
public class MessageController {
    private static final Logger logger = LoggerFactory.getLogger(MessageController.class);

    @Autowired
    private MessageService messageService;

    @PostMapping(path="")
    public @ResponseBody ResponseEntity sendMessage(@RequestBody Message message) {
        logger.debug("Entered MessageController.sendMessage()");
        try {
            return ResponseEntity.status(201).body(messageService.saveMessage(message));
        } catch (DataIntegrityViolationException e) {
            logger.error("Error sending message");
            throw e;
        }
    }

    @PutMapping(path="/{messageId}")
    public @ResponseBody
    ResponseEntity editMessage(@RequestBody Message message) {
        logger.debug("Entered MessageController.editMessage()");
        return ResponseEntity.ok(messageService.editMessageText(message));
    }

    @DeleteMapping(path="/{messageId}")
    public @ResponseBody
    ResponseEntity<String> deleteMessage(@PathVariable Long messageId) {
        logger.debug("Entered MessageController.deleteMessage()");
        return messageService.deleteMessage(messageId);
    }

    @GetMapping(path="/{messageId}")
    public @ResponseBody
    ResponseEntity getMessage(@PathVariable Long messageId) {
        logger.debug("Entered MessageController.getMessage()");
        Message message = messageService.getMessageById(messageId);
        if (message == null) throw new MessageNotFoundException(String.valueOf(messageId));
        return ResponseEntity.ok(message);
    }

    @GetMapping(path="")
    public @ResponseBody Iterable<Message> getAllMessages() {
        // This returns a JSON or XML with the messages
        logger.debug("Entered MessageController.getAllMessages()");
        return messageService.getAllMessages();
    }
}
