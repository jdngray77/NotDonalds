package network.packet;

import util.Helper;

import java.io.Serializable;

/**
 * Serializable network data object.
 *
 * Basis for all client > server relations for NotDonalds.
 * @author Jordan Gray
 * @since 0.1.0
 * @version 1
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
    public PacketType type = _type;

    //#endregion

    /**
     * Creates a new packet
     * @param _type - Message type contained within this packet
     */
    public Packet(PacketType _type){
        this._type = _type;
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
            case REJECT:                                    // Client cannot request with a response. ACK and REJ are rejected.
            case ACKNOWLEDGE:
                return new Packet(PacketType.REJECT);

            case PING:
                return new Packet(PacketType.ACKNOWLEDGE); // PING is acknowledged

            case ORDER:                                     // Recieved an order, handle. Return REJ or ACK depending on if the order is accepted.
                return new Packet(PacketType.REJECT);      // Temporary, not implemented.

            case REFUND:
                return new Packet(PacketType.REJECT);      // Temporary, not implemented.

        }

        Helper.Log("[Packet]", "[!! WARN !!] packet processing did not return a response. Defaulting to REJECT");
        return new Packet(PacketType.REJECT);              // This should never be allowed to be reached.
    }
    //#endregion
}
