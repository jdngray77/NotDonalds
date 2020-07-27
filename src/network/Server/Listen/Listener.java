package network.Server.Listen;

import network.Server.Packet;
import util.Logger;

import java.io.IOException;
import java.io.InvalidClassException;
import java.io.ObjectInputStream;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.Socket;

/**
 * Abstract Network Listener Thread for the server
 */
public class Listener implements Runnable {

    public Listener(Inet4Address _ip, int _port) {
        ip = _ip;
        port = _port;
    }

    private Socket socket;

    ObjectInputStream in;

    private Thread thread;

    public Thread getThread = thread;

    private boolean active = false;

    private void setActive(boolean f) {active = f;}

    protected void kill(){setActive(false);}

    private InetAddress ip;

    private int port;


    private void init(){
        thread = Thread.currentThread();

        try {
            Logger.internal(this, "New Socket waiting for connection");
            socket = new Socket(ip, port);
            Logger.internal(this, "New connection on port " + port + ". Waiting to recieve");
            in = new ObjectInputStream(socket.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    private void read(){
        while (active){
            try {
                Packet recieved = (Packet) in.readObject();

                //TODO
            } catch (InvalidClassException e) {     // Object was not valid
            } catch (IOException e) {               // Read error
            } catch (ClassNotFoundException e) {    // Nothing found
            }
        }
    }

    @Override
    public void run() {
        setActive(true);
        init();
        read();
    }
}
