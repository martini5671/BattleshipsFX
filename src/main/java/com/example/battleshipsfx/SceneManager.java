package com.example.battleshipsfx;

import javafx.animation.*;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.util.Objects;

public class SceneManager {
     private Image image = new Image(getClass().getResource("img/black_ship.png").toExternalForm());
    private AnchorPane anchor;
    private Stage stage;
    private Scene scene;

    private String urlFXML;
    private String urlCssStyle;

    private Button button;

    public void setScene(Scene scene) {
        this.scene = scene;
    }

    public void setUrlFXML(String urlFXML) {
        this.urlFXML = urlFXML;
    }

    public void setUrlCssStyle(String urlCssStyle) {
        this.urlCssStyle = urlCssStyle;
    }

    public SceneManager (Button button, String UrlFXML, String UrlCssStyle)
    {
        this.button = button;
        this.urlFXML = UrlFXML;
        this.urlCssStyle = UrlCssStyle;
        this.anchor = getAnchorPane();
    }

    private AnchorPane getAnchorPane() {
        Parent highestParent = button.getParent();
        while (highestParent.getParent() != null) {
            highestParent = highestParent.getParent();
        }
        return (AnchorPane) highestParent;
    }

    public SceneManager (Button button, String urlFXML)
    {
        this.button = button;
        this.urlFXML = urlFXML;
        this.anchor = getAnchorPane();
    }
    public void openNewSceneWithNewStage() throws IOException {
            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource(urlFXML)));
            if (urlCssStyle != null) {
                root.getStylesheets().add(Objects.requireNonNull(getClass().getResource(urlCssStyle)).toExternalForm());
            }
            scene = new Scene(root);
            stage = new Stage();
            stage.getIcons().add(image);
            stage.setTitle("BattleshipsFX");
            stage.setResizable(false); // Lock the size of the screen
            stage.setScene(scene);
            stage.show();
    }

    public void openNewScene() throws IOException {
        if(button == null || urlFXML == null) {
            throw new IOException("Missing required parameters: button, FXML url");
        }

        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource(urlFXML)));
        stage = (Stage) button.getScene().getWindow();
        stage.getIcons().add(image);
        stage.setTitle("BattleshipsFX");
        scene = new Scene(root);
        if(urlCssStyle != null) {
            scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource(urlCssStyle)).toExternalForm());
        }
        stage.setResizable(false); // Lock the size of the screen
        stage.setScene(scene);
        stage.show();
    }
    public void openNewSceneWithSlidingAnimation() throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource(urlFXML)));
        Scene scene = button.getScene();

        //Set Y of second scene to Height of window
        root.translateYProperty().set(scene.getHeight());
        //add styling
        if (urlCssStyle != null) {
            root.getStylesheets().add(Objects.requireNonNull(getClass().getResource(urlCssStyle)).toExternalForm());
        }
        anchor.getChildren().add(root);

        //Create new TimeLine animation
        Timeline timeline = new Timeline();
        //Animate Y property
        KeyValue kv = new KeyValue(root.translateYProperty(), 0, Interpolator.EASE_IN);
        KeyFrame kf = new KeyFrame(Duration.seconds(1), kv);
        timeline.getKeyFrames().add(kf);
        timeline.play();

    }


    public Stage getStage() {
        return stage;
    }
}