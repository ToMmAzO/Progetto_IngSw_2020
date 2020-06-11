package it.polimi.ingsw.view.gui;

import it.polimi.ingsw.network.client.Client;
import it.polimi.ingsw.view.gui.panels.*;

import javax.swing.*;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.NoSuchElementException;

public class Gui extends Client<String> {

    public Gui(String ip, int port){
        super(ip, port);
        new PanelManager(this);
    }

    public Thread asyncReadFromSocket(){
        Thread t = new Thread(() -> {
            try{
                while(isActive()){
                    Object inputObject = getSocketIn().readObject();
                    PanelManager.getInstance().readObject(inputObject);
                }
            } catch(Exception e){
                setActive(false);
                e.printStackTrace();
            }
        });
        t.start();
        return t;
    }

    public Thread asyncWriteToSocket(String instruction){
        Thread t = new Thread(() -> {
            try{
                getSocketOut().println(instruction);
                getSocketOut().flush();
            } catch(Exception e){
                setActive(false);
            }
        });
        t.start();
        return t;
    }

    public void run() throws IOException {
        Socket socket = new Socket(getIp(), getPort());
        try(socket){
            setSocketIn(new ObjectInputStream(socket.getInputStream()));
            setSocketOut(new PrintWriter(socket.getOutputStream()));
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
            getSocketIn().close();
            getSocketOut().close();
        }
    }

}