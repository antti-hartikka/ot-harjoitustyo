package trainerapp.ui;

public class Note {

    private Note next;
    private final int midiValue;

    // coordinates on canvas
    private final int x;
    private final int y;

    public Note(int midiValue, int x, int y) {
        this.midiValue = midiValue;
        this.x = x;
        this.y = y;
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

    public int getVal() {
        return midiValue;
    }

    public String toString() {
        if (midiValue > 70) {
            String noteQuarterDown = "\uE1D6";
            return noteQuarterDown;
        } else {
            String noteQuarterUp = "\uE1D5";
            return noteQuarterUp;
        }
    }
}
