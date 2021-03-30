package trainerapp.ui;

import javafx.scene.canvas.GraphicsContext;
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
    private int y;

    public ScoreDrawer(Score score, GraphicsContext gc, int x, int y) {
        this.score = score;
        this.gc = gc;
        this.x = x;
        this.y = y;
    }

    public void draw(){
        String a = staff+staff+staff+staff+staff+staff+staff+staff+staff+staff+staff+staff;
        String s = gStaff+a+a+a+a+a+a+a+a+a+a+a+a+a+a+a+a+a+a+a+a+a+a+a+a+a+a+a+a+a+a+a+a+a+"\n"+
                fStaff+a+a+a+a+a+a+a+a+a+a+a+a+a+a+a+a+a+a+a+a+a+a+a+a+a+a+a+a+a+a+a+a+a;
        gc.fillText(s, x, y);
        x += 100;
        y -= 10;

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
                x += 50;
            }
        }
    }

    private int getY(int note) {
        int oct = note / 12;
        int val = note % 12;
        int deg = score.getDegrees(val);
        int c3Deg = score.getDegrees(60);
        int octChange = oct - 6;
        int degChange = deg - c3Deg;
        return y - ((octChange * 70) + (degChange * 10));
    }

}
