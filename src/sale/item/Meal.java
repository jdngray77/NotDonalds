package sale.item;

import sale.price.IPrice;
import sale.price.ShortPrice;
import util.Finals;

import java.util.ArrayList;
import java.util.List;

/**
 * A collection of single menu items, conciliated into a single sellable object.
 */
public class Meal extends ArrayList<Item> implements Sellable {

    /**
     * @inheritDoc
     * @return Cumulative price of all items within this Meal.
     * @apiNote It's assumed that a single meal will not exceed Short.MAX_VALUE
     */
    @Override
    public ShortPrice Price() {
        short decimal = 0;                                                                                              // Temporary storage for both sides of a decimalised price.
        short fractional = 0;

        for (Item item : this){                                                                                         // For all items in this meal,
            decimal += item.Price().getDecimal();                                                                       // Add thier decimalised price to total values
            fractional += item.Price().getFractional();
        }

        ShortPrice total = new ShortPrice(decimal, fractional, Finals.DEFAULT_CURRENCY_SYMBOL);                         // Create a new price with the total values
        total.formatDecimal((short) Finals.DEFAULT_CURRENCY_DECIMAL_LENGTH);                                            // Format the price to match decimalisation length. (i.e £10.174 => £27.4)
        return total;                                                                                                   // return requested total to caller.
    }
}
