package sale.price;

import jdk.jshell.spi.ExecutionControl.NotImplementedException;
import util.Finals;

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
     *  Fractional, partial, section of the currency notation.
     *  i.e Fractional 11 = £0.11
     */
    private T fractional;

    /**
     * Decimal, larger section of the currency notation.
     * i.e Decimal 11 = £11.00
     */
    private T decimal;

    /**
     * Notation symbol for human friendly viewing.
     */
    private char symbol = Finals.nullChar;

    /**
     * Right to left
     * Determines if the notation symbol appears on the left or right.
     * Left if false, Right if true.
     */
    private boolean rtl;


    /**
     * Create a price
     * @param _decimal Larger, whole section of the price. (XX.)
     * @param _fractional Smaller, partial section of the price. (.XX)
     */
    public Price(T _decimal, T _fractional){
        this(_decimal, _fractional, Finals.nullChar, false);
    }

    /**
     * Create a price with a denoted currency symbol
     * @param _decimal Larger, whole section of the price. (XX.)
     * @param _fractional Smaller, partial section of the price. (.XX)
     * @param _symbol Currency notation symbol (i.e $/£/¥/€)
     */
    public Price(T _decimal, T _fractional, char _symbol){
        this(_decimal, _fractional, _symbol, false);
    }

    /**
     * Create a price with a denoted currency symbol
     * @param _decimal Larger, whole section of the price. (XX.)
     * @param _fractional Smaller, partial section of the price. (.XX)
     * @param _symbol Currency notation symbol (i.e $/£/¥/€)
     * @param _rtl Determines if the notation symbol appears on the left or right. Right if true.
     */
    public Price(T _decimal, T _fractional, char _symbol, boolean _rtl){
        this.overridePrice(_decimal, _fractional, _symbol, _rtl);
    }

    /**
     * @inheritDoc
     */
    @Override
    public void overridePrice(T _decimal, T _fractional, char _symbol, boolean _rtl){
        fractional = _fractional;
        decimal = _decimal;
        symbol = _symbol;
        rtl = _rtl;
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
                Double.parseDouble( decimal.toString() + "." + fractional.toString())
                :
                Double.parseDouble( fractional.toString() + "." +  decimal.toString());
    }

    /**
     * @inheritDoc
     *
     * Formats price according to rtl; if rtl, fractional.decimal$, else $decimal.fractional.)
     */
    @Override
    public String asDisplay(){
        return rtl? String.valueOf(symbol + asDouble()) : String.valueOf(asDouble() + symbol);
    }

    /**
     * @inheritDoc
     */
    @Override
    public void formatDecimal(T len) throws NotImplementedException {
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
    public static Integer[] SplitDouble(Double d){
        int a, b;
        String dString = Double.toString(d);
        String aString = dString.substring(0, 1);

        String bString = dString.substring(2);
        a = Integer.parseInt(aString);
        b = Integer.parseInt(bString);
        return new Integer[]{a,b};
    }

}
