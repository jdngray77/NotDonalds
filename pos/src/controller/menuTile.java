package controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import sale.item.Item;
import sale.price.ShortPrice;
import util.GlobalConstants;

import java.io.IOException;

public final class menuTile extends Parent {

    /**
     * point of sale base fxml document location.
     */
    private static final String MENU_TILE_FXML = "../fxml/menuItemTile.fxml";

    /**
     * constant item representative of a null item.
     * TODO move to item.item
     */
    public static final Item NULL_ITEM = new Item("Super largely named menu item like christ does it ever end", new ShortPrice((short) 0, (short) 0, GlobalConstants.DEFAULT_CURRENCY_SYMBOL));

    /**
     * What menu item this tile represents
     */
    private Item item = NULL_ITEM;

    //#region FXML references
    @FXML
    private Label lblPrice;

    @FXML
    private Label lblName;
    //#endregion

    /**
     * Sets the item this tile represents, and updates the UI elements accordingly.
     * @param _item
     */
    public void setItem(Item _item){
        item = _item;
        lblPrice.setText(item.price().asDisplay());
        lblName.setText(item.name());
    }

    /**
     * Creates a new menuTile instance from FXML.
     *
     * - Creates loader
     *      - parses FXML (this.MENU_TILE_FXML)
     *      - creates an instance of controller.menuTile (No argument constructor)
     *      - creates UI elements from parsed FXML,
     *          - injecting elements with matching fx:id's into the controller.
     *      - registers event handlers
     *      - invokes initialise on the controller
     *      - returns UI hierarchy of the tile.
     * - Gets controller
     * - Sets item of representation
     *      - Updates UI elements accordingly
     * - returns resulting instance.
     *
     * @param _item the item this tile represents
     * @return A new instance of AnchorPane, following the FXML design for a menu tile that represents the provided item.
     * @throws IOException
     */
    public static AnchorPane create(Item _item) throws IOException {                                                    // This used to be a single line.
        FXMLLoader loader = new FXMLLoader(menuTile.class.getResource(MENU_TILE_FXML));                                 // I need to create an instance of this JUST to get the controller of the new pane. This should be statically available.
        AnchorPane ap = (AnchorPane) loader.load();                                                                     // create menu tile pane from FXMl
        menuTile controller = (menuTile) loader.getController();                                                        // Get controller to set the item the pane represents.
        controller.setItem(_item);                                                                                      // Set and render the item.
        return ap;                                                                                                      // Return newly created menu tile.
    }

    public void select(){
        new Alert(Alert.AlertType.CONFIRMATION, "It Works!", ButtonType.OK).showAndWait();
    }
}
