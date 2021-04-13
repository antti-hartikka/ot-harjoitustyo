package trainerapp.ui;

import javafx.scene.canvas.GraphicsContext;
import trainerapp.domain.Note;
import trainerapp.domain.Score;

import java.util.Arrays;

public class ScoreDrawer {

    private final String gClef = "\uE050";
    private final String fClef = "\uE062";
    private final String barLine = "\uE030";
    private final String noteQuarterUp = "\uE1D5";
    private final String noteQuarterDown = "\uE1D6";
    private final String leger = "\uE022";
    private final String staff = "\uE014-";
    private final String wideStaff = "\uE01A";
    private final String narrowStaff = "\uE020";
    private final String finalBarline = "\uE032";
    private final String timeSigCommon = "\uE08A";
    private final String accidentalFlat = "\uE260";
    private final String accidntalNatural = "\uE261";
    private final String accindentalSharp = "\uE262";

    private String gStaff = wideStaff+barLine+gClef;
    private String fStaff = wideStaff+barLine+fClef;

    private Score score;
    private GraphicsContext gc;
    private int x;
    private final int y;
    Note first;
    private final int space = 75;
    private final int noteStart = 100;

    public ScoreDrawer(Score score, GraphicsContext gc, int x, int y) {
        this.score = score;
        int[] notes = score.getNotes();
        first = new Note(notes[0], 4, noteStart, getY(notes[0]));
        Note previousNote = first;
        for (int i = 1; i < notes.length; i++) {
            previousNote.setNext(new Note(notes[i], 4, previousNote.getX() + space, getY(notes[i])));
            Note newNote = previousNote.next();
            newNote.setPrevious(previousNote);
            previousNote = previousNote.next();
        }

        this.gc = gc;
        this.x = x;
        this.y = y;
    }

    public void draww(){
        String a = staff+staff+staff+staff+staff+staff+staff+staff+staff+staff+staff+staff;
        String s = gStaff+a+a+a+a+a+a+a+a+a+a+a+a+a+a+a+a+a+a+a+a+a+a+a+a+a+a+a+a+a+a+a+a+a+"\n"+
                   fStaff+a+a+a+a+a+a+a+a+a+a+a+a+a+a+a+a+a+a+a+a+a+a+a+a+a+a+a+a+a+a+a+a+a;
        gc.fillText(s, x, y);
        x += 100;

        int[] raw = score.getNotes();
        System.out.println(Arrays.toString(raw));
        int[][] notes = new int[(raw.length / 4) + 1][4];
        for (int i = 0; i < raw.length; i++) {
            notes[i / 4][i % 4] = raw[i];
        }
        for (int measure = 0; measure < notes.length; measure++) {
            for (int beat = 0; beat < 4; beat++) {
                int note = notes[measure][beat];
                if (note == 0) break;
                int noteY = getY(notes[measure][beat]);
                gc.fillText(noteQuarterUp, x, noteY);
                if (note == 40 || note == 60 || note == 81) {
                    gc.fillText(leger, x, noteY);
                }
                x += 75;
            }
            gc.fillText(barLine + "\n" + barLine, x, y);
            x += 75;
        }
    }

    public void draw() {
        String a = staff+staff+staff+staff+staff+staff+staff+staff+staff+staff+staff+staff;
        String s = gStaff+a+a+a+a+a+a+a+a+a+a+a+a+a+a+a+a+a+a+a+a+a+a+a+a+a+a+a+a+a+a+a+a+a+"\n"+
                fStaff+a+a+a+a+a+a+a+a+a+a+a+a+a+a+a+a+a+a+a+a+a+a+a+a+a+a+a+a+a+a+a+a+a;
        gc.fillText(s, x, y);
        x += 100;

        Note note = first;
        int count = 0;

        while(note != null) {
            int val = note.getVal();
            if (val == 40 || val == 60 || val == 81) {
                gc.fillText(leger, note.getX(), note.getY());
            }
            gc.fillText(note.toString(), note.getX(), note.getY());
            note = note.next();
        }

    }

    private int getY(int note) {
        int oct = note / 12;
        int val = note % 12;
        int deg = score.getDegrees(val);
        int c3Deg = score.getDegrees(60);
        int octChange = oct - 6;
        int degChange = deg - c3Deg;
        return y - ((octChange * 70) + (degChange * 10)) - 10 + 300;
    }

}
