package sale.price;

import jdk.jshell.spi.ExecutionControl;

/**
 * External interface for any price
 */
public interface IPrice<T> {

    /**
     * Overwrites the exsisting stored price with a new value, with out creating a new instance.
     */
    void overridePrice(T _decimal, T _fractional, char _symbol, boolean _rtl);

    /**
     * @return price of this instance, formatted, and represented as a floating point integer.
     */
    float asFloat();

    /**
     * @return price of this instance, and represented as a double precision floating point integer.
     */
    double asDouble();

    /**
     * @return Formatted price, including a currency symbol if available, in text form for display.
     */
    String asDisplay();

    /**
     * Formats the partial side of the price, such that
     * <code>decimal / len</code> is moved from fractional to decimal,
     * and the remainder is left as decimal.
     *
     * @example <p>
     *         Functionality:
     *         decimal = decimal + fractional / len (flooring remainder.)
     *         fractional = fractiol % ( modulo ) len
     *         </p>
     *
     * @apiNote <p>
     *         Example override (for Byte):
     *         overridePrice((byte) (getDecimal() + Math.floorDiv(getFractional(), len)), (byte) (getFractional() % len), getSymbol(), getRTL());
     *         </p>
     *
     * @apiNote Unless overriden for extended datatypes, this abstract will throw:
     * @throws ExecutionControl.NotImplementedException if called where not overriden with typed extention.
     *
     * @example $3.105 formatted with len 10 results in $13.5
     * @param len Maximum quantity permitted
     */
    void formatDecimal(T len) throws ExecutionControl.NotImplementedException;

    /**
     * @return pre-decimal section of the price
     */
    T getDecimal();

    /**
     * @return post-decimal section of the price
     */
    T getFractional();

    /**
     * @return true if the price is requested to be right-to-left
     */
    boolean getRTL();

    /**
     * @return symbol character of the price (i.e $ / £ / ¥ / €)
     */
    char getSymbol();
}
