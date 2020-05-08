package it.polimi.ingsw.network.client;

import it.polimi.ingsw.model.board.Map;
import it.polimi.ingsw.model.cards.Deck;
import it.polimi.ingsw.model.cards.God;
import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.network.message.Message;
import it.polimi.ingsw.network.message.Message_LoadState;
import it.polimi.ingsw.view.cli.Cli;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class Client {

    private final String ip;
    private final int port;
    private boolean active = true;
    private Cli cli = new Cli();

    public Client(String ip, int port){
        this.ip = ip;
        this.port = port;
    }

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
                    if(inputObject instanceof Message_LoadState){
                        //Cli.loadState(((Message_LoadState) inputObject).getToLoad());
                        //Cli.runState();

                        /*quando il client stabilisce la connessione il server manda come primo messaggio di ritorno un Message_LoadState con il
                        * quale dice al Client come impostare il proprio GameState.
                        * se la richiesta di connessione è la prima, il server risponderà con un Message_LoadState.toLoad = WELCOME_FIRST,
                        * altrimenti WAIT.
                        * inoltre, in questo modo possiamo:
                        * 1) una volta che il primo ha scelto il numero di giocatori far cambiare lo stato ai Client facendogli ricevere un
                        *    Message_LoadState.toLoad = CARD_CHOICE, in questo modo i Client che riceveranno il messaggio aggiorneranno il proprio
                        *    playerState a CARD_CHOICE
                        * 2) nel caso in cui ci fosse un errore durante il gioco perchè il playerState lato Client fosse diverso dal GameState lato server,
                        *    il server potrebbe notificare al client quale stato ha lui in memoria, così da risintonizzarsi nello stesso stato
                        *  */
                    } else if(inputObject instanceof Message){
                        cli.setPlayerState(((Message)inputObject).getGameState());
                        ((Message)inputObject).printMessage();
                    } else if(inputObject instanceof String){
                        System.out.println((String)inputObject);
                    } else if(inputObject instanceof Deck){
                        ((Deck)inputObject).printCards();
                    } else if(inputObject instanceof God){
                        God.printCardChosen(((God)inputObject));
                    } else if(inputObject instanceof Map){
                        ((Map)inputObject).print();
                        cli.runState();
                        cli.nextState();
                    } else if(inputObject instanceof Player){
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
