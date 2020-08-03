package sale.price;


/**
 * Price with both partitions utilising Short Integer.
 *
 * Uses less memory and processing power, but can store reasonably large prices.
 * Both halves of a price are an instance of Short,
 * limited by <code>Short.MAX_VALUE</code> to <code>Short.MIN_VALUE</code>
 *
 * @inheritDoc
 */
public final class ShortPrice extends Price<Short> {

    /**
     * Create a ShortPrice with default value, symbol, and specified rtl.
     *
     * @inheritDoc
     */
    public ShortPrice(Short _decimal, Short _fractional) {
        super(_decimal, _fractional);
    }

    /**
     * Create a ShortPrice with default value, symbol, and specified rtl.
     *
     * @inheritDoc
     */
    public ShortPrice(Short _decimal, Short _fractional, char _symbol) {
        super(_decimal, _fractional, _symbol);
    }

    /**
     * Create a ShortPrice with default value, symbol, and specified rtl.
     *
     * @inheritDoc
     */
    public ShortPrice(Short _decimal, Short _fractional, char _symbol, boolean _rtl) {
        super(_decimal, _fractional, _symbol, _rtl);
    }
}
