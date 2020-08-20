package controller;

import io.MenuHelper;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.TilePane;
import sale.item.Item;
import util.HaltCodes;
import util.RuntimeHelper;

import java.io.IOException;

public final class pos extends FXMLController {

    @FXML
    public TilePane menuTilePanel;

    @FXML
    public TilePane orderTileView;

    /**
     * point of sale base fxml document location.
     */
    public static final String POS_FXML = "./fxml/pos.fxml";

    /**
     * Creates a new FXML controller.
     */
    public pos() {
        super(POS_FXML);
    }

    public void test() {
        new Alert(Alert.AlertType.CONFIRMATION, "It Works!", ButtonType.OK).showAndWait();
    }

    /**
     * Adds a null menu item to the pos.
     * @throws IOException
     */
    public void addMenuItem() throws IOException {
        addMenuItem(Item.NULL_ITEM);
    }

    /**
     * Adds a specified menu item to the pos.
     * @param item
     * @throws IOException
     */
    public void addMenuItem(Item item) throws IOException {
        menuTile c = (menuTile) menuTile.create(this, item);
        menuTilePanel.getChildren().add(c.anchorPane);
    }

    public void renderMenu() throws IOException {
        for (Item item : MenuHelper.allItems(MenuHelper.menu))
            addMenuItem(item);
    }

    /**
     * User Close Request
     */
    public void close(){
        RuntimeHelper.halt(HaltCodes.INTENDED_HALT);
    }
}
