package sale.price;

public final class LongPrice extends Price<Long> {

    public LongPrice(Long _decimal, Long _fractional) {
        super(_decimal, _fractional);
    }

    public LongPrice(Long _decimal, Long _fractional, char _symbol) {
        super(_decimal, _fractional, _symbol);
    }

    public LongPrice(Long _decimal, Long _fractional, char _symbol, boolean _rtl) {
        super(_decimal, _fractional, _symbol, _rtl);
    }
}
