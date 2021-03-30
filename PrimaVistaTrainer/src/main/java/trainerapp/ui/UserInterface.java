package trainerapp.ui;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.scene.text.Font;
import trainerapp.domain.Score;

import java.io.File;
import java.util.Map;

public class UserInterface extends Application {

    @Override
    public void start(Stage primaryStage) {
        Font bravura = Font.loadFont("file:resources/fonts/BravuraText.otf", 100);
        Font edwin = Font.loadFont("file:resources/fonts/Edwin-Roman.otf", 36);

        BorderPane pane = new BorderPane();
        Canvas canvas = new Canvas(1000, 700);
        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.setFill(Color.BLACK);
        gc.setFont(bravura);

        BorderPane asettelu = new BorderPane();
        asettelu.setCenter(canvas);

        Score score = new Score();
        score.generate(12);
        ScoreDrawer drawer = new ScoreDrawer(score, gc, 30, 300);
        gc.strokeLine(30, 300, 300, 300);
        drawer.draw();

        Scene scene = new Scene(asettelu);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Prima vista trainer");
        primaryStage.show();

    }

    public static void main(String[] args) {
        launch(args);
    }
}
