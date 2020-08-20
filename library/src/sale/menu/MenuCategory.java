package sale.menu;

import java.util.ArrayList;

/**
 * A category is a collection that can contain more categories, meals, or items.
 */
public class MenuCategory extends MenuItem {



    /**
     * The contents of this category
     */
    public ArrayList<MenuItem> contents = new ArrayList<>();


    /**
     * Creates a new menu category.
     * @param _name
     */
    public MenuCategory(String _name){
        super(_name, true);
    }
}
