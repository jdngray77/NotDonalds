package network.Server

import network.Server.Client

/**
 * Client Session
 *
 * @author Jordan Gray
 * @since 0.1.0
 * @version 1
 */
class Session(_id: Int, _client: Client) {

    /**
     * ID of this session
     */
    val id: Int = _id

    /**
     * Client related to this session
     */
    val client: Client = _client
}