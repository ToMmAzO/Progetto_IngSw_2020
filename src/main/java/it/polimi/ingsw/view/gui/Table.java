package it.polimi.ingsw.view.gui;

import it.polimi.ingsw.model.board.BlockType;
import it.polimi.ingsw.model.board.Map;
import it.polimi.ingsw.model.board.MapCopy;
import it.polimi.ingsw.model.game.GameState;
import it.polimi.ingsw.model.workers.WorkerDemeter;
import it.polimi.ingsw.model.workers.WorkerPan;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static javax.swing.SwingUtilities.isLeftMouseButton;

public class Table extends JLayeredPane{

    private final static String backGroundPath = "src/main/java/it/polimi/ingsw/view/gui/img/SantoriniBoard.png";
    private final static String iconsPath = "src/main/java/it/polimi/ingsw/view/gui/img/icons/";

    private final static Dimension TABLE_DIMENSION = new Dimension(1280,720);
    private final static Dimension BOARD_PANEL_DIMENSION = new Dimension(530,530);
    private final static Dimension PLAYER_PANEL_DIMENSION = new Dimension(200,530);

    private GameState gameState = GameState.WAIT_TURN;
    private MapCopy map;

    private final static int NUM_TILES = 25;

    public Table() throws IOException {
        super();
        this.setSize(TABLE_DIMENSION);

        add(getBackGround(), Integer.valueOf(0));


        new Map();

        try {
            new WorkerDemeter("RED1", 2, 3);
        }catch(NullPointerException ignore){}

        try {
            new WorkerPan("YEL1", 3, 3);
        }catch(NullPointerException ignore){}

        try {
            Map.getInstance().setCellBlockType(3, 3, BlockType.BLOCK3);
        }catch(NullPointerException ignore){}

        try {
            Map.getInstance().setCellBlockType(4, 3, BlockType.CUPOLA);
        }catch(NullPointerException ignore){}


        PlayerPanel playerPanel = new PlayerPanel();
        add(playerPanel, Integer.valueOf(1));

        BoardPanel boardPanel = new BoardPanel();
        add(boardPanel, Integer.valueOf(1));

        validate();
    }

    public JLabel getBackGround() throws IOException {
        final Image image = ImageIO.read(new File(backGroundPath));
        JLabel label = new JLabel();
        label.setSize(TABLE_DIMENSION);
        ImageIcon img = new ImageIcon(image.getScaledInstance(label.getWidth(), label.getHeight(), Image.SCALE_SMOOTH));
        label.setIcon(img);
        return label;
    }

    /*
    private void enableLayer(Component container, boolean enable, boolean alwaysTrue){
        container.setEnabled(enable);            //fase1: abilita/disabilita il container e tutti i suoi component
        container.setVisible(enable);
        boolean mainContainer = alwaysTrue;

        //fase 2: se il componente è stato disabilitato (enable=false) viene spostato il un layer più basso,
        //        se è stato abilitato (enable=true) viene spostato nel livello più alto

        if(mainContainer) {      //entra solo se è il container principale (non i sotto-conteiner contenuti in esso)
            if (!enable) {
                moveToBack(container);
            }
            if (enable) {
                moveToFront(container);
            }

        }
        mainContainer = false;

        try {
            Component[] components= ((Container) container).getComponents();
            for (Component component : components) {
                enableLayer(component, enable,false);
            }
        } catch (ClassCastException e) {

        }



    }
    */

    public GameState getGameState() {
        return gameState;
    }

    public void setGameState(GameState gameState) {
        this.gameState = gameState;
    }

    public MapCopy getMap() {
        return map;
    }

    public void setMap(MapCopy map) {
        this.map = map;
    }

    private class PlayerPanel extends JPanel{

        PlayerPanel() {
            super();
            //setBorder(new EmptyBorder(100, 100, 100, 100));
            //setBackground(new Color(0, 0, 0, 0));       //Sfondo invisibile
            setBounds(1005, 95, getWidth(), getHeight());
            setSize(PLAYER_PANEL_DIMENSION);

            //setVisible(false);    FUNZIONA
            //setDisable/setEnable
            //setOpacity

            validate();
        }

    }

    private class BoardPanel extends JPanel{
        BoardPanel() throws IOException {
            super(new GridLayout(5,5));
            for(int i = 0; i < NUM_TILES; i++) {
                final TilePanel tilePanel = new TilePanel(this, i);
                add(tilePanel);
            }
            setBackground(new Color(0, 0, 0, 0));
            setBounds(375, 95, getWidth(), getHeight());
            setSize(BOARD_PANEL_DIMENSION);
            validate();
        }
    }

    private class TilePanel extends JPanel{     //piastrella
        private final int tileId;
        private int coordX, coordY;

        TilePanel(final BoardPanel boardPanel, final int tileId) throws IOException {
            super(new GridBagLayout());
            this.tileId = tileId;
            tileIdInCoordinate();
            setBackground(new Color(0, 0, 0, 0));
            assignTilePieceIcon(Map.getInstance());     //getMap()

            addMouseListener(new MouseListener() {
                @Override
                public void mouseClicked(final MouseEvent e) {
                    if(isLeftMouseButton(e)){
                        switch (getGameState()){
                            case WORKER_CHOICE ->{
                                //CONTROLLO WORKER PLAYER  ok color
                                //CONTROLLO PRESENZA WORKER
                                //ERRORE MAPPA
                                //MAPCOPY BRUTTO
                                System.out.println(Map.getInstance().getWorkerInCell(coordX, coordY).getIdWorker().substring(4));
                                //Gui.getInstance().asyncWriteToSocket(Map.getInstance().getWorkerInCell(coordX, coordY).getIdWorker().substring(4));
                            }
                            case WAIT_TURN -> {
                                JDialog wait = new JDialog();
                                wait.setModalityType(Dialog.ModalityType.APPLICATION_MODAL);

                                JButton button = new JButton ("OK");
                                button.addActionListener (e1 -> wait.setVisible(false));

                                JPanel panel = new JPanel();

                                panel.add(new JLabel ("ATTENDI!"));
                                panel.add(button);
                                panel.setSize(200, 200);

                                wait.add(panel);
                                wait.dispose();

                                wait.setLocation(700,375);
                                wait.pack();
                                wait.setVisible(true);
                            }
                            default -> {
                                String coordinate = coordX + ", " + coordY;
                                System.out.println(coordinate);
                                //Gui.getInstance().asyncWriteToSocket(coordinate);
                            }
                        }
                    }
                }

                @Override
                public void mousePressed(final MouseEvent e) {

                }

                @Override
                public void mouseReleased(final MouseEvent e) {

                }

                @Override
                public void mouseEntered(final MouseEvent e) {

                }

                @Override
                public void mouseExited(final MouseEvent e) {

                }
            });

            validate();
        }

        private void tileIdInCoordinate(){
            switch (tileId){
                case 0 -> {
                    coordX = 0;
                    coordY = 0;
                }
                case 1 -> {
                    coordX = 0;
                    coordY = 1;
                }
                case 2 -> {
                    coordX = 0;
                    coordY = 2;
                }
                case 3 -> {
                    coordX = 0;
                    coordY = 3;
                }
                case 4 -> {
                    coordX = 0;
                    coordY = 4;
                }
                case 5 -> {
                    coordX = 1;
                    coordY = 0;
                }
                case 6 -> {
                    coordX = 1;
                    coordY = 1;
                }
                case 7 -> {
                    coordX = 1;
                    coordY = 2;
                }
                case 8 -> {
                    coordX = 1;
                    coordY = 3;
                }
                case 9 -> {
                    coordX = 1;
                    coordY = 4;
                }
                case 10 -> {
                    coordX = 2;
                    coordY = 0;
                }
                case 11 -> {
                    coordX = 2;
                    coordY = 1;
                }
                case 12 -> {
                    coordX = 2;
                    coordY = 2;
                }
                case 13 -> {
                    coordX = 2;
                    coordY = 3;
                }
                case 14 -> {
                    coordX = 2;
                    coordY = 4;
                }
                case 15 -> {
                    coordX = 3;
                    coordY = 0;
                }
                case 16 -> {
                    coordX = 3;
                    coordY = 1;
                }
                case 17 -> {
                    coordX = 3;
                    coordY = 2;
                }
                case 18 -> {
                    coordX = 3;
                    coordY = 3;
                }
                case 19 -> {
                    coordX = 3;
                    coordY = 4;
                }
                case 20 -> {
                    coordX = 4;
                    coordY = 0;
                }
                case 21 -> {
                    coordX = 4;
                    coordY = 1;
                }
                case 22 -> {
                    coordX = 4;
                    coordY = 2;
                }
                case 23 -> {
                    coordX = 4;
                    coordY = 3;
                }
                case 24 -> {
                    coordX = 4;
                    coordY = 4;
                }
            }
        }

        private void assignTilePieceIcon(final Map map) throws IOException {    //MapCopy
            BufferedImage image;
            this.removeAll();
            if(map.noWorkerHere(coordX, coordY)) {
                if(!map.getCellBlockType(coordX, coordY).equals(BlockType.GROUND)) {
                    image = ImageIO.read(new File(iconsPath + map.getCellBlockType(coordX, coordY).toString() + ".png"));
                }else{
                    return;
                }
            }else {
                image = ImageIO.read(new File(iconsPath + map.getCellBlockType(coordX, coordY).toString() + map.getWorkerInCell(coordX, coordY).getIdWorker().substring(0, 3) + ".png"));
            }
            add(new JLabel(new ImageIcon(image)));
        }
    }

    public static void main(String[] args) {

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

    private static void startBackgroundInitialization(final Window splashScreen) {
        new Thread(() -> {
            try {
                Thread.sleep(5000);              //simula qualcosa da fare...
            } catch(InterruptedException e) {
                e.printStackTrace();
            } finally {
                disposeWindow(splashScreen);
            }
        }).start();
    }

    private static void disposeWindow(final Window window) {
        EventQueue.invokeLater(window::dispose);
    }

    private static void showMainFrame() throws IOException {
        JFrame mainFrame = new JFrame("Main Frame");
        mainFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        mainFrame.add(new Table());

        //CHIUSURA
        mainFrame.addWindowListener(new WindowAdapter() {
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

        mainFrame.setVisible(true);
        mainFrame.setSize(1280,755);
        mainFrame.setLocationRelativeTo(null);
    }

}
