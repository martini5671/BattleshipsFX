package com.example.battleshipsfx;

import BattleshipUtils.AlertUtil;
import BattleshipUtils.Battlefield;
import BattleshipUtils.DataTransfer;
import BattleshipUtils.Validators;
import javafx.animation.PauseTransition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.ResourceBundle;

public class BoardBuilderController implements Initializable {

    DataTransfer dataTransfer = DataTransfer.getInstance();
    public BorderPane borderPane;
    public HBox Hbox_Center;
    public Button scan_button;
    public Label instructions_label;
    public Label steps_label;
    public Button back_to_menu_button;
    public Label information_label;
    public Button reset_button;
    GridPane gridPane = new GridPane();

    public double rectDim = 40;
    private final int[] shipLengthsArray = {5,4,3,3,2,-1};
    private int shipLengthsArrayIndex  = 0;
    private int shipLengthLimitStart = 5;
    private int stepCounter = 2;

    public Battlefield battlefield = new Battlefield(false);

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        for (int row = 0; row < 10; row++) {
            for (int col = 0; col < 10; col++) {
                Button button = new Button();
                button.setPrefWidth(rectDim);
                button.setPrefHeight(rectDim);
                button.setId("buttonInGrid");

                // change behavior of the button
                button.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent actionEvent) {
                        // check count of shipLengthLimitStart
                        if(button.getId().equals("buttonInGrid"))
                        {
                            button.setId("buttonInGridClicked");
                            shipLengthLimitStart--;
                            System.out.println(shipLengthLimitStart);
                            // block grid if ship length = 0
                        }
                        else
                        {
                            button.setId("buttonInGrid");
                            shipLengthLimitStart++;
                            System.out.println(shipLengthLimitStart);
                            // block grid if ship length = 0
                        }
                    }
                });
                gridPane.add(button, col, row);
            }
        }
        // Set the gridPane in the center of the borderPane
        Hbox_Center.getChildren().add(gridPane);
    }
    // button to go to the second phase
    // save the state of the board and pass it to the next scene

    public void submitBoard(ActionEvent actionEvent) throws IOException {
        ArrayList<Integer> rows = getClickedButtonsCoordinates().get("rows");
        ArrayList<Integer> columns = getClickedButtonsCoordinates().get("columns");
        // add one to each
        if (rows.isEmpty() || rows.size() == 1) {
            AlertUtil.showErrorAlert("Invalid number of tiles selected", "No tiles (or one) were selected. Please select appropriate number of tiles for ship");
        } else {
            for (int i = 0; i < rows.size(); i++) {
                rows.set(i, rows.get(i) + 1);
                columns.set(i, columns.get(i) + 1);
            }
            // check if coordinates are valid:
            if (Validators.validateShipPlacementForPlayer(rows, columns, battlefield.getBattlefield()) && shipLengthLimitStart == 0) {
                battlefield.addShipToBattlefield(shipLengthsArray[shipLengthsArrayIndex], rows, columns);
                //block newly added buttons
                blockButtons();
                // adjust text
                steps_label.setText("Step " + (stepCounter) + "/6");
                // adjust step
                stepCounter++;
                // adjust index for ships lengths
                shipLengthsArrayIndex++;
                //set instruction
                instructions_label.setText(getInstructionsString());
                if (shipLengthsArrayIndex <= 4) {
                    shipLengthLimitStart = shipLengthsArray[shipLengthsArrayIndex];
                } else {
                    // method to save board to data transfer
                    dataTransfer.setBattlefield(battlefield);
                    // method to show success screen
                    showSuccessAndBlockButtons();
                    // method to load another scene
                    goToGame();
                }
            } else {
                System.out.println("Not valid");
                AlertUtil.showErrorAlert("Invalid ship", "Please provide valid ship coordinates");
            }
        }
    }

    private Node getNodeFromGridPane(GridPane gridPane, int col, int row) {
        for (Node node : gridPane.getChildren()) {
            if (GridPane.getColumnIndex(node) == col && GridPane.getRowIndex(node) == row) {
                return node;
            }
        }
        return null;
    }
    private Button getButtonFromNode(Node node)
    {
        if (node instanceof Button){
            return (Button) node;
        }
        else return null;
    }

    public void resetBoard(ActionEvent actionEvent)
    {
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                Node node = getNodeFromGridPane(gridPane, i, j);
                Button button = getButtonFromNode(node);
                if (button != null) {
                    if(button.getId().contains("buttonInGridClicked"))
                    {
                        button.setId("buttonInGrid");
                    }
                }
            }
        }
        // reset counter
        shipLengthLimitStart = shipLengthsArray[shipLengthsArrayIndex];

    }

    private HashMap<String, ArrayList<Integer>> getClickedButtonsCoordinates()
    {
        HashMap<String, ArrayList<Integer>> stringArrayListHashMap = new HashMap<>();
        ArrayList<Integer> rows = new ArrayList<>();
        ArrayList<Integer> columns = new ArrayList<>();


        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                Node node = getNodeFromGridPane(gridPane, i, j);
                Button button = getButtonFromNode(node);
                if (button != null) {
                    if(button.getId().contains("buttonInGridClicked"))
                    {
                        rows.add(j);
                        columns.add(i);
                    }
                }
            }
        }
        // construct hash map
        stringArrayListHashMap.put("rows", rows);
        stringArrayListHashMap.put("columns", columns);
        return stringArrayListHashMap;

    }
    private void blockButtons()
    {
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                Node node = getNodeFromGridPane(gridPane, i, j);
                Button button = getButtonFromNode(node);
                if (button != null) {
                    if(button.getId().contains("buttonInGridClicked"))
                    {
                        button.setDisable(true);
                        button.setId("disabled");
                    }
                }
            }
        }

    }
    private String getInstructionsString()
    {
        String preString = "Click on squares to draw ";
        String afterString = " square long ship.";
        return preString + shipLengthsArray[shipLengthsArrayIndex] + afterString;
    }

    public void goBackToMMenu(ActionEvent actionEvent) throws IOException {
        SceneManager sceneManager = new SceneManager(back_to_menu_button, "MainMenu.fxml", "Menu.css");
        sceneManager.openNewScene();
    }
    public void showSuccessAndBlockButtons()
    {
        scan_button.setDisable(true);
        reset_button.setDisable(true);
        back_to_menu_button.setDisable(true);
        //change labels
        information_label.setStyle("-fx-font-size: 15px;");
        information_label.setAlignment(Pos.CENTER);
        information_label.setText("You have successfully added all of the ships to the board. Now the game will be started.");
        // instructions label
        instructions_label.setAlignment(Pos.CENTER);
        instructions_label.setStyle("-fx-text-fill: green;");
        instructions_label.setText("Success!");
    }
    public void goToGame() throws IOException {

        PauseTransition delay = new PauseTransition(Duration.seconds(4)); // 2 second delay
        delay.setOnFinished(event -> {
            SceneManager sceneManager = new SceneManager(reset_button, "GameBoard.fxml", "GameBoard.css");
            try {
                sceneManager.openNewScene();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });

        delay.play();
    }
}
