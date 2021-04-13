package trainerapp.domain;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
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

    private Note[] noteObjects;

    // sets scale to C major
    public Score() {
        scale = new int[]{0,2,4,5,7,9,11};
        degrees = new int[]{0,0,1,1,2,3,3,4,4,5,5,6};
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
        int octave;
        while(notes[0] < noteBoundLow || notes[0] > noteBoundHigh) {
            octave = ((noteBoundLow / 12) + r.nextInt((noteBoundHigh - noteBoundLow) / 12)) * 12;
            notes[0] = octave + scale[0];
        }
        for (int i = 1; i < noteCount; i++) {
            while (notes[i] < noteBoundLow || notes[i] > noteBoundHigh) {
                float a = r.nextFloat();
                int prevNote = notes[i - 1] % 12;
                int prevDeg = degrees[prevNote];
                if (a < 0.2) {
                    notes[i] = prevNote;
                } else if (a < 0.3) {
                    notes[i] = getNoteByDegreeChange(prevDeg, -1);
                } else if (a < 0.4) {
                    notes[i] = getNoteByDegreeChange(prevDeg, 1);
                } else if (a < 0.45) {
                    notes[i] = getNoteByDegreeChange(prevDeg, -2);
                } else if (a < 0.5) {
                    notes[i] = getNoteByDegreeChange(prevDeg, 2);
                } else if (a < 0.55) {
                    notes[i] = getNoteByDegreeChange(prevDeg, -3);
                } else if (a < 0.6) {
                    notes[i] = getNoteByDegreeChange(prevDeg, 3);
                } else if (a < 0.7) {
                    notes[i] = getNoteByDegreeChange(prevDeg, -4);
                } else if (a < 0.8) {
                    notes[i] = getNoteByDegreeChange(prevDeg, 4);
                } else if (a < 0.85) {
                    notes[i] = getNoteByDegreeChange(prevDeg, -5);
                } else if (a < 0.9) {
                    notes[i] = getNoteByDegreeChange(prevDeg, 5);
                } else if (a < 0.95){
                    notes[i] = getNoteByDegreeChange(prevDeg, -6);
                } else {
                    notes[i] = getNoteByDegreeChange(prevDeg, 6);
                }
                int newOctave = (2 + r.nextInt(4)) * 12;
                notes[i] += newOctave;
                if (notes[i] < noteBoundLow || notes[i] > noteBoundHigh) {
                    continue;
                }
                octave = newOctave;
            }
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
