package io;

import javafx.scene.image.Image;
import sale.item.Item;
import sale.menu.Menu;
import sale.menu.MenuCategory;
import sale.menu.MenuItem;
import sale.price.ShortPrice;
import util.GlobalConstants;
import xmlwise.Plist;
import xmlwise.XmlParseException;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Static menu utility container
 *
 * @author Jordan Gray
 * @since 0.1.0
 * @version 1
 */
public class MenuHelper {

    /**
     * Static menu storage. Currently loaded menu for either client or server.
     */
    public static Menu menu;

    public static final String DEFAULT_MENU_LOC = "Menu.plist";

    public static final Image DEFAULT_ITEM_IMG = new Image(MenuHelper.class.getResource("/img/default.png").toString());

    /**
     * Loads a menu from file, using the default menu location this.DEFAULT_MENU_LOC.
     */
    public static void loadDefaultMenu() throws IOException, XmlParseException {
        loadMenuFile(DEFAULT_MENU_LOC);
    }

    /**
     * Compiles a Menu object instance from a plist file on disk.
     * @param path location of file to parse
     * @return Compiled menu tree instance.
     * @throws IOException if the file could not be read
     * @throws XmlParseException if the file was not valid xml
     */
    public static Menu loadMenuFile(Path path) throws IOException, XmlParseException {
        return loadMenuFile(path.toString());
    }

    /**
     * Compiles a Menu object instance from a plist file on disk.
     * @param path location of file to parse
     * @return Compiled menu tree instance.
     * @throws IOException if the file could not be read
     * @throws XmlParseException if the file was not valid xml
     */
    public static Menu loadMenuFile(String path) throws IOException, XmlParseException {
        return loadMenuFile(new File(path));
    }

    /**
     * Compiles a Menu object instance from a plist file on disk.
     * @param file File to parse
     * @return Compiled menu tree instance.
     * @throws IOException if the file could not be read
     * @throws XmlParseException if the file was not valid xml
     */
    public static Menu loadMenuFile(File file) throws IOException, XmlParseException {
        menu = parseMenuTree((HashMap) Plist.loadObject(file));
        return menu;
    }

    /**
     * Creates a menu object populated with the provided hashed menu map.
     * @param XMLMenuObject hashmap to parse
     * @return Compiled Menu Object
     */
    private static Menu parseMenuTree(HashMap XMLMenuObject) {
        Menu menu = new Menu("Default Menu");
        menu.contents.add( parseBranch("Menu Tree Root", XMLMenuObject));
        return menu;
    }

    /**
     * Recursively parses branches to traverse the tree of the hash map loaded from the plist formatted xml file.
     * @param branch HashMap to parse
     * @return MenuCategory of the current branch. After recursion, returns entire menu tree from the provided map.
     */
    private static MenuCategory parseBranch(String name, HashMap branch){
        MenuCategory category = new MenuCategory(name);

        branch.forEach((k,v) ->{
            /*
             * Parse child branch;
             *      If Value is another hash map, parse it then add it to current branch.
             *      If not a HM<k,v>, then treat it as a value leaf for this category, as opposed to a new branch.
             */
            try {
                category.contents.add(parseBranch((String) k, (HashMap) v));                                            // Parse child branch.
            } catch (ClassCastException e) {
                try {
                    Integer[] fractions = ShortPrice.splitDouble((Double) v);                                           // Parse as leaf; create and add item.
                    ShortPrice price = new ShortPrice(fractions[0].shortValue(), fractions[1].shortValue(), GlobalConstants.DEFAULT_CURRENCY_SYMBOL, false);            // Split value into fractionals, and store in price
                    Item item = new Item((String) k, price);                                                            // Create item with key as it's name, and price.
                    System.out.println("[MENU LOAD] ADDED NEW ITEM " + (String) k + " @ " + price.asDisplay());
                    category.contents.add(item);                                                                        // Add item to this category
                } catch (ClassCastException e1)
                {
                    // Invalid value, ignore
                    System.out.println("[LOAD EXCEPTION] INVALID MENU ITEM K:" + k.toString() + " V:" +  v.toString() + " ITEM NOT INCLUDED.");
                }
            }
        });
        return category;
    }

    public static ArrayList<Item> allItems(MenuCategory category) {
        ArrayList<Item> list = new ArrayList<>();
        for (MenuItem item : category.contents) {
            if (item.isCategory()){
                list.addAll(allItems((MenuCategory) item));
            } else {
                list.add((Item) item);
            }
        }
        return list;
    }

    /**
     * Debug execution, parses menu plist.
     * @param args
     */
    public static void main(String[] args) throws IOException, XmlParseException {
        loadDefaultMenu();
        ArrayList<Item> items = allItems(menu);
        assert items != null;
    }


}
