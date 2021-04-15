package trainerapp.domain;

import java.util.Arrays;
import java.util.Random;

public class Score {

    private int noteBoundLow = 38;
    private int noteBoundHigh = 83;
    private int[] notes;
    private int[] scale;
    private boolean[] isFlat;
    private boolean[] isNormalized;
    private boolean[] isSharp;
    private int[] degrees;
    private boolean[] flats = new boolean[7];
    private boolean[] sharps = new boolean[7];

    // sets scale to C major
    public Score() {
        scale = new int[]{0, 2, 4, 5, 7, 9, 11};
        degrees = new int[]{0, 0, 1, 1, 2, 3, 3, 4, 4, 5, 5, 6};
    }

    public Score(int[] scale, int[] degrees) {
        this.scale = scale;
        this.degrees = degrees;
    }

    public void setFlats(boolean[] flats) {
        this.flats = flats;
    }

    public void setSharps(boolean[] sharps) {
        this.sharps = sharps;
    }

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

    public int getDegrees(int note) {
        return degrees[note % 12];
    }

    public static void main(String[] args) {
        Score s = new Score();
        s.generate(32);
        System.out.println(Arrays.toString(s.notes));
    }

}
