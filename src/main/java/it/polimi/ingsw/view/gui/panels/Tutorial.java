package it.polimi.ingsw.view.gui.panels;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

public class Tutorial extends JPanel {

    private final static String backgroundsPath = "src/main/java/it/polimi/ingsw/view/gui/img/backgrounds/";
    private final static String buttonsPath = "src/main/java/it/polimi/ingsw/view/gui/img/buttons/";
    Image backgroundImage = ImageIO.read(new File(backgroundsPath.concat("tutorial_background2.png")));

    public Tutorial() throws IOException {

       /* JLabel background = new JLabel();
        Image backgroundImage = ImageIO.read(new File(backgroundsPath.concat("tutorial_background2.png")));
        background.setIcon(new ImageIcon(backgroundImage));
        background.setBounds(0,0,1280,720);
        background.setOpaque(false);
        background.setVisible(true);*/



        JButton exit = new JButton();
        Image btnImage = ImageIO.read(new File(buttonsPath.concat("close_tut_btn.png")));
        exit.setIcon(new ImageIcon(btnImage));
        exit.setBounds(1080,630,200,90);
        exit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
            }
        });
        exit.setVisible(true);

        setOpaque(false);
        setBackground(new Color(0,0,0,0));
        setLayout(null);
        setBounds(0,0,1280,720);
        setVisible(true);
        add(exit);
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        super.paintComponent(g2d);
        g2d.drawImage(backgroundImage, 0, 0, null);
    }
}
