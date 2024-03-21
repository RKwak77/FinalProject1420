package com.example.demo;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.scene.text.Text;
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

            // Create players
            Player player1 = new Player(Color.GREY);
            Player player2 = new Player(Color.PURPLE);

            // Roll dice to determine who plays first
            Random dice = new Random();
            boolean player1Starts = dice.nextBoolean();

            // Place players in opposite corners of the grid
            if (player1Starts) {
                gridPane.add(player1, 0, 0);
                gridPane.add(player2, 9, 9);
            } else {
                gridPane.add(player1, 9, 9);
                gridPane.add(player2, 0, 0);
            }

            // Event handler for keyboard input
            Scene scene = new Scene(gridPane, 800, 600); // Initialize the scene with gridPane
            scene.setOnKeyPressed(event -> {
                KeyCode keyCode = event.getCode();
                switch (keyCode) {
                    case UP:
                        player1.moveUp();
                        break;
                    case DOWN:
                        player1.moveDown();
                        break;
                    case LEFT:
                        player1.moveLeft();
                        break;
                    case RIGHT:
                        player1.moveRight();
                        break;
                    default:
                        break;
                }
            });

            // Create dice
            Dice diceRoller = new Dice();

            // Button to roll the dice
            Button rollButton = new Button("Roll Dice");
            rollButton.setOnAction(e -> {
                int diceValue = diceRoller.roll();
                System.out.println("Dice rolled: " + diceValue);
                // Pass the dice value to the players for moving
                player1.move(diceValue);
                player2.move(diceValue);
                rollButton.setDisable(true); // Disable roll button after rolling dice
            });

            // Layout for dice and roll button
            VBox diceLayout = new VBox(10);
            diceLayout.getChildren().addAll(diceRoller, rollButton);
            diceLayout.setAlignment(Pos.CENTER_RIGHT);

            // Layout for the MainBoard scene
            VBox layout = new VBox(10);
            layout.getChildren().addAll(gridPane, diceLayout);
            layout.setAlignment(Pos.CENTER);

            // Creating the MainBoard scene
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

    // Player class
    public static class Player extends Rectangle {
        private int currentXPos = 0;
        private int currentYPos = 0;
        private boolean hitBuilding = false;

        public Player(Color color) {
            super(50, 50);
            this.setFill(color);
            this.setStroke(Color.BLACK);
        }

        // Method to move the player up
        public void moveUp() {
            move(0, -1);
        }

        // Method to move the player down
        public void moveDown() {
            move(0, 1);
        }

        // Method to move the player left
        public void moveLeft() {
            move(-1, 0);
        }

        // Method to move the player right
        public void moveRight() {
            move(1, 0);
        }

        // Helper method for movement
        private void move(int dx, int dy) {
            int nextXPos = currentXPos + dx;
            int nextYPos = currentYPos + dy;
            if (nextXPos >= 0 && nextXPos < 10 && nextYPos >= 0 && nextYPos < 10) {
                currentXPos = nextXPos;
                currentYPos = nextYPos;
                // Update player's position in the GridPane
                GridPane.setColumnIndex(this, currentXPos);
                GridPane.setRowIndex(this, currentYPos);
            }
        }

        // Method to move the player
        public void move(int steps) {
            // Example implementation, actual movement logic depends on game rules
            System.out.println("Player moved " + steps + " steps.");

            // Iterate through each step
            for (int i = 0; i < steps; i++) {
                // Calculate next position
                int nextXPos = currentXPos + 1;
                int nextYPos = currentYPos;

                // Check if the next position is within bounds
                if (nextXPos >= 0 && nextXPos < 10 && nextYPos >= 0 && nextYPos < 10) {
                    // Check if the next position is a wall
                    boolean isWall = false;
                    for (Building building : MainBoard.buildings) {
                        if (building.getXPos() / 50 == nextXPos && building.getYPos() / 50 == nextYPos &&
                                building.getFill() == Color.BLACK) {
                            isWall = true;
                            break;
                        }
                    }

                    // If not a wall, move to the next position
                    if (!isWall) {
                        // Check if the next position contains a building
                        for (Building building : MainBoard.buildings) {
                            if (building.getXPos() / 50 == nextXPos && building.getYPos() / 50 == nextYPos) {
                                hitBuilding = true;
                                break;
                            }
                        }
                        if (!hitBuilding) {
                            // Update player's position
                            currentXPos = nextXPos;
                            currentYPos = nextYPos;
                        }
                    }
                }
            }

            // Update player's position in the GridPane
            GridPane.setColumnIndex(this, currentXPos);
            GridPane.setRowIndex(this, currentYPos);
        }
    }


    // Dice class
    public static class Dice extends StackPane {
        private Random random;
        private Text diceValueText;

        public Dice() {
            random = new Random();
            Rectangle dice = new Rectangle(50, 50);
            dice.setFill(Color.WHITE);
            dice.setStroke(Color.BLACK);

            diceValueText = new Text();
            diceValueText.setStyle("-fx-font-size: 20px;");
            diceValueText.setVisible(false);

            getChildren().addAll(dice, diceValueText);

            setOnMouseClicked(e -> roll());
        }

        public int roll() {
            int value = random.nextInt(6) + 1; // Roll a dice from 1 to 6
            diceValueText.setText(String.valueOf(value));
            diceValueText.setVisible(true);
            return value;
        }
    }
}

