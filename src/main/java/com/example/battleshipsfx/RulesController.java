package com.example.battleshipsfx;

import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.text.Font;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class RulesController implements Initializable {
    public Button GoBackToMMenuButton;
    public Label rules_label;

    private final String rules =
            "1. Each player will have a 10x10 grid to place their ships on. \n" +
                    "2. The sizes of the ships are: 5 spaces (carrier), 4 spaces (battleship),\n" +
                    "3 spaces (cruiser and submarine), and 2 spaces (destroyer).\n"+
                    "3. Ships cannot touch each other, they can only touch adjacently.\n" +
                    "4. The player will be playing against an AI.\n" +
                    "5. Each player takes turns to guess the location of the opponent's ships.\n" +
                    "6. A ship is sunk when all of its cells are hit.\n" +
                    "7. The first player to sink all of the opponent's ships wins the game.\n";


    public void goBackToMenu(ActionEvent actionEvent) throws IOException {
        SceneManager sceneManager = new SceneManager(GoBackToMMenuButton, "MainMenu.fxml", "Menu.css");
        sceneManager.openNewScene();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        rules_label.setText(rules);
        rules_label.setFont(Font.font( 15)); // Set text size to 20
        //rules_label.setStyle("-fx-background-color: white; -fx-padding: 10px;");
        rules_label.setAlignment(Pos.CENTER);
        rules_label.setPadding(new Insets(10, 10, 10, 10)); // Add padding of 10 pixels on all sides
        rules_label.setStyle("-fx-background-color: white; -fx-border-color: black; -fx-border-width: 2px;"); // Add a black border with a width of 2 pixels

    }
}
