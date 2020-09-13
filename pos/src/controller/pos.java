package controller;

import fxml.FXMLController;
import io.MenuHelper;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.CheckMenuItem;
import javafx.scene.control.Label;
import javafx.scene.layout.TilePane;
import network.Client;
import network.packet.Packet;
import sale.Order;
import sale.item.Item;
import util.HaltCodes;
import util.RuntimeHelper;
import util.currency.multiplication;
import util.packettest.tester;

import java.io.IOException;

/**
 * General controller for the front end pos window.
 *
 * Contains and manages pos order. Handles FXML user events from the pos window.
 * DOES NOT handle menu tile or order item tile events.
 * @see menuTile
 * @see orderItem
 * @author gordie
 * @version 1.0.2
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
     * Considered default for all order items. Represents an item.null_item.
     *
     * Not static since order items are children to this FXMLController instance.
     */
    public final orderItem NULL_ORDER_ITEM = orderItem.create(this, Item.NULL_ITEM);

    /**
     * Determines if the background of order items on this pos are to flash when they are updated.
     */
    protected boolean flashOrderItem = true;
    //#endregion

    //#region FXML elements
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

    /**
     * Label that represents the number of items in the current order.
     */
    @FXML
    protected Label txtOrderItems;

    /**
     * Label that represents the number of meals in the current order
     */
    @FXML
    protected Label txtOrderMeals;


    /**
     * Debug menu item that can disable render flashing on order items.
     */
    @FXML
    protected CheckMenuItem chkOrderFlash;


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

    /**
     * Heedlessly launches a new packet tester tool.
     */
    public void launchPacketTool()  {
        new tester();
    }

    /**
     * Attempts to ping the connected server, and displays the
     * returned message in an alert.
     *
     * Failures are notified as a fail alert.
     */
    public void ping() {
        try {                                                                                                           // TRY: Connection may fail.
            RuntimeHelper.alert(                                                                                        //      Display the result of;
                    Client.sendToServer(Packet.PING).metaMessage                                                        //      Send a ping to the server, and get the message contained in the response.
            );
        } catch (IOException e) {                                                                                       // CATCH: Ping failed
            RuntimeHelper.alertFailiure("Failed to ping server!", e);                                                //        Use utility to alert and log failure.
        }

    }
    //#endregion FXML events

    //#region order methods
    /**
     * Adds a new item to the current order.
     * @param item
     */
    public void addOrderItem(Item item) {
        // SAME ITEM AS WAS LAST ADDED
        if (isLastItem(item)){                                                                                          // IF:      the same item is being added multiple times,
            lastItem.getItem().addQuantity();                                                                           //          add it to the existing item,
            lastItem.reRender();                                                                                        //          then update ui.
            return;                                                                                                     // RETURN:  Request handled, don't continue.
        }

        // NEW ITEM
        try {                                                                                                           // TRY:     UI Creation can fail
            lastItem = orderItem.create(this, item.clone());                                                   //          Create a new order item, and store it as new newest item added to the order.
                                                                                                                        // TRY CONDITIONAL: calling upon failure is redundant.
            activeOrder.items.add(lastItem.getItem());                                                                  //          Add the new item to the active order.
            orderTileView.getChildren().add(lastItem.getAnchorPane());                                                  //          Add order item tile to UI to display the new item to the user.
            reRenderSuborder();                                                                                         //          re-render order meta data (order total price, etc.)
        } catch (IOException e) {                                                                                       // CATCH:   Notify of failure to create ui. Don't retry.
            RuntimeHelper.alertFailiure("Failed to add item to order", e);
        }
    }

    /**
     * Removes matching order item instance from the active order.
     * Clears this#lastItem if necessary
     *
     * Call is ignored with no effect if there is no matching item.
     * @return true of item was found and removed. false if there was no match.
     * @param orderItem item to find and remove.
     * @apiNote THIS IS THE ONLY REMOVE CALL THAT WILL REMOVE UI ORDER ITEM ELEMENTS!
     */
    public boolean removeOrderItem(orderItem orderItem){
        if (!removeOrderItem(orderItem.getItem()))                                                                      //          Attempt to remove item from order.
            return false;                                                                                               // RETURN:  if item is not found on the active order.
        orderTileView.getChildren().remove(orderItem.getAnchorPane());                                                  //          otherwise, remove the order item from the ui.
        reRenderSuborder();                                                                                             //          re-render order meta data to match the updated order.
        return true;                                                                                                    // RETURN:  Matching item existed and was removed.
    }

    /**
     * Removes matching item instance from the active order.
     * Clears last item register if param was last item added to order.
     *
     * @param item item to remove.
     * @return true of item was found and removed. false if there was no match.
     * @apiNote ONLY REMOVES ITEM DATA FROM ACTIVE ORDER. HAS NO AFFECT ON REMOVING UI ELEMENTS!
     * @see pos#removeOrderItem(orderItem)
     */
    public boolean removeOrderItem(Item item) {
        boolean exists = activeOrder.items.remove(item);                                                                //          Attempt to remove item data from order. Store result.
        if (!exists) return exists;                                                                                     // RETURN:  FALSE; item did not exist. Call ignored.
        if (isLastItem(item)) clearLastItem();                                                                          //          Clear last item register if param is last item added to order.
        reRenderSuborder();                                                                                             //          re-render order meta data to match the updated order.
        return exists;                                                                                                  // RETURN:  TRUE; item existed and was removed.
    }
    //#endregion order methods

    //#region menu methods
    /**
     * Adds a null menu item to the pos.
     * Called by user from UI debug menu.
     * @throws IOException if item could not be created and added.
     */
    @FXML
    public void addMenuItem() throws IOException {
        addMenuItem(Item.NULL_ITEM);
    }

    /**
     * Creates an FXML menuTile which represents a specified menu item,
     * and adds it to the menu tile view.
     * @param item Item to add to the menu view.
     * @throws IOException if item could not be created and added.
     */
    public void addMenuItem(Item item) throws IOException {
        menuTile c = (menuTile) menuTile.create(this, item);
        menuTilePanel.getChildren().add(c.getAnchorPane());
    }
    //#endregion menu methods

    //#region rendering

    /**
     * Renders the menu currently loaded on this client from fresh.
     *
     * Clears all menuTile FXML elements from pos#menuTilePanel,
     * then recursively creates, populates and adds menu tiles for every
     * item found on the loaded menu.
     */
    public void renderMenu() {
        boolean failFlag = false;                                                                                       //          flag used to minimise errors displayed. No matter how many elements fail, only one alert is shown.
        menuTilePanel.getChildren().clear();                                                                            //          Remove all items displayed
        if (MenuHelper.menu == null) return;                                                                            //          Don't try to render if there is no menu. Clear items and return.
        for (Item item : MenuHelper.allItems(MenuHelper.menu))                                                          // FOR:     each menu item,
            try {                                                                                                       // TRY:     Failure to create FXML ui
                addMenuItem(item);                                                                                      //          add it to the menu tile pane
            } catch(IOException e) {                                                                                    // CATCH:   FXML failure,
                failFlag = true;                                                                                        //          flag failure
                RuntimeHelper.log(this, "Failed to add menu item '" + item.getName() + "'" + e.getMessage());//      Log failure for easier menu debugging.
            }

        if (failFlag)                                                                                                   // IF:      Any items failed,
            RuntimeHelper.alertFailiure("Failed to fully populate menu! Refer to logs for more details.");           //          Declare to user.
    }

    /**
     * Reloads the menu from the server, then re-renders.
     */
    //TODO when menu has changed, check order is still compatible with menu. i.e missing items, not enough stock etc.
    @FXML
    public void reloadMenu() {
        MenuHelper.clearMenu();                                                                                         //           Clear directly, this#clearmenu adds an unnecessary re-render
        try {                                                                                                           // TRY:      Failure to connect to the server.
            MenuHelper.loadMenuFromServer();                                                                            //           Load menu from server
            RuntimeHelper.alert("Menu loaded from server!");
        } catch (IOException e) {                                                                                       // CATCH:    failure to render a get menu from server.
            RuntimeHelper.alertFailiure("Failed to reload menu. POS now has no menu!", e);
        } finally {
            renderMenu();                                                                                               //           Render result.
        }
    }

    /**
     * Re-renders the entire order panel from fresh to
     * match the active order, then
     * triggers sub-order to re-render.
     *
     * Clears all orderItems from the UI,
     * then creates, populates and renders new orderItems
     * for every item on the active order.
     *
     * @apiNote This is NOT an efficient means of updating orders regularly,
     *          but for one-off events it ensures that what's displayed matches
     *          order data.
     */
    public void reRenderOrder() {
        assert orderTileView != null;                                                                                   // DBG ASSERT:  Cannot manipulate order tiles if there is no order tile view.
        if (orderTileView == null) return;                                                                              // RT ASSERT    Ignores calls to prevent exceptions if renders are called for before the ux is created.
        orderTileView.getChildren().clear();                                                                            //              Clear all order items
        for (Item item : activeOrder.items) {                                                                           // FOR:         every item on the active order,
            try {                                                                                                       // TRY:         FXML failure
                orderTileView.getChildren().add(orderItem.create(this, item).getAnchorPane());                 //              Create a new order item representing the current item, and add it's content pane to the view.
            } catch (IOException e) {                                                                                   // CATCH:       FXML failure
                RuntimeHelper.logException(this, "[WARN] Failed to render order item " + item, e);                      //              Log, don't react.
            }
        }
        reRenderSuborder();
    }

    /**
     * Re-renders the sub-order panel with
     * order meta data, including order price,
     * number of items, and number of meals.
     */
    public void reRenderSuborder() {
    assert txtOrderTotal != null;                                                                                       // DBG ASSERT:  Cannot manipulate sub order if not ux is not created yet. one label should indicate the presence of all.
        if (txtOrderTotal == null) return;                                                                              // RT ASSERT:   ignores calls to prevent null pointers.
        txtOrderTotal.setText(activeOrder.price().asDisplay());                                                         //              Calculate and display price of active order.
        txtOrderItems.setText(String.valueOf(activeOrder.items.size()));
    }
    //#endregion rendering

    //#region utility methods

    /**
     * Determines if <c>param</c> is the same as the last item that was added the order.
     * @param item Item to test
     * @return True if matches the last item added to the active order.
     */
    public boolean isLastItem(Item item) {
            return item.name() == lastItem.getItem().name();
    }

    /**
     * Clears current 'last item' reference.
     *
     * If there's no more items in the order, it's set to a null item,
     * otherwise it's set the the current last item in the order list.
     */
    @FXML
    private void clearLastItem() {
        // REDACTED: CANNOT GET ORDERITEM CONTROLLER FOR ORDER ITEM FROM THE ANCHOR PANE, OR ITEM IN ORDER.
        //if (activeOrder.items.size() > 0)
        //    lastItem = orderTileView.getChildren().get(orderTileView.getChildren().size() - 1);
        //else
            lastItem = NULL_ORDER_ITEM;
    }

    /**
     * Removes locally stored menu, then re-renders.
     */
    @FXML
    public void clearMenu() {
        MenuHelper.clearMenu();                                                                                         // Clear menu
        renderMenu();                                                                                                   // render
    }

    /**
     * JFX event. Launches a new price multiplication debug tool.
     */
    @FXML
    public void pricetest() {
         new multiplication();
    }

    /**
     * JFX event. Sets flag for 'order items flash on update', according to the user's preference.
     */
    @FXML
    public void setOrderFlash() {
        flashOrderItem = chkOrderFlash.isSelected();
    }

    /**
     * Easter egg for anyone who clicks the banner on the debug menu.
     * Non-essential gimmick.
     */
    @FXML
    private void lmo() {
        new Alert(Alert.AlertType.WARNING, "Hold up, how did you get here?? Who are you? This is a banner, not a button! It wasn't even supposed to be clicked! " +
                "I mean, what did you really expect, Some kind of easter egg? Life altering advice? A new career? Listen here you mischievous little shit," +
                " This menu is for people who actually know what they're doing, so why don't you pop off, pettle.")
                .showAndWait();
    }
    //#endregion utility methods


}
