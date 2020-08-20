package sale.menu;

import java.util.ArrayList;
import java.util.List;

/**
 * A category is a collection that can contain more categories, meals, or items.
 */
public class MenuCategory extends MenuItem {

    /**
     * Name of this menu category
     */
    private String name;

    /**
     * The contents of this category
     */
    public List contents = new ArrayList<MenuItem>();

    /**
     * @return the name of this category
     */
    public String getName() {
        return name;
    }

    /**
     * Creates a new menu category.
     * @param _name
     */
    public MenuCategory(String _name){
        name = _name;
    }

}
