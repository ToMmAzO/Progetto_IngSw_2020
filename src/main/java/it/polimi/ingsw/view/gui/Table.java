package it.polimi.ingsw.view.gui;

import it.polimi.ingsw.model.board.BlockType;
import it.polimi.ingsw.model.game.GameState;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
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
    BoardPanel boardPanel;

    public Table() throws IOException {
        super();
        this.setSize(TABLE_DIMENSION);

        add(getBackGround(), Integer.valueOf(0));
        //add(playerPanel, Integer.valueOf(1));
        add(boardPanel = new BoardPanel(), Integer.valueOf(1));
    }

    private JLabel getBackGround() throws IOException {
        final Image image = ImageIO.read(new File(backGroundPath));
        JLabel label = new JLabel();
        label.setSize(TABLE_DIMENSION);
        label.setIcon(new ImageIcon(image));
        return label;
    }

    public void resetMap() throws IOException {
        remove(boardPanel);
        add(boardPanel = new BoardPanel(), Integer.valueOf(1));
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
        public BoardPanel() throws IOException {
            super(new GridLayout(5,5));

            for(int i = 0; i < NUM_TILES; i++) {
                TilePanel tilePanel = new TilePanel(i);
                add(tilePanel);
            }
            setBackground(new Color(0, 0, 0, 0));
            setLocation(375, 95);
            setSize(BOARD_PANEL_DIMENSION);
        }

    }

    private class TilePanel extends JPanel{     //piastrella
        private JLabel label;

        private final int tileId;
        private int coordX, coordY;

        public TilePanel(int tileId) throws IOException {
            super();

            label = new JLabel();

            this.tileId = tileId;
            tileIdInCoordinate();
            setBackground(new Color(0, 0, 0, 0));
            assignTilePieceIcon();

            add(label);

            addMouseListener(new MouseListener() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    if(isLeftMouseButton(e)){
                        if (Gui.getInstance().getGameState() == GameState.WORKER_CHOICE) {
                            if (!Gui.getInstance().getMap().noWorkerHere(coordX, coordY)) {
                                if (Gui.getInstance().getMap().getWorkerInCell(coordX, coordY).getIdWorker().substring(0, 3).equals(Gui.getInstance().getColor().toString())) {
                                    System.out.println(Gui.getInstance().getMap().getWorkerInCell(coordX, coordY).getIdWorker().substring(3));
                                    Gui.getInstance().asyncWriteToSocket(Gui.getInstance().getMap().getWorkerInCell(coordX, coordY).getIdWorker().substring(3));
                                }
                            }
                        } else {
                            String coordinate = coordX + ", " + coordY;
                            System.out.println(coordinate);
                            Gui.getInstance().asyncWriteToSocket(coordinate);
                        }
                    }
                }

                @Override
                public void mousePressed(MouseEvent e) {

                }

                @Override
                public void mouseReleased(MouseEvent e) {

                }

                @Override
                public void mouseEntered(MouseEvent e) {

                }

                @Override
                public void mouseExited(MouseEvent e) {

                }
            });
        }

        private void tileIdInCoordinate(){

            /*
            int x = 0;
            int y = 0;

            for(int i = 0; i < NUM_TILES; i++){
                if(i == tileId){
                    coordX = x;
                    coordY = y;
                }else{
                    y++;
                    if(i % 5 == 0 && i != 0){
                        x++;
                        y = 0;
                    }
                }
            }
            */

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

        private void assignTilePieceIcon() throws IOException {
            Image image;
            //removeAll();
            if(Gui.getInstance().getMap().noWorkerHere(coordX, coordY)) {
                if(!Gui.getInstance().getMap().getCellBlockType(coordX, coordY).equals(BlockType.GROUND)) {
                    image = ImageIO.read(new File(iconsPath + Gui.getInstance().getMap().getCellBlockType(coordX, coordY).toString() + ".png"));
                }else{
                    //cose
                    return;
                }
            }else {
                image = ImageIO.read(new File(iconsPath + Gui.getInstance().getMap().getCellBlockType(coordX, coordY).toString() + Gui.getInstance().getMap().getWorkerInCell(coordX, coordY).getIdWorker().substring(0, 3) + ".png"));
            }
            label.setIcon(new ImageIcon(image));
        }
    }
}
