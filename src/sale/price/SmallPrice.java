package sale.price;

/**
 * Price with both partitions utilising a Byte.
 *
 * Uses least memory and computing, storing the shortest quantity of data; a Byte.
 * Byte can store prices from <code>0.0</code> to <code>255.255</code>
 * limited by <code0x0000, int 0</code> to <code>0x1111, int 255</code>
 *
 * @inheritDoc
 */
public final class SmallPrice extends Price<Byte>{

    /**
     * Create a SmallPrice with default value, symbol, and specified rtl.
     *
     * @inheritDoc
     */
    public SmallPrice(Byte _decimal, Byte _fractional) {
        super(_decimal, _fractional);
    }

    /**
     * Create a SmallPrice with default value, symbol, and specified rtl.
     *
     * @inheritDoc
     */
    public SmallPrice(Byte _decimal, Byte _fractional, char _symbol) {
        super(_decimal, _fractional, _symbol);
    }

    /**
     * Create a SmallPrice with default value, symbol, and specified rtl.
     *
     * @inheritDoc
     */
    public SmallPrice(Byte _decimal, Byte _fractional, char _symbol, boolean _rtl) {
        super(_decimal, _fractional, _symbol, _rtl);
    }

    /**
     * @inheritDoc
     * @param len Maximum quantity permitted
     */
    @Override
    public void formatDecimal(Byte len){
        overridePrice((byte) (getDecimal() + Math.floorDiv(getFractional(), len)), (byte) (getFractional() % len), getSymbol(), getRTL());
    }

}
