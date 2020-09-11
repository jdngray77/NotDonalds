package sale.menu;

import java.io.Serializable;

/**
 * Generic menu item. Can be a catergory, meal, or item.
 * @author Jordan Gray
 * @since 0.1.0
 * @version 1
 */
public class MenuItem implements Serializable {

    /**
     * Name of this menu category
     */
    private String name;

    private boolean isCategory;

    /**
     * @return the name of this category
     */
    public String getName() { return name; }

    public boolean isCategory() { return isCategory; }

    public MenuItem(String _name){
        this(_name, false);
    }

    public MenuItem(String name, boolean _isCategory) {
        isCategory = _isCategory;
    }
}
