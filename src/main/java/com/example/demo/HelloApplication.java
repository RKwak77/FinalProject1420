package com.example.demo;
// Import necessary classes for JavaFX application
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class HelloApplication extends Application {

    // Declare buttons for the main scene
    Button startGame;
    Button quitGame;

    // Main method to launch the JavaFX application
    public static void main(String[] args) {
        launch(args);
    }

    // Override start method from Application class
    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("Title of the window");

        // Initialize buttons and intro text for the main scene
        startGame = new Button("Start game");
        quitGame = new Button("Quit game");
        Text introParagraph = new Text("Intro text goes here");

        // Layout for the main scene
        VBox layout = new VBox(10);
        layout.getChildren().addAll(introParagraph, startGame, quitGame);
        layout.setAlignment(Pos.CENTER); // Center align VBox content

        // Actions for the buttons in the main scene
        startGame.setOnAction(e -> {
            // Open a new scene with RuleDescription
            RuleDescription ruleDescriptionScene = new RuleDescription();
            ruleDescriptionScene.start(primaryStage);
        });

        quitGame.setOnAction(e -> primaryStage.close());

        // StackPane to center the layout in the scene
        StackPane root = new StackPane();
        root.getChildren().add(layout);

        // Creating the main scene
        Scene scene = new Scene(root, 600, 400);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    // RuleDescription class for the new scene
    static class RuleDescription extends Application {
        @Override
        public void start(Stage primaryStage) {
            primaryStage.setTitle("Rules Description");

            // Text for rules description
            Text ruleText = new Text("Rules description goes here");
            ruleText.setStyle("-fx-font-size: 18px;");

            // Button to continue
            Button continueButton = new Button("Continue");
            continueButton.setOnAction(e -> {
                // Open a new scene with MainBoard
                MainBoard mainBoardScene = new MainBoard();
                mainBoardScene.start(primaryStage);

            });

            // Layout for the RuleDescription scene
            VBox layout = new VBox(20);
            layout.getChildren().addAll(ruleText, continueButton);
            layout.setAlignment(Pos.CENTER);

            // Creating the RuleDescription scene
            Scene scene = new Scene(layout, 600, 400);
            primaryStage.setScene(scene);
            primaryStage.show();
            // We can also ask for the two player names in this window
        }
    }

    // MainBoard class for the new scene
    static class MainBoard extends Application {
        @Override
        public void start(Stage primaryStage) {
            primaryStage.setTitle("Main Board");

            // Text for the main board
            Text mainBoardText = new Text("Main board goes here");
            mainBoardText.setStyle("-fx-font-size: 18px;");

            // Layout for the MainBoard scene
            StackPane layout = new StackPane();
            layout.getChildren().add(mainBoardText);

            // Creating the MainBoard scene
            Scene scene = new Scene(layout, 600, 400);
            primaryStage.setScene(scene);
            primaryStage.show();
        }
    }
}
