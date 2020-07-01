package it.polimi.ingsw.view.gui.panels;

import it.polimi.ingsw.model.cards.God;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.awt.Graphics;

public class CardChoice extends JPanel {

    private final static String backgroundsPath = "/img/backgrounds/";
    private final static String cardsPath = "/img/cards/";

    private final int imageWidth = 150;
    private final int imageHeight = 250;
    private final int panelHeight = 600;
    private final int panelWidth = 600;

    private final Image loadingBack = ImageIO.read(getClass().getResource(backgroundsPath.concat("CardsBackground.png")));
    private final Image img = loadingBack.getScaledInstance(panelWidth, panelHeight, Image.SCALE_SMOOTH);

    /**
     * create the JPanel for the God's Card selection phase of the game.
     * let the player choose between 3 different Gods
     * @param god1 option number 1
     * @param god2 option number 2
     * @param god3 option number 3
     * @param availability array of boolean that tells wich of the 3 options is still availiable
     * @throws IOException for errors when reading from file
     */
    public CardChoice(God god1, God god2, God god3, boolean[] availability) throws IOException {
        super();
        JLabel l1 = new JLabel("Choose a God, here's your options:");
        l1.setBounds(38,30,400,20);
        l1.setForeground(Color.WHITE);
        JButton card1 = new JButton();
        card1.setBounds(38,60,imageWidth,imageHeight);
        card1.setIcon(new ImageIcon(getClass().getResource(cardsPath + god1.toString() + ".png")));
        if(!availability[0]) {
            card1.setEnabled(false);
            card1.setToolTipText("This God has already been chosen by another player");
        }
        JButton card2 = new JButton();
        card2.setBounds(38 + imageWidth + 30,60,imageWidth,imageHeight);
        card2.setIcon(new ImageIcon(getClass().getResource(cardsPath + god2.toString() + ".png")));
        if(!availability[1]) {
            card2.setEnabled(false);
            card2.setToolTipText("This God has already been chosen by another player");
        }
        JButton card3 = new JButton();
        card3.setBounds(38 + 2*imageWidth + 30 + 30,60,imageWidth,imageHeight);
        card3.setIcon(new ImageIcon(getClass().getResource(cardsPath + god3.toString() + ".png")));

        if(!availability[2]) {
            card3.setEnabled(false);
            card3.setToolTipText("This God has already been chosen by another player");
        }
        JTextArea info1 = new JTextArea(God.getGodDescription(god1));
        info1.setWrapStyleWord(true);
        info1.setLineWrap(true);
        info1.setEditable(false);
        info1.setBounds(38,60 + imageHeight + 20,imageWidth,150);
        info1.setOpaque(false);
        info1.setForeground(Color.WHITE);

        JTextArea info2 = new JTextArea(God.getGodDescription(god2));
        info2.setWrapStyleWord(true);
        info2.setLineWrap(true);
        info2.setEditable(false);
        info2.setBounds(38 + imageWidth + 30,60 + imageHeight + 20,imageWidth,150);
        info2.setOpaque(false);
        info2.setForeground(Color.WHITE);

        JTextArea info3 = new JTextArea(God.getGodDescription(god3));
        info3.setWrapStyleWord(true);
        info3.setLineWrap(true);
        info3.setEditable(false);
        info3.setBounds(38 + 2*imageWidth + 30 + 30,60 + imageHeight + 20,imageWidth,150);
        info3.setOpaque(false);
        info3.setForeground(Color.WHITE);

        add(l1);
        add(card1);
        add(card2);
        add(card3);
        add(info1);
        add(info2);
        add(info3);

        card1.addActionListener(e -> {
            PanelManager.getInstance().setGodChoice(god1);
            PanelManager.getInstance().asyncWriteToSocket("1");
            card1.setEnabled(false);
            card2.setEnabled(false);
            card3.setEnabled(false);
        });

        card2.addActionListener(e -> {
            PanelManager.getInstance().setGodChoice(god2);
            PanelManager.getInstance().asyncWriteToSocket("2");
            card1.setEnabled(false);
            card2.setEnabled(false);
            card3.setEnabled(false);

        });

        card3.addActionListener(e -> {
            PanelManager.getInstance().setGodChoice(god3);
            PanelManager.getInstance().asyncWriteToSocket("3");
            card1.setEnabled(false);
            card2.setEnabled(false);
            card3.setEnabled(false);
        });

        setSize(panelWidth,panelHeight);
        setLayout(null);
        setVisible(true);
    }

    /**
     * create the JPanel for the God's Card selection phase of the game.
     * let the player choose between 2 different Gods
     * @param god1 option number 1
     * @param god2 option number 2
     * @param availability array of boolean that tells wich of the 2 options is still availiable
     * @throws IOException for errors when reading from file
     */
    public CardChoice(God god1, God god2, boolean[] availability) throws IOException {
        super();
        JLabel l1 = new JLabel("Choose a God, here's your options:");
        l1.setBounds(120,30,400,20);
        l1.setForeground(Color.WHITE);
        JButton card1 = new JButton();
        card1.setBounds(120,60,imageWidth,imageHeight);
        card1.setIcon(new ImageIcon(getClass().getResource(cardsPath + god1.toString() + ".png")));
        if(!availability[0]) {
            card1.setEnabled(false);
            card1.setToolTipText("This God has already been chosen by another player");
        }
        JButton card2 = new JButton();
        card2.setBounds(120 + imageWidth + 50,60,imageWidth,imageHeight);
        card2.setIcon(new ImageIcon(getClass().getResource(cardsPath + god2.toString() + ".png")));
        if(!availability[1]) {
            card2.setEnabled(false);
            card2.setToolTipText("This God has already been chosen by another player");
        }

        JTextArea info1 = new JTextArea(God.getGodDescription(god1));
        info1.setWrapStyleWord(true);
        info1.setLineWrap(true);
        info1.setEditable(false);
        info1.setBounds(120,60 + imageHeight + 20,imageWidth,150);
        info1.setOpaque(false);
        info1.setForeground(Color.WHITE);

        JTextArea info2 = new JTextArea(God.getGodDescription(god2));
        info2.setWrapStyleWord(true);
        info2.setLineWrap(true);
        info2.setEditable(false);
        info2.setBounds(120 + imageWidth + 50,60 + imageHeight + 20,imageWidth,150);
        info2.setOpaque(false);
        info2.setForeground(Color.WHITE);

        add(l1);
        add(card1);
        add(card2);
        add(info1);
        add(info2);

        card1.addActionListener(e -> {
            PanelManager.getInstance().setGodChoice(god1);
            PanelManager.getInstance().asyncWriteToSocket("1");
            card1.setEnabled(false);
            card2.setEnabled(false);
        });

        card2.addActionListener(e -> {
            PanelManager.getInstance().setGodChoice(god2);
            PanelManager.getInstance().asyncWriteToSocket("2");
            card1.setEnabled(false);
            card2.setEnabled(false);
        });

        setSize(panelWidth,panelHeight);
        setLayout(null);
        setVisible(true);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(img, 0, 0, null);
    }

}
