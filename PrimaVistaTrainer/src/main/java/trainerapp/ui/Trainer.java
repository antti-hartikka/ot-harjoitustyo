package trainerapp.ui;

import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import trainerapp.domain.Score;
import trainerapp.domain.TrainerSession;

public class Trainer {

    private final Font edwin = Font.loadFont("file:resources/fonts/Edwin-Roman.otf", 20);
    private final Font bravura = Font.loadFont("file:resources/fonts/BravuraText.otf", 100);
    GraphicsContext graphicsContext;
    Canvas canvas;
    Pane pane = new Pane();
    ScrollPane scrollPane = new ScrollPane();
    Score score = new Score();
    ScoreDrawer scoreDrawer;
    Rectangle highlight = new Rectangle(100, 150, 90, 350);
    TrainerSession trainerSession;
    Scene scene = new Scene(scrollPane);
    private final MidiService midiService = new MidiService();
    private int noteCount = 32;

    public Trainer() {

        canvas = new Canvas(5000, 700);
        graphicsContext = canvas.getGraphicsContext2D();
        graphicsContext.setFill(Color.BLACK);
        graphicsContext.setFont(bravura);

        scoreDrawer = new ScoreDrawer(graphicsContext, 30, 300);
        scoreDrawer.setScore(score);

        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.setContent(pane);
    }

    public Scene getScene() {
        return scene;
    }

    public Scene startTraining() {
        trainerSession.setScore(score);

        graphicsContext.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());

        pane.getChildren().clear();
        pane.getChildren().add(canvas);

        score.generate(noteCount);
        trainerSession.resetSession();
        scoreDrawer.draw();

        highlight.setFill(Color.rgb(50, 150, 250, 0.2));
        highlight.setTranslateX(75);

        pane.getChildren().add(highlight);

        midiService.openMidiDevice(this);

        return scene;
    }

    public void handleKeyEvent(KeyEvent keyEvent) {
        int v = -1;
        switch (keyEvent.getCode()) {
            case A:
                v = 0;
                break;
            case W:
                v = 1;
                break;
            case S:
                v = 2;
                break;
            case E:
                v = 3;
                break;
            case D:
                v = 4;
                break;
            case F:
                v = 5;
                break;
            case T:
                v = 6;
                break;
            case G:
                v = 7;
                break;
            case Y:
                v = 8;
                break;
            case H:
                v = 9;
                break;
            case U:
                v = 10;
                break;
            case J:
                v = 11;
                break;
            default:
                break;
        }
        handleNote(v);
    }

    public void moveHighlight() {
        highlight.setTranslateX(highlight.getTranslateX() + 75);
    }

    public void setNoteCount(int noteCount) {
        this.noteCount = noteCount;
    }

    public void endTraining() {
        midiService.closeMidiDevice();
        trainerSession.endSession();
    }

    public void handleMidiNote(byte midiValue) {
        moveHighlight();
        handleNote(midiValue);
    }

    private void handleNote(int noteValue) {
        double d = 1 / (canvas.getWidth() / 75);
        if (scrollPane.getHvalue() + d < scrollPane.getHmax()) {
            scrollPane.setHvalue(scrollPane.getHvalue() + d);
        }
        moveHighlight();
        trainerSession.noteInput(noteValue);
        if (trainerSession.isEnded()) {
            midiService.closeMidiDevice();
            graphicsContext.clearRect(0, 0, 5000, 600);
            highlight.setTranslateX(2000);
            scrollPane.setHvalue(0);
            graphicsContext.setFont(edwin);
            graphicsContext.fillText("press escape to continue", 300, 300);
            graphicsContext.setFont(bravura);
            return;
        }
    }

    public void setTrainerSession(TrainerSession trainerSession) {
        this.trainerSession = trainerSession;
    }

    private Text text(String s) {
        Text text = new Text(s);
        text.setFont(edwin);
        return text;
    }
}
