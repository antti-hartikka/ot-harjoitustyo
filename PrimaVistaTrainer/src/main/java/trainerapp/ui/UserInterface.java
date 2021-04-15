package trainerapp.ui;

import com.sun.javafx.scene.control.InputField;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.LongProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.image.WritableImage;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.scene.text.Font;
import trainerapp.domain.*;
import javafx.scene.shape.*;

import java.util.concurrent.atomic.LongAccumulator;

public class UserInterface extends Application {

    private final Font bravura = Font.loadFont("file:resources/fonts/BravuraText.otf", 100);
    private final Font edwin = Font.loadFont("file:resources/fonts/Edwin-Roman.otf", 20);
    private final DataAPI dataAPI = new DataAPI();
    private final User user = new User("");
    private final Statistics statistics = new Statistics();

    @Override
    public void start(Stage primaryStage) {

        //   -----  first scene for user selection / creation

        Text selectUserText = new Text("Select user from the list or create new user");
        selectUserText.setFont(edwin);

        ChoiceBox<String> selectUserList = new ChoiceBox<>();
        selectUserList.getItems().addAll(dataAPI.getAllUsers());

        Button selectUserButton = new Button("select");
        selectUserButton.setFont(edwin);

        TextField createUserInput = new TextField("username");
        createUserInput.setFont(edwin);

        Text createUserErrorMessage = new Text();
        createUserErrorMessage.setFont(edwin);
        createUserErrorMessage.setFill(Color.RED);

        Button createUserButton = new Button("create new user");
        createUserButton.setFont(edwin);

        VBox userSelection = new VBox();
        userSelection.getChildren().add(selectUserText);
        userSelection.getChildren().add(selectUserList);
        userSelection.getChildren().add(selectUserButton);
        userSelection.getChildren().add(createUserInput);
        userSelection.getChildren().add(createUserButton);
        userSelection.getChildren().add(createUserErrorMessage);

        BorderPane borderPane = new BorderPane();
        borderPane.setPrefSize(1000, 600);
        borderPane.setPadding(new Insets(100,100,100,100));
        borderPane.setCenter(userSelection);

        Scene mainScene = new Scene(borderPane);


        //   ----- main menu  --------

        Text startTraining = new Text("start training");
        startTraining.setFont(edwin);

        Text statisticsText = new Text("statistics");
        statisticsText.setFont(edwin);

        Text settings = new Text("settings");
        settings.setFont(edwin);

        Text exit = new Text("exit");
        exit.setFont(edwin);

        VBox mainMenu = new VBox();
        mainMenu.getChildren().add(startTraining);
        mainMenu.getChildren().add(statisticsText);
        mainMenu.getChildren().add(settings);
        mainMenu.getChildren().add(exit);



        //   -----  trainer scene  ---------


        Canvas trainerCanvas = new Canvas(5000, 700);

        GraphicsContext graphicsContext = trainerCanvas.getGraphicsContext2D();
        graphicsContext.setFill(Color.BLACK);
        graphicsContext.setFont(bravura);

        Pane trainerCanvasPane = new Pane();

        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.setContent(trainerCanvasPane);

        Score score = new Score();

        ScoreDrawer scoreDrawer = new ScoreDrawer(graphicsContext, 30, 300);

        Rectangle highlight = new Rectangle(0, 150, 90, 350);

        TrainerSession session = new TrainerSession(user);

        Scene trainerScene = new Scene(scrollPane);

        primaryStage.setScene(mainScene);
        primaryStage.setTitle("Prima vista trainer");
        primaryStage.show();

        // -------  event handlers  ----

        selectUserButton.setOnAction(actionEvent -> {
            user.setUsername(selectUserList.getValue());
            borderPane.setCenter(mainMenu);
        });

        createUserButton.setOnAction(actionEvent -> {
            if (dataAPI.createUser(createUserInput.getText())) {
                user.setUsername(createUserInput.getText());
                borderPane.setCenter(mainMenu);
            } else {
                createUserErrorMessage.setText("username already taken or too long (20 characters)");
            }
        });

        startTraining.setOnMouseClicked(mouseEvent -> {
            graphicsContext.clearRect(0,0, trainerCanvas.getWidth(), trainerCanvas.getHeight());

            trainerCanvasPane.getChildren().clear();
            trainerCanvasPane.getChildren().add(trainerCanvas);

            score.generate(64);
            scoreDrawer.setScore(score);
            session.resetSession(score);
            scoreDrawer.draw();

            highlight.setFill(Color.rgb(50,150,250,0.2));
            highlight.setTranslateX(75);

            trainerCanvasPane.getChildren().add(highlight);
            primaryStage.setScene(trainerScene);
        });

        statisticsText.setOnMouseClicked(mouseEvent -> {
            // TODO: set scene to settings
        });

        settings.setOnMouseClicked(mouseEvent -> {
            // TODO: set scene to settings
        });

        exit.setOnMouseClicked(mouseEvent -> {
            System.exit(0);
        });

        trainerScene.setOnKeyPressed(keyEvent -> {
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
                case ESCAPE:
                    session.endSession();
                    primaryStage.setScene(mainScene);
                    return;
                default: break;
            }
            session.noteInput(v);
        });
    }

    public static void main(String[] args) {
        launch(args);
    }
}
