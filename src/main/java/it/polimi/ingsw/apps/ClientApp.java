package it.polimi.ingsw.apps;

import it.polimi.ingsw.network.client.Client;
import it.polimi.ingsw.view.cli.Cli;
import it.polimi.ingsw.view.gui.Gui;

import java.io.IOException;

/**
 * This is the executable client app.
 * To run it, some arguments are needed:
 *      - "cli" or "gui" to set the user interface
 *      - client IP
 * By default the port used to establish connection with the server is 12345.
 */
public class ClientApp {

    public static void main(String[] args) throws IOException {
        String ip = null;
        boolean isGui = true;
        try{
            for (int i = 0; i < args.length; i++) {
                switch(args[i]) {
                    case "cli" -> isGui = false;
                    case "gui" -> isGui = true;
                    case "-ip" -> ip = args[i + 1];
                }
            }
        } catch(ArrayIndexOutOfBoundsException e){
            System.out.println("Wrong parameters!");
            return;
        }
        if(ip == null) {
            System.out.println("IP needed, type -ip [your_ip]");
            return;
        }
        Client client;
        if(isGui){
            client = new Gui(ip, 12345);
        } else{
            client = new Cli(ip, 12345);
        }
        client.run();
    }

}
