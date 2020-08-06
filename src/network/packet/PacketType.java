package network.packet;

import java.io.Serializable;

/**
 * Types of packet messages or requests.
 * @author Jordan Gray
 * @since 0.1.1
 * @version 1
 */
public enum PacketType implements Serializable {
    PING,
    ORDER,
    REFUND,
    ACKNOWLEDGE,
    REJECT,
    MENU_REQUEST    // Client reqeust copy of menu from the server
}
