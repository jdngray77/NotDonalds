package sale.menu;

import java.util.ArrayList;
import java.util.List;

/**
 * A category is a collection that can contain more categories, meals, or items.
 */
public class MenuCategory extends MenuItem {
    public List contents = new ArrayList<MenuItem>();
}
