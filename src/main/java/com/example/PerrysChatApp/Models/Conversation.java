package com.example.PerrysChatApp.Models;

import com.sun.istack.NotNull;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "conversations")
public class Conversation {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private Long participantOneId;

    @NotNull
    private Long participantTwoId;

    @OneToMany(cascade = CascadeType.ALL, targetEntity = Message.class)
    @JoinColumn(name = "conversationId")
    private List<Message> conversationList;

    public Conversation() {}

    public Conversation(Long participantOneId, Long participantTwoId) {
        this.participantOneId = participantOneId;
        this.participantTwoId = participantTwoId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getParticipantOneId() {
        return participantOneId;
    }

    public void setParticipantOneId(Long participantOneId) {
        this.participantOneId = participantOneId;
    }

    public Long getParticipantTwoId() {
        return participantTwoId;
    }

    public void setParticipantTwoId(Long participantTwoId) {
        this.participantTwoId = participantTwoId;
    }
}
