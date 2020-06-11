package it.polimi.ingsw.network.client;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.PrintWriter;

public abstract class Client<T> {

    private final String ip;
    private final int port;
    private boolean active = true;
    private ObjectInputStream socketIn;
    private PrintWriter socketOut;

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

    public abstract Thread asyncReadFromSocket();

    public abstract Thread asyncWriteToSocket(T object);

    public abstract void run() throws IOException;

}
