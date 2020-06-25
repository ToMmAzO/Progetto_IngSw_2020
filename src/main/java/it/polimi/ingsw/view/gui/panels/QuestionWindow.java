package it.polimi.ingsw.view.gui.panels;

import it.polimi.ingsw.network.message.*;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;

public class QuestionWindow extends JWindow {

    private final static String backgroundsPath = "/img/backgrounds/";
    private final static String buttonsPath = "/img/buttons/";

    public QuestionWindow(JFrame owner, Message message) throws IOException {
        super(owner);
        JButton yesBtn = new JButton();
        JButton noBtn = new JButton();
        JLabel background = new JLabel();

        background.setIcon(new ImageIcon(getClass().getResource(backgroundsPath.concat("QuestionBackground.png"))));
        background.setBounds(0,0,580,300);

        JTextPane info = new JTextPane();
        info.setText(message.getMessage());
        StyledDocument doc = info.getStyledDocument();
        SimpleAttributeSet center = new SimpleAttributeSet();
        StyleConstants.setAlignment(center, StyleConstants.ALIGN_CENTER);
        doc.setParagraphAttributes(0, doc.getLength(), center, false);
        Font f = new Font(Font.SANS_SERIF, Font.BOLD | Font.ITALIC,13);
        info.setFont(f);
        info.setEditable(false);
        info.setOpaque(false);
        info.setBounds(230,50,200,80);

        Image yesButtonImage = ImageIO.read(getClass().getResource(buttonsPath.concat("YesButton.png")));
        ImageIcon yesBt = new ImageIcon(yesButtonImage);
        yesBtn.setIcon(yesBt);
        yesBtn.setBounds(230,180,140,80);
        yesBtn.setVisible(true);
        yesBtn.setOpaque(false);
        yesBtn.setBorderPainted(false);
        yesBtn.setBackground(Color.DARK_GRAY);
        yesBtn.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                PanelManager.getInstance().asyncWriteToSocket("yes");
                dispose();
            }

            @Override
            public void mousePressed(MouseEvent e) {

            }

            @Override
            public void mouseReleased(MouseEvent e) {

            }

            @Override
            public void mouseEntered(MouseEvent e) {
                ImageIcon buttonEntered = new ImageIcon(yesButtonImage.getScaledInstance(134 + 10, 70 + 10, Image.SCALE_SMOOTH));
                yesBtn.setIcon(buttonEntered);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                yesBtn.setIcon(yesBt);
            }
        });

        Image noButtonImage = ImageIO.read(getClass().getResource(buttonsPath.concat("NoButton.png")));
        ImageIcon noBt = new ImageIcon(noButtonImage);
        noBtn.setIcon(noBt);
        noBtn.setBounds(400,180,140,80);
        noBtn.setVisible(true);
        noBtn.setOpaque(false);
        noBtn.setBorderPainted(false);
        noBtn.setBackground(Color.DARK_GRAY);
        noBtn.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                PanelManager.getInstance().asyncWriteToSocket("no");
                dispose();
            }

            @Override
            public void mousePressed(MouseEvent e) {

            }

            @Override
            public void mouseReleased(MouseEvent e) {

            }

            @Override
            public void mouseEntered(MouseEvent e) {
                ImageIcon buttonEntered = new ImageIcon(noButtonImage.getScaledInstance(134 + 10, 70 + 10, Image.SCALE_SMOOTH));
                noBtn.setIcon(buttonEntered);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                noBtn.setIcon(noBt);
            }
        });


        setLayout(null);
        setSize(580,300);
        setVisible(true);
        add(yesBtn);
        add(noBtn);
        add(info);
        add(background);
    }

}
