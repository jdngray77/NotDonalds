package util;

/**
 * Standardised Runtime Halt Codes.
 *
 * When parsed to JVM with Runtime.Halt, the ordinal of one of these values becomes the halt code.
 * The ordinal is essentailly the number in the list,
 * default = 0, etc.
 */
public enum HaltCodes {
    UNMANAGED_HALT,                    // Ambiguous with JVM default halt code. When runtime closes without parsing a code it will default to this, code 0.
    INTENDED_HALT,                     // Intended, controlled shutdown of some kind. i.e POS or Server has been requested to shutdown.
    FATAL_SERVER_RESOLVE_FAILED,       // Server failed to resolve ipv4 host address. Fatal exception, server needs an address to bind.
    FATAL_SERVER_BIND_FAILED;          // Server failed to attach to specified address and port. Address is not valid or accessable OR port is in use already.
}
