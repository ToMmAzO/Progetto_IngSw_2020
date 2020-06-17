package it.polimi.ingsw.apps;

import it.polimi.ingsw.view.gui.Gui;

import java.io.IOException;

public class ClientApp_Gui {

    public static void main(String[] args){
        Gui gui = new Gui("127.0.0.1", 12345);
        try{
            gui.run();
        } catch(IOException e){
            System.err.println(e.getMessage());
        }
    }

}
