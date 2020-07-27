package network;

import java.io.Serializable;

public enum MessageType implements Serializable {
    PREAMBLE,
    ORDER,
    ACKNOWLEDGE,
    REJECT
}
