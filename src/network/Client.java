package network;

import network.packet.Packet;
import network.packet.PacketType;
import util.RuntimeHelper;

import java.io.*;
import java.net.*;

/**
 * Simple Client utility class.
 * @author Jordan Gray
 * @since 0.1.0
 * @version 1.1
 */
public final class Client {

    /**
     * Attempts to open a connection with the server, send a packet, and get a response.
     * @return The server's response.
     * @throws IOException If the connections could not be esablished.
     */
    public static Packet sendToServer(Packet toSend) throws IOException {
        RuntimeHelper.log("[Client]", "Client attempting to connect to server..");
        Socket socket = new Socket(Server.DEFAULT_IP, Server.PORT);                                                     // Create socket connected to the server's
        RuntimeHelper.log("[Client]", "Connected to " + socket.getInetAddress());

        // Connected to server, create stream.
        ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
        ObjectInputStream in = new ObjectInputStream(socket.getInputStream());

        RuntimeHelper.log("[Client]", "Sending packet of type " + toSend.type + "to connected server");
        out.writeObject(toSend);                                                                                        // Send packet

        RuntimeHelper.log("[Client]",  "Awaiting response.");
        Packet response = null;                                                                                         // Get response
        try {
            response = (Packet) in.readObject();                                                                        // Read response from server
            assert response != null;                                                                                    // Assert response
        } catch (ClassNotFoundException | ClassCastException e) {
            e.printStackTrace();                                                                                        // Response was not found, or was not a valid packet object.
        } // Response was not a packet object


        RuntimeHelper.log("[Client]", "Client recieved response of type " + response.type.toString());
        RuntimeHelper.log("[Client]", "Disconnecting from server");

        // Teardown
        in.close();
        out.close();
        socket.close();
        return response;
    }

    /**
     * Debug entry only.
     * Sends a test ackknowledgement packet to a server at local host.
     * @param args Commandline args - unused.
     * @throws IOException If an io error occoured whilst creating or using the connection.
     */
    public static void main(String[] args) throws IOException {
        sendToServer(new Packet(PacketType.ACKNOWLEDGE));
    }
}