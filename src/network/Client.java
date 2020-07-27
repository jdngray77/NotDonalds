package network;

import util.Helper;

import java.io.*;
import java.net.*;

/**
 * Simple Client utility class.
 * @author Jordan Gray
 */
public final class Client {

    /**
     * Attempts to open a connection with the server, send a packet, and get a response.
     * @return The server's response.
     * @throws IOException If the connections could not be esablished.
     */
    public static Packet SendToServer(Packet toSend) throws IOException {
        Helper.internal("[Client]", "Client attempting to connect to server..");
        Socket socket = new Socket(Server.DEFAULT_IP, Server.PORT);                                                     // Create socket connected to the server's
        Helper.internal("[Client]", "Connected to " + socket.getInetAddress());

        // Connected to server, create stream.
        ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
        ObjectInputStream in = new ObjectInputStream(socket.getInputStream());

        Helper.internal("[Client]", "Sending packet of type " + toSend.type + "to connected server");
        out.writeObject(toSend);                                                                                        // Send packet

        Helper.internal("[Client]",  "Awaiting response.");
        Packet response = null;                                                                                         // Get response
        try {
            response = (Packet) in.readObject();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();                                                                                        // Response was not found
        } catch (ClassCastException e) {
            e.printStackTrace();                                                                                        // Response was not a packet object
        }
        Helper.internal("[Client]", "Client recieved response of type " + response.type.toString());
        Helper.internal("[Client]", "Disconnecting from server");
        // Teardown
        in.close();
        out.close();
        socket.close();
        return response;
    }

    public static void main(String[] args) throws IOException {
        SendToServer(new Packet(MessageType.ACKNOWLEDGE));
    }

}