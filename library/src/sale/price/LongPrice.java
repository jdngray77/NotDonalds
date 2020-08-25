package sale.price;

/**
 * Price with both partitions utilising Long Integer.
 *
 * Uses more memory and processing power, but can store considerably larger prices.
 * Both halves of a price are an instance of Long,
 * limited by <code>Long.MAX_VALUE</code> to <code>Long.MIN_VALUE</code>
 *
 * @inheritDoc
 */
public final class LongPrice extends Price<Long> {

    /**
     * Create a LongPrice with default value
     *
     * @inheritDoc
     */
    public LongPrice(Long _decimal, Long _fractional) {
        super(_decimal, (long) DEFAULT_LENGTH, _fractional);
    }

    /**
     * Create a LongPrice with default value and currency symbol.
     *
     * @inheritDoc
     */
    public LongPrice(Long _decimal, Long _fractional, char _symbol) {
        super(_decimal, _fractional, (long) DEFAULT_LENGTH, _symbol);
    }

    /**
     * Create a LongPrice with default value, symbol, and specified rtl.
     *
     * @inheritDoc
     */
    public LongPrice(Long _decimal, Long _fractional, char _symbol, boolean _rtl) {
        super(_decimal, _fractional, (long) DEFAULT_LENGTH, _symbol, _rtl);
    }

    /**
     * @inheritDoc
     */
    @Override
    public void formatDecimal(){
        overridePrice(getDecimal() + Math.floorDiv(getFractional(), length), (long) DEFAULT_LENGTH, getFractional() % length, getSymbol(), getRTL());
    }
}
