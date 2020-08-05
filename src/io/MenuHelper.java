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
 * Static menu plist utility container
 *
 *
 * @author
 * @since 0.1.0
 * @version 1
 */
public class MenuHelper {

    public static Menu loadMenuFile(Path path){
        return loadMenuFile(path.toString());
    }

    public static Menu loadMenuFile(String path){
        return loadMenuFile(new File(path));
    }

    public static Menu loadMenuFile(File file){
        try {
            return ParseMenuTree((HashMap) Plist.loadObject(file));
        } catch (XmlParseException e) {
            e.printStackTrace();    // File not formatted correctly
        } catch (IOException e) {
            e.printStackTrace();    // Error in getting file
        }
        return null;
    }

    private static Menu ParseMenuTree(HashMap XMLMenuObject) {
        Menu menu = new Menu();
        menu.contents.add(parseBranch(XMLMenuObject));
        return menu;
    }

    private static MenuCategory parseBranch(HashMap branch){
        MenuCategory category = new MenuCategory();

        branch.forEach((k,v) ->{
            /*
             * Parse child branch;
             *      If Value is another hash map, parse it then add it to current branch.
             *      If not a HM<k,v>, then treat it as a value leaf for this category, as opposed to a new branch.
             */
            try {
                category.contents.add(parseBranch((HashMap) v));                                                        // Parse child branch.
            } catch (ClassCastException e) {
                try {
                    Integer[] fractions = ShortPrice.SplitDouble((Double) v);                                               // Parse as leaf; create and add item.
                    ShortPrice price = new ShortPrice(fractions[0].shortValue(), fractions[1].shortValue());                // Split value into fractionals, and store in price
                    Item item = new Item((String) k, price);                                                                // Create item with key as it's name, and price.
                    category.contents.add(item);                                                                            // Add item to this category
                } catch (ClassCastException e1)
                {
                    // Invalid value, ignore
                    System.out.println("[LOAD EXCEPTION] INVALID MENU ITEM K:" + k.toString() + " V:" +  v.toString() + " ITEM NOT INCLUDED.");
                }
            }
        });
        return category;
    }



    public static void main(String[] args) {
        loadMenuFile("Menu.plist");
    }
}
