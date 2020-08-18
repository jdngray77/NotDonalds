package sale.item;

import sale.price.ShortPrice;

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
     * Price of the Item
     *
     * @apiNote Via abstraction, Short, Small or Long prices are compatable here.
     */
    private ShortPrice price;

    public Item(String _name, ShortPrice _price) {
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
    public ShortPrice Price() {
        return price;
    }
}
