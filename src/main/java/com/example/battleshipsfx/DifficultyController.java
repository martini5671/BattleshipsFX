package com.example.battleshipsfx;

import BattleshipUtils.AlertUtil;
import BattleshipUtils.DataTransfer;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class DifficultyController implements Initializable {
    DataTransfer dataTransfer = DataTransfer.getInstance();
    public TextField PlayerNameTextbox;
    public ChoiceBox<String> PickDifficultyChoiceBox;
    public Button GoBackToMMenuButton;
    public Button StartGameButton;
    public String[] DifficultyChoices = {"Easy", "Medium", "Hard"};



    public void goBackToMMenu(ActionEvent actionEvent) throws IOException {
        SceneManager sceneManager = new SceneManager(GoBackToMMenuButton, "MainMenu.fxml", "Menu.css");
        sceneManager.openNewScene();
    }

    public void goToBoardBuilder(ActionEvent actionEvent) throws IOException {
        if(PlayerNameTextbox.getText().isEmpty() || PickDifficultyChoiceBox.getValue() == null)
        {
            AlertUtil.showWarningAlert("Empty nickname or no difficulty picked", "Please provide valid nickname and make sure to pick a difficulty");
        }
        else
        {
            getDifficultyToDataTransfer();
            getPlayerNameToDataTransfer();
            //load new screen
            SceneManager sceneManager = new SceneManager(StartGameButton, "BuildBoard.fxml", "BoardBuilder.css");
            sceneManager.openNewScene();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        PickDifficultyChoiceBox.getItems().addAll(DifficultyChoices);
        PickDifficultyChoiceBox.setMinWidth(200);

    }

    // pick a difficulty and put it into datatransfer
    private void getDifficultyToDataTransfer()
    {
        String difficulty = PickDifficultyChoiceBox.getValue();
        dataTransfer.setDifficulty(difficulty);
    }
    public void getPlayerNameToDataTransfer() {
           dataTransfer.setPlayerId(PlayerNameTextbox.getText());
    }
}
