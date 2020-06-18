package it.polimi.ingsw.view.gui.panels;

import it.polimi.ingsw.model.board.MapCopy;
import it.polimi.ingsw.model.cards.DeckCopy;
import it.polimi.ingsw.model.cards.God;
import it.polimi.ingsw.model.game.GameState;
import it.polimi.ingsw.model.player.Color;
import it.polimi.ingsw.network.message.*;
import it.polimi.ingsw.view.gui.Gui;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.IOException;

public class PanelManager {

    private static PanelManager panelManager;
    private final Gui gui;

    private JFrame gameFrame;
    private Welcome welcome;
    private WelcomeFirst welcomeFirst;
    private JLabel waiting;
    private CardChoice cardChoice;
    private Table table;

    private GameState gameState;
    private Color color;
    private DeckCopy deck;
    private God godChoice;
    private MapCopy map;

    boolean tableCreated = false;
    boolean firstWorkerIsSet = false;

    public PanelManager(Gui gui){
        panelManager = this;
        this.gui = gui;
    }

    public static PanelManager getInstance(){
        return panelManager;
    }

    public void asyncWriteToSocket(String string){
        gui.asyncWriteToSocket(string);
    }

    public void readObject(Object inputObject) throws IOException {
        if(inputObject instanceof GameState){
            gameState = ((GameState)inputObject);
            switch ((GameState)inputObject){
                case WELCOME_FIRST ->{
                    gameFrame.add(welcomeFirst);
                    welcome.setVisible(false);
                }
                case WAIT_PLAYERS -> {
                    gameFrame.add(waiting);
                    waiting.setVisible(true);
                    welcome.setVisible(false);
                    welcomeFirst.setVisible(false);
                }
                case WAIT_CARD_CHOICE -> {
                    waiting.setVisible(true);
                    cardChoice.setVisible(false);
                }
                case CARD_CHOICE -> {
                    God[] cards = deck.getCardsSelected();
                    if (cards.length == 2) {
                        gameFrame.add(cardChoice = new CardChoice(cards[0], cards[1], deck.getAvailability()));
                    }
                    if (cards.length == 3) {
                        gameFrame.add(cardChoice = new CardChoice(cards[0], cards[1], cards[2], deck.getAvailability()));
                    }
                    waiting.setVisible(false);
                }
                case SET_WORKER -> {
                    if(!firstWorkerIsSet) {
                        table.addText("It`s your turn, set your Workers on map.");
                        firstWorkerIsSet = true;
                    }
                }
                case WORKER_CHOICE -> table.addText("It`s your turn, select a Worker.");
                case QUESTION_ARTEMIS,QUESTION_ATLAS,QUESTION_DEMETER,QUESTION_HESTIA,
                        QUESTION_HEPHAESTUS,QUESTION_PROMETHEUS,QUESTION_TRITON -> {
                    Message message = null;
                    switch ((GameState)inputObject){
                        case QUESTION_ARTEMIS -> message = new Message_QuestionArtemis();
                        case QUESTION_ATLAS -> message = new Message_QuestionAtlas();
                        case QUESTION_DEMETER -> message = new Message_QuestionDemeter();
                        case QUESTION_HESTIA -> message = new Message_QuestionHestia();
                        case QUESTION_HEPHAESTUS -> message = new Message_QuestionHephaestus();
                        case QUESTION_PROMETHEUS -> message = new Message_QuestionPrometheus();
                        case QUESTION_TRITON -> message = new Message_QuestionTriton();
                    }
                    QuestionWindow question = new QuestionWindow(gameFrame, message);
                    question.setLocationRelativeTo(gameFrame);
                }
                case WIN, LOSE, INVALIDATION, ERROR -> {
                    FinalWindow window = new FinalWindow(gameFrame, (GameState)inputObject);
                    window.setLocationRelativeTo(gameFrame);
                }
            }
        } else if(inputObject instanceof Color){
            color = ((Color) inputObject);
        } else if(inputObject instanceof DeckCopy){
            deck = ((DeckCopy) inputObject);
        } else if(inputObject instanceof MapCopy) {
            map = ((MapCopy) inputObject);
            if(!tableCreated) {
                table = new Table();
                gameFrame.add(table);
                waiting.setVisible(false);
                gameFrame.setSize(1280, 755);
                gameFrame.setLocationRelativeTo(null);
                table.setVisible(true);
                tableCreated = true;
            } else{
                table.updateMap();
            }
        } else if(inputObject instanceof String string){
            if(tableCreated && !string.contains("(")){
                table.addText(string);
            }
        } else{
            throw new IllegalArgumentException();
        }
    }

    public void start() {
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
            image = ImageIO.read(new File("src/main/java/it/polimi/ingsw/view/gui/img/backgrounds/SantoriniLogo.png"));
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
    }

    private void startBackgroundInitialization(final Window splashScreen) {
        new Thread(() -> {
            try {
                Thread.sleep(1000);
            } catch(InterruptedException e) {
                e.printStackTrace();
            } finally {
                EventQueue.invokeLater(splashScreen::dispose);
            }
        }).start();
    }

    private void showMainFrame() throws IOException {
        gameFrame = new JFrame("SANTORINI");
        gameFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        gameFrame.setResizable(false);

        welcome = new Welcome();
        welcomeFirst = new WelcomeFirst();
        waiting = new JLabel(new ImageIcon("src/main/java/it/polimi/ingsw/view/gui/img/backgrounds/LoadingBackground.gif"));

        gameFrame.add(welcome);
        gameFrame.setVisible(true);
        gameFrame.setSize(600,600);
        gameFrame.setLocation(400,20);
        gameFrame.validate();
    }

    public GameState getGameState() {
        return gameState;
    }

    public Color getColor() {
        return color;
    }

    public void setGodChoice(God godChoice) {
        this.godChoice = godChoice;
    }

    public God getGodChoice() {
        return godChoice;
    }

    public MapCopy getMap(){
        return map;
    }

}
