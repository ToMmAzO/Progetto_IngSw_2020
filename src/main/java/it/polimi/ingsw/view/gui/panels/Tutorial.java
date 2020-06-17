package it.polimi.ingsw.view.gui.panels;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class Tutorial extends JWindow {

    private final static String backgroundsPath = "src/main/java/it/polimi/ingsw/view/gui/img/backgrounds/";
    private final static String buttonsPath = "src/main/java/it/polimi/ingsw/view/gui/img/buttons/";
    private final Image backgroundImage = ImageIO.read(new File(backgroundsPath.concat("TutorialBackground.png")));

    public Tutorial(JFrame owner) throws IOException {
        super(owner);
        Background background = new Background();

        JButton exit = new JButton();
        Image btnImage = ImageIO.read(new File(buttonsPath.concat("CloseButton.png")));
        exit.setIcon(new ImageIcon(btnImage));
        exit.setSize(200,90);
        exit.setLocation(545,12);
        exit.addActionListener(e -> setVisible(false));
        exit.setVisible(true);
        exit.setBackground(Color.WHITE);
        exit.setOpaque(false);
        exit.setBorderPainted(false);
        add(exit);

        add(background);
        setBackground(new Color(0,0,0,0));
        setLayout(null);
        setSize(1280,720);
        setVisible(true);
    }

    private class Background extends JPanel {

        protected Background(){
            this.setBounds(25, 0, 1100, 720);
            this.setOpaque(false);
            this.setVisible(true);
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            g.drawImage(backgroundImage, 0, 0, null);
        }

    }

}
