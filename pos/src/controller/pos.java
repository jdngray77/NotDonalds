package controller;

import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.TilePane;

import java.io.IOException;

public final class pos extends Parent {

    @FXML
    public TilePane menuTilePanel;

    public void test() {
        new Alert(Alert.AlertType.CONFIRMATION, "It Works!", ButtonType.OK).showAndWait();
    }

    public void addItem() throws IOException {
        menuTilePanel.getChildren().add(menuTile.create());
    }
}
