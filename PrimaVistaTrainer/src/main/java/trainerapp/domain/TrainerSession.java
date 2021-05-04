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

    /**
     * This class is used to manage training session data. Takes input values and stores session when ended.
     * @param user Username of current user
     * @param dataService Data service object for data storing
     */
    public TrainerSession(String user, DataService dataService) {
        this.user = user;
        this.dataService = dataService;
    }

    public void setScore(Score score) {
        this.score = score;
    }

    /**
     * Takes input value and checks if score is in the end.
     * @param midiValue Input value as a midi standard.
     *                  Values 0...11 can be used to represent plain note values without octave.
     */
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

    /**
     * This can be used to check if session is ended
     * @return Returns true if session is ended.
     */
    public boolean isEnded() {
        return isEnded;
    }

    /**
     * Sets isEnded to true, calculates average miss and stores session object to data service.
     */
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

    /**
     * Resets all variables for new session. Call this after new score is generated.
     */
    public void resetSession() {
        isEnded = false;
        playedNotes = new int[score.getNotes().length];
        Arrays.fill(playedNotes, -1);
        noteCount = 0;
    }

}
