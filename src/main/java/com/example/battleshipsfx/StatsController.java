package com.example.battleshipsfx;

import BattleshipUtils.MongoDB;
import BattleshipUtils.MongoDM;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.ResourceBundle;

public class StatsController implements Initializable {

    public TableView<MongoDM> table_view;
    public TableColumn<MongoDM,String> id_column;
    public TableColumn<MongoDM, String> score_column;
    public TableColumn<MongoDM, String> time_column;
    public TableColumn<MongoDM, String> misses_column;
    public TableColumn<MongoDM, String> date_column;
    public TableColumn<MongoDM, String> ai_id;
    private final String connectionString = "mongodb://localhost:27017/";
    private final String dbName = "battleships_scores";
    private final String collectionName = "stats";
    public Button GoBackToMMenuButton;
    private int n_records = 10;

    ObservableList<MongoDM> initializeData()
    {
        ArrayList<MongoDM> mongoDMList = new ArrayList<>();
        MongoDB mongoDB = new MongoDB(connectionString, dbName, collectionName);
        HashMap<String, ArrayList<String>> data = mongoDB.fetchSortedDataByScore(n_records);
        for (int i = 0; i < data.get("winners").size(); i++) {
            MongoDM mongoDM = new MongoDM(
                    data.get("winners").get(i),
                    data.get("losers").get(i),
                    data.get("scores").get(i),
                    data.get("dates").get(i),
                    data.get("times").get(i),
                    data.get("misses").get(i)
            );
            // put it into array
            mongoDMList.add(mongoDM);
        }
        return FXCollections.<MongoDM> observableArrayList(mongoDMList);
    };

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        id_column.setCellValueFactory(new PropertyValueFactory<MongoDM, String>("winner"));
        score_column.setCellValueFactory(new PropertyValueFactory<MongoDM, String>("score"));
        time_column.setCellValueFactory(new PropertyValueFactory<MongoDM, String>("time"));
        misses_column.setCellValueFactory(new PropertyValueFactory<MongoDM, String>("misses"));
        date_column.setCellValueFactory(new PropertyValueFactory<MongoDM, String>("date"));
        ai_id.setCellValueFactory(new PropertyValueFactory<MongoDM, String>("looser"));

        table_view.setItems(initializeData());
    }

    public void goBackToMenu(ActionEvent actionEvent) throws IOException {
        SceneManager sceneManager = new SceneManager(GoBackToMMenuButton, "MainMenu.fxml", "Menu.css");
        sceneManager.openNewScene();
    }
}
