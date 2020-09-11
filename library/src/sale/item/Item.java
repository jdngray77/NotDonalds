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
     * Maximum quantity permitable for an item.
     */
    public static final byte MAX_QUANTITY = Byte.MAX_VALUE;

    /**
     * Minimum quantity permitable for an item.
     */
    public static final byte MIN_QUANTITY = 1;

    /**
     * Price of the Item
     *
     * @apiNote Via abstraction, Short, Small or Long prices are compatible here.
     */
    private ShortPrice price;

    public Item(String _name, ShortPrice _price) {
        super(_name);
        name = _name;
        price = _price;
    }

    /**
     * @param i quantity to test is within permittable range
     * @return true if i is between 0 and 256.
     */
    public static boolean inQuantityRange(int i) {
        return (i <= MAX_QUANTITY && i >= MIN_QUANTITY);
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
        if (!inQuantityRange(quantity + i)) {
            alertOOR();
        } else {
            quantity += i;
        }
    }

    public void setQuantity(int i){
        if (inQuantityRange(i))
            setQuantity((byte) i);
        else
            alertOOR();
    }

    /**
     * Alert out of range.
     */
    private static void alertOOR(){
        RuntimeHelper.alertFailiure("Item quantity must be between" + MIN_QUANTITY + " £ " + MAX_QUANTITY);
    }

    public void setQuantity(byte i){
        quantity = i;
    }

    public byte getQuantity() {
        return quantity;
    }
}
