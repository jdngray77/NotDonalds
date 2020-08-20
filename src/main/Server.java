package main;

import io.MenuHelper;
import util.HaltCodes;
import util.RuntimeHelper;
import xmlwise.XmlParseException;

import javax.swing.*;
import java.io.IOException;

/**
 * Main server entry point
 */
public class Server {

    /**
     * Global server instance for server side references only.
     */
    public static network.Server instance = new network.Server();                                                       // Create server instance

    /**
     * Creates and starts a server, and loads a menu to serve.
     * @param args
     */
    public static void main(String[] args) {
        new Thread(instance).start();                                                                                   // Start server in a new thread.
        try {
            MenuHelper.loadMenuFile("Menu.plist");
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Could not read default menu file from disk!", "Could not start!", JOptionPane.ERROR_MESSAGE);
            RuntimeHelper.halt(HaltCodes.NONFATAL_MENU_READ_ERROR);
        } catch (XmlParseException e) {
            JOptionPane.showMessageDialog(null, "Default menu file is not a valid XML plist file!", "Could not start!", JOptionPane.ERROR_MESSAGE);
            RuntimeHelper.halt(HaltCodes.NONFATAL_MENU_XML_ERROR);
        }
    }
}
