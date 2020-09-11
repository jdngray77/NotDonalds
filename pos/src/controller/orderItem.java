package controller;

import io.MenuHelper;
import javafx.animation.Animation;
import javafx.animation.Interpolator;
import javafx.animation.Transition;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;
import javafx.util.Duration;
import sale.item.Item;

import java.io.IOException;

/**
 * FXML Controller for order items.
 *
 * @author gordie
 * @version 1
 */
public class orderItem extends FXMLController {

    //#region constants
    /**
     * FXML base for an order item
     */
    public static final String ORDER_ITEM_FXML = "./fxml/orderItem.fxml";
    //#endregion

    //#region FXML objects
    /**
     * text label representing the quantity of this item
     */
    @FXML
    protected Label txtQuantity;

    /**
     * text label representing price of this item
     */
    @FXML
    protected Label txtPrice;

    /**
     * text label representing name of this item
     */
    @FXML
    protected Label txtName;

    /**
     * Image representing this item.
     */
    @FXML
    protected ImageView itemImage;
    //#endregion

    //#region properties
    /**
     * The item that this order item represents.
     */
    private Item item;

    //#endregion

    //#region constructor

    /**
     * Creates a new FXML controller.
     */
    public orderItem() {
        super(ORDER_ITEM_FXML);
    }

    //#endregion

    //#region getter / setter
    /**
     * @return the item this represents.
     */
    public Item getItem() {return item;}

    /**
     * Sets the item this order item represents,
     * then re-renders to display it.
     * @param _item
     * @return self, for neater code when setting item upon creation
     */
    public orderItem setItem(Item _item) {
        item = _item;
        render();
        return this;
    }
    //#endregion

    //#region rendering

    /**
     * Renders the item this represents.
     */
    public void render(){
        txtName.setText(item.name());
        itemImage.setImage(MenuHelper.getImage(item));

        /*
         REDACTED:

         DO NOT RE-RENDER ORDER HERE.
         RE-RENDERING AN ORDER RE-RENDERS ALL ITEMS ON THE ORDER,
         CAUSING AN INFINITE LOOP.
         */

        // posParentController().reRenderOrder();
        reRender(); // To set price values, not actually re-render
    }

    /**
     * Updates value fields to match the stored item,
     * then animates a background flash to bring attention to the change.
     */
    public void reRender(){
        txtPrice.setText(item.price().asDisplay());
        txtQuantity.setText(String.valueOf(item.getQuantity()));
        posParent().reRenderSuborder();                                                                                 // Updates the order meta, i.e order total, item count.
        animateUpdate();                                                                                                // Trigger background animation.
    }
    //#endregion


    //#region FXML events

    /**
     * User has requested to increment the quantity of item in thier order.
     *
     * Requests to increase quantity by 1,
     * then re-renders self.
     * @see Item#addQuantity
     */
    @FXML
    public void inc(){
        item.addQuantity(1);
        render();
    }

    /**
     * User has requested to increment the quantity of item in their order.
     *
     * Requests to decrease quantity by 1.
     *
     * If the
     * then re-renders self.
     * @see Item#addQuantity
     */
    @FXML
    public void dec(){
        item.addQuantity(-1);                                                                                         // Request decrement

        if (item.getQuantity() < Item.MIN_QUANTITY)                                                                      // If below min,
            remove();                                                                                                    // remove item from order.
        else
            render();                                                                                                    // Otherwise continue; render. No point calling for a render if this has been removed.
    }


    /**
     * User has tapped the quantity,
     * prompt for numeric input to set quantity with.
     *
     * If user confirms a remove item request, this this item
     * is removed from the parenting order.
     */
    @FXML
    public void readQuantity() {
        int input = NumericInput.promptNumericInput(item.getQuantity());                                                //         Open a numeric input prompt, and store the result locally.
        if (input == NumericInput.ITEM_REMOVE_INDICATOR)                                                                // IF:     If the result was a remove request,
            remove();                                                                                                   //         Remove this item from the order
         else                                                                                                           // ELSE:   The result was a numeric,
            item.setQuantity(input);                                                                                    //         Request to set the item quantity to the result.
        reRender();                                                                                                     // FINALLY:Render the result of this event.
    }

    //#endregion

    //#region methods

    /**
     * Removes this item from the containing order.
     * Does not destroy FXML elements, or instance;
     * thus it's possible to recycle or re-add instances.
     */
    public void remove(){
        posParent().removeOrderItem(this);
    }

    /**
     * Animates a background flash, if enabled.
     *
     * Intended to bring focus to an order item when it's updated / changed.
     * Called on a re-render event, since updates trigger a re-render of order items.
     */
    private void animateUpdate(){
        if (!posParent().flashOrderItem) return;                                                                        // Don't flash if not enabled.

        final Animation animation = new Transition() {                                                                  // Define animation to play.
            // Local Final, only created the first time this is called.
            // Local, not class level - only way to get access to super.anchorPane.
            // Simplified constructor
            {
                setCycleDuration(Duration.millis(500));                                                                 // 0.5 sec
                setInterpolator(Interpolator.EASE_OUT);                                                                 // Animation type. Sets how the below method is called, starting at max and reducing to null over the time specified above.
            }

            @Override
            protected void interpolate(double frac) {
                Color vColor = new Color(0.5, 0.5, 0.5, 1 - frac);                              // Color to use, with opacity based on current point in animation.
                getAnchorPane().setBackground(new Background(new BackgroundFill(vColor, CornerRadii.EMPTY, Insets.EMPTY)));  // Set background of order item pane with a new background containing the generated color, since the colour of an existing background cannot be modified.
            }
        };

        animation.play();                                                                                               // Start animation; let FXML handle.
    }
    //#endregion

    //#region static utility
    /**
     * Create a new orderItem populated from FXML, pre rendered to represent an item.
     */
    public static orderItem create(FXMLController controller, Item _item) throws IOException {
        return ((orderItem) FXMLController.create(orderItem.ORDER_ITEM_FXML, controller)).setItem(_item);
    }
    //#endregion
}
