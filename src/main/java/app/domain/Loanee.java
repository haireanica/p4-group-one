package app.domain;

import jakarta.persistence.*;

@Entity
@Table(name = "loanee")
public class Loanee {

    @Id
    private Integer participantId;

    protected Loanee() {}



    public Integer getParticipantId() {
        return participantId;
    }



    @Override
    public String toString() {
        return "Loanee{" +
                "participantId='" + participantId + '\'' +
                '}';
    }
}
