package it.polimi.ingsw.network.client;

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
    private final Cli cli = new Cli();

    public Client(String ip, int port){
        this.ip = ip;
        this.port = port;
    }

    public synchronized void setActive(boolean active){
        this.active = active;
    }

    public synchronized boolean isActive(){
        return active;
    }

    public Thread asyncReadFromSocket(final ObjectInputStream socketIn){
        Thread t = new Thread(() -> {
            try{
                while(isActive()){
                    Object inputObject = socketIn.readObject();
                    cli.readObject(inputObject);
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
        System.out.println("""
                            ----------------------------------------------------------------------------------------------------------------------------------------------------
                            ||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||
                            ----------------------------------------------------------------------------------------------------------------------------------------------------
                            |||||              ________          __                       __________      ________       ________                                          |||||\s
                            |||||             //                //\\\\        ||\\\\   ||         ||         ||      ||     ||      \\\\     ||     ||\\\\   ||     ||             |||||\s
                            |||||             \\\\_______        //__\\\\       || \\\\  ||         ||         ||      ||     ||______//     ||     || \\\\  ||     ||             |||||\s
                            |||||                     \\\\      //    \\\\      ||  \\\\ ||         ||         ||      ||     ||    \\\\       ||     ||  \\\\ ||     ||             |||||\s
                            |||||              _______//     //      \\\\     ||   \\\\||         ||         ||______||     ||     \\\\      ||     ||   \\\\||     ||             |||||\s
                            |||||                                                                                                                                          |||||\s
                            ----------------------------------------------------------------------------------------------------------------------------------------------------
                            ||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||
                            ----------------------------------------------------------------------------------------------------------------------------------------------------
                            """
        );
        ObjectInputStream socketIn = new ObjectInputStream(socket.getInputStream());
        PrintWriter socketOut = new PrintWriter(socket.getOutputStream());
        Scanner stdin = new Scanner(System.in);
        try{
            Thread t0 = asyncReadFromSocket(socketIn);
            Thread t1 = asyncWriteToSocket(stdin, socketOut);
            System.out.print("What's your name? ");
            t0.join();
            t1.join();
        } catch(InterruptedException | NoSuchElementException e){
            System.out.println("Connection closed!");
        } finally{
            stdin.close();
            socketIn.close();
            socketOut.close();
            socket.close();
        }
    }

}
