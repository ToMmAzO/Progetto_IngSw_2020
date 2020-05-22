package it.polimi.ingsw.view.gui;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Table {

    private final JFrame gameFrame;
    private final BoardPanel boardPanel;
    private final WorkerPanel workerPanel;

    private final static String backGroundPath = "src/main/java/it/polimi/ingsw/view/gui/img/SantoriniBoard.png";
    //private final static String godCardPath = "src/main/java/it/polimi/ingsw/view/gui/img/cards/";
    //godCardPath + nome divinità + ".png"


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

        boardPanel = new BoardPanel();
        layeredPane.add(boardPanel, Integer.valueOf(1));

        workerPanel = new WorkerPanel();
        layeredPane.add(workerPanel, Integer.valueOf(2));

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

    private class WorkerPanel extends JPanel{
        WorkerPanel(){
            super(new GridLayout(5,5));
            for(int i = 0; i < 25; i++) {
                JButton pulsante = new JButton(String.valueOf(i));
                pulsante.setContentAreaFilled(false);
                pulsante.setBorderPainted(false);
                int finalI = i;
                pulsante.addActionListener(e -> System.out.println(finalI));
                add(pulsante);
            }
            //setBorder(new EmptyBorder(100, 100, 100, 100));
            setBackground(new Color(0, 0, 0, 0));       //Sfondo invisibile
            setOpaque(false);                                      //Vedi sotto
            setBounds(420, 105, 600, 600);
            setSize(BOARD_PANEL_DIMENSION);
            //setVisible(false);
            validate();
        }
    }

    private class BoardPanel extends JPanel{
        final List<TilePanel> boardTiles;

        BoardPanel(){
            super(new GridLayout(5,5));
            this.boardTiles = new ArrayList<>();
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

        TilePanel(final BoardPanel boardPanel, final int tileId){
            super(new GridBagLayout());
            this.tileId = tileId;
            //setPreferredSize(TILE_PANEL_DIMENSION);
            assignTileColor();
            //setBackground(new Color(0, 0, 0, 0));
            validate();
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
