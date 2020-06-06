package it.polimi.ingsw.view.gui;

import it.polimi.ingsw.model.board.BlockType;
import it.polimi.ingsw.model.board.MapCopy;

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
    //private final static Dimension PLAYER_PANEL_DIMENSION = new Dimension(200,530);

    private final static int NUM_TILES = 25;



    //PlayerPanel playerPanel = new PlayerPanel();
    BoardPanel boardPanel = new BoardPanel();

    public Table(MapCopy map) throws IOException {
        super();
        System.out.println("fase 1");
        this.setSize(TABLE_DIMENSION);

        System.out.println("fase 2");
        add(getBackGround(), Integer.valueOf(0));

        //add(playerPanel, Integer.valueOf(1));
        System.out.println("fase 3");
        add(boardPanel, Integer.valueOf(1));
        System.out.println("fase 4");
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



      // al posto che prendere la mappa da qui in table devi andare a prendere quella salvata in GUI
    public void resetMap() throws IOException {

        boardPanel.drawBoard();
    }

    /*
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
    */

    private class BoardPanel extends JPanel{
        final List<TilePanel> boardTiles;

        public BoardPanel() throws IOException {
            super(new GridLayout(5,5));
            this.boardTiles = new ArrayList<>();
            for(int i = 0; i < NUM_TILES; i++) {
                final TilePanel tilePanel = new TilePanel(i);
                this.boardTiles.add(tilePanel);
                add(tilePanel);
            }
            setBackground(new Color(0, 0, 0, 0));
            setBounds(375, 95, getWidth(), getHeight());
            setSize(BOARD_PANEL_DIMENSION);
            validate();
        }

        public void drawBoard() throws IOException {
            removeAll();
            for(final TilePanel tilePanel : boardTiles){
                tilePanel.drawTile();
                add(tilePanel);
            }
            validate();
            repaint();
        }
    }

    private class TilePanel extends JPanel{     //piastrella
        private final int tileId;
        private int coordX, coordY;

        public TilePanel(final int tileId) throws IOException {
            super(new GridBagLayout());
            this.tileId = tileId;
            tileIdInCoordinate();
            setBackground(new Color(0, 0, 0, 0));
            assignTilePieceIcon();

            addMouseListener(new MouseListener() {
                @Override
                public void mouseClicked(final MouseEvent e) {
                    if(isLeftMouseButton(e)){
                        switch (Gui.getInstance().getGameState()){
                            case WORKER_CHOICE ->{
                                if(!Gui.getInstance().getMap().noWorkerHere(coordX, coordY)){
                                    if(Gui.getInstance().getMap().getWorkerInCell(coordX, coordY).getIdWorker().substring(0, 3).equals(Gui.getInstance().getColor().toString())){
                                        Gui.getInstance().asyncWriteToSocket(Gui.getInstance().getMap().getWorkerInCell(coordX, coordY).getIdWorker().substring(4));
                                    }
                                }
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
                                Gui.getInstance().asyncWriteToSocket(coordinate);
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

        public void drawTile() throws IOException {
            assignTilePieceIcon();
            validate();
            repaint();
        }

        private void assignTilePieceIcon() throws IOException {
            BufferedImage image;
            this.removeAll();
            if(Gui.getInstance().getMap().noWorkerHere(coordX, coordY)) {
                if(!Gui.getInstance().getMap().getCellBlockType(coordX, coordY).equals(BlockType.GROUND)) {
                    image = ImageIO.read(new File(iconsPath + Gui.getInstance().getMap().getCellBlockType(coordX, coordY).toString() + ".png"));
                }else{
                    return;
                }
            }else {
                image = ImageIO.read(new File(iconsPath + Gui.getInstance().getMap().getCellBlockType(coordX, coordY).toString() + Gui.getInstance().getMap().getWorkerInCell(coordX, coordY).getIdWorker().substring(0, 3) + ".png"));
            }
            add(new JLabel(new ImageIcon(image)));
        }
    }

    /*public static void main(String[] args) throws IOException {
        JFrame mainFrame = new JFrame("Main Frame");
        mainFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        mainFrame.add(new Table(map));

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
    }*/

}
