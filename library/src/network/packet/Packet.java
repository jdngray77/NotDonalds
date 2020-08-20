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
     * Can be used for descriptions or other human friendly messages. Not for packet data.
     */
    public String metaMessage = "";

    /**
     * The message type expected to be found in this packet
     */
    private PacketType _type;

    /**
     * Serialized data contained within the packet.
     */
    private Serializable PacketData;

    /**
     * Get data serialized within this packet.
     * @return Null if no data, Othersize serialized object.
     *
     * @apiNote Cast type should be infered from message type, e.g if client requests a Menu, the returned serialized data will be a Menu object.
     */
    public Serializable getPacketData() {
        return PacketData;
    }

    /**
     * returns the type of this packet's message
     */
    public PacketType type() {return _type;}
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
        this(type, null, _metaMessage);
    }

    /**
     * Creates a new packet
     * @param type - Message type contained within this packet
     * @param data - Serialized object sent within this packet.
     */
    public Packet(PacketType type, Serializable data){
        this(type, data, "");
    }

    /**
     * Creates a new packet
     * @param type - Message type contained within this packet
     * @param data - Serialized object sent within this packet.
     * @param _metaMessage Human friendly message about this packet.
     */
    public Packet(PacketType type, Serializable data ,String _metaMessage){
        _type = type;
        metaMessage = _metaMessage;
        PacketData = data;
    }

}
