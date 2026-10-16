package com.example.assettracker;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class Loanee {

    @Id
    @Column(name = "participantId")
    private Long participantId;

    public Loanee() {}

    public Loanee(Long participantId) {
        this.participantId = participantId;
    }

    public Long getParticipantId() { return participantId; }
}
