package controller;

import io.MenuHelper;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import sale.item.Item;

import java.io.IOException;

public class orderItem extends FXMLController {

    /**
     * FXML base for an order item
     */
    public static String ORDER_ITEM_FXML = "../fxml/orderItem.fxml";

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
    }

    /**
     * Creates a new FXML controller.
     */
    public orderItem() {
        super(ORDER_ITEM_FXML);
    }

    public void select() {
        if (item.getQuantity() > 1) {
            item.addQuantity(-1);
            render();
        } else
            ((pos) parentController).removeOrderItem(this);

        ((pos) parentController).reRenderOrder();
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
            ((pos) parentController).removeOrderItem(this);
        else
            render();
    }


}
