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
    FATAL_SERVER_RESOLVE_FAILED        // Server failed to bind to host address. Fatal exception, server need to bind to network.
}
