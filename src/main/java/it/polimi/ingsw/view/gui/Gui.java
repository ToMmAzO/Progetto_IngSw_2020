package it.polimi.ingsw.view.gui;

import it.polimi.ingsw.model.board.MapCopy;
import it.polimi.ingsw.model.game.GameState;

import javax.swing.*;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.NoSuchElementException;

public class Gui {

    private static Gui gui;
    private final String ip;
    private final int port;
    private boolean active = true;
    private ObjectInputStream socketIn;
    private PrintWriter socketOut;

    private JFrame gameFrame;
    private Welcome welcome;
    private WelcomeFirst welcomeFirst;
    private Table table;

    public Gui(String ip, int port){
        gui = this;
        this.ip = ip;
        this.port = port;
    }

    public static Gui getInstance(){
        return gui;
    }

    public synchronized void setActive(boolean active){
        this.active = active;
    }

    public synchronized boolean isActive(){
        return active;
    }

    public Thread asyncReadFromSocket(){
        Thread t = new Thread(() -> {
            try{
                while(isActive()){
                    Object inputObject = socketIn.readObject();
                    readObject(inputObject);
                }
            } catch(Exception e){
                setActive(false);
            }
        });
        t.start();
        return t;
    }

    private void readObject(Object inputObject) throws IOException {

        //fai cose con quello che manda il server
        System.out.println(inputObject.toString());
        if(inputObject instanceof GameState){
            switch ((GameState)inputObject){
                case WELCOME_FIRST ->{
                    gameFrame.add(welcomeFirst = new WelcomeFirst());
                    welcome.setVisible(false);
                    gameFrame.setSize(600,600);
                    gameFrame.setLocation(400,20);
                    welcomeFirst.setVisible(true);
                }
                case CARD_CHOICE -> {}
                case SET_WORKER -> {
                    gameFrame.add(table = new Table());
                    table.setGameState((GameState)inputObject);
                    welcomeFirst.setVisible(false);
                    gameFrame.setSize(1280,755);
                    gameFrame.setLocationRelativeTo(null);
                    table.setVisible(true);
                }
                default -> table.setGameState((GameState)inputObject);  //mappa
            }
        } else if(inputObject instanceof MapCopy){
            table.setMap((MapCopy)inputObject);
        } else{
            throw new IllegalArgumentException();
        }

    }

    public void asyncWriteToSocket(String instruction){
        Thread t = new Thread(() -> {
            try{
                socketOut.println(instruction);
                socketOut.flush();
            } catch(Exception e){
                setActive(false);
            }
        });
        t.start();
    }

    private void start() throws IOException {
        gameFrame = new JFrame("SANTORINI");
        gameFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        welcome = new Welcome();
        gameFrame.add(welcome);
        gameFrame.setVisible(true);
        gameFrame.setSize(600,600);
        gameFrame.setLocation(400,20);

        //gameFrame.pack();     //la finestra assuma le dimensioni minime necessarie e sufficienti affinchè ciò che contiene sia visualizzato secondo le sue dimensioni ottimali
        gameFrame.validate();
    }

    public void run() throws IOException {
        Socket socket = new Socket(ip, port);
        try(socket){
            socketIn = new ObjectInputStream(socket.getInputStream());
            socketOut = new PrintWriter(socket.getOutputStream());
            Thread t0 = asyncReadFromSocket();
            SwingUtilities.invokeLater(() -> {
                try {
                    start();
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