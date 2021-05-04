package trainerapp.domain;

import java.time.LocalDateTime;
import java.util.Arrays;

public class TrainerSession {

    private int[] playedNotes;
    private int noteCount = 0;
    private Score score;
    private final DataService dataService;
    private final String user;
    private boolean isEnded = false;

    public TrainerSession(String user, DataService dataService) {
        this.user = user;
        this.dataService = dataService;
    }

    public void setScore(Score score) {
        this.score = score;
    }

    public void noteInput(int midiValue) {
        if (isEnded) {
            return;
        }
        playedNotes[noteCount] = midiValue;
        noteCount++;
        if (noteCount == playedNotes.length) {
            endSession();
        }
    }

    public boolean isEnded() {
        return isEnded;
    }

    public void endSession() {
        isEnded = true;
        int[] notes = score.getNotes();
        double misses = 0;
        int count = 0;
        for (int i = 0; i < notes.length; i++) {
            if (playedNotes[i] == -1) {  // -1 means that training was not completed to end
                break;
            } else if (playedNotes[i] < 12) {  // this means the input type was keyboard
                misses += Math.abs((notes[i] % 12) - playedNotes[i]);
                count++;
            } else {  // input type is midi device
                misses += Math.abs(notes[i] - playedNotes[i]);
                count++;
            }
        }
        double average = misses / count;
        dataService.addSession(new Session(user, LocalDateTime.now(), average));
    }

    public void resetSession() {
        isEnded = false;
        playedNotes = new int[score.getNotes().length];
        Arrays.fill(playedNotes, -1);
        noteCount = 0;
    }

}
