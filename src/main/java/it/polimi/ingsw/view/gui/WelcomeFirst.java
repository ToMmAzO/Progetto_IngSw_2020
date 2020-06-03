package it.polimi.ingsw.view.gui;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.IOException;

public class WelcomeFirst extends JPanel {

    
    /*For thread safety,
     * this method should be invoked from the
     * event-dispatching thread.

     */
    private final int panelHeight = 600;
    private final int panelWidth = 600;


    private final static String imagePath = "src/main/java/it/polimi/ingsw/view/gui/img/";
    private final Image loadingBack = ImageIO.read(new File(imagePath.concat("Odyssey-Olympus.png")));
    Image img = loadingBack.getScaledInstance(panelWidth, panelHeight, Image.SCALE_SMOOTH);

    public WelcomeFirst() throws IOException {
        final int buttonHeight = 220;
        final int buttonWidth = 200;


        setLayout(null);
        JTextPane welcomeFirst = new JTextPane();
        welcomeFirst.setText("You are the first player to connect to the lobby!!");
        Font f = new Font(Font.SANS_SERIF,3,25);
        welcomeFirst.setFont(f);
        welcomeFirst.setEditable(false);

        StyledDocument doc = welcomeFirst.getStyledDocument();
        SimpleAttributeSet center = new SimpleAttributeSet();
        StyleConstants.setAlignment(center, StyleConstants.ALIGN_CENTER);
        doc.setParagraphAttributes(0, doc.getLength(), center, false);

        welcomeFirst.setOpaque(false);
        welcomeFirst.setBounds(100,60,400,70);
        add(welcomeFirst);
        JLabel chosePlayers = new JLabel();
        chosePlayers.setText("Chose the number of players: ");
        Font f2 = new Font(Font.SANS_SERIF,3,15);
        chosePlayers.setFont(f2);

        chosePlayers.setBounds(190,200,250,30);
        add(chosePlayers);

        JButton button2 = new JButton();
        Image button2Png = ImageIO.read(new File(imagePath.concat("2playersButton.png")));
        ImageIcon button2Img = new ImageIcon(button2Png.getScaledInstance(buttonWidth, buttonHeight, Image.SCALE_SMOOTH));
        button2.setIcon(button2Img);
        button2.setBounds(70,250,buttonWidth,buttonHeight);
        button2.setOpaque(false);
        button2.setBorderPainted(false);
        button2.setBackground(Color.DARK_GRAY);

        JButton button3 = new JButton();
        button3.setBounds(70 + buttonWidth + 50,250,buttonWidth,buttonHeight);
        Image button3Png = ImageIO.read(new File(imagePath.concat("3playersButton.png")));
        ImageIcon button3Img = new ImageIcon(button3Png.getScaledInstance(buttonWidth, buttonHeight, Image.SCALE_SMOOTH));
        button3.setIcon(button3Img);
        button3.setOpaque(false);
        button3.setBorderPainted(false);
        button3.setBackground(Color.DARK_GRAY);

        button2.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                //manda notifica al server
                Gui.getInstance().asyncWriteToSocket("2");
                button2.setEnabled(false);
                button3.setEnabled(false);


            }

            @Override
            public void mousePressed(MouseEvent e) {

            }

            @Override
            public void mouseReleased(MouseEvent e) {

            }

            @Override
            public void mouseEntered(MouseEvent e) {
                ImageIcon button2Img = new ImageIcon(button2Png.getScaledInstance(buttonWidth + 10, buttonHeight + 10, Image.SCALE_SMOOTH));
                button2.setIcon(button2Img);

            }

            @Override
            public void mouseExited(MouseEvent e) {
                ImageIcon button2Img = new ImageIcon(button2Png.getScaledInstance(buttonWidth, buttonHeight, Image.SCALE_SMOOTH));
                button2.setIcon(button2Img);

            }
        });

        button3.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                //manda notifica al server
                Gui.getInstance().asyncWriteToSocket("3");
                button2.setEnabled(false);
                button3.setEnabled(false);



            }

            @Override
            public void mousePressed(MouseEvent e) {

            }

            @Override
            public void mouseReleased(MouseEvent e) {

            }

            @Override
            public void mouseEntered(MouseEvent e) {
                ImageIcon button3Img = new ImageIcon(button3Png.getScaledInstance(buttonWidth + 10, buttonHeight + 10, Image.SCALE_SMOOTH));
                button3.setIcon(button3Img);

            }

            @Override
            public void mouseExited(MouseEvent e) {
                ImageIcon button3Img = new ImageIcon(button3Png.getScaledInstance(buttonWidth, buttonHeight, Image.SCALE_SMOOTH));
                button3.setIcon(button3Img);

            }
        });

        add(button2);
        add(button3);
        setSize(panelWidth,panelHeight);
        setVisible(true);

    }

    @Override
    protected void paintComponent(Graphics g) {

        super.paintComponent(g);
        g.drawImage(img, 0, 0, null);
    }


    /*public static void main(String[] args) {

        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                JFrame mainFrame = new JFrame();
                try {
                    mainFrame.add(new WelcomeFirst());
                    mainFrame.setSize(600, 600);
                    mainFrame.setLocation(400, 20);
                    mainFrame.setVisible(true);
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        });

    }*/



}
