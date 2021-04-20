package trainerapp.domain;

import java.time.LocalDate;

public class TrainerSession {

    private int[] playedNotes;
    int i = 0;
    private Score score;
    private final DataService dataService = new DataService();
    private final String user;

    public TrainerSession(String user) {
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
        dataService.addSession(new Session(user, LocalDate.now(), average));
    }

    public void resetSession(Score score) {
        this.score = score;
        playedNotes = new int[score.getNotes().length];
    }

}
