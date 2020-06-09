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
    private final Image image = ImageIO.read(new File(backGroundPath));

    private final Cell[] cells = new Cell[25];

    public Table() throws IOException {
        super();
        this.setSize(TABLE_DIMENSION);
        setLayout(null);
        addCells();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(image, 0, 0, null);
    }

    public void addCells() throws IOException {
        int x = 0, y = 0;
        int xPos = 375, yPos = 95;
        for(int i = 0; i < 25; i++) {
            cells[i] = new Cell(x, y);
            add(cells[i]);
            cells[i].setLocation(xPos, yPos);
            y++;
            xPos = xPos + 106;
            if(y == 5){
                y = 0;
                xPos = 375;
                x++;
                yPos = yPos + 106;
            }
        }
    }

    public void updateMap() throws IOException {
        for(Cell c: cells){
            c.assignIcon();
        }
    }

    private static class Cell extends JButton{

        private final int coordX, coordY;

        public Cell(int coordX, int coordY) throws IOException {
            setSize(106, 106);
            this.coordX = coordX;
            this.coordY = coordY;
            assignIcon();
            setBorderPainted(false);
            setBackground(new Color(0, 0, 0, 0));
            addMouseListener(new MouseListener(){

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
                            String coordinate = coordX + "," + coordY;
                            System.out.println(coordinate);
                            Gui.getInstance().asyncWriteToSocket(coordinate);
                        }
                    }
                }

                @Override
                public void mousePressed(MouseEvent e){}

                @Override
                public void mouseReleased(MouseEvent e){}

                @Override
                public void mouseEntered(MouseEvent e){}

                @Override
                public void mouseExited(MouseEvent e){}

            });
            setVisible(true);
            setOpaque(false);
        }

        private void assignIcon() throws IOException {
            Image image;
            if(Gui.getInstance().getMap().noWorkerHere(coordX, coordY)) {
                if(!Gui.getInstance().getMap().getCellBlockType(coordX, coordY).equals(BlockType.GROUND)) {
                    image = ImageIO.read(new File(iconsPath + Gui.getInstance().getMap().getCellBlockType(coordX, coordY).toString() + ".png"));
                } else{
                    setIcon(null);
                    return;
                }
            } else{
                image = ImageIO.read(new File(iconsPath + Gui.getInstance().getMap().getCellBlockType(coordX, coordY).toString() + Gui.getInstance().getMap().getWorkerInCell(coordX, coordY).getIdWorker().substring(0, 3) + ".png"));
            }
            setIcon(new ImageIcon(image));
        }

    }

}
