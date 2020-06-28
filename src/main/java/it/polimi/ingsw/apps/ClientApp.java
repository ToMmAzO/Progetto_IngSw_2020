package it.polimi.ingsw.apps;

import it.polimi.ingsw.network.client.Client;
import it.polimi.ingsw.view.cli.Cli;
import it.polimi.ingsw.view.gui.Gui;

import java.io.IOException;
import java.net.InetAddress;

/**
 * This is the executable client app.
 * Arguments "-cli" or "-gui" are used to set the user interface, if omitted the UI will be Gui.
 * By default the port used to establish connection with the server is 12345.
 */
public class ClientApp {

    public static void main(String[] args) throws IOException {
        InetAddress inetAddress = InetAddress.getLocalHost();
        String ip = inetAddress.getHostAddress();
        boolean isGui = true;
        try{
            for(String arg : args){
                switch(arg){
                    case "-cli" -> isGui = false;
                    case "-gui" -> isGui = true;
                }
            }
        } catch(ArrayIndexOutOfBoundsException e){
            System.out.println("Wrong parameters!");
            return;
        }
        Client<?> client;
        if(isGui){
            client = new Gui(ip, 12345);
        } else{
            client = new Cli(ip, 12345);
        }
        client.run();
    }

}
