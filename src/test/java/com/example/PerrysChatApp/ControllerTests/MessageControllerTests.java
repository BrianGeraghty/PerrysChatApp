package com.example.PerrysChatApp.ControllerTests;

import com.example.PerrysChatApp.Controllers.MessageController;
import com.example.PerrysChatApp.Models.Message;
import com.example.PerrysChatApp.Services.MessageService;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
public class MessageControllerTests {

    @InjectMocks
    private MessageController messageController;

    @Mock
    private MessageService messageService;

    @Before
    public void init() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCreateMessage() {
        Message testMessage = new Message();
        testMessage.setConversationId(1l);
        testMessage.setRecipientId(2l);
        testMessage.setSenderId(3l);
        testMessage.setMessageText("Test message");
        when(messageService.saveMessage(testMessage)).thenReturn(testMessage);
        ResponseEntity response = messageController.sendMessage(testMessage);
        Message returnedMessage = (Message) response.getBody();
        verify(messageService).saveMessage(testMessage);
        assertEquals(response.getStatusCode(), HttpStatus.CREATED);
        assertEquals(returnedMessage, testMessage);
    }

    @Test
    public void testGetMessage() {
        Message testMessage = new Message();
        testMessage.setId(1l);
        when(messageService.getMessageById(1l)).thenReturn(testMessage);
        ResponseEntity response = messageController.getMessage(1l);
        Message returnedMessage = (Message) response.getBody();
        verify(messageService).getMessageById(1l);
        assertEquals(returnedMessage, testMessage);
        assertEquals(returnedMessage.getId(), testMessage.getId());
    }

    @Test
    public void testGetAllMessages() {
        ArrayList<Message> allMessages = new ArrayList<>();
        allMessages.add(new Message());
        allMessages.add(new Message());

        when(messageService.getAllMessages()).thenReturn(allMessages);
        ArrayList<Message> returnedMessages = (ArrayList) messageController.getAllMessages();
        verify(messageService).getAllMessages();
        assertEquals(returnedMessages.size(), allMessages.size());
    }

    @Test
    public void testEditMessage() {
        Message message = new Message();
        message.setId(1l);
        message.setMessageText("Some text");

        when(messageService.editMessageText(message)).thenReturn(message);
        ResponseEntity response = messageController.editMessage(message);
        Message returnedMessage = (Message) response.getBody();
        verify(messageService).editMessageText(message);
        assertEquals(message, returnedMessage);
    }

    @Test
    public void testDeleteMessages() {
        when(messageService.deleteMessage(2l)).thenReturn(ResponseEntity.ok("Message Deleted"));
        ResponseEntity responseEntity = messageController.deleteMessage(2l);
        verify(messageService).deleteMessage(2l);
        assertEquals(responseEntity.getStatusCode(), HttpStatus.OK);
    }
}
