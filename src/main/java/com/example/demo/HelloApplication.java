package com.example.demo;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

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
    public static class MainBoard extends Application {
        @Override
        public void start(Stage primaryStage) {
            primaryStage.setTitle("Main Board");

            // Creating a GridPane for the board
            GridPane gridPane = new GridPane();
            gridPane.setAlignment(Pos.CENTER); // Center the grid within the scene
            gridPane.setHgap(5); // Add horizontal gap between cells
            gridPane.setVgap(5); // Add vertical gap between cells
            gridPane.setPadding(new javafx.geometry.Insets(20)); // Add padding around the grid

            // Adding cells to the GridPane
            for (int i = 0; i < 10; i++) {
                for (int j = 0; j < 10; j++) {
                    Rectangle cell = new Rectangle(50, 50); // Size of each cell
                    cell.setFill(Color.BEIGE); // Color of the cell
                    cell.setStroke(Color.BLACK); // Border color
                    gridPane.add(cell, j, i); // Add cell to the GridPane
                }
            }

            // Creating buildings
            List<Building> buildings = new ArrayList<>();

            // Add castle
            Building castle = new Building(Color.YELLOW, 4 * 50, 4 * 50, 50);
            buildings.add(castle);

            // Add treasures
            Random random = new Random();
            for (int i = 0; i < 8; i++) {
                int xPos, yPos;
                do {
                    xPos = random.nextInt(10) * 50;
                    yPos = random.nextInt(10) * 50;
                } while (isOverlapping(buildings, xPos, yPos));
                Building treasure = new Building(Color.GREEN, xPos, yPos, 50);
                buildings.add(treasure);
            }

            // Add walls
            for (int i = 0; i < 5; i++) {
                int xPos, yPos;
                do {
                    xPos = random.nextInt(10) * 50;
                    yPos = random.nextInt(10) * 50;
                } while (isOverlapping(buildings, xPos, yPos));
                Building wall = new Building(Color.BLACK, xPos, yPos, 50);
                buildings.add(wall);
            }

            // Add markets
            for (int i = 0; i < 5; i++) {
                int xPos, yPos;
                do {
                    xPos = random.nextInt(10) * 50;
                    yPos = random.nextInt(10) * 50;
                } while (isOverlapping(buildings, xPos, yPos));
                Building market = new Building(Color.ORANGE, xPos, yPos, 50);
                buildings.add(market);
            }

            // Add lost items
            for (int i = 0; i < 13; i++) {
                int xPos, yPos;
                do {
                    xPos = random.nextInt(10) * 50;
                    yPos = random.nextInt(10) * 50;
                } while (isOverlapping(buildings, xPos, yPos));
                Building lostItem = new Building(Color.BLUE, xPos, yPos, 50);
                buildings.add(lostItem);
            }

            // Add traps
            for (int i = 0; i < 5; i++) {
                int xPos, yPos;
                do {
                    xPos = random.nextInt(10) * 50;
                    yPos = random.nextInt(10) * 50;
                } while (isOverlapping(buildings, xPos, yPos));
                Building trap = new Building(Color.RED, xPos, yPos, 50);
                buildings.add(trap);
            }

            // Add buildings to the gridPane
            for (Building building : buildings) {
                gridPane.add(building, (int) building.getXPos() / 50, (int) building.getYPos() / 50);
            }

            // Layout for the MainBoard scene
            StackPane layout = new StackPane();
            layout.getChildren().add(gridPane);

            // Creating the MainBoard scene
            Scene scene = new Scene(layout, 600, 600); // Adjust the size as needed
            primaryStage.setScene(scene);
            primaryStage.show();
        }

        // Method to check if a new building overlaps with existing ones
        private boolean isOverlapping(List<Building> buildings, double xPos, double yPos) {
            for (Building building : buildings) {
                if (building.getXPos() == xPos && building.getYPos() == yPos) {
                    return true;
                }
            }
            return false;
        }
    }

    // Building class
    public static class Building extends Rectangle {
        private double xPos;
        private double yPos;

        public Building(Color color, double xPos, double yPos, double size) {
            super(size, size);
            this.xPos = xPos;
            this.yPos = yPos;
            this.setFill(color);
            this.setStroke(Color.BLACK);
            this.setX(xPos);
            this.setY(yPos);
        }

        // Getters and setters for position
        public double getXPos() {
            return xPos;
        }

        public void setXPos(double xPos) {
            this.xPos = xPos;
            this.setX(xPos);
        }

        public double getYPos() {
            return yPos;
        }

        public void setYPos(double yPos) {
            this.yPos = yPos;
            this.setY(yPos);
        }
    }
}