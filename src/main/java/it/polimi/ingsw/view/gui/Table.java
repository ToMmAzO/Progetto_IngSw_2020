package it.polimi.ingsw.view.gui;

import it.polimi.ingsw.model.board.BlockType;
import it.polimi.ingsw.model.game.GameState;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;

import static javax.swing.SwingUtilities.isLeftMouseButton;

public class Table extends JPanel{

    private final static String backGroundPath = "src/main/java/it/polimi/ingsw/view/gui/img/SantoriniBoard.png";
    private final static String iconsPath = "src/main/java/it/polimi/ingsw/view/gui/img/icons/";

    private final static Dimension TABLE_DIMENSION = new Dimension(1280,720);
    private final static Dimension BOARD_PANEL_DIMENSION = new Dimension(530,530);

    private final Image image = ImageIO.read(new File(backGroundPath));

    private final BoardPanel boardPanel;

    public Table() throws IOException {
        super();
        this.setSize(TABLE_DIMENSION);
        setLayout(null);
        add(boardPanel = new BoardPanel());
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(image, 0, 0, null);
    }

    public void updateMap() throws IOException {
        boardPanel.update();
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
            coordX = x;
            coordY = y;
            assignTilePieceIcon();
            setBorderPainted(false);
            setBackground(new Color(0, 0, 0, 0));

            addMouseListener(new MouseListener() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    if(isLeftMouseButton(e)){
                        if (Gui.getInstance().getGameState() == GameState.WORKER_CHOICE) {
                            if (!Gui.getInstance().getMap().noWorkerHere(coordX, coordY)) {
                                if (Gui.getInstance().getMap().getWorkerInCell(coordX, coordY).getIdWorker().substring(0, 3).equals(Gui.getInstance().getColor().toString().substring(0, 3))) {
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
            if(Gui.getInstance().getMap().noWorkerHere(coordX, coordY)) {
                if(!Gui.getInstance().getMap().getCellBlockType(coordX, coordY).equals(BlockType.GROUND)) {
                    image = ImageIO.read(new File(iconsPath + Gui.getInstance().getMap().getCellBlockType(coordX, coordY).toString() + ".png"));
                }else{
                    setIcon(null);
                    return;
                }
            }else {
                image = ImageIO.read(new File(iconsPath + Gui.getInstance().getMap().getCellBlockType(coordX, coordY).toString() + Gui.getInstance().getMap().getWorkerInCell(coordX, coordY).getIdWorker().substring(0, 3) + ".png"));
            }
            setIcon(new ImageIcon(image));
        }
    }

}
