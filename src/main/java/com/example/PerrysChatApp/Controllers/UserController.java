package com.example.PerrysChatApp.Controllers;

import com.example.PerrysChatApp.Exceptions.DuplicateUserException;
import com.example.PerrysChatApp.Exceptions.UserNotFoundException;
import com.example.PerrysChatApp.Models.User;
import com.example.PerrysChatApp.Services.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@Controller
@RequestMapping(path="/api/v1/users")
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserService userService;

    @PostMapping(path="")
    public @ResponseBody ResponseEntity createUser(@RequestBody User user) {
        logger.debug("Entered UserController.createUser()");
        try {
            return ResponseEntity.status(201).body(userService.saveUser(user));
        } catch (DataIntegrityViolationException e) {
            logger.info("Exception thrown!!!");
            throw new DuplicateUserException("User Name " + user.getUserName() + " is already in use.");
        }
    }

    @GetMapping(path="")
    public @ResponseBody Iterable<User> getAllUsers() {
        logger.debug("Entered UserController.getAllUsers()");
        // This returns a JSON or XML with the users
        return userService.getAllUsers();
    }

    @GetMapping(path="/{userId}")
    public @ResponseBody
    User getUser(@PathVariable Long userId) {
        logger.debug("Entered UserController.getUser()");
        User user = userService.getUserById(userId);
        if (user == null) throw new UserNotFoundException(String.valueOf(userId));
        return user;
    }

    @GetMapping(path="/{userId}/contacts")
    public @ResponseBody
    Set<User> getAllUsersContacts(@PathVariable Long userId) {
        logger.debug("Entered UserController.getAllUsersContacts()");
        return userService.getAllContactsForUser(userId);
    }
}
