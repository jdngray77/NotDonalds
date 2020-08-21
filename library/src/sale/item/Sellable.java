package sale.item;

import sale.menu.MenuItem;
import sale.price.ShortPrice;

/**
 * Sellable menu item. It's assumed that items sold at this resturaunt will not exceed Short.Max.
 * @author Jordan Gray
 * @since 0.1.0
 * @version 1
 */
public abstract class Sellable extends MenuItem {

    public Sellable(String _name) {
        super(_name);
    }

    /**
     * @return Price instance of the Item
     * @apiNote Via abstraction, the instance returned may be Long, Short or Small (Byte) formatted prices.
     * @see sale.price
     */
    public ShortPrice price(){return null;}


}
