package it.polimi.ingsw.network;

import it.polimi.ingsw.view.cli.Cli;

import java.io.IOException;

public class ClientApp {
    public static void main(String[] args){
        Cli cli = new Cli("127.0.0.1", 12345);
        try{
            cli.run();
        } catch(IOException e){
            System.err.println(e.getMessage());
        }
    }

}
