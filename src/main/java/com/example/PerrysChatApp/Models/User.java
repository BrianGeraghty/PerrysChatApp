package com.example.PerrysChatApp.Models;

import com.sun.istack.NotNull;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private String userName;

    @OneToMany(cascade = CascadeType.ALL, targetEntity = Conversation.class)
    @JoinColumn(name = "participantOne")
    private List<Conversation> initiatedConversationList;

    @OneToMany(cascade = CascadeType.ALL, targetEntity = Conversation.class)
    @JoinColumn(name = "participantTwo")
    private List<Conversation> secondaryConversationList;

    @OneToMany(cascade = CascadeType.ALL, targetEntity = Message.class)
    @JoinColumn(name = "sender")
    private List<Message> messagesSentList;

    @OneToMany(cascade = CascadeType.ALL, targetEntity = LikedMessage.class)
    @JoinColumn(name = "userId")
    private List<LikedMessage> messagesLikedList;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
