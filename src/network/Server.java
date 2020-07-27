package network;

import network.packet.Packet;
import util.HaltCodes;
import util.RuntimeHelper;

import javax.swing.*;
import java.io.*;
import java.net.*;

/**
 * Simple Single Line Server for receiving serialized objects.
 *
 * @author Jordan Gray
 * @verison 1
 * @since 0.1.2
 */
public class Server implements Runnable {

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
     * Socket for the server to listen on
     */
    protected ServerSocket listener;

    /**
     * Time at which this server instance was created.
     * Used to determine up-time.
     */
    private final long startTime = System.currentTimeMillis();

    /**
     * # of ms that've passed since this server instance was created
     */
    public long getUpTime() {return System.currentTimeMillis() - startTime;}

    /**
     * Assigns static address to the server, if there isn't one already.
     *
     * Attempts to resolve default address.
     * If default failed, prompts user for host.
     * If failed, fatal - server has no address. Halts with SERVER_RESOLVE_FAILED
     */
    private static void setAddress(){
            try {
                address = (Inet4Address) Inet4Address.getByName(DEFAULT_IP);
            } catch (UnknownHostException e) {
                RuntimeHelper.log("[Server]", "Failed to resolve default IP, prompting for manual IP");
                try {
                    address = (Inet4Address) Inet4Address.getByName(JOptionPane.showInputDialog(null, "Enter Host IP", DEFAULT_IP, JOptionPane.WARNING_MESSAGE));
                } catch (UnknownHostException unknownHostException) {
                    JOptionPane.showMessageDialog(null, unknownHostException.getMessage(), "Failed to resolve", JOptionPane.ERROR_MESSAGE);
                    RuntimeHelper.halt(HaltCodes.FATAL_SERVER_RESOLVE_FAILED);
                }
            }
    }
    //#endregion


    /**
     * Creates and starts a new server
     * @throws IOException
     */
    public Server() throws IOException {
        setAddress();
        try {
            listener = new ServerSocket(PORT, 0, address);
        } catch (BindException e) {
            JOptionPane.showMessageDialog(null, e.getMessage(),"Failed to Bind", JOptionPane.ERROR_MESSAGE); // Failed to bind to address
            RuntimeHelper.halt(HaltCodes.FATAL_SERVER_BIND_FAILED);
        }
    }

    /**
     * Run the server in a new thread.
     *
     * Monitors socket for connection attempts. Reads objects in objects and responds.
     *
     * Connections are only alive for a single request. Concurrent connections are not possible.
     */
    @Override
    public void run() {
        RuntimeHelper.log(this, "Server now listening @ " + listener.getInetAddress() + ":" + listener.getLocalPort());

        while(true) {
            try {
                Socket clientSocket = listener.accept();                                                                // Wait and listen for a client to connect

                // We now have a client, create streams to it
                ObjectOutputStream out = new ObjectOutputStream(clientSocket.getOutputStream());
                ObjectInputStream in = new ObjectInputStream(clientSocket.getInputStream());

                Packet recieved = (Packet) in.readObject();                                                             // Wait for client to send object, and read it
                Packet response = Packet.processServerResponse(recieved);
                out.writeObject(response);                                                                              // Process packet, get response, return response to client.

                RuntimeHelper.log(this,
                        "[" + getUpTime() + "] " +
                        "Recieved " + recieved.type() +
                        ", responded with " + response.type());

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
        RuntimeHelper.log("[Server]", "Server running as a standalone debug session.");
        new Thread(new Server()).start();                                                                               // Start a new server thread.
    }
}

