package com.example.battleshipsfx;

import BattleshipUtils.AI.BasicAI;
import BattleshipUtils.Battlefield;
import BattleshipUtils.DataTransfer;
import BattleshipUtils.Ship;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.binding.Bindings;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.awt.*;
import java.io.IOException;
import java.net.URL;
import java.util.*;

public class GameBoardController implements Initializable {
    DataTransfer dataTransfer = DataTransfer.getInstance();
    public Button take_fire_button;
    public Label hp_label_player;
    public Label miss_label_player;
    public Label ships_label_player;
    public Label hp_label_enemy;
    public Label miss_label_enemy;
    public Label ships_label_enemy;
    public Button menu_button;
    public Button info_button;
    public Label player_name_label;
    public Label ai_name_label;
    public Label timer_label;
    private Stage infoStage;
    int rectDim = 35; //
    String rectangleSeaCssID = "rectangle_sea";
    String buttonEnemyHit = "button_enemy_hit";
    String buttonEnemyMiss = "button_enemy_miss";
    String rectangleShipCssID = "rectangle_ship";
    String buttonEnemyDefault = "button_default_enemy";
    String buttonEnemyClicked = "button_clicked_enemy";
    String buttonEnemyDisabled = "button_disabled_enemy";
    String rectanglePlayerHit = "rectangle_ship_hit";
    String rectanglePlayerMiss = "rectangle_miss";

    BasicAI basicAI = new BasicAI();


    private GridPane playerGridPane;
    private GridPane enemyGridPane;
    private Battlefield playerBattlefield;
    private Battlefield enemyBattlefield;
    public HBox players_bord_hbox;
    public HBox enemy_board_hbox;
    // Make timeSeconds a Property
    private static final int STARTTIME = 0;
    private Timeline timeline;
    private final IntegerProperty timeSeconds = new SimpleIntegerProperty(STARTTIME);
    private boolean isTimerInitialized = false;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //start timer
        // Start timer

        //set players nickname and AI name
        ai_name_label.setText(dataTransfer.getDifficulty() + " AI");
        player_name_label.setText(dataTransfer.getPlayerId());
        // set boards
        this.playerBattlefield = dataTransfer.getBattlefield();
        this.enemyBattlefield = new Battlefield(true);

        // generate grids
        setEnemyGridPane(generatedEnemyGridPane());
        setPlayerGridPane(generatePlayersGridPane());

        // add them to boxes
        players_bord_hbox.getChildren().add(getPlayerGridPane());
        enemy_board_hbox.getChildren().add(getEnemyGridPane());
        // set take hit to disabled:
        blockHitButton();
    }


    private GridPane generatePlayersGridPane() {
        GridPane gridPane = new GridPane();
        gridPane.setHgap(0); // Horizontal gap between squares
        gridPane.setVgap(0); // Vertical gap between squares
        gridPane.setPadding(new Insets(5)); // Padding around the GridPane

        for (int i = 1; i < 11; i++) {
            for (int j = 1; j < 11; j++) {
                char battlefield_content = playerBattlefield.getBattlefield()[i][j];
                Rectangle rectangle;
                rectangle = new Rectangle(34,34);
                if (battlefield_content == '~') {
                    rectangle.setId(rectangleSeaCssID);
                } else {
                    rectangle.setId(rectangleShipCssID);
                }
                GridPane.setConstraints(rectangle, j, i);
                gridPane.getChildren().add(rectangle);
            }
        }

        return gridPane;
    }

    private GridPane generatedEnemyGridPane() {
        // create grid pane
        GridPane gridPane = new GridPane();
        gridPane.setHgap(0); // Horizontal gap between squares
        gridPane.setVgap(0); // Vertical gap between squares
        gridPane.setPadding(new Insets(5)); // Padding around the GridPane
        // add buttons to grid pane
        for (int i = 1; i < 11; i++) {
            for (int j = 1; j < 11; j++) {
                Button button = new Button();
                button.setPrefWidth(rectDim);
                button.setPrefHeight(rectDim);
                button.setId(buttonEnemyDefault); // default style for unclicked button
                button.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent actionEvent) {
                        // check count of shipLengthLimitStart
                        if(button.getId().equals(buttonEnemyDefault))
                        {
                            button.setId(buttonEnemyClicked);
                            // block other buttons
                            blockOtherButtons();
                            unblockHitButton();
                        }
                        else if(button.getId().equals(buttonEnemyClicked))
                        {
                            button.setId(buttonEnemyDefault);
                            // unblock other buttons
                            unblockOtherButtons();
                            blockHitButton();
                        }
                    }
                });
                gridPane.add(button, j -1 ,i -1);
            }
        }
        return gridPane;
    }

    private void blockOtherButtons()
    {
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                Node node = getNodeFromGridPane(getEnemyGridPane(), i, j);
                Button button = getButtonFromNode(node);
                if (button != null) {
                    if(button.getId().contains(buttonEnemyDefault))
                    {
                        button.setDisable(true);
                        button.setId(buttonEnemyDisabled);
                    }
                }
            }
        }

    }
    private void unblockOtherButtons()
    {
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                Node node = getNodeFromGridPane(getEnemyGridPane(), i, j);
                Button button = getButtonFromNode(node);
                if (button != null) {
                    if(button.getId().contains(buttonEnemyDisabled))
                    {
                        button.setDisable(false);
                        button.setId(buttonEnemyDefault);
                    }
                }
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
    private Rectangle getRectangleFromNode(Node node)
    {
        if (node instanceof Rectangle){
            return (Rectangle) node;
        }
        else return null;
    }


    public GridPane getPlayerGridPane() {
        return playerGridPane;
    }

    public void setPlayerGridPane(GridPane playerGridPane) {
        this.playerGridPane = playerGridPane;
    }

    public GridPane getEnemyGridPane() {
        return enemyGridPane;
    }

    public void setEnemyGridPane(GridPane enemyGridPane) {
        this.enemyGridPane = enemyGridPane;
    }

    public void takeShot(ActionEvent actionEvent) throws IOException {
        if (!isTimerInitialized)
        {
            initializeTimer();
        }

        int row_coordinate = 0;
        int column_coordinate = 0;
        // scan grid
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                Node node = getNodeFromGridPane(getEnemyGridPane(), j,i);
                Button button = getButtonFromNode(node);
                if (button != null) {
                    if (button.getId().contains(buttonEnemyClicked)) {
                        row_coordinate = i;
                        column_coordinate = j;
                        break;
                    }
                }
            }
        }
        // take hit
        int feedback = enemyBattlefield.receiveHit(row_coordinate +1, column_coordinate +1);
        enemyBattlefield.displayFullBattlefield();
        System.out.println(feedback);
        // change grid accordingly to hit
        adjustEnemyBoard(row_coordinate, column_coordinate, feedback == 1);
        // check it its the end
        if(isEndOfGame(enemyBattlefield))
        {
            stopTimer();
            // transfer data
            dataTransfer.setScore(getScore());
            dataTransfer.setGameplayTime(timeSeconds.get());
            dataTransfer.setNumberMisses(Integer.parseInt(miss_label_enemy.getText()));
            SceneManager winManager = new SceneManager(take_fire_button, "YouWon.fxml", "Menu.css");
            winManager.openNewSceneWithSlidingAnimation();
        }
        // enemy AI turn
        // get random shot
        Point ai_shot = basicAI.getRandomShot();
        int feedback2 = playerBattlefield.receiveHit(ai_shot.x, ai_shot.y );
        // adjust players board
        adjustPlayersBoard(ai_shot.x, ai_shot.y, feedback2 == 1);
        //check if its the end
        if (isEndOfGame(playerBattlefield))
        {
            stopTimer();
            System.out.println(getScore());
            SceneManager lossManager = new SceneManager(take_fire_button, "YouLost.fxml", "Menu.css");
            lossManager.openNewSceneWithSlidingAnimation();
        }
        //change destryed ship style
        changeStyleOfDestroyedShip();
        // change stats
        updateStats();
    }

    private void adjustEnemyBoard(int row, int column, boolean isHit) {
        Node node = getNodeFromGridPane(getEnemyGridPane(), column, row);
        Button button = getButtonFromNode(node);
        if (button != null) {
            if (button.getId().equals(buttonEnemyClicked) && isHit) {
                //change to hit
                button.setId(buttonEnemyHit);
                unblockOtherButtons();
                blockHitButton();
            } else if (button.getId().equals(buttonEnemyClicked) && !isHit) {
                // change style to miss
                button.setId(buttonEnemyMiss);
                unblockOtherButtons();
                blockHitButton();
            }
        }
    }
    private void adjustPlayersBoard(int row, int column, boolean isHit) {
        Node node = getNodeFromGridPane(getPlayerGridPane(), column, row);
        Rectangle rectangle = getRectangleFromNode(node);
        if (rectangle != null) {
            if (rectangle.getId().equals(rectangleShipCssID) && isHit) {
                //change to hit
                rectangle.setId(rectanglePlayerHit);
            } else {
                // change style to miss
                rectangle.setId(rectanglePlayerMiss);
            }
        }
    }

    private void unblockHitButton()
    {
        take_fire_button.setDisable(false);
    }
    private void blockHitButton()
    {
        take_fire_button.setDisable(true);
    }
    private boolean isEndOfGame(Battlefield battlefield)
    {
        long dead_ships = battlefield.getShipsArray().stream().filter(ship -> ship.getHealth() ==0).count();
        return dead_ships == 5;
    }
    private void changeStyleOfDestroyedShip()
    {
        // method to check destroyed ships and return their coordinates
        HashMap<String, ArrayList<Integer>> dead_coordinates = enemyBattlefield.getDestroyedShipsCoordinates();
        // then once the coordinates are retrieved change the style of existing hits to something different
        for (int i = 0; i < dead_coordinates.get("rows").size(); i++) {
            int row = dead_coordinates.get("rows").get(i);
            int column = dead_coordinates.get("columns").get(i);
            Node node = getNodeFromGridPane(getEnemyGridPane(), column -1 , row -1);
            Button button = getButtonFromNode(node);
            if (button != null) {
                button.setText("X");
                button.setStyle("-fx-font-size: 15px; -fx-alignment: CENTER;");
            }
        }
        // maybe add 'X' to those buttons
    }
    private void updateStats()
    {
        // health
        hp_label_player.setText(String.valueOf(getHealth(playerBattlefield)));
        hp_label_enemy.setText(String.valueOf(getHealth(enemyBattlefield)));
        // ships
        ships_label_player.setText(String.valueOf(getAliveShips(playerBattlefield)));
        ships_label_enemy.setText(String.valueOf(getAliveShips(enemyBattlefield)));
        // misses
        miss_label_player.setText(String.valueOf(getMisses(playerBattlefield)));
        miss_label_enemy.setText(String.valueOf(getMisses(enemyBattlefield)));
    }
    private int getHealth(Battlefield battlefield)
    {
        return battlefield.getShipsArray().stream().map(Ship::getHealth).reduce(Integer::sum).orElse(0);
    }
    private int getAliveShips(Battlefield battlefield)
    {
        return (int) battlefield.getShipsArray().stream().filter(ship -> ship.getHealth() != 0).count();
    }
    private int getMisses(Battlefield battlefield)
    {
        char[][] char_battlefield = battlefield.getBattlefield();
        int counter = 0;
        for (int i = 0; i < 11; i++) {
            for (int j = 0; j < 11; j++) {
                if(char_battlefield[i][j] == 'M')
                {
                    counter ++;
                }
            }
        }
        return counter;
    }

    public void goToInfoSection(ActionEvent actionEvent) throws IOException {
        if (infoStage == null)
        {
            SceneManager sceneManager = new SceneManager(info_button, "info.fxml", "Info.css");
            sceneManager.openNewSceneWithNewStage();
            infoStage = sceneManager.getStage();
        }
        else if (infoStage.isShowing()) {
            infoStage.toFront();
        } else {
            infoStage.show();
        }
    }
    public void initializeTimer()
    {
        isTimerInitialized = true;
        timer_label.textProperty().bind(Bindings.createStringBinding(() -> {
            long seconds = timeSeconds.get();
            long minutes = seconds / 60;
            seconds %= 60;
            return String.format("%02d:%02d", minutes, seconds);
        }, timeSeconds));
        startTimer();
    }

    private void updateTime() {
        // increment seconds
        int seconds = timeSeconds.get();
        timeSeconds.set(seconds+1);
    }

    private void startTimer() {
        timeline = new Timeline(new KeyFrame(Duration.seconds(1), evt -> updateTime()));
        timeline.setCycleCount(Animation.INDEFINITE); // repeat over and over again
        timeSeconds.set(STARTTIME);
        timeline.play();
    }
    private void stopTimer() {
        if (timeline != null) {
            timeline.stop();
        }
    }

    private int getScore() {
        int all_points = 10000;
        int misses = Integer.parseInt(miss_label_enemy.getText());
        int seconds_passed = timeSeconds.get();
        int final_score = all_points - misses * 10 - seconds_passed * 20;
        if (final_score < 0) {
            return 0;
        } else {
            return final_score;
        }
    }

    public void goBackToMenu(ActionEvent actionEvent) throws IOException {
        SceneManager sceneManager = new SceneManager(info_button, "MainMenu.fxml", "Menu.css");
        sceneManager.openNewScene();
    }

}