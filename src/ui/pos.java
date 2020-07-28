package ui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class pos extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        Parent rootfxml = FXMLLoader.load(getClass().getResource("pos.fxml"));

        primaryStage.setTitle("NotDonalds");
        primaryStage.setScene(new Scene(rootfxml, 1920, 1080));
        primaryStage.show();
    }
}
