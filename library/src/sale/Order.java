package sale;

import sale.item.Sellable;
import sale.item.SellableCollection;
import sale.price.ShortPrice;
import util.GlobalConstants;

/**
 * A collection of references to sellable items for purchase.
 *
 * @inheritDoc
 * @author Jordan Gray
 * @since 0.1.0
 * @version 1
 */
public class Order extends SellableCollection<Sellable> {

    public ShortPrice total(){
        ShortPrice total = new ShortPrice((short) 0, (short) 0, GlobalConstants.DEFAULT_CURRENCY_SYMBOL, GlobalConstants.DEFAULT_RTL);
        for (Sellable item : super.items){
            total.add(item.price().getDecimal(), item.price().getFractional());
        }

        return total;
    }

}
