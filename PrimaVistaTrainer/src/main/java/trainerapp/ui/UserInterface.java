package trainerapp.ui;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class UserInterface extends Application {

    @Override
    public void start(Stage primaryStage) {
        Font leland = Font.loadFont("file:resources/fonts/Leland.otf", 72);
        Font edwin = Font.loadFont("file:resources/fonts/Edwin-Roman.otf", 36);

        BorderPane pane = new BorderPane();
        Text hello = new Text("Hello World!");
        hello.setFont(edwin);
        pane.setCenter(hello);
        Scene scene = new Scene(pane, 1000, 600);
        primaryStage.setScene(scene);

        primaryStage.setTitle("Prima vista trainer");
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
