package sale.item;

import sale.price.ShortPrice;
import util.RuntimeHelper;

/**
 * Single Menu Item.
 *
 * Stores the name and price of a single menu item.
 *
 * @since 0.1.0
 * @version 1
 * @author Jordan Gray
 */
public class Item extends Sellable {

    /**
     * Name of the Item
     */
    private String name;

    /**
     * Quantity of this item.
     *
     * Used in ordering, i.e buy 5 of an item.
     */
    private byte quantity = 1;

    /**
     * Global constant. Representation of a null or invalid item.
     *
     * Displays as "INVALID", with a price of '0.0!'
     */
    public static final Item NULL_ITEM = new Item("INVALID", new ShortPrice((short) 0, (short) 0, '!', true));

    /**
     * Price of the Item
     *
     * @apiNote Via abstraction, Short, Small or Long prices are compatable here.
     */
    private ShortPrice price;

    public Item(String _name, ShortPrice _price) {
        super(_name);
        name = _name;
        price = _price;
    }

    /**
     * @return name of the Item
     */
    public String name() {
        return name;
    }

    /**
     * @return Price instance of the Item
     *
     * @apiNote Via abstraction, the instance returned may be Long, Short or Small (Byte) formatted prices.
     * @see sale.price
     */
    @Override
    public ShortPrice price() {
        return price.times(quantity);
    }

    @Override
    public Item clone(){
        return new Item(this.name, this.price);
    }

    public void addQuantity(){
        addQuantity(1);
    }

    public void addQuantity(int i) {
        if (quantity + i > 255 || quantity + i < 0) {
            RuntimeHelper.alertFailiure("Item quantity must be between 0 - 255");
        } else {
            quantity += i;
        }
    }

    public byte getQuantity() {
        return quantity;
    }
}
