package com.example.PerrysChatApp.Exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value= HttpStatus.CONFLICT)
public class DuplicateConversationException extends RuntimeException {

    private static final String ERROR_MESSAGE = "Unable to create conversation. ";
    public DuplicateConversationException(String message) {
        super(ERROR_MESSAGE + message);
    }
}
