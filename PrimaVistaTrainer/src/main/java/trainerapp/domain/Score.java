package trainerapp.domain;

import java.util.Arrays;
import java.util.Random;

public class Score {

    private int noteBoundLow = 38;
    private int noteBoundHigh = 83;
    private int[] notes;
    private int[] scale;
    private int[] degrees;

    /**
     * This class is used for score related methods, such as generating new score.
     * Constructor sets scale to C major.
     */
    public Score() {
        scale = new int[]{0, 2, 4, 5, 7, 9, 11};
        degrees = new int[]{0, 0, 1, 1, 2, 3, 3, 4, 4, 5, 5, 6};
    }

    /**
     * Generates new score taking length as a parameter.
     * @param noteCount Defines how many quarter notes will be generated
     */
    public void generate(int noteCount) {
        Random r = new Random();
        notes = new int[noteCount];
        int octave = 0;
        while (notes[0] < noteBoundLow || notes[0] > noteBoundHigh) {
            octave = ((noteBoundLow / 12) + r.nextInt((noteBoundHigh - noteBoundLow) / 12)) * 12;
            notes[0] = octave + scale[0];
        }
        for (int i = 1; i < noteCount; i++) {
            int prevNote = notes[i - 1] % 12;
            int prevDeg = degrees[prevNote];
            int newOctave = 0;
            while (notes[i] < noteBoundLow || notes[i] > noteBoundHigh) {
                notes[i] = getNoteByDegreeChange(prevDeg, r.nextInt(13) - 6);
                newOctave = octave + ((r.nextInt(3) - 1) * 12);
                notes[i] += newOctave;
            }
            octave = newOctave;
        }
    }

    private int getNoteByDegreeChange(int prev, int change) {
        return scale[(prev + 12 + change) % 7];
    }

    public int[] getNotes() {
        return notes;
    }

    public int[] getScale() {
        return scale;
    }

    public int getNoteDegrees(int note) {
        return degrees[note % 12];
    }

    /**
     * Sets musical scale for new score generation
     * @param root Root note of the scale as a integer; 0 is C, 1 is C-sharp etc.
     * @param mode Mode as a string; "major" and "minor" is implemented. Uses major if incorrect value.
     */
    public void setScale(int root, String mode) {
        int[] cMajor = new int[]{0, 2, 4, 5, 7, 9, 11};
        int[] majorDegrees = new int[]{0, 0, 1, 1, 2, 3, 3, 4, 4, 5, 5, 6};

        int[] cMinor = new int[]{0, 2, 3, 5, 7, 8, 10};
        int[] minorDegrees = new int[]{0, 0, 1, 2, 2, 3, 3, 4, 5, 5, 6, 6};

        if (mode.equals("major")) {
            scale = Arrays.stream(cMajor)
                    .map(note -> (note + root) % 12)
                    .toArray();
            degrees = majorDegrees;
        }

        if (mode.equals("minor")) {
            scale = Arrays.stream(cMinor)
                    .map(note -> (note + root) % 12)
                    .toArray();
            degrees = minorDegrees;
        }

    }

}
