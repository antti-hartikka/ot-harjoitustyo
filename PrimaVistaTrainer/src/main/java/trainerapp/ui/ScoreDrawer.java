package trainerapp.ui;

import javafx.scene.canvas.GraphicsContext;
import trainerapp.domain.Score;

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
    private GraphicsContext graphicsContext;
    private int x;
    private final int y;
    Note first;
    private final int space = 75;
    private final int noteStart = 100;
    private final int setX;

    public ScoreDrawer(GraphicsContext graphicsContext, int x, int y) {
        this.graphicsContext = graphicsContext;
        this.x = x;
        this.y = y;
        this.setX = x;
    }

    public void setScore(Score score) {
        this.score = score;
        score.generate(8);
        int[] notes = score.getNotes();
        first = new Note(notes[0], 4, noteStart, getY(notes[0]));
        Note previousNote = first;
        for (int i = 1; i < notes.length; i++) {
            previousNote.setNext(new Note(notes[i], 4, previousNote.getX() + space, getY(notes[i])));
            Note newNote = previousNote.next();
            newNote.setPrevious(previousNote);
            previousNote = previousNote.next();
        }
        this.x = setX;
    }

    public void draw() {
        String a = staff+staff+staff+staff+staff+staff+staff+staff+staff+staff+staff+staff;
        String s = gStaff+a+a+a+a+a+a+a+a+a+a+a+a+a+a+a+a+a+a+a+a+a+a+a+a+a+a+a+a+a+a+a+a+a+"\n"+
                fStaff+a+a+a+a+a+a+a+a+a+a+a+a+a+a+a+a+a+a+a+a+a+a+a+a+a+a+a+a+a+a+a+a+a;
        graphicsContext.fillText(s, x, y);
        x += 100;

        Note note = first;
        int count = 0;

        while(note != null) {
            int val = note.getVal();
            drawLeger(note);
            graphicsContext.fillText(note.toString(), note.getX(), note.getY());
            note = note.next();
        }
    }

    private void drawLeger(Note note) {
        int val = note.getVal();
        if (val == 40 || val == 60 || val == 81) {
            graphicsContext.fillText(leger, note.getX(), note.getY());
        } else if (val == 38) {
            graphicsContext.fillText(leger, note.getX(), note.getY() - 10);
        } else if (val == 83) {
            graphicsContext.fillText(leger, note.getX(), note.getY() + 10);
        }
    }

    private int getY(int note) {
        int oct = note / 12;
        int val = note % 12;
        int deg = score.getDegrees(val);
        int c3Deg = score.getDegrees(60);
        int octChange = oct - 6;
        int degChange = deg - c3Deg;
        return y - ((octChange * 70) + (degChange * 10)) - 10;
    }

}
