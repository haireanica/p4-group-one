package App.Domain;

public class Loanee {

    private int participantId;

    public Loanee(int participantId) {
        this.participantId = participantId;
    }

    public int getParticipantId() {
        return participantId;
    }

    public void setParticipantId(String participantId) {
        this.participantId = Integer.parseInt(participantId);

    }

    @Override
    public String toString() {
        return "Loanee{" +
                "participantId='" + participantId + '\'' +
                '}';
    }
}
