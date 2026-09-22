package app.domain;

import jakarta.persistence.*;

@Entity
@Table(name = "loanee")
public class Loanee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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
