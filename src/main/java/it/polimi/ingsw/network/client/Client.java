package it.polimi.ingsw.network.client;

import it.polimi.ingsw.model.Board.Map;
import it.polimi.ingsw.model.Cards.Deck;
import it.polimi.ingsw.model.Cards.God;
import it.polimi.ingsw.model.Player.Player;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class Client {

    private final String ip;
    private final int port;

    public Client(String ip, int port){
        this.ip = ip;
        this.port = port;
    }

    private boolean active = true;

    public synchronized boolean isActive(){
        return active;
    }

    public synchronized void setActive(boolean active){
        this.active = active;
    }

    public Thread asyncReadFromSocket(final ObjectInputStream socketIn){
        Thread t = new Thread(() -> {
            try{
                while(isActive()){
                    Object inputObject = socketIn.readObject();
                    if(inputObject instanceof String){
                        System.out.println((String)inputObject);
                    } else if(inputObject instanceof Deck){
                        Deck.printCards();
                    } else if(inputObject instanceof God) {
                        God.printCardChosen(((God)inputObject));
                    } else if(inputObject instanceof Map){
                        Map.print();
                    } else if(inputObject instanceof Player) {
                        ((Player)inputObject).printWorkersPositions();
                    } else{
                        throw new IllegalArgumentException();
                    }
                }
            } catch(Exception e){
                setActive(false);
            }
        });
        t.start();
        return t;
    }

    public Thread asyncWriteToSocket(final Scanner stdin, final PrintWriter socketOut){
        Thread t = new Thread(() -> {
            try{
                while(isActive()) {
                    String inputLine = stdin.nextLine();
                    socketOut.println(inputLine);
                    socketOut.flush();
                }
            } catch(Exception e){
                setActive(false);
            }
        });
        t.start();
        return t;
    }

    public void run() throws IOException {
        Socket socket = new Socket(ip, port);
        System.out.println("Connection established");
        ObjectInputStream socketIn = new ObjectInputStream(socket.getInputStream());
        PrintWriter socketOut = new PrintWriter(socket.getOutputStream());
        Scanner stdin = new Scanner(System.in);
        try{
            Thread t0 = asyncReadFromSocket(socketIn);
            Thread t1 = asyncWriteToSocket(stdin, socketOut);
            t0.join();
            t1.join();
        } catch(InterruptedException | NoSuchElementException e){
            System.out.println("Connection closed from the client side");
        } finally{
            stdin.close();
            socketIn.close();
            socketOut.close();
            socket.close();
        }
    }

}
