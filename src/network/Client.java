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
        Socket socket = new Socket(Server.DEFAULT_IP, Server.PORT);                                                     // Create socket connected to the server's

        // Connected to server, create stream.
        ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
        ObjectInputStream in = new ObjectInputStream(socket.getInputStream());


        out.writeObject(toSend);                                                                                        // Send packet

        Packet response = null;                                                                                         // Get response
        try {
            response = (Packet) in.readObject();                                                                        // Read response from server
            assert response != null;                                                                                    // Assert response recieved
        } catch (ClassNotFoundException | ClassCastException e) {
            e.printStackTrace();                                                                                        // Response was not found, or was not a valid packet object.
        } // Response was not a packet object

        RuntimeHelper.log("[Client]", "Sent " + toSend.type() + ", Recieved " + response.type() +       // Log entire communication.
                ((response.metaMessage == null) ? "" : "; " + response.metaMessage)                                     // If no message, append nothing. Else format and append.
                );

        // Teardown
        in.close();
        out.close();
        socket.close();
        return response;
    }

    /**
     * Debug entry only.
     * Sends an empty packet for every packet type available to the client.
     * @param args Commandline args - unused.
     * @throws IOException If an io error occoured whilst creating or using the connection.
     */
    public static void main(String[] args) throws IOException {
        while (true)
            for (PacketType e : PacketType.values())
                sendToServer(new Packet(e));
    }
}