package sale.price;

/**
 * External interface for any price
 */
public interface IPrice<T> {

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

}
