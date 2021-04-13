package trainerapp.ui;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.WritableImage;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;
import javafx.scene.text.Font;
import trainerapp.domain.Score;
import javafx.scene.control.ScrollPane;
import javafx.scene.shape.*;

public class UserInterface extends Application {

    private int highlightX;

    @Override
    public void start(Stage primaryStage) {
        Font bravura = Font.loadFont("file:resources/fonts/BravuraText.otf", 100);
        Font edwin = Font.loadFont("file:resources/fonts/Edwin-Roman.otf", 36);

        Canvas canvas = new Canvas(5000, 700);
        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.setFill(Color.BLACK);
        gc.setFont(bravura);

        ScrollPane pane = new ScrollPane();
        pane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        pane.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);

        Pane bp = new Pane();
        bp.getChildren().add(canvas);

        pane.setContent(bp);

        Score score = new Score();
        score.generate(64);
        ScoreDrawer drawer = new ScoreDrawer(score, gc, 30, 300);
        drawer.draw();

        highlightX = 75;
        Rectangle highlight = new Rectangle(highlightX, 150, 90, 350);
        highlight.setFill(Color.rgb(50,150,250,0.2));
        bp.getChildren().add(highlight);

        gc.setFill(Color.rgb(50,150,250,0.2));

        Scene scene = new Scene(pane);

        primaryStage.setWidth(1000);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Prima vista trainer");
        primaryStage.show();

        scene.setOnKeyPressed(keyEvent -> {
            if (keyEvent.getCode() == KeyCode.A) {
                highlightX += 75;
                highlight.setTranslateX(highlightX);
            }
        });
    }

    public static void main(String[] args) {
        launch(args);
    }
}
