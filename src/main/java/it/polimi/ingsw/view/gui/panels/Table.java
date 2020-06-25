package it.polimi.ingsw.view.gui.panels;

import it.polimi.ingsw.model.board.BlockType;
import it.polimi.ingsw.model.cards.God;
import it.polimi.ingsw.model.game.GameState;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;

import static javax.swing.SwingUtilities.isLeftMouseButton;

public class Table extends JPanel{

    private final static String backgroundsPath = "/img/backgrounds/";
    private final static String mapIconsPath = "/img/mapIcons/";
    private final static String cardsPath = "/img/cards/";

    private final static Dimension TABLE_DIMENSION = new Dimension(1280,720);
    private final static Dimension BOARD_PANEL_DIMENSION = new Dimension(530,530);
    private final static Dimension PLAYER_PANEL_DIMENSION = new Dimension(275,720);

    private final Image boardImage = ImageIO.read(getClass().getResource(backgroundsPath.concat("SantoriniBoard.png")));
    private final Image scrollPanelImage = ImageIO.read(getClass().getResource(backgroundsPath.concat("ScrollPanel.png")));
    private final Image godPanelImage = ImageIO.read(getClass().getResource(backgroundsPath.concat("GodPanel.png")));

    private final JLabel tutorial;
    private final BoardPanel boardPanel;
    private final TextPanel textPanel;

    private boolean tutorialIsClosed = false;

    public Table() throws IOException {
        super();
        this.setSize(TABLE_DIMENSION);
        setLayout(null);

        JLabel color = new JLabel();
        color.setIcon(new ImageIcon(getClass().getResource(backgroundsPath + "Color" + PanelManager.getInstance().getColor().toString() + ".png")));
        color.setBounds(543,0,200,84);

        tutorial = new JLabel();
        tutorial.setIcon(new ImageIcon(getClass().getResource(backgroundsPath.concat("TutorialBackground.png"))));
        tutorial.setBounds(25, 0, 1100, 720);

        add(color);
        add(tutorial);
        add(boardPanel = new BoardPanel());
        add(new PlayerPanel());
        add(textPanel = new TextPanel());
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(boardImage, 0, 0, null);
    }

    public void updateMap(){
        boardPanel.update();
    }

    public void addText(String string){
        textPanel.addString(string);
    }

    private class PlayerPanel extends JPanel{

        public PlayerPanel(){
            super();
            setLayout(null);
            setBackground(new Color(0, 0, 0, 0));
            setLocation(995, 0);
            setSize(PLAYER_PANEL_DIMENSION);

            JLabel godImage = getGodCard();
            godImage.setLocation(70,160);

            JTextPane info = new JTextPane();
            info.setText(God.getGodDescription(PanelManager.getInstance().getGodChoice()));
            info.setEditable(false);
            info.setBounds(55,412,180,120);
            info.setOpaque(false);
            info.setForeground(new Color(0x3C3C3C));
            StyledDocument doc = info.getStyledDocument();
            SimpleAttributeSet center = new SimpleAttributeSet();
            StyleConstants.setAlignment(center, StyleConstants.ALIGN_CENTER);
            doc.setParagraphAttributes(0, doc.getLength(), center, false);

            add(godImage);
            add(info);

            setVisible(true);
        }

        private JLabel getGodCard() {
            JLabel label = new JLabel();
            label.setSize(150,250);
            label.setIcon(new ImageIcon(getClass().getResource(cardsPath + PanelManager.getInstance().getGodChoice().toString() + ".png")));
            return label;
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            g.drawImage(godPanelImage, 0, 0, null);
        }

    }

    private class TextPanel extends JPanel{

        JTextArea info;

        public TextPanel(){
            super();
            setLayout(null);
            setBackground(Color.WHITE);
            setLocation(0, 0);
            setSize(275,720);

            info = new JTextArea();
            info.setWrapStyleWord(true);
            info.setLineWrap(true);
            info.setOpaque(false);
            info.setForeground(new Color(0xFF91511D, true));
            info.setBackground(new Color(0,0,0,0));
            info.setEditable(false);

            JScrollPane scrollPane = new JScrollPane(info);
            scrollPane.setBounds(50,220,165,250);
            scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);
            scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
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
            g.drawImage(scrollPanelImage, 0, 0, null);
        }

        private void addString(String string){
            info.append(string + "\n----------------------------------------\n");
        }

    }

    private class BoardPanel extends JPanel{

        private final TileButton[] tiles = new TileButton[25];

        public BoardPanel(){
            super(new GridLayout(5,5));
            int x = 0;
            int y = 0;
            for(int i = 0; i < 25; i++) {
                tiles[i] = new TileButton(x, y);
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

        public void update(){
            for(TileButton t: tiles){
                t.assignTilePieceIcon();
            }
        }

    }

    private class TileButton extends JButton{

        private final int coordX, coordY;

        public TileButton(int x, int y){
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
                    if(!tutorialIsClosed) {
                        tutorial.setVisible(false);
                        tutorialIsClosed = true;
                    }
                    if(isLeftMouseButton(e)){
                        if (PanelManager.getInstance().getGameState() == GameState.WORKER_CHOICE) {
                            if (!PanelManager.getInstance().getMap().noWorkerHere(coordX, coordY)) {
                                if (PanelManager.getInstance().getMap().getWorkerInCell(coordX, coordY).getIdWorker().substring(0, 3).equals(PanelManager.getInstance().getColor().toString().substring(0, 3))) {
                                    PanelManager.getInstance().asyncWriteToSocket(PanelManager.getInstance().getMap().getWorkerInCell(coordX, coordY).getIdWorker().substring(3));
                                }
                            }
                        } else {
                            String coordinate = coordX + ", " + coordY;
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

        private void assignTilePieceIcon(){
            if(PanelManager.getInstance().getMap().noWorkerHere(coordX, coordY)) {
                if(!PanelManager.getInstance().getMap().getCellBlockType(coordX, coordY).equals(BlockType.GROUND)) {
                    setIcon(new ImageIcon(getClass().getResource(mapIconsPath + PanelManager.getInstance().getMap().getCellBlockType(coordX, coordY).toString() + ".png")));
                } else{
                    setIcon(null);
                }
            } else{
                setIcon(new ImageIcon(getClass().getResource(mapIconsPath + PanelManager.getInstance().getMap().getCellBlockType(coordX, coordY).toString() + PanelManager.getInstance().getMap().getWorkerInCell(coordX, coordY).getIdWorker().substring(0, 3) + ".png")));
            }
        }

    }

}
