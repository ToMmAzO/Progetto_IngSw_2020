package it.polimi.ingsw.view.gui;

import it.polimi.ingsw.network.client.Client;
import it.polimi.ingsw.view.gui.panels.*;

import javax.swing.*;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.NoSuchElementException;

public class Gui implements Client<String> {

    private final String ip;
    private final int port;
    private boolean active = true;
    private ObjectInputStream socketIn;
    private PrintWriter socketOut;

    public Gui(String ip, int port){
        this.ip = ip;
        this.port = port;
        new PanelManager(this);
    }

    public Thread asyncReadFromSocket(){
        Thread t = new Thread(() -> {
            try{
                while(active){
                    Object inputObject = socketIn.readObject();
                    PanelManager.getInstance().readObject(inputObject);
                }
            } catch(Exception e){
                active = false;
                e.printStackTrace();
            }
        });
        t.start();
        return t;
    }

    public Thread asyncWriteToSocket(String instruction){
        Thread t = new Thread(() -> {
            try{
                socketOut.println(instruction);
                socketOut.flush();
            } catch(Exception e){
                active = false;
            }
        });
        t.start();
        return t;
    }

    public void run() throws IOException {
        Socket socket = new Socket(ip, port);
        try(socket){
            socketIn = new ObjectInputStream(socket.getInputStream());
            socketOut = new PrintWriter(socket.getOutputStream());
            Thread t0 = asyncReadFromSocket();
            SwingUtilities.invokeLater(() -> {
                try {
                    PanelManager.getInstance().start();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
            t0.join();
        } catch (InterruptedException | NoSuchElementException e) {
            System.out.println("Connection closed!");
        } finally {
            socketIn.close();
            socketOut.close();
        }
    }

}