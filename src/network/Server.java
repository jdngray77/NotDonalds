package network;

import network.packet.Packet;
import util.HaltCodes;
import util.Helper;

import javax.swing.*;
import java.io.*;
import java.net.*;

/**
 * Simple Single Line Server for receiving serialized objects.
 *
 * @author Jordan Gray
 * @verison 1
 * @since 0.1.0
 */
public class Server implements Runnable {

    /**
     * Creates and starts a new server
     * @throws IOException
     */
    public Server() throws IOException {
        setAddress();
        listener = new ServerSocket(PORT, 0, address);
    }

    //#region properties
    /**
     * Server Port
     */
    public static final int PORT = 11113;

    /**
     * Default IP for the server
     */
    public static final String DEFAULT_IP = "localhost";

    /**
     * Actual address of the server
     */
    protected static Inet4Address address;

    /**
     * gets the address of the server.
     */
    public static Inet4Address getAddress = address;

    /**
     * Assigns static address to the server, if there isn't one already.
     *
     * Attempts to resolve default address.
     * If default failed, prompts user for host.
     * If failed, fatal - server has no address. Halts with SERVER_RESOLVE_FAILED
     */
    private static void setAddress(){
        if (address == null){
            try {
                address = (Inet4Address) Inet4Address.getByName(DEFAULT_IP);
            } catch (UnknownHostException e) {
                Helper.Log("[Server]", "Failed to resolve default IP, prompting for manual IP");
                try {
                    address = (Inet4Address) Inet4Address.getByName(JOptionPane.showInputDialog(null, "Enter Host IP", DEFAULT_IP, JOptionPane.WARNING_MESSAGE));
                } catch (UnknownHostException unknownHostException) {
                    JOptionPane.showMessageDialog(null, unknownHostException.getMessage(), "Failed to resolve", JOptionPane.ERROR_MESSAGE);
                    Helper.halt(HaltCodes.FATAL_SERVER_RESOLVE_FAILED);
                }
            }
        }
    }

    /**
     * Socket for the server to listen on
     */
    protected ServerSocket listener;
    //#endregion


    /**
     * Run the server in a new thread.
     *
     * Monitors socket for connection attempts. Reads objects in objects and responds.
     *
     * Connections are only alive for a single request. Concurrent connections are not possible.
     */
    @Override
    public void run() {
        Helper.Log(this, "Server now listening @ " + listener.getInetAddress() + ":" + listener.getLocalPort());

        while(true) {
            try {
                Socket clientSocket = listener.accept();                                                                // Wait and listen for a client to connect
                Helper.Log(this, "New Client Connected");

                // We now have a client, create streams to it
                ObjectOutputStream out = new ObjectOutputStream(clientSocket.getOutputStream());
                ObjectInputStream in = new ObjectInputStream(clientSocket.getInputStream());

                Packet packet = (Packet) in.readObject();                                                               // Wait for client to send object, and read it
                Helper.Log(this, "Recieved packet of type " + packet.type);

                out.writeObject(Packet.processServerResponse(packet));                                                        // Process packet, get response, return response to client.

                // Teardown connection with current client.
                out.close();
                in.close();
                clientSocket.close();
            } catch (IOException | ClassNotFoundException | ClassCastException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * For debugging only. This class is not standalone.
     * @param args command line arguments
     * @throws IOException if an io error occurs whilst initialising the server.
     */
    public static void main (String[] args) throws IOException {
        Helper.Log("[Server]", "Server running as a debug session.");
        new Thread(new Server()).start();                                                                               // Start a new server thread.
    }
}

