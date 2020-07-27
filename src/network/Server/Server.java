package network.Server;

import network.Server.Listen.Listener;
import util.Logger;
import javax.swing.*;
import java.io.IOException;
import java.net.Inet4Address;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

/**
 * Static NotDonalds Server
 *
 * Recieves serialized requests from connected clients.
 *
 * @author Jordan Gray
 * @since 0.1.0
 * @version 1
 */
public final class Server {

    /**
     * Private instantiator.
     * This class is not instantiable.
     */
    private Server(){}

    //#region properties
    /**
     * Active client sessions
     */
    private static List<Session> sessions = new ArrayList<Session>();

    /**
     * Active network listeners
     */
    private static List<Listener> listeners = new ArrayList<Listener>();

    /**
     * Number of network listeners to create on server start.
     */
    protected static byte requiredListenerCount = 4;

    /**
     * is the server currently running
     */
    private static boolean isRunning = false;

    /**
     * @return true if server is running
     */
    protected static boolean getIsRunning = isRunning;

    /**
     * Sets running flag
     */
    private static void setRunning() {isRunning = true;}

    /**
     * Server Host Address
     */
    private static Inet4Address IP = null;

    /**
     * Gets the host addresss
     */
    protected static Inet4Address getIP = IP;

    protected static final int PORT = 2020;
    //#endregion

    //#region Methods
    /**
     * Starts and stores network listeners.
     * @see this.requiredListenerCount
     */
    private static void startListeners(){
        for (int i = 0; i <= requiredListenerCount; i++){
            Listener l = null;
            l = new Listener(IP, PORT + i);
            Thread t = new Thread(l);
            listeners.add(l);
            t.start();
        }
    }

    /**
     * Prompts the admin for the host IP or domain.
     *
     *
     */
    private static void getIP(){
        try {
            Logger.internal("[Server]", "Requesting host");
            IP = (Inet4Address) Inet4Address.getByName(JOptionPane.showInputDialog(null, "Enter HOST IP", "0.0.0.0"));
            Logger.internal("[Server]", "Recieved host " + IP.toString());
        } catch (UnknownHostException e) {
            Logger.internal("[Server]", "Invalid host");
            JOptionPane.showMessageDialog(null, e.getMessage(), "Invalid IP", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }

    }

    //#endregion

    /**
     * Startup routine
     *
     * @throws Exception if the server is already running.
     */
    public static void start() {
        if (isRunning) return;
        getIP();
        startListeners();
    }

    /**
     * For debug and tesing only. This class is not standalone.
     * @param args Commandline arguments
     */
    public static void main(String[] args) {
        Logger.internal("[Server]", "Debug startup request.");
        start();
    }

}
