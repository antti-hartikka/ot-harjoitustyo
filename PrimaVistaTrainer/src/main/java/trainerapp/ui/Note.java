package trainerapp.ui;

public class Note {

    private int length; // 1/nth note
    private Note next;
    private Note previous;
    private int midiValue;

    // coordinates on canvas
    private int x;
    private int y;

    private boolean isFlat = false;
    private boolean isSharp = false;
    private boolean isBarline = false;

    private final String noteQuarterUp = "\uE1D5";
    private final String noteQuarterDown = "\uE1D6";
    private final String leger = "\uE022";
    private final String barLine = "\uE030";

    public Note(int midiValue, int length, int x, int y) {
        this.midiValue = midiValue;
        this.length = length;
        this.x = x;
        this.y = y;
    }

    public Note(Note previous) {
        this.previous = previous;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setNext(Note next) {
        this.next = next;
    }

    public Note next() {
        return next;
    }

    public boolean hasNext() {
        return next != null;
    }

    public void setSharp(boolean sharp) {
        isSharp = sharp;
    }

    public void setFlat(boolean flat) {
        isFlat = flat;
    }

    public void setBarline() {
        isBarline = true;
    }

    public void setPrevious(Note previous) {
        this.previous = previous;
    }

    public int getVal() {
        return midiValue;
    }

    public String toString() {
        return noteQuarterUp;
    }
}
