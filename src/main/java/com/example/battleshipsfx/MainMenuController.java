package com.example.battleshipsfx;

import javafx.event.ActionEvent;
import javafx.scene.control.Button;

import java.io.IOException;

public class MainMenuController {


    public Button new_game_button;

    public void goToDifficultySettings(ActionEvent actionEvent) throws IOException {
        SceneManager sceneManager = new SceneManager( new_game_button, "PickDifficulty.fxml","Menu.css");
        sceneManager.openNewScene();
    }
    public void goToStatsScreen(ActionEvent actionEvent) throws IOException {
        SceneManager statsManager = new SceneManager(new_game_button, "Stats.fxml", "Menu.css");
        statsManager.openNewScene();
    }
    public void goToRules(ActionEvent actionEvent) throws IOException {
        SceneManager rulesManager = new SceneManager(new_game_button, "Rules.fxml", "Menu.css");
        rulesManager.openNewScene();
    }
}