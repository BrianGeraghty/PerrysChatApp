package com.example.PerrysChatApp.Controllers;

import com.example.PerrysChatApp.Models.LikedMessage;
import com.example.PerrysChatApp.Services.LikedMessageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping(path="/api/v1/likes")
public class LikedMessageController {

    private static final Logger logger = LoggerFactory.getLogger(LikedMessageController.class);

    @Autowired
    private LikedMessageService likedMessageService;

    @PostMapping(path="")
    public @ResponseBody
    ResponseEntity likeMessage(@RequestBody LikedMessage likedMessage) {
        logger.debug("Entered LikedMessageController.likeMessage()");
        LikedMessage savedLike = likedMessageService.saveLike(likedMessage);

        if (savedLike == null) {
            logger.warn("Message " + likedMessage.getMessageId() + " was already liked by user " + likedMessage.getUserId());
        }

        return ResponseEntity.status(201).body(savedLike);
    }


    @GetMapping(path="")
    public @ResponseBody
    ResponseEntity getLikedMessages() {
        logger.debug("Entered LikedMessageController.likeMessage()");
        return ResponseEntity.ok().body(likedMessageService.getAllLikedMessages());
    }

}
