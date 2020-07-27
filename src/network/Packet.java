package network;

import util.Helper;

import java.io.Serializable;
import java.util.logging.Logger;

public class Packet implements Serializable {

    public Packet(MessageType _type){
        type = _type;
    }

    MessageType type;

    public static Packet processResponse(Packet packet) {
        if (packet == null) return new Packet(MessageType.REJECT);

        switch (packet.type) {
            case ACKNOWLEDGE:
                return new Packet(MessageType.ACKNOWLEDGE);

            case ORDER:
                break;
            case PREAMBLE:
                break;
            case REJECT:
                return new Packet(MessageType.ACKNOWLEDGE);
        }

        Helper.internal("[Packet]", "[!! WARN !!] packet processing did not return a response. Defaulting to REJECT");
        return new Packet(MessageType.REJECT);                                                                          // This should never be allowed to be reached.
    }
}
