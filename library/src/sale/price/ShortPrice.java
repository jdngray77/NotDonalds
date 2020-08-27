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
        super(_decimal, (short) DEFAULT_LENGTH, _fractional);
    }

    /**
     * Create a ShortPrice with default value, symbol, and specified rtl.
     *
     * @inheritDoc
     */
    public ShortPrice(Short _decimal, Short _fractional, char _symbol) {
        super(_decimal, _fractional, (short) DEFAULT_LENGTH, _symbol);
    }

    /**
     * Create a ShortPrice with default value, symbol, and specified rtl.
     *
     * @inheritDoc
     */
    public ShortPrice(Short _decimal, Short _fractional, char _symbol, boolean _rtl) {
        super(_decimal, _fractional, (short) DEFAULT_LENGTH, _symbol, _rtl);
    }

    /**
     * @inheritDoc
     */
    @Override
    public void formatDecimal(){
        overridePrice((short) (getDecimal() + (getFractional() - (getFractional() % length)) / length), (short) (getFractional() % length), (short) DEFAULT_LENGTH, getSymbol(), getRTL());
    }

    public void overridePrice(Short _decimal, Short _fractional){
        fractional = _fractional;
        decimal = _decimal;
        formatDecimal();
    }

    public ShortPrice times(byte i){
        ShortPrice total = new ShortPrice((short) 0, (short) 0, getSymbol(), getRTL());
        for (int j = 0; j < i; j++) {
            total.overridePrice((short) (total.getDecimal() + getDecimal()), (short) (total.getFractional() + getFractional()));
        }
        return total;
    }

    public void add(short decimal, short fractional){
        overridePrice((short) (getDecimal() + decimal), (short) (getFractional() + fractional));

    }
}
