package com.example.battleshipsfx;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class StartApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(StartApplication.class.getResource("MainMenu.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("Menu.css")).toExternalForm());
        stage.setTitle("BattleshipsFX");

        Image image = new Image(getClass().getResource("img/black_ship.png").toExternalForm());
        stage.getIcons().add(image);
        stage.setScene(scene);
        stage.setResizable(false); // Lock the size of the screen
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}