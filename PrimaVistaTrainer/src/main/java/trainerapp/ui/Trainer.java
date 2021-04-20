package trainerapp.ui;

import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import trainerapp.domain.Score;
import trainerapp.domain.TrainerSession;

public class Trainer {

    GraphicsContext graphicsContext;
    Canvas canvas = new Canvas(5000, 700);
    Pane pane = new Pane();
    ScrollPane scrollPane = new ScrollPane();
    Score score = new Score();
    ScoreDrawer scoreDrawer = new ScoreDrawer(graphicsContext, 30, 300);
    Rectangle highlight = new Rectangle(0, 150, 90, 350);
    TrainerSession trainerSession;
    Scene scene = new Scene(scrollPane);
    private final Font bravura = Font.loadFont("file:resources/fonts/BravuraText.otf", 100);

    public Trainer(TrainerSession trainerSession) {
        this.trainerSession = trainerSession;

        graphicsContext = canvas.getGraphicsContext2D();
        graphicsContext.setFill(Color.BLACK);
        graphicsContext.setFont(bravura);

        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.setContent(pane);

        scoreDrawer.setScore(score);
    }

    public Scene getScene() {
        return scene;
    }

    public Scene startTraining() {
        graphicsContext.clearRect(0,0, canvas.getWidth(), canvas.getHeight());

        pane.getChildren().clear();
        pane.getChildren().add(canvas);

        score.generate(64);
        trainerSession.resetSession();
        scoreDrawer.draw();

        highlight.setFill(Color.rgb(50,150,250,0.2));
        highlight.setTranslateX(75);

        pane.getChildren().add(highlight);
        return scene;
    }

    public void handleKeyEvent(KeyEvent keyEvent) {
        highlight.setTranslateX(highlight.getTranslateX() + 75);
        int v = -1;
        switch (keyEvent.getCode()) {
            case A: v = 0; break;
            case W: v = 1; break;
            case S: v = 2; break;
            case E: v = 3; break;
            case D: v = 4; break;
            case F: v = 5; break;
            case T: v = 6; break;
            case G: v = 7; break;
            case Y: v = 8; break;
            case H: v = 9; break;
            case U: v = 10; break;
            case J: v = 11; break;
            default: break;
        }
        trainerSession.noteInput(v);
    }

}
