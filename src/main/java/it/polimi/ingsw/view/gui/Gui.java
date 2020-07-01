package it.polimi.ingsw.view.gui;

import it.polimi.ingsw.network.client.Client;
import it.polimi.ingsw.view.gui.panels.*;

import javax.swing.*;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.NoSuchElementException;

/**
 * This class derives from Client and implements its methods.
 */
public class Gui extends Client<String> {

    /**
     * This constructor performs the Client constructor, but also initializes the manager of graphic interface.
     *
     * @param ip is the server IP address.
     * @param port is the server port to which this client should connect.
     */
    public Gui(String ip, int port){
        super(ip, port);
        new PanelManager(this);
    }

    /**
     * This method starts a new thread for reading object from the socket and forwards it to PanelManager.
     * Thread stays active until the connection between client and server is closed or occurs an error.
     *
     * @return the thread started.
     */
    public Thread asyncReadFromSocket(){
        Thread t = new Thread(() -> {
            try{
                while(isActive()){
                    Object inputObject = getSocketIn().readObject();
                    PanelManager.getInstance().readObject(inputObject);
                }
            } catch(Exception e){
                setActive(false);
            }
        });
        t.start();
        return t;
    }

    /**
     * This method starts a new thread for writing to the socket and this thread finishes after sending the message.
     *
     * @param instruction is the message content.
     * @return the thread started.
     */
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

    /**
     * This method tries to connect to te server. After doing that, it configures its functionalities and starts the graphic interface.
     *
     * @throws IOException for connection errors with socket.
     */
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