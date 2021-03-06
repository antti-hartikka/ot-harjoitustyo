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
import java.util.Arrays;

/**
 * Main application
 */
public class UserInterface extends Application {

    private final Font edwin = Font.loadFont("file:resources/fonts/Edwin-Roman.otf", 20);
    private DataService dataService;
    private String user = "";
    private Statistics statistics;
    private TrainerSession session;
    private Trainer trainer;
    private Scene trainerScene;

    @Override
    public void start(Stage primaryStage) {

        //   -----  first scene for user selection / creation

        VBox userSelection = new VBox();
        userSelection.setSpacing(10);

        userSelection.getChildren().add(text("Select user from the list or create new user"));

        HBox listBox = new HBox();
        userSelection.getChildren().add(listBox);

        listBox.setSpacing(5);
        ChoiceBox<String> selectUserList = new ChoiceBox<>();
        selectUserList.getItems().addAll(dataService.getAllUsers());
        listBox.getChildren().add(selectUserList);

        Button selectUserButton = button("select");
        listBox.getChildren().add(selectUserButton);


        HBox inputBox = new HBox();
        inputBox.setSpacing(5);
        userSelection.getChildren().add(inputBox);

        TextField createUserInput = textField("");
        inputBox.getChildren().add(createUserInput);

        Button createUserButton = button("create new user");
        inputBox.getChildren().add(createUserButton);

        Text selectUserErrorMessage = text(null);
        selectUserErrorMessage.setFill(Color.RED);
        userSelection.getChildren().add(selectUserErrorMessage);

        BorderPane borderPane = new BorderPane();
        borderPane.setPrefSize(1000, 600);
        borderPane.setPadding(new Insets(100, 100, 100, 100));
        borderPane.setCenter(userSelection);

        Scene mainScene = new Scene(borderPane);


        //   ----- main menu  --------

        VBox mainMenu = new VBox();
        mainMenu.setSpacing(5);

        Text startTraining = text("start training");
        mainMenu.getChildren().add(startTraining);

        Text statisticsText = text("statistics");
        mainMenu.getChildren().add(statisticsText);

        Text settings = text("settings");
        mainMenu.getChildren().add(settings);

        Text exit = text("exit");
        mainMenu.getChildren().add(exit);


        //   -----  statistics view -----

        Text mainMenuStatistics = text("back to main menu");

        statisticsText.setOnMouseClicked(mouseEvent -> {
            VBox statisticsView = new VBox();
            statisticsView.setSpacing(10);

            statisticsView.getChildren().add(statistics.getChart(user));
            statisticsView.getChildren().add(mainMenuStatistics);
            borderPane.setCenter(statisticsView);
        });


        //   -----  settings menu --------

        VBox settingsMenu = new VBox();
        settingsMenu.setSpacing(5);

        HBox noteCountSettingBox = new HBox();
        noteCountSettingBox.getChildren().add(text("notes per training session"));
        TextField noteCountInput = textField("32");
        noteCountSettingBox.getChildren().add(noteCountInput);
        Text noteCountErrorMessage = errorText(null);
        noteCountSettingBox.getChildren().add(noteCountErrorMessage);
        settingsMenu.getChildren().add(noteCountSettingBox);

        HBox scaleSelectionBox = new HBox();
        scaleSelectionBox.setSpacing(3);
        ChoiceBox<String> root = new ChoiceBox<>();
        root.getItems().addAll(Arrays.asList(
                "C", "D", "E", "F", "G", "A", "B"
        ));
        scaleSelectionBox.getChildren().add(root);
        ChoiceBox<String> accidental = new ChoiceBox<>();
        accidental.getItems().addAll(Arrays.asList(
                "neutral", "sharp", "flat"
        ));
        scaleSelectionBox.getChildren().add(accidental);
        ChoiceBox<String> mode = new ChoiceBox<>();
        mode.getItems().addAll(Arrays.asList(
                "major", "minor"
        ));
        scaleSelectionBox.getChildren().add(mode);
        Button setScaleButton = button("set scale");
        scaleSelectionBox.getChildren().add(setScaleButton);
        settingsMenu.getChildren().add(scaleSelectionBox);

        Text mainMenuText = text("back to main menu");
        settingsMenu.getChildren().add(mainMenuText);


        //   -----  trainer scene  ---------

        trainer = new Trainer();
        trainerScene = trainer.getScene();

        // ----- stage -----

        primaryStage.setScene(mainScene);
        primaryStage.setTitle("Prima vista trainer");
        primaryStage.show();

        // -------  event handlers  ----

        selectUserButton.setOnAction(actionEvent -> {
            if (selectUserList.getValue() != null) {
                user = selectUserList.getValue();
                session = new TrainerSession(user, dataService);
                trainer.setTrainerSession(session);
                borderPane.setCenter(mainMenu);
            } else {
                selectUserErrorMessage.setText("invalid selection");
            }
        });

        createUserButton.setOnAction(actionEvent -> {
            if (dataService.createUser(createUserInput.getText())) {
                user = createUserInput.getText();
                session = new TrainerSession(user, dataService);
                trainer.setTrainerSession(session);
                borderPane.setCenter(mainMenu);
            } else {
                selectUserErrorMessage.setText("username already taken or incorrect (3-20 characters a-z, 0-9)");
            }
        });

        startTraining.setOnMouseClicked(mouseEvent -> primaryStage.setScene(trainer.startTraining()));
        startTraining.setOnMouseEntered(mouseEvent -> startTraining.setFill(Color.CRIMSON));
        startTraining.setOnMouseExited(mouseEvent -> startTraining.setFill(Color.BLACK));

        statisticsText.setOnMouseEntered(mouseEvent -> statisticsText.setFill(Color.CRIMSON));
        statisticsText.setOnMouseExited(mouseEvent -> statisticsText.setFill(Color.BLACK));

        settings.setOnMouseClicked(mouseEvent -> borderPane.setCenter(settingsMenu));
        settings.setOnMouseEntered(mouseEvent -> settings.setFill(Color.CRIMSON));
        settings.setOnMouseExited(mouseEvent -> settings.setFill(Color.BLACK));

        exit.setOnMouseClicked(mouseEvent -> System.exit(0));
        exit.setOnMouseEntered(mouseEvent -> exit.setFill(Color.CRIMSON));
        exit.setOnMouseExited(mouseEvent -> exit.setFill(Color.BLACK));

        mainMenuText.setOnMouseClicked(mouseEvent -> borderPane.setCenter(mainMenu));
        mainMenuText.setOnMouseEntered(mouseEvent -> mainMenuText.setFill(Color.CRIMSON));
        mainMenuText.setOnMouseExited(mouseEvent -> mainMenuText.setFill(Color.BLACK));

        mainMenuStatistics.setOnMouseClicked(mouseEvent -> borderPane.setCenter(mainMenu));
        mainMenuStatistics.setOnMouseEntered(mouseEvent -> mainMenuStatistics.setFill(Color.CRIMSON));
        mainMenuStatistics.setOnMouseExited(mouseEvent -> mainMenuStatistics.setFill(Color.BLACK));

        trainerScene.setOnKeyPressed(keyEvent -> {
            if (keyEvent.getCode() == KeyCode.ESCAPE) {
                if (!session.isEnded()) {
                    trainer.endTraining();
                }
                primaryStage.setScene(mainScene);
            } else {
                trainer.handleKeyEvent(keyEvent);
            }
        });

        noteCountInput.setOnKeyTyped(event -> {
            String input = noteCountInput.getText();
            if (input.matches("[0-9]{1,3}") && Integer.parseInt(input) > 3) {
                trainer.setNoteCount(Integer.parseInt(input));
                noteCountErrorMessage.setText(null);
            } else {
                noteCountErrorMessage.setText("incorrect value");
            }
        });

        setScaleButton.setOnMouseClicked(event -> {
            if (root.getValue() == null || accidental.getValue() == null || mode.getValue() == null) {
                return;
            }
            int rootNote = getRootNote(root.getValue());
            if (accidental.getValue().equals("sharp")) {
                rootNote = (rootNote + 1) % 12;
            } else if (accidental.getValue().equals("flat")) {
                rootNote = (rootNote + 11) % 12;
            }
            trainer.getScore().setScale(rootNote, mode.getValue());
        });

    }

    private int getRootNote(String value) {
        switch (value) {
            case "C": return 0;
            case "D": return 2;
            case "E": return 4;
            case "F": return 5;
            case "G": return 7;
            case "A": return 9;
            case "B": return 11;
        }
        return -1;
    }

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void init() {
        DBUserDao userDao = new DBUserDao("jdbc:sqlite:database.db");
        DBSessionDao sessionDao = new DBSessionDao("jdbc:sqlite:database.db");
        dataService = new DataService(userDao, sessionDao);
        statistics = new Statistics(dataService);
    }

    private Text text(String s) {
        Text text = new Text(s);
        text.setFont(edwin);
        return text;
    }

    private Button button(String s) {
        Button button = new Button(s);
        button.setFont(edwin);
        return button;
    }

    private TextField textField(String s) {
        TextField textField = new TextField(s);
        textField.setFont(edwin);
        return textField;
    }

    private Text errorText(String s) {
        Text t = text(s);
        t.setFill(Color.RED);
        return t;
    }

}
