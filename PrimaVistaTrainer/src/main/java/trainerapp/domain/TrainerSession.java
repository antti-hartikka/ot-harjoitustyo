package trainerapp.domain;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

public class TrainerSession {

    private int[] playedNotes;
    int i = 0;
    private Score score;
    private final DataAPI dataAPI = new DataAPI();
    private final User user;

    public TrainerSession(User user) {
        this.user = user;
    }

    public void noteInput(int midiValue) {
        playedNotes[i] = midiValue;
        i++;
        if (i == playedNotes.length) {
            endSession();
        }
    }

    public void endSession() {
        int[] notes = score.getNotes();
        int misses = 0;
        for (i = 0; i < notes.length; i++) {
            misses += notes[i] - playedNotes[i];
        }
        int average = misses / notes.length;
        dataAPI.addSession(new Session(user.name(), LocalDate.now(), average));
    }

    public void resetSession(Score score) {
        this.score = score;
        playedNotes = new int[score.getNotes().length];
    }

}
