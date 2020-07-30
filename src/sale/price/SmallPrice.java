package sale.price;

public final class SmallPrice extends Price<Byte>{

    public SmallPrice(Byte _decimal, Byte _fractional) {
        super(_decimal, _fractional);
    }

    public SmallPrice(Byte _decimal, Byte _fractional, char _symbol) {
        super(_decimal, _fractional, _symbol);
    }

    public SmallPrice(Byte _decimal, Byte _fractional, char _symbol, boolean _rtl) {
        super(_decimal, _fractional, _symbol, _rtl);
    }
}
