package controller;

import io.MenuHelper;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import sale.item.Item;

import java.io.IOException;

public final class menuTile extends FXMLController {

    /**
     * point of sale base fxml document location.
     */
    public static final String MENU_TILE_FXML = "./fxml/menuItemTile.fxml";

    /**
     * What menu item this tile represents
     */
    private Item item = Item.NULL_ITEM;

    //#region FXML references
    @FXML
    private Label lblPrice;

    @FXML
    private Label lblName;

    @FXML
    private ImageView itemImage;

    //#endregion

    /**
     * Creates a new FXML controller.
     */
    public menuTile() {
        super(MENU_TILE_FXML);
    }
    /**
     * Sets the item this tile represents, and the parent it belongs to; then updates the UI elements accordingly.
     * @param _item item this tile represents
     */
    public void setItem(Item _item){
        item = _item;
        lblPrice.setText(item.price().asDisplay());
        lblName.setText(item.name());
        itemImage.setImage(MenuHelper.getImage(item));
    }

    /**
     * Menu tile has been tapped on the POS window. Adds the item to the active order.
     */
    public void select(){
            posParent().addOrderItem(item);                                                                             // Add the item to the active order.
    }



    public static FXMLController create(FXMLController controller, Item _item) throws IOException {
        menuTile c = (menuTile) FXMLController.create(MENU_TILE_FXML, controller);
        c.setItem(_item);
        return c;
    }

}
