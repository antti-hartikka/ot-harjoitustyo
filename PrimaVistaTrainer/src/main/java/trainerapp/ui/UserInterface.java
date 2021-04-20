package trainerapp.ui;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.scene.text.Font;
import trainerapp.dao.DBSessionDao;
import trainerapp.dao.DBUserDao;
import trainerapp.domain.*;

public class UserInterface extends Application {

    private final Font bravura = Font.loadFont("file:resources/fonts/BravuraText.otf", 100);
    private final Font edwin = Font.loadFont("file:resources/fonts/Edwin-Roman.otf", 20);
    private DataService dataService;
    private String user = "";
    private final Statistics statistics = new Statistics();

    @Override
    public void start(Stage primaryStage) {

        //   -----  first scene for user selection / creation

        Text selectUserText = new Text("Select user from the list or create new user");
        selectUserText.setFont(edwin);

        ChoiceBox<String> selectUserList = new ChoiceBox<>();
        selectUserList.getItems().addAll(dataService.getAllUsers());

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
        borderPane.setPadding(new Insets(100, 100, 100, 100));
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

        TrainerSession session = new TrainerSession(user, dataService);
        Trainer trainer = new Trainer(session);
        Scene trainerScene = trainer.getScene();

        // ----- stage -----

        primaryStage.setScene(mainScene);
        primaryStage.setTitle("Prima vista trainer");
        primaryStage.show();

        // -------  event handlers  ----

        selectUserButton.setOnAction(actionEvent -> {
            user = selectUserList.getValue();
            borderPane.setCenter(mainMenu);
        });

        createUserButton.setOnAction(actionEvent -> {
            if (dataService.createUser(createUserInput.getText())) {
                user = createUserInput.getText();
                borderPane.setCenter(mainMenu);
            } else {
                createUserErrorMessage.setText("username already taken or too long (20 characters)");
            }
        });

        startTraining.setOnMouseClicked(mouseEvent -> {
            primaryStage.setScene(trainer.startTraining());
        });

        statisticsText.setOnMouseClicked(mouseEvent -> {
            // TODO: set scene to statistics
        });

        settings.setOnMouseClicked(mouseEvent -> {
            // TODO: set scene to settings
        });

        exit.setOnMouseClicked(mouseEvent -> {

            System.exit(0);
        });

        trainerScene.setOnKeyPressed(keyEvent -> {
            if (keyEvent.getCode() == KeyCode.ESCAPE) {
                session.endSession();
                primaryStage.setScene(mainScene);
            } else {
                trainer.handleKeyEvent(keyEvent);
            }
        });
    }

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void init() {
        DBUserDao userDao = new DBUserDao("jdbc:sqlite:database.db");
        DBSessionDao sessionDao = new DBSessionDao("jdbc:sqlite:database.db");
        dataService = new DataService(userDao, sessionDao);
    }
}
