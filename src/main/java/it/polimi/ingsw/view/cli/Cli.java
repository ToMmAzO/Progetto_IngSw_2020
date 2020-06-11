package it.polimi.ingsw.view.cli;

import it.polimi.ingsw.model.board.MapCopy;
import it.polimi.ingsw.model.cards.DeckCopy;
import it.polimi.ingsw.model.game.GameState;
import it.polimi.ingsw.model.player.Color;
import it.polimi.ingsw.network.client.Client;
import it.polimi.ingsw.network.message.*;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class Cli extends Client<Scanner> {

    public Cli(String ip, int port){
        super(ip, port);
    }

    public Thread asyncReadFromSocket(){
        Thread t = new Thread(() -> {
            try{
                while(isActive()){
                    Object inputObject = getSocketIn().readObject();
                    if(inputObject instanceof String){
                        System.out.println((String)inputObject);
                    } else if(inputObject instanceof GameState){
                        runState((GameState)inputObject);
                    } else if(inputObject instanceof Color){
                        System.out.println("Your color is " + inputObject.toString() + ".");
                    } else if(inputObject instanceof DeckCopy){
                        ((DeckCopy)inputObject).printCards();
                    } else if(inputObject instanceof MapCopy){
                        ((MapCopy)inputObject).print();
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

    public Thread asyncWriteToSocket(Scanner stdin){
        Thread t = new Thread(() -> {
            try{
                while(isActive()) {
                    String inputLine = stdin.nextLine();
                    getSocketOut().println(inputLine);
                    getSocketOut().flush();
                }
            } catch(Exception e){
                setActive(false);
            }
        });
        t.start();
        return t;
    }

    public void run() throws IOException {
        Socket socket = new Socket(getIp(), getPort());
        System.out.println("""
                            ----------------------------------------------------------------------------------------------------------------------------------------------------
                            ||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||
                            ----------------------------------------------------------------------------------------------------------------------------------------------------
                            |||||              ________          __         ___    __     __________      ________       ________      __     ___    __     __             |||||\s
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
        setSocketIn(new ObjectInputStream(socket.getInputStream()));
        setSocketOut(new PrintWriter(socket.getOutputStream()));
        Scanner stdin = new Scanner(System.in);
        try{
            Thread t0 = asyncReadFromSocket();
            Thread t1 = asyncWriteToSocket(stdin);
            System.out.print("What is your name? ");
            t0.join();
            t1.join();
        } catch(InterruptedException | NoSuchElementException e){
            System.out.println("Connection closed!");
        } finally{
            stdin.close();
            getSocketIn().close();
            getSocketOut().close();
            socket.close();
        }
    }

    private void runState(GameState state){
        Message message;
        switch (state){
            case WELCOME_FIRST -> message = new Message_WelcomeFirst();
            case WAIT_PLAYERS -> message = new Message_WaitPlayers();
            case WAIT_CARD_CHOICE -> message = new Message_WaitCardChoice();
            case WAIT_TURN -> message = new Message_WaitTurn();
            case CARD_CHOICE -> message = new Message_CardChoice();
            case SET_WORKER -> message = new Message_SetWorker();
            case WORKER_CHOICE -> message = new Message_WorkerChoice();
            case QUESTION_PROMETHEUS -> message = new Message_QuestionPrometheus();
            case PREBUILD_PROMETHEUS -> message = new Message_PrebuildPrometheus();
            case MOVEMENT -> message = new Message_Movement();
            case QUESTION_TRITON -> message = new Message_QuestionTriton();
            case QUESTION_ARTEMIS -> message = new Message_QuestionArtemis();
            case QUESTION_ATLAS -> message = new Message_QuestionAtlas();
            case CONSTRUCTION_CUPOLA -> message = new Message_ConstructionCupola();
            case SECOND_MOVE -> message = new Message_SecondMove();
            case QUESTION_HEPHAESTUS -> message = new Message_QuestionHephaestus();
            case DOUBLE_CONSTRUCTION -> message = new Message_DoubleConstruction();
            case QUESTION_DEMETER -> message = new Message_QuestionDemeter();
            case SECOND_CONSTRUCTION_DEMETER -> message = new Message_SecondConstructionDemeter();
            case QUESTION_HESTIA -> message = new Message_QuestionHestia();
            case SECOND_CONSTRUCTION_HESTIA -> message = new Message_SecondConstructionHestia();
            case CONSTRUCTION -> message = new Message_Construction();
            default -> message = new Message_Error();
        }
        System.out.print(message.getMessage());
    }

}
