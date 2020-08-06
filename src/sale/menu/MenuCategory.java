package sale.menu;

import java.util.ArrayList;
import java.util.List;

/**
 * A category is a collection that can contain more categories, meals, or items.
 */
public class MenuCategory extends MenuItem {
    private String name;
    public List contents = new ArrayList<MenuItem>();

    public MenuCategory(String _name){
        name = _name;
    }

}
