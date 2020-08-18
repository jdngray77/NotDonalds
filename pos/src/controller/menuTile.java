package controller;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

public final class menuTile extends Parent {

    //public menuTile()


    /**
     * point of sale base fxml document location.
     */
    private static final String MENU_TILE_FXML = "../fxml/menuItemTile.fxml";

    /**
     * Creates a new menuTile instance from FXML.
     * @return
     * @throws IOException
     */
    public static AnchorPane create() throws IOException {
        return (AnchorPane) FXMLLoader.load(menuTile.class.getResource(MENU_TILE_FXML));
    }

    public void select(){
        new Alert(Alert.AlertType.CONFIRMATION, "It Works!", ButtonType.OK).showAndWait();
    }
}
