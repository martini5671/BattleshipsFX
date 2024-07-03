package com.example.battleshipsfx;

import BattleshipUtils.AlertUtil;
import BattleshipUtils.DataTransfer;
import BattleshipUtils.MongoDB;
import com.mongodb.MongoException;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import animatefx.animation.*;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class YouWonController implements Initializable {
    public Button backToMenu_button;
    public ImageView image_viewer;
    public Label score_label;
    private boolean viewScore = true;

    DataTransfer dataTransfer = DataTransfer.getInstance();

    private String connectionString = "mongodb://localhost:27017/";
    private String dbName = "battleships_scores";
    private String collectionName = "stats";



    public void goBackToMenu(ActionEvent actionEvent) throws IOException {
        SceneManager sceneManager = new SceneManager(backToMenu_button, "MainMenu.fxml", "Menu.css");
        sceneManager.openNewScene();
    }

    public void viewScore(ActionEvent actionEvent) {
        score_label.setText("Score: "+ String.valueOf(dataTransfer.getScore()));
        if(viewScore)
        {
            FadeOut fadeOut = new FadeOut(image_viewer);
            fadeOut.play();
            FadeIn fadeIn = new FadeIn(score_label);
            fadeIn.play();
            viewScore = false;
        }
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        putDataToMongoDB();
    }
    private void putDataToMongoDB()
    {
        MongoDB mongoDB = new MongoDB(connectionString, dbName, collectionName);
        mongoDB.insertStats(dataTransfer.getPlayerId(),
                dataTransfer.getDifficulty(),
                dataTransfer.getScore(),
                dataTransfer.getGameplayTime(),
                dataTransfer.getNumberMisses());
        //MongoDB.printSortedHashMap(mongoDB.fetchSortedDataByScore(2));
    }
}
