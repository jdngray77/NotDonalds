package io;

import sale.item.Item;
import sale.menu.Menu;
import sale.menu.MenuCategory;
import sale.price.ShortPrice;
import xmlwise.Plist;
import xmlwise.XmlParseException;

import java.io.File;
import java.io.IOException;
import java.lang.management.MemoryUsage;
import java.nio.file.Path;
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
        return ParseMenuTree((HashMap) Plist.loadObject(file));
    }

    /**
     * Creates a menu object populated with the provided hashed menu map.
     * @param XMLMenuObject hashmap to parse
     * @return Compiled Menu Object
     */
    private static Menu ParseMenuTree(HashMap XMLMenuObject) {
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
                    Integer[] fractions = ShortPrice.SplitDouble((Double) v);                                           // Parse as leaf; create and add item.
                    ShortPrice price = new ShortPrice(fractions[0].shortValue(), fractions[1].shortValue());            // Split value into fractionals, and store in price
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


    /**
     * Debug execution, parses menu plist.
     * @param args
     */
    public static void main(String[] args) throws IOException, XmlParseException {
        loadMenuFile("Menu.plist");
    }
}
