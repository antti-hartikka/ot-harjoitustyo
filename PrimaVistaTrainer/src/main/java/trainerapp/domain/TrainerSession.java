package trainerapp.domain;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;

public class TrainerSession {

    private int[] playedNotes;
    int noteCount = 0;
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
        playedNotes[noteCount] = midiValue;
        noteCount++;
        if (noteCount == playedNotes.length) {
            endSession();
        }
    }

    public void endSession() {
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
        System.out.println("addSession: " + user);
        dataService.addSession(new Session(user, LocalDateTime.now(), average));
    }

    public void resetSession() {
        playedNotes = new int[score.getNotes().length];
        Arrays.fill(playedNotes, -1);
        noteCount = 0;
    }

}
