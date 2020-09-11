package controller;

import io.MenuHelper;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import sale.item.Item;

import java.io.IOException;

/**
 * FXML controller for a Menu Tile,
 * Where a menu tile is a POS tile representing a menu item that
 * can be added to an order.
 *
 * @author gordie
 * @version 1
 */
public final class menuTile extends FXMLController {

    //#region constants
    /**
     * point of sale base fxml document location.
     */
    public static final String MENU_TILE_FXML = "./fxml/menuItemTile.fxml";
    //#endregion

    //#region fields
    /**
     * What menu item this tile represents
     */
    private Item item = Item.NULL_ITEM;
    //#endregion

    //#region FXML references
    /**
     * The text label that represents the total price for this menu item.
     */
    @FXML
    private Label lblPrice;

    /**
     * the text label that represents the name for this menu item.
     */
    @FXML
    private Label lblName;

    /**
     * the image that showcases this menu item.
     */
    @FXML
    private ImageView itemImage;
    //#endregion

    //#region constructor

    /**
     * Creates a new MenuTile controller.
     */
    public menuTile() {
        super(MENU_TILE_FXML);
    }
    //#endregion

    //#region methods
    /**
     * Sets the item this tile represents, and the parent it belongs to; then updates the UI elements accordingly.
     * @param _item item this tile represents
     */
    public menuTile setItem(Item _item){
        //#endregion
        item = _item;
        lblPrice.setText(item.price().asDisplay());
        lblName.setText(item.name());
        itemImage.setImage(MenuHelper.getImage(item));
        return this;
    }
    //#endregion

    //#region FXML events

    /**
     * Menu tile has been tapped on the POS window.
     * Adds the item to the active order.
     */
    public void select(){
            posParent().addOrderItem(item);                                                                             // Add the item to the active order.
    }
    //#endregion

    //#region static utilities

    /**
     * Creates a new MenuTile Controller populated with FXML,
     * Sets the item this tile represents,
     * Renders the item this represents.
     * @param parentController the controller that is the parent to this tile. Should be main pos controller.
     * @param _item The item this tile renders.
     * @return The new menu Tile, rendered and ready to go.
     * @throws IOException if the FXML failed to load.
     */
    public static FXMLController create(FXMLController parentController, Item _item) throws IOException {
        return ((menuTile)FXMLController.create(MENU_TILE_FXML, parentController)).setItem(_item);
    }
    //#endregion

}
