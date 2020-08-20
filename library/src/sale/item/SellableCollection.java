package sale.item;

import sale.price.ShortPrice;
import util.GlobalConstants;

import java.util.ArrayList;
import java.util.List;

/**
 * A collection of single menu items, conciliated into a single sellable object, with a collective price
 * @author Jordan Gray
 * @since 0.1.0
 * @version 1
 */
public class SellableCollection<T> extends Sellable {

    public List<Item> items = new ArrayList<Item>();

    public SellableCollection(){
        super("");
    }

    /**
     * @inheritDoc
     * @return Cumulative price of all items within this collection
     * @apiNote It's assumed that a single meal will not exceed Short.MAX_VALUE
     */
    @Override
    public ShortPrice price() {
        short decimal = 0;                                                                                              // Temporary storage for both sides of a decimalised price.
        short fractional = 0;

        for (Item item : items){                                                                                         // For all items in this meal,
            decimal += item.price().getDecimal();                                                                       // Add thier decimalised price to total values
            fractional += item.price().getFractional();
        }

        ShortPrice total = new ShortPrice(decimal, fractional, GlobalConstants.DEFAULT_CURRENCY_SYMBOL);                         // Create a new price with the total values
        total.formatDecimal((short) GlobalConstants.DEFAULT_CURRENCY_DECIMAL_LENGTH);                                            // Format the price to match decimalisation length. (i.e £10.174 => £27.4)
        return total;                                                                                                   // return requested total to caller.
    }
}
