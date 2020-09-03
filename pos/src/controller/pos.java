package controller;

import io.MenuHelper;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.TilePane;
import network.Client;
import network.packet.Packet;
import sale.Order;
import sale.item.Item;
import util.HaltCodes;
import util.RuntimeHelper;
import util.packettest.tester;

import java.io.IOException;

/**
 * General controller for the front end pos window.
 *
 * Contains and manages pos order. Handles FXML user events from the pos window.
 * DOES NOT handle menu tile or order item tile events.
 */
public final class pos extends FXMLController {

    //#region constants
    /**
     * Relative location of FXML document containing the definition for the POS window UI.
     */
    public static final String POS_FXML = "./fxml/pos.fxml";

    /**
     * Null order item pane controller.
     *
     * Concidered default for all order items. Represents an item.null_item.
     *
     * Not static since order items are children to this FXMLcontroller instance.
     */
    public final orderItem NULL_ORDER_ITEM = (orderItem) orderItem.create(this, Item.NULL_ITEM);
    //#endregion

    //#region FXML region
    /**
     * Tile Panel containing all menu items that may be added to an order.
     */
    @FXML
    protected TilePane menuTilePanel;

    /**
     * Tile view that represents the current order.
     */
    @FXML
    protected TilePane orderTileView;

    /**
     * Label that represents the current order's total price.
     */
    @FXML
    protected Label txtOrderTotal;
    //#endregion

    //#region fields
    /**
     * The order that this POS window is currently viewing / compiling / editing.
     */
    private Order activeOrder = new Order();

    /**
     * The last item added to the active order.
     * TODO should be a part of the Order object
     */
    public orderItem lastItem = NULL_ORDER_ITEM;
    //#endregion fields

    //#region construction
    /**
     * Creates a new FXML controller.
     */
    public pos() throws IOException {
        super(POS_FXML);
    }
    //#endregion construction

    //#region FXML events
    /**
     * User Close Request
     * Halts the runtime as an intended request.
     */
    public void close(){
        RuntimeHelper.halt(HaltCodes.INTENDED_HALT);
    }


    public void launchPacketTool(ActionEvent actionEvent)  {
        new tester();
    }


    public void ping(ActionEvent actionEvent) {
        try {
            RuntimeHelper.alert(
                    Client.sendToServer(Packet.PING).metaMessage
            );
        } catch (IOException e) {
            RuntimeHelper.alertFailiure("Failed to ping server!", e);
        }

    }
    //#endregion FXML events

    //#region order methods
    /**
     * Adds a new item to the current order.
     * @param item
     */
    public void addOrderItem(Item item) {
        if (isLastItem(item)){
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
        reRenderSuborder();
    }

    public void removeOrderItem(orderItem orderItem){
        removeOrderItem(orderItem.getItem());
        orderTileView.getChildren().remove(orderItem.anchorPane);
        reRenderSuborder();
    }

    public void removeOrderItem(Item item) {
        activeOrder.items.remove(item);
        if (isLastItem(item)) clearLastItem();
        reRenderSuborder();
    }

    //#endregion order methods

    //#region menu methods
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


    //#endregion menu methods

    //#region rendering

    public void renderMenu() {
        menuTilePanel.getChildren().clear();                                                                            // Remove all items displayed
        if (MenuHelper.menu == null) return;                                                                            // Don't try to render if there is no menu. Clear items and return.
        for (Item item : MenuHelper.allItems(MenuHelper.menu))                                                          // for each menu item,
            try {
                addMenuItem(item);                                                                                      // add it to the menu tile pane
            } catch(IOException e) {
                RuntimeHelper.alertFailiure("Failed to add menu item '" + item.getName() + "'", e);
            }

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

        reRenderSuborder();
    }

    public void reRenderSuborder() {
        assert txtOrderTotal != null;
        if (txtOrderTotal == null) return;
        txtOrderTotal.setText(activeOrder.price().asDisplay());
    }

    //#endregion rendering

    //#region utility methods
    protected boolean isLastItem(Item item) {
        return item.name() == lastItem.getItem().name();
    }

    /**
     * Clears current 'last item' reference.
     *
     * If there's no more items in the order, it's set to a null item,
     * otherwise it's set the the current last item in the order list.
     */
    private void clearLastItem() {
        // REDACTED: CANNOT GET ORDERITEM CONTROLLER FOR ORDER ITEM FROM THE ANCHOR PANE, OR ITEM IN ORDER.
        //if (activeOrder.items.size() > 0)
        //    lastItem = orderTileView.getChildren().get(orderTileView.getChildren().size() - 1);
        //else
            lastItem = NULL_ORDER_ITEM;
    }

    //TODO when menu has changed, check order is still compatible with menu. i.e missing items, not enough stock etc.
    public void reloadMenu() {
        MenuHelper.clearMenu();                                                                                         // Clear directly, this.clearmenu adds an unnescacerry re-render
        try {
            MenuHelper.loadMenuFromServer();                                                                            // Load menu from server
            RuntimeHelper.alert("Menu up to date with server!");
        } catch (IOException e) {
            RuntimeHelper.alertFailiure("Failed to reload menu. POS now has no menu!", e);
        } finally {
            renderMenu();                                                                                               // Render result.
        }
    }

    public void clearMenu() {
        MenuHelper.clearMenu();                                                                                         // Clear menu
        renderMenu();                                                                                                   // render
    }
    //#endregion utility methods


}
