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

public class orderItem extends FXMLController {

    /**
     * FXML base for an order item
     */
    public static String ORDER_ITEM_FXML = "./fxml/orderItem.fxml";

    @FXML
    protected Label txtQuantity;

    @FXML
    protected Label txtPrice;

    @FXML
    protected Label txtName;

    @FXML
    protected ImageView itemImage;


    /**
     * The item that this order item represents.
     */
    private Item item;

    public Item getItem() {return item;}

    /**
     * Sets the item this order item represents.
     * @param _item
     */
    public void setItem(Item _item) {
        item = _item;
        render();
    }

    public void render(){
        txtName.setText(item.name());
        itemImage.setImage(MenuHelper.getImage(item));

        // ((pos) parentController).reRenderOrder();
        /*
         DO NOT RE-RENDER ORDER HERE.
         RE-RENDERING AN ORDER RE-RENDERS ALL ITEMS ON THE ORDER,
         CAUSING AN INFINITE LOOP.
         */
        reRender(); // To set price values, not actually re-render
    }

    public void reRender(){
        txtPrice.setText(item.price().asDisplay());
        txtQuantity.setText(String.valueOf(item.getQuantity()));
        posParent().reRenderSuborder();
        animateUpdate();
    }

    /**
     * Creates a new FXML controller.
     */
    public orderItem() {
        super(ORDER_ITEM_FXML);
    }


    /**
        Create a new orderItem from FXML.
     */
    public static orderItem create(FXMLController controller, Item _item) throws IOException {
        orderItem c = (orderItem) FXMLController.create(orderItem.ORDER_ITEM_FXML, controller);
        c.setItem(_item);
        return c;
    }

    public void inc(){
        item.addQuantity(1);
        render();
    }

    public void dec(){
        item.addQuantity(-1);

        if (item.getQuantity() < 1)
            remove();
        else
            render();
    }

    public void remove(){
        posParent().removeOrderItem(this);
    }

    public void readQuantity() {
        int input = NumericInput.promptNumericInput(item.getQuantity());
        if (input == NumericInput.ITEM_REMOVE_INDICATOR) {
            remove();
            return;
        } else {
            item.setQuantity(input);
        }

        reRender();
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
                anchorPane.setBackground(new Background(new BackgroundFill(vColor, CornerRadii.EMPTY, Insets.EMPTY)));  // Set background of order item pane with a new background containing the generated color, since the colour of an existing background cannot be modified.
            }
        };

        animation.play();
    }
}
