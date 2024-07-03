package com.example.battleshipsfx;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

import java.io.IOException;

public class YouLostController {
    public Button backToMenu_Button;

    public void goBackToMenu(ActionEvent actionEvent) throws IOException {
        SceneManager sceneManager = new SceneManager(backToMenu_Button, "MainMenu.fxml", "Menu.css");
        sceneManager.openNewScene();
    }
}
