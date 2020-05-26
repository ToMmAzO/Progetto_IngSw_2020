package it.polimi.ingsw.view.gui;

import it.polimi.ingsw.model.board.BlockType;
import it.polimi.ingsw.model.board.Map;
import it.polimi.ingsw.model.workers.WorkerDemeter;
import it.polimi.ingsw.model.workers.WorkerPan;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static javax.swing.SwingUtilities.isLeftMouseButton;

public class Table extends JPanel {

    private final static String backGroundPath = "src/main/java/it/polimi/ingsw/view/gui/img/SantoriniBoard.png";
    private final static String iconsPath = "src/main/java/it/polimi/ingsw/view/gui/img/icons/";

    private final static Dimension TABLE_DIMENSION = new Dimension(1440,810);
    private final static Dimension BOARD_PANEL_DIMENSION = new Dimension(600,600);
    private final static Dimension PLAYER_PANEL_DIMENSION = new Dimension(200,600);

    private final static int NUM_TILES = 25;

    /*
    public static void main(String[] args) throws IOException {
       new Table();
    }
    */

    public Table() throws IOException {
        setSize(TABLE_DIMENSION);

        JLayeredPane layeredPane = new JLayeredPane();

        layeredPane.add(getBackGround(), Integer.valueOf(0));

        new Map();

        /*
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
        */

        PlayerPanel playerPanel = new PlayerPanel();
        layeredPane.add(playerPanel, Integer.valueOf(1));

        BoardPanel boardPanel = new BoardPanel();
        layeredPane.add(boardPanel, Integer.valueOf(1));

        add(layeredPane);
        setVisible(true);
        setEnabled(true);
        setOpaque(true);
    }

    public JLabel getBackGround() throws IOException {
        final Image image = ImageIO.read(new File(backGroundPath));
        JLabel label = new JLabel();
        label.setSize(TABLE_DIMENSION);
        ImageIcon img = new ImageIcon(image.getScaledInstance(label.getWidth(), label.getHeight(), Image.SCALE_SMOOTH));
        label.setIcon(img);
        return label;
    }

    private class PlayerPanel extends JPanel{

        PlayerPanel() {
            super();
            //setBorder(new EmptyBorder(100, 100, 100, 100));
            //setBackground(new Color(0, 0, 0, 0));       //Sfondo invisibile
            setBounds(1120, 105, getWidth(), getHeight());
            setSize(PLAYER_PANEL_DIMENSION);

            //setVisible(false);    FUNZIONA
            //setDisable/setEnable
            //setOpacity

            validate();
        }

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
            setBackground(new Color(0, 0, 0, 0));
            setBounds(420, 105, getWidth(), getHeight());
            setSize(BOARD_PANEL_DIMENSION);
            validate();
        }

        public void drawBoard(final Map map) throws IOException {
            removeAll();
            for(final TilePanel tilePanel : boardTiles){
                tilePanel.drawTile(map);
                add(tilePanel);
            }
            validate();
            repaint();
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
            assignTilePieceIcon(Map.getInstance());

            addMouseListener(new MouseListener() {
                @Override
                public void mouseClicked(final MouseEvent e) {
                    if(isLeftMouseButton(e)){
                        /*
                        if(GameState == WORKER_CHOICE){
                            seleszione pedina
                            per tornare il numero del worker:
                            Map.getInstance().getWorkerInCell(coordX, coordY).getIdWorker().substring(4);
                        }else{
                            mossa
                            coordX, coordY
                        }
                        */

                        /*  repaint (?)
                        SwingUtilities.invokeLater(() -> {
                            try {
                                boardPanel.drawBoard(Map.getInstance());
                            } catch (IOException ioException) {
                                ioException.printStackTrace();
                            }
                        });
                        */
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

        public void drawTile(final Map map) throws IOException {
            assignTilePieceIcon(map);
            validate();
            repaint();
        }

        private void assignTilePieceIcon(final Map map) throws IOException {
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

}
