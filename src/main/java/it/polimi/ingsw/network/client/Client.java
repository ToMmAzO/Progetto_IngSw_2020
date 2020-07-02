package it.polimi.ingsw.network.client;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.PrintWriter;

/**
 * Cli and Gui classes derive from this class, indeed its attributes are the same for these two classes.
 *
 * @param <T> is used to specify what attribute must be used for write Objects to socket.
 */
public abstract class Client<T> {

    private final String ip;
    private final int port;
    private boolean active = true;
    private ObjectInputStream socketIn;
    private PrintWriter socketOut;

    /**
     * @param ip is the server IP address.
     * @param port is the server port to which this client should connect.
     */
    public Client(String ip, int port){
        this.ip = ip;
        this.port = port;
    }

    public String getIp(){
        return ip;
    }

    public int getPort(){
        return port;
    }

    public synchronized void setActive(boolean active){
        this.active = active;
    }

    public synchronized boolean isActive(){
        return active;
    }

    public void setSocketIn(ObjectInputStream socketIn) {
        this.socketIn = socketIn;
    }

    public ObjectInputStream getSocketIn() {
        return socketIn;
    }

    public void setSocketOut(PrintWriter socketOut) {
        this.socketOut = socketOut;
    }

    public PrintWriter getSocketOut() {
        return socketOut;
    }

    /**
     * This method is used for reading Objects from socket. A new thread is used to do that.
     *
     * @return the thread created.
     */
    public abstract Thread asyncReadFromSocket();

    /**
     * This method is used for writing Objects from socket. A new thread is used to do that.
     *
     * @param object for Cli is Scanner, for Gui is String.
     * @return the thread created.
     */
    public abstract Thread asyncWriteToSocket(T object);

    /**
     * This method starts the execution of Client and tries to connect to server socket.
     *
     * @throws IOException for connection errors with socket.
     */
    public abstract void run() throws IOException;

}
