package sale.price;

import jdk.jshell.spi.ExecutionControl.NotImplementedException;
import util.GlobalConstants;

/**
 * Unmanaged, Read only, Dual section, non currency-specific price storage.
 *
 * Holds decimal and fractional currency seperately to prevent rounding.
 * Human friendly currency symbol is optionally available to show expected currency.
 * Format is ($/£/¥/€)
 *
 * Used only to store and get values, does not calculate or validate.
 *
 * Only intended to be used within this package.
 * Whilst generic abstract, this should only be used with instances of numeric data types.
 */
abstract class Price<T> implements IPrice<T> {

    /**
     * Default decimal length.
     */
    public static final byte DEFAULT_LENGTH = 100;

    /**
     *  Fractional, partial, section of the currency notation.
     *  i.e Fractional 11 = £0.11
     */
    protected T fractional;

    /**
     * Decimal, larger section of the currency notation.
     * i.e Decimal 11 = £11.00
     */
    protected T decimal;

    /**
     * Notation symbol for human friendly viewing.
     */
    private char symbol = GlobalConstants.nullChar;

    /**
     * Right to left
     * Determines if the notation symbol appears on the left or right.
     * Left if false, Right if true.
     */
    private boolean rtl;

    /**
     * Decimalesque length of the fractional side of the price.
     *
     * This length must be reached by the fractional before incrementing the decimal side.
     *
     * @example length 100
     *          £10.99 + 1p = £11.00
     */
    protected T length;


    /**
     * Create a price
     * @param _decimal Larger, whole section of the price. (XX.)
     * @param _fractional Smaller, partial section of the price. (.XX)
     * @param _length Length of fractional before decimal is incremented (length 100: £10.99 + 1p = £11.00)
     */
    public Price(T _decimal, T _fractional, T _length){
        this(_decimal, _fractional, _length, GlobalConstants.nullChar);
    }

    /**
     * Create a price with a denoted currency symbol
     * @param _decimal Larger, whole section of the price. (XX.)
     * @param _fractional Smaller, partial section of the price. (.XX)
     * @param _symbol Currency notation symbol (i.e $/£/¥/€)
     * @param _length Length of fractional before decimal is incremented (length 100: £10.99 + 1p = £11.00)
     */
    public Price(T _decimal, T _fractional, T _length, char _symbol){
        this(_decimal, _fractional, _length, _symbol, false);
    }

    /**
     * Create a price with a denoted currency symbol
     * @param _decimal Larger, whole section of the price. (XX.)
     * @param _fractional Smaller, partial section of the price. (.XX)
     * @param _symbol Currency notation symbol (i.e $/£/¥/€)
     * @param _rtl Determines if the notation symbol appears on the left or right. Right if true.
     * @param _length Length of fractional before decimal is incremented (length 100: £10.99 + 1p = £11.00)
     */
    public Price(T _decimal, T _fractional, T _length, char _symbol, boolean _rtl){
        this.overridePrice(_decimal, _fractional, _length, _symbol, _rtl);
    }

    /**
     * @inheritDoc
     */
    @Override
    public void overridePrice(T _decimal, T _fractional,T _length, char _symbol, boolean _rtl){
        fractional = _fractional;
        decimal = _decimal;
        symbol = _symbol;
        rtl = _rtl;
        length = _length;
    }

    /**
     * @inheritDoc
     */
    @Override
    public T getDecimal() {return decimal;}

    /**
     * @inheritDoc
     */
    @Override
    public T getFractional() {return fractional;}

    /**
     * @inheritDoc
     */
    @Override
    public boolean getRTL() {return rtl;}

    /**
     * @inheritDoc
     */
    @Override
    public char getSymbol() {return symbol;}


    /**
     * @inheritDoc
     *
     * Formats price according to rtl; if rtl, fractional.decimal, else decimal.fractional.)
     */
    @Override
    public float asFloat() {
        return rtl?
                Float.parseFloat( fractional.toString() + "." + decimal.toString())
                :
                Float.parseFloat( decimal.toString() + "." + fractional.toString()) ;
    }

    /**
     * @inheritDoc
     * Formats price according to rtl; if rtl, fractional.decimal, else decimal.fractional.)
     */
    @Override
    public double asDouble() {
        return rtl?
                Double.parseDouble( fractional.toString() + "." +  decimal.toString())
                :
                Double.parseDouble( decimal.toString() + "." + fractional.toString());
    }

    /**
     * @inheritDoc
     *
     * Formats price according to rtl; if rtl, fractional.decimal$, else $decimal.fractional.)
     */
    @Override
    public String asDisplay(){
        /*
            I don't like this.

            I need the double as a string.

            Double's cannot be directly cast to a string.

            this.asDouble() returns the data type double, not a class of Double, meaning asDouble cannot be operated on,
            thus .toString() is not directly possible.

            So the options are:
                String.valueOf(asDouble())
                OR
                redundantly cast double (data) to Double (class instance), to operate on.
                ((Double) asDouble()).toString()
         */
        return fillDisplayFractional(
                rtl?
                        String.valueOf(asDouble()) + symbol
                        :
                        symbol + String.valueOf(asDouble())
        );
    }

    /**
     * Suffixes fractional display strings with 0,
     * as to display
     *
     * ```
     * £10.50
     * ```
     * instead of
     * ```
     * £10.5
     * ```
     * @param s FRACTIONAL price to append a '0' to.
     * @return
     */
    private String fillDisplayFractional(String s){
        return s.length() > 1?                                                                                          // If price string is greater than one in length,
                s                                                                                                       // Ignore filler; return string.
                :                                                                                                       // else,
                rtl?                                                                                                    // Append "0" based on rtl.
                        "0" + s
                        :
                        s + "0";
    }

    /**
     * @inheritDoc
     */
    @Override
    public void formatDecimal() throws NotImplementedException {
        throw new NotImplementedException("");
    }

    /**
     * Splits a double into two integers at the decimal point.
     * Useful for generating price fractionals from real.
     * i.e 10.13 = int[10, 13]
     *
     * @param d Double to split
     * @return
     */
    public static Integer[] splitDouble(Double d){
        int a, b;
        String dString = Double.toString(d);
        String aString = dString.substring(0, 1);

        String bString = dString.substring(2);
        a = Integer.parseInt(aString);
        b = Integer.parseInt(bString);
        return new Integer[]{a,b};
    }

}
