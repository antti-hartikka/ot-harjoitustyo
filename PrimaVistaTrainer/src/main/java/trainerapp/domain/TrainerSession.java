package trainerapp.domain;

import java.time.LocalDate;
import java.util.Arrays;

public class TrainerSession {

    private int[] playedNotes;
    int i = 0;
    private Score score;
    private final DataService dataService;
    private final String user;

    public TrainerSession(String user, DataService dataService) {
        this.user = user;
        this.dataService = dataService;
    }

    public void setScore(Score score) {
        this.score = score;
    }

    public void noteInput(int midiValue) {
        playedNotes[i] = midiValue;
        i++;
        if (i == playedNotes.length) {
            endSession();
        }
    }

    public void endSession() {
        System.out.println(Arrays.toString(playedNotes));
        int[] notes = score.getNotes();
        int misses = 0;
        for (i = 0; i < notes.length; i++) {
            misses += notes[i] - playedNotes[i];
        }
        int average = misses / notes.length;
        dataService.addSession(new Session(user, LocalDate.now(), average));
    }

    public void resetSession() {
        playedNotes = new int[score.getNotes().length];
    }

}
