package controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import sale.item.Item;

import java.io.IOException;

public class orderItem extends FXMLController {

    /**
     * FXML base for an order item
     */
    public static String ORDER_ITEM_FXML = "../fxml/orderItem.fxml";

    @FXML
    public Label txtQuantity;

    @FXML
    public Label txtPrice;

    @FXML
    public Label txtName;


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
        txtPrice.setText(item.price().asDisplay());
        txtQuantity.setText(String.valueOf(item.getQuantity()));
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
}
