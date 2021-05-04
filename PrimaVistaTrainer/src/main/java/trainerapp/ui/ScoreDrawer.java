package trainerapp.ui;

import javafx.scene.canvas.GraphicsContext;
import trainerapp.domain.Score;

import java.util.Arrays;

public class ScoreDrawer {

    private Score score;
    private final GraphicsContext graphicsContext;
    private int x;
    private final int y;
    Note first;
    private final int space = 75;
    private final int noteStart = 200;
    private final int initX;

    public ScoreDrawer(GraphicsContext graphicsContext, int x, int y) {
        this.graphicsContext = graphicsContext;
        this.x = x;
        this.y = y;
        this.initX = x;
    }

    public void setScore(Score score) {
        this.score = score;
    }

    public void draw() {
        int[] notes = score.getNotes();
        first = new Note(notes[0], noteStart, getY(notes[0]));
        Note previousNote = first;
        for (int i = 1; i < notes.length; i++) {
            previousNote.setNext(new Note(notes[i], previousNote.getX() + space, getY(notes[i])));
            previousNote = previousNote.next();
        }

        drawScaleMarks();

        x = initX;

        String staff = "\uE014-\uE014-\uE014-\uE014-";
        String gClef = "\uE050";
        String barLine = "\uE030";
        String wideStaff = "\uE01A";
        String gStaff = wideStaff + barLine + gClef;
        String fClef = "\uE062";
        String fStaff = wideStaff + barLine + fClef;

        graphicsContext.fillText(
                gStaff + staff.repeat(notes.length + 1) + "-" + barLine + "\n" +
                        fStaff + staff.repeat(notes.length + 1) + "-" + barLine,
                x, y);
        x += 100;

        Note note = first;
        while (note.hasNext()) {
            drawLeger(note);
            graphicsContext.fillText(note.toString(), note.getX(), note.getY());
            note = note.next();
        }
    }

    private void drawLeger(Note note) {
        int val = note.getVal();
        String leger = "\uE022";
        if (val == 40 || val == 60 || val == 81) {
            graphicsContext.fillText(leger, note.getX(), note.getY());
        } else if (val == 38) {
            graphicsContext.fillText(leger, note.getX(), note.getY() - 10);
        } else if (val == 83) {
            graphicsContext.fillText(leger, note.getX(), note.getY() + 10);
        }
    }

    private void drawScaleMarks() {
        String flat = "\uE260";
        String sharp = "\uE262";

        int[] scale = score.getScale();
        int[] sharps = new int[]{6, 1, 8, 3, 10, 5, 0};
        int[] flats = new int[]{10, 3, 8, 1, 6, 11, 4};
        int[] markings;
        String marking;
        int minF;
        int minG;

        if (arrayContains(scale, sharps[0])) {
            markings = sharps;
            marking = sharp;
            minF = 46;
            minG = 70;
        } else if (arrayContains(scale, flats[0])) {
            markings = flats;
            marking = flat;
            minF = 40;
            minG = 64;
        } else {
            return;
        }

        x = initX + 60;

        for (int i = 0; i < 7; i++) {
            if (arrayContains(scale, markings[i])) {
                int midiVal = markings[i];
                while(midiVal < minF) {
                    midiVal += 12;
                }
                graphicsContext.fillText(marking, x, getY(midiVal));
                while(midiVal < minG) {
                    midiVal += 12;
                }
                graphicsContext.fillText(marking, x, getY(midiVal));
                x += 17;
            } else {
                return;
            }
        }
    }

    private int getY(int note) {
        int oct = note / 12;
        int val = note % 12;
        int deg = score.getNoteDegrees(val);
        int c3Deg = score.getNoteDegrees(60);
        int octChange = oct - 6;
        int degChange = deg - c3Deg;
        return y - ((octChange * 70) + (degChange * 10)) - 10;
    }

    private boolean arrayContains(int[] array, int value) {
        for (int i : array) {
            if (i == value) {
                return true;
            }
        }
        return false;
    }

}
