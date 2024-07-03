module com.example.battleshipsfx {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;
    requires animatefx;
    requires mongo.java.driver;


    opens com.example.battleshipsfx to javafx.fxml;
    exports com.example.battleshipsfx;
    exports BattleshipUtils;
    opens BattleshipUtils to javafx.fxml;
}