package controller;

import io.MenuHelper;
import javafx.fxml.FXML;
import javafx.scene.layout.TilePane;
import sale.Order;
import sale.item.Item;
import util.HaltCodes;
import util.RuntimeHelper;

import java.io.IOException;

public final class pos extends FXMLController {

    @FXML
    public TilePane menuTilePanel;

    @FXML
    public TilePane orderTileView;

    private Order activeOrder = new Order();

    /**
     * point of sale base fxml document location.
     */
    public static final String POS_FXML = "./fxml/pos.fxml";

    /**
     * Creates a new FXML controller.
     */
    public pos() { super(POS_FXML); }

    /**
     * Adds a null menu item to the pos.
     * @throws IOException
     */
    public void addMenuItem() throws IOException {
        addMenuItem(Item.NULL_ITEM);
    }

    /**
     * Adds a new item to the current order.
     * @param item
     */
    public void addOrderItem(Item item){
        try {
            orderTileView.getChildren().add(orderItem.create(this, item).anchorPane);
        } catch (IOException e) {
            e.printStackTrace();
            RuntimeHelper.alertFailiure("Failed to add item to order (" + e.getMessage() + ")");
        }
        activeOrder.items.add(item);
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
        menuTilePanel.getChildren().clear();                                                                            // Remove all items displayed
        for (Item item : MenuHelper.allItems(MenuHelper.menu))                                                          // for each menu item,
            addMenuItem(item);                                                                                          // add it to the menu tile pane
    }

    /**
     * User Close Request
     *
     * Halts the runtime as an intended request.
     */
    public void close(){
        RuntimeHelper.halt(HaltCodes.INTENDED_HALT);
    }
}
