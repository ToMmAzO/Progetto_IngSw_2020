package it.polimi.ingsw.view.gui.panels;

import it.polimi.ingsw.model.board.BlockType;
import it.polimi.ingsw.model.cards.God;
import it.polimi.ingsw.model.game.GameState;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.plaf.ScrollBarUI;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;

import static javax.swing.SwingUtilities.isLeftMouseButton;

public class Table extends JPanel{

    private final static String backGroundPath = "src/main/java/it/polimi/ingsw/view/gui/img/SantoriniBoard.png";
    private final static String textBackPath = "src/main/java/it/polimi/ingsw/view/gui/img/left_panel6.png";
    private final static String playerBackPath = "src/main/java/it/polimi/ingsw/view/gui/img/right_panel5.png";
    private final static String iconsPath = "src/main/java/it/polimi/ingsw/view/gui/img/icons/";
    private final static String godPath = "src/main/java/it/polimi/ingsw/view/gui/img/cards/";

    private final static Dimension TABLE_DIMENSION = new Dimension(1280,720);
    private final static Dimension BOARD_PANEL_DIMENSION = new Dimension(530,530);
    private final static Dimension PLAYER_PANEL_DIMENSION = new Dimension(275,720);

    private final Image image = ImageIO.read(new File(backGroundPath));
    private final Image image2 = ImageIO.read(new File(textBackPath));
    private final Image image3 = ImageIO.read(new File(playerBackPath));

    private final PlayerPanel playerPanel;
    private final BoardPanel boardPanel;
    private TextPanel textPanel;

    public Table() throws IOException {
        super();
        this.setSize(TABLE_DIMENSION);
        setLayout(null);
        add(playerPanel = new PlayerPanel());
        add(boardPanel = new BoardPanel());
        add(textPanel = new TextPanel());
    }

    public void addText(String string){
        textPanel.addString(string);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(image, 0, 0, null);
    }

    public void updateMap() throws IOException {
        boardPanel.update();
    }

    private class PlayerPanel extends JPanel{

        PlayerPanel() throws IOException {
            super();
            setLayout(null);
            setBackground(new Color(0, 0, 0, 0));
            setLocation(1030, 0);
            setSize(PLAYER_PANEL_DIMENSION);

            JLabel godImage = getGodCard();
            godImage.setLocation(70,160);

            JTextArea info = new JTextArea(God.getGodDescription(PanelManager.getInstance().getGodChoice()));
            info.setWrapStyleWord(true);
            info.setLineWrap(true);
            info.setEditable(false);
            info.setBounds(50,412,180,120);
            info.setOpaque(false);
            info.setForeground(Color.BLACK);

            add(godImage);
            add(info);

            setVisible(true);
        }

        private JLabel getGodCard() throws IOException {
            final Image image = ImageIO.read(new File(godPath + PanelManager.getInstance().getGodChoice().toString() + ".png"));
            JLabel label = new JLabel();
            label.setSize(150,250);
            label.setIcon(new ImageIcon(image));
            return label;
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            g.drawImage(image3, 0, 0, null);
        }


    }

    private class TextPanel extends JPanel{

        JTextArea info;

        TextPanel(){
            super();
            setLayout(null);
            //setBackground(new Color(0, 0, 0, 0));
            setBackground(Color.WHITE);
            setLocation(0, 0);
            setSize(275,720);


            info = new JTextArea();
            /*info.setWrapStyleWord(true);
            info.setLineWrap(true);
            info.setEditable(false);
            info.setSize(200,40);*/
            //info.setBounds(27,180,200,300);
            info.setOpaque(false);
            info.setForeground(new Color(0xFF91511D, true));
            info.setBackground(new Color(0,0,0,0));


            JScrollPane scrollPane = new JScrollPane(info);
            scrollPane.setBounds(50,220,160,250);
            scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);
            scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
            scrollPane.getHorizontalScrollBar().setBackground(new Color(0,0,0,0));

            scrollPane.setOpaque(false);
            scrollPane.getViewport().setOpaque(false);

            scrollPane.getVerticalScrollBar().addAdjustmentListener(e -> e.getAdjustable().setValue(e.getAdjustable().getMaximum()));
            scrollPane.setBorder(BorderFactory.createEmptyBorder());
            scrollPane.setBackground(new Color(0,0,0,0));

            add(scrollPane);
            setOpaque(false);
            setVisible(true);
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            g.drawImage(image2, 0, 0, null);
        }

        private void addString(String string){
            info.append(string + "\n");
        }


    }

    private static class BoardPanel extends JPanel{

        private final TilePanel[] tiles = new TilePanel[25];

        public BoardPanel() throws IOException {
            super(new GridLayout(5,5));
            int x = 0;
            int y = 0;
            for(int i = 0; i < 25; i++) {
                tiles[i] = new TilePanel(x, y);
                add(tiles[i]);
                y++;
                if(y == 5){
                    y = 0;
                    x++;
                }
            }
            setBackground(new Color(0, 0, 0, 0));
            setLocation(375, 95);
            setSize(BOARD_PANEL_DIMENSION);
            setOpaque(false);
        }

        public void update() throws IOException {
            for(TilePanel t: tiles){
                t.assignTilePieceIcon();
            }
        }
    }

    private static class TilePanel extends JButton{

        private final int coordX, coordY;

        public TilePanel(int x, int y) throws IOException {
            super();
            coordX = x;
            coordY = y;
            assignTilePieceIcon();
            setBorderPainted(false);
            setBackground(new Color(0, 0, 0, 0));
            setFocusPainted(false);

            addMouseListener(new MouseListener() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    if(isLeftMouseButton(e)){
                        if (PanelManager.getInstance().getGameState() == GameState.WORKER_CHOICE) {
                            if (!PanelManager.getInstance().getMap().noWorkerHere(coordX, coordY)) {
                                if (PanelManager.getInstance().getMap().getWorkerInCell(coordX, coordY).getIdWorker().substring(0, 3).equals(PanelManager.getInstance().getColor().toString().substring(0, 3))) {
                                    System.out.println(PanelManager.getInstance().getMap().getWorkerInCell(coordX, coordY).getIdWorker().substring(3));
                                    PanelManager.getInstance().asyncWriteToSocket(PanelManager.getInstance().getMap().getWorkerInCell(coordX, coordY).getIdWorker().substring(3));
                                }
                            }
                        } else {
                            String coordinate = coordX + ", " + coordY;
                            System.out.println(coordinate);
                            PanelManager.getInstance().asyncWriteToSocket(coordinate);
                        }
                    }
                }

                @Override
                public void mousePressed(MouseEvent e){ }

                @Override
                public void mouseReleased(MouseEvent e){ }

                @Override
                public void mouseEntered(MouseEvent e){ }

                @Override
                public void mouseExited(MouseEvent e){ }
            });

            setOpaque(false);
        }

        private void assignTilePieceIcon() throws IOException {
            Image image;
            if(PanelManager.getInstance().getMap().noWorkerHere(coordX, coordY)) {
                if(!PanelManager.getInstance().getMap().getCellBlockType(coordX, coordY).equals(BlockType.GROUND)) {
                    image = ImageIO.read(new File(iconsPath + PanelManager.getInstance().getMap().getCellBlockType(coordX, coordY).toString() + ".png"));
                }else{
                    setIcon(null);
                    return;
                }
            }else {
                image = ImageIO.read(new File(iconsPath + PanelManager.getInstance().getMap().getCellBlockType(coordX, coordY).toString() + PanelManager.getInstance().getMap().getWorkerInCell(coordX, coordY).getIdWorker().substring(0, 3) + ".png"));
            }
            setIcon(new ImageIcon(image));
        }
    }

}
