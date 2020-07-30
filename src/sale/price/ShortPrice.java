package sale.price;

/**
 *
 */
public final class ShortPrice extends Price<Short> {

    public ShortPrice(Short _decimal, Short _fractional) {
        super(_decimal, _fractional);
    }

    public ShortPrice(Short _decimal, Short _fractional, char _symbol) {
        super(_decimal, _fractional, _symbol);
    }

    public ShortPrice(Short _decimal, Short _fractional, char _symbol, boolean _rtl) {
        super(_decimal, _fractional, _symbol, _rtl);
    }
}
