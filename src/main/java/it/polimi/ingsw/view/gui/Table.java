package it.polimi.ingsw.view.gui;

import it.polimi.ingsw.model.board.Map;
import it.polimi.ingsw.model.workers.Worker;
import it.polimi.ingsw.model.workers.WorkerDemeter;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static javax.swing.SwingUtilities.isLeftMouseButton;

public class Table {

    private final JFrame gameFrame;
    private final BoardPanel boardPanel;

    private final static String backGroundPath = "src/main/java/it/polimi/ingsw/view/gui/img/SantoriniBoard.png";
    private final static String iconsPath = "src/main/java/it/polimi/ingsw/view/gui/img/icons/";

    private final static Dimension TABLE_DIMENSION = new Dimension(1440,810);
    private final static Dimension BOARD_PANEL_DIMENSION = new Dimension(600,600);
    private final static Dimension TILE_PANEL_DIMENSION = new Dimension(10,10);

    private final static int NUM_TILES = 25;
    private final static int NUM_TILES_PER_ROW = 5;

    public static void main(String[] args) throws IOException {
       new Table();
    }

    public Table() throws IOException {
        gameFrame = new JFrame("SANTORINI");
        gameFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        gameFrame.setSize(TABLE_DIMENSION);

        JLayeredPane layeredPane = new JLayeredPane();

        layeredPane.add(getBackGround(), Integer.valueOf(0));

        new Map();

        try {
            new WorkerDemeter("RED1", 2, 3);
        }catch(NullPointerException ignore){}

        try {
            new WorkerDemeter("YEL1", 4, 3);
        }catch(NullPointerException ignore){}


        boardPanel = new BoardPanel();
        layeredPane.add(boardPanel, Integer.valueOf(1));

        //gameFrame.pack();     la finestra assuma le dimensioni minime necessarie e sufficienti affinchè ciò che contiene sia visualizzato secondo le sue dimensioni ottimali
        gameFrame.add(layeredPane);
        gameFrame.setLocationRelativeTo(null);
        gameFrame.setVisible(true);
    }

    public JLabel getBackGround() throws IOException {
        final Image image = ImageIO.read(new File(backGroundPath));

        //ImageIcon img = new ImageIcon("C:\\Users\\tomma\\Desktop\\Progect\\Risorse grafiche Santorini\\SantoriniBoard.png");
        //Image image = img.getImage();
        //Image modifiedImage = image.getScaledInstance(1440, 810, Image.SCALE_SMOOTH);       //qualità con cui scala
        //img = new ImageIcon(modifiedImage);

        JLabel label = new JLabel(new ImageIcon(image.getScaledInstance(1440, 810, Image.SCALE_SMOOTH)));
        label.setSize(TABLE_DIMENSION);

        return label;
    }

    private class BoardPanel extends JPanel{
        final List<TilePanel> boardTiles;

        BoardPanel() throws IOException {
            super(new GridLayout(5,5));
            boardTiles = new ArrayList<>();
            for(int i = 0; i < NUM_TILES; i++) {
                final TilePanel tilePanel = new TilePanel(this, i);
                boardTiles.add(tilePanel);          //lista
                add(tilePanel);                     //JPanel
            }
            //setBorder(new EmptyBorder(100, 100, 100, 100));
            //setBackground(new Color(0, 0, 0, 0));       //Sfondo invisibile
            setBounds(420, 105, 600, 600);
            setSize(BOARD_PANEL_DIMENSION);
            //setVisible(false);    FUNZIONA
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
            //setPreferredSize(TILE_PANEL_DIMENSION);
            assignTileColor();
            assignTilePieceIcon(Map.getInstance());

            addMouseListener(new MouseListener() {
                @Override
                public void mouseClicked(final MouseEvent e) {
                    if(isLeftMouseButton(e)){

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

            //setBackground(new Color(0, 0, 0, 0));
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

        private void assignTilePieceIcon(final Map map) throws IOException {
            BufferedImage image;
            this.removeAll();
            if(map.noWorkerHere(coordX, coordY)) {
                image = ImageIO.read(new File(iconsPath + map.getCellBlockType(coordX, coordY).toString() + ".png"));
            }else {
                image = ImageIO.read(new File(iconsPath + map.getCellBlockType(coordX, coordY).toString() + map.getWorkerInCell(coordX, coordY).getIdWorker().substring(0, 3) + ".png"));
            }
            add(new JLabel(new ImageIcon(image)));
        }

        private void assignTileColor() {
            if(tileId % 2 == 0) {
                setBackground(Color.red);
            }else{
                setBackground(Color.yellow);
            }
        }
    }

}
