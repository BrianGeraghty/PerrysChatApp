package com.example.PerrysChatApp.Exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value= HttpStatus.NOT_FOUND)
public class ConversationNotFoundException extends RuntimeException {

    private static final String ERROR_MESSAGE = "Conversation not found using conversation id ";
    public ConversationNotFoundException(String message) {
        super(ERROR_MESSAGE + message);
    }
}
