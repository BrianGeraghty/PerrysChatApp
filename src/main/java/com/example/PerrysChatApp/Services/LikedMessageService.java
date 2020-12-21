package com.example.PerrysChatApp.Services;

import com.example.PerrysChatApp.Models.LikedMessage;
import com.example.PerrysChatApp.Repositories.LikedMessageRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LikedMessageService {

    private static final Logger logger = LoggerFactory.getLogger(LikedMessageService.class);

    @Autowired
    private LikedMessageRepository likedMessageRepository;

    public LikedMessage saveLike(LikedMessage likedMessage) {
        logger.debug("Entering LikedMessageService.saveLike()");

        LikedMessage savedLike = null;
        // check if like already exists
        if (likedMessageRepository.likeAlreadyExists(likedMessage.getMessageId(), likedMessage.getUserId()) == 0) {
            logger.debug("Creating like)");
            savedLike = likedMessageRepository.save(likedMessage);
        }
        return savedLike;
    }


    public Iterable<LikedMessage> getAllLikedMessages() {
        logger.debug("Entering LikedMessageService.getAllLikedMessages()");
        return likedMessageRepository.findAll();
    }
}
