package network.packet;

import java.io.Serializable;

/**
 * Packet
 */
public enum PacketType implements Serializable {
    PING,
    ORDER,
    REFUND,
    ACKNOWLEDGE,
    REJECT,
    MENU_REQUEST    // Client reqeust copy of menu from the server
}
