package controller;

import io.MenuHelper;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.TilePane;
import sale.Order;
import sale.item.Item;
import util.HaltCodes;
import util.RuntimeHelper;

import java.io.IOException;

public final class pos extends FXMLController {

    @FXML
    protected TilePane menuTilePanel;

    @FXML
    protected TilePane orderTileView;

    @FXML
    protected Label txtOrderTotal;

    private Order activeOrder = new Order();

    /**
     * point of sale base fxml document location.
     */
    public static final String POS_FXML = "./fxml/pos.fxml";

    public orderItem lastItem = (orderItem) orderItem.create(this, Item.NULL_ITEM);

    /**
     * Creates a new FXML controller.
     */
    public pos() throws IOException { super(POS_FXML); }



    /**
     * Adds a new item to the current order.
     * @param item
     */
    public void addOrderItem(Item item) {
        if (item.name() == lastItem.getItem().name()){
            lastItem.getItem().addQuantity();
            lastItem.reRender();
            return;
        }

        try {
            lastItem = orderItem.create(this, item.clone());
            orderTileView.getChildren().add(lastItem.anchorPane);
        } catch (IOException e) {
            e.printStackTrace();
            RuntimeHelper.alertFailiure("Failed to add item to order (" + e.getMessage() + ")");
        }
        activeOrder.items.add(lastItem.getItem());
    }

    public void removeOrderItem(orderItem orderItem){
        removeOrderItem(orderItem.getItem());
        orderTileView.getChildren().remove(orderItem.anchorPane);
    }

    public void removeOrderItem(Item item){
        activeOrder.items.remove(item);
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
        menuTilePanel.getChildren().clear();                                                                            // Remove all items displayed
        for (Item item : MenuHelper.allItems(MenuHelper.menu))                                                          // for each menu item,
            addMenuItem(item);                                                                                          // add it to the menu tile pane
    }

    public void reRenderOrder() {
        assert orderTileView != null;
        if (orderTileView == null) return;                                                                              // If there is no tile view to render, don't try to render it. This is called before the window is even created, when the NULL_ITEM is initialised - causing null pointers at startup.
        orderTileView.getChildren().clear();
        for (Item item : activeOrder.items) {
            try {
                orderTileView.getChildren().add(orderItem.create(this, item).anchorPane);
            } catch (IOException e) {
                RuntimeHelper.log(this, "[WARN] Failed to render order item " + item + " (" + e.getMessage() + ")");
            }
        }

        txtOrderTotal.setText(activeOrder.price().asDisplay());
    }

    /**
     * User Close Request
     *
     * Halts the runtime as an intended request.
     */
    public void close(){
        RuntimeHelper.halt(HaltCodes.INTENDED_HALT);
    }

    public void launchPacketTool(ActionEvent actionEvent) {

    }

    public void ping(ActionEvent actionEvent) {

    }
}
