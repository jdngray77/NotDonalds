package sale.item;

import sale.price.IPrice;
import sale.price.ShortPrice;

/**
 * Sellable item. It's assumed that items sold at this resturaunt will not exceed Short.Max.
 */
abstract interface Sellable {

    /**
     * @return Price instance of the Item
     * @apiNote Via abstraction, the instance returned may be Long, Short or Small (Byte) formatted prices.
     * @see sale.price
     */
    public ShortPrice Price();
}
