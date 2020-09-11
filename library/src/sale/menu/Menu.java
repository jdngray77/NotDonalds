package sale.menu;

/**
 * Menu is the final parent of a menu, functionally equal to a Menu category,
 * behaving as a collection of menu categories and items.
 */
public final class Menu extends MenuCategory {

    /**
     * Creates a new menu
     * @param _name Name of the menu.
     */
    public Menu(String _name) {
        super(_name);
    }
}
