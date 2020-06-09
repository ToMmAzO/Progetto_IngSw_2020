package it.polimi.ingsw.view.gui;

import it.polimi.ingsw.model.board.MapCopy;
import it.polimi.ingsw.model.cards.DeckCopy;
import it.polimi.ingsw.model.cards.God;
import it.polimi.ingsw.model.game.GameState;
import it.polimi.ingsw.model.player.Color;
import it.polimi.ingsw.network.message.*;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
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
    private CardChoice cardChoice;
    private Table table;

    private Color color;
    private DeckCopy deck;
    private MapCopy map;
    private GameState gameState;

    boolean notFirst = false;

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
        if(inputObject instanceof GameState){
            gameState = ((GameState)inputObject);
            System.out.println(Gui.getInstance().getGameState().toString());
            switch ((GameState)inputObject){
                case WELCOME_FIRST ->{
                    gameFrame.add(welcomeFirst);
                    welcome.setVisible(false);
                    gameFrame.setSize(600,600);
                    gameFrame.setLocation(400,20);
                    welcomeFirst.setVisible(true);
                }
                case CARD_CHOICE -> {
                    God[] cards = deck.getCardsSelected();
                    if (cards.length == 3) {
                        gameFrame.add(cardChoice = new CardChoice(cards[0], cards[1], cards[2], deck.getAvailability()));
                    }
                    if (cards.length == 2) {
                        gameFrame.add(cardChoice = new CardChoice(cards[0], cards[1], deck.getAvailability()));
                    }
                    welcome.setVisible(false);
                    welcomeFirst.setVisible(false);
                    gameFrame.setSize(600, 600);
                    gameFrame.setLocation(400, 20);
                    cardChoice.setVisible(true);

                }
                case SET_WORKER -> {
                    if(!notFirst) {
                        table = new Table();
                        gameFrame.add(table);
                        cardChoice.setVisible(false);
                        gameFrame.setSize(1280, 755);
                        gameFrame.setLocationRelativeTo(null);
                        table.setVisible(true);
                        notFirst = true;
                    }
                }
                case QUESTION_ARTEMIS,QUESTION_ATLAS,QUESTION_DEMETER,QUESTION_HESTIA,
                        QUESTION_HEPHAESTUS,QUESTION_PROMETHEUS,QUESTION_TRITON-> {
                    Message message;
                    switch ((GameState)inputObject){
                        case QUESTION_PROMETHEUS -> message = new Message_QuestionPrometheus();
                        case QUESTION_TRITON -> message = new Message_QuestionTriton();
                        case QUESTION_ARTEMIS -> message = new Message_QuestionArtemis();
                        case QUESTION_ATLAS -> message = new Message_QuestionAtlas();
                        case QUESTION_HEPHAESTUS -> message = new Message_QuestionHephaestus();
                        case QUESTION_DEMETER -> message = new Message_QuestionDemeter();
                        case QUESTION_HESTIA -> message = new Message_QuestionHestia();
                        default -> message = new Message_Error();
                    }
                    QuestionWindow question = new QuestionWindow(message);
                    question.setVisible(true);
                    question.setSize(580,300);
                    question.setLocationRelativeTo(gameFrame);
                    gameFrame.add(question);

                }
            }
        } else if(inputObject instanceof MapCopy){
            map = ((MapCopy)inputObject);
            if(notFirst) {
                table.updateMap();
            }
        } else if(inputObject instanceof DeckCopy) {
            deck = ((DeckCopy) inputObject);
        } else if(inputObject instanceof String){
            /*JDialog wait = new JDialog();
            wait.setModalityType(Dialog.ModalityType.APPLICATION_MODAL);

            JButton button = new JButton ("OK");
            button.addActionListener (e1 -> wait.setVisible(false));

            JPanel panel = new JPanel();

            panel.add(new JLabel ((String) inputObject));
            panel.add(button);
            panel.setSize(200, 200);

            wait.add(panel);
            wait.dispose();

            wait.setLocation(700,375);
            wait.pack();
            wait.setVisible(true); */
        } else if(inputObject instanceof Color){
            color = ((Color) inputObject);
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

    private void start() {
        javax.swing.SwingUtilities.invokeLater(() -> {
            JWindow splashScreen = new JWindow();
            splashScreen.addWindowListener(new WindowAdapter() {
                private boolean closed = false;
                public void windowOpened(WindowEvent e) {
                    startBackgroundInitialization(e.getWindow());
                }
                public void windowClosed(WindowEvent e) {
                    if(!closed) {
                        closed = true;
                        try {
                            showMainFrame();
                        } catch (IOException ioException) {
                            ioException.printStackTrace();
                        }
                    }
                }
            });

            JLabel label = new JLabel();
            final Image image;
            try {
                image = ImageIO.read(new File("src/main/java/it/polimi/ingsw/view/gui/img/santorini-logo.png"));
                label.setSize(400, 130);
                ImageIcon img = new ImageIcon(image);
                label.setIcon(img);
            } catch (IOException e) {
                e.printStackTrace();
            }

            splashScreen.setLayout(new GridBagLayout());
            splashScreen.add(label, new GridBagConstraints());
            splashScreen.setSize(400, 130);
            splashScreen.setLocationRelativeTo(null);
            splashScreen.setVisible(true);

        });
    }

    private void startBackgroundInitialization(final Window splashScreen) {
        new Thread(() -> {
            try {
                Thread.sleep(1000);
            } catch(InterruptedException e) {
                e.printStackTrace();
            } finally {
                disposeWindow(splashScreen);
            }
        }).start();
    }

    private void disposeWindow(final Window window) {
        EventQueue.invokeLater(window::dispose);
    }

    private void showMainFrame() throws IOException {
        gameFrame = new JFrame("SANTORINI");
        gameFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        gameFrame.setResizable(false);

        welcome = new Welcome();
        welcomeFirst = new WelcomeFirst();
        gameFrame.add(welcome);

        //CHIUSURA
        /*
        gameFrame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                int answer = JOptionPane.showConfirmDialog(
                        e.getWindow(),
                        "Vuoi veramente chiudere la finestra?",
                        "Conferma chiusura",
                        JOptionPane.YES_NO_OPTION);
                if(answer == JOptionPane.YES_OPTION) {
                    e.getWindow().dispose();
                }
            }
        });
        */

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

    public Color getColor() {
        return color;
    }

    public GameState getGameState() {
        return gameState;
    }

    public MapCopy getMap(){
        return map;
    }

}