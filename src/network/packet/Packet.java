package network.packet;

import java.io.Serializable;

/**
 * Serializable network data object.
 *
 * Basis for all client > server relations for NotDonalds.
 * @author Jordan Gray
 * @since 0.1.0
 * @version 1.1
 */
public class Packet implements Serializable {

    //#region properties
    /**
     * The message type expected to be found in this packet
     */
    private PacketType _type;

    /**
     * returns the type of this packet's message
     */
    public PacketType type() {return _type;}

    /**
     * Can be used for descriptions or other human friendly messages. Not for packet data.
     */
    public String metaMessage = "";

    //#endregion

    /**
     * Creates a new packet
     * @param type - Message type contained within this packet
     */
    public Packet(PacketType type){
        this(type, "");
    }

    /**
     * Creates a new packet
     * @param type - Message type contained within this packet
     * @param _metaMessage Human friendly message about this packet.
     */
    public Packet(PacketType type, String _metaMessage){
        _type = type;
        metaMessage = _metaMessage;
    }



    //#region static utils
    /**
     * Processes a packet recieved server side. Performs any required subroutine associated with requests, then returns a response.
     *
     * Packets processed server side must return a response.
     * @param packet Packet to process
     * @return Response packet
     */
    public static Packet processServerResponse(Packet packet) {
        if (packet == null) return new Packet(PacketType.REJECT);

        switch (packet._type) {
            default:
                return new Packet(PacketType.REJECT, "Message type not recognised.");

            case REJECT:                                                                                                // Client cannot request the server with a response response. ACK and REJ are rejected as not being requests.
            case ACKNOWLEDGE:
                return new Packet(PacketType.REJECT, "Packet recieved was not a request.");

            case PING:
                return new Packet(PacketType.ACKNOWLEDGE, "Pong!");                                         // PING is acknowledged

            case REFUND:
            case ORDER:                                                                                                 // Recieved an order, handle. Return REJ or ACK depending on if the order is accepted.
                return new Packet(PacketType.REJECT, "Server not yet equipt to handle this request.");     // Temporary, not implemented.
        }
    }
    //#endregion
}
