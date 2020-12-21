package com.example.PerrysChatApp.ControllerTests;

import com.example.PerrysChatApp.Controllers.UserController;
import com.example.PerrysChatApp.Models.User;
import com.example.PerrysChatApp.Services.UserService;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

@SpringBootTest
public class UserControllerTests {

    @InjectMocks
    private UserController userController;

    @Mock
    private UserService userService;

    @Before
    public void init() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCreateUser() {
        User testUser = new User();
        testUser.setUserName("test");
        when(userService.saveUser(testUser)).thenReturn(testUser);
        ResponseEntity response = userController.createUser(testUser);
        User returnedUser = (User) response.getBody();
        verify(userService).saveUser(testUser);
        assertEquals(response.getStatusCode(), HttpStatus.CREATED);
        assertEquals(returnedUser.getUserName(), testUser.getUserName());
    }

    @Test
    public void testGetUser() {
        User testUser = new User();
        testUser.setUserName("test");
        testUser.setId(1l);
        when(userService.getUserById(1l)).thenReturn(testUser);
        User returnedUser = userController.getUser(1l);
        verify(userService).getUserById(1l);
        assertEquals(returnedUser.getUserName(), testUser.getUserName());
        assertEquals(returnedUser.getId(), testUser.getId());
    }

    @Test
    public void testGetAllUsers() {
        User testUser1 = new User();
        testUser1.setUserName("test1");
        testUser1.setId(1l);
        ArrayList<User> allUsers = new ArrayList<>();
        allUsers.add(new User());
        allUsers.add(new User());

        when(userService.getAllUsers()).thenReturn(allUsers);
        ArrayList<User> returnedUsers = (ArrayList) userController.getAllUsers();
        verify(userService).getAllUsers();
        assertEquals(returnedUsers.size(), allUsers.size());
    }

    @Test
    public void testGetUsersContacts() {
        User testUser = new User();
        testUser.setUserName("test");

        User contactOne = new User();
        contactOne.setId(2l);
        contactOne.setUserName("user2");

        User contactTwo = new User();
        contactOne.setId(3l);
        contactOne.setUserName("user3");

        Set<User> userContacts = new HashSet<>();
        userContacts.add(contactOne);
        userContacts.add(contactTwo);

        when(userService.getAllContactsForUser(1l)).thenReturn(userContacts);
        Set<User> userSet = userController.getAllUsersContacts(1l);
        verify(userService).getAllContactsForUser(1l);
        assertEquals(userSet, userContacts);
    }

}
