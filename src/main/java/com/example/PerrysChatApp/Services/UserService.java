package com.example.PerrysChatApp.Services;

import com.example.PerrysChatApp.Controllers.UserController;
import com.example.PerrysChatApp.Exceptions.DuplicateUserException;
import com.example.PerrysChatApp.Models.Conversation;
import com.example.PerrysChatApp.Repositories.ConversationRepository;
import com.example.PerrysChatApp.Models.User;
import com.example.PerrysChatApp.Repositories.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class UserService {

    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ConversationRepository conversationRepository;

    @Autowired
    private ConversationService conversationService;

    public User saveUser(User user) {
        logger.debug("Entered UserService.saveUser()");
        return userRepository.save(user);
    }

    public Iterable<User> getAllUsers() {
        logger.debug("Entered UserService.getAllUsers()");
        return userRepository.findAll();
    }

    public User getUserById(@PathVariable Long userId) {
        logger.debug("Entered UserService.getUserById()");
        Optional<User> user = userRepository.findById(userId);
        return user.isPresent() ? user.get() : null;
    }

    public Set<User> getAllContactsForUser(Long userId) {
        logger.debug("Entered UserService.getAllContactsForUserAsUsers()");

        Iterable<Conversation> usersConversations =
                conversationService.getAllConversationsForUser(userId);
        Set<Long> userIds = new HashSet<>();
        usersConversations.forEach(c ->
            {
                if (c.getParticipantOneId() != userId) {
                    userIds.add(c.getParticipantOneId());
                } else {
                    userIds.add(c.getParticipantTwoId());
                }
            }
        );

        return StreamSupport.stream(
                userRepository.findAllById(userIds).spliterator(), true).collect(Collectors.toSet());
    }
}
