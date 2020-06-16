package it.polimi.ingsw.view.gui.panels;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.IOException;

public class Welcome extends JPanel {

    private final static String backgroundsPath = "src/main/java/it/polimi/ingsw/view/gui/img/backgrounds/";
    private final static String buttonsPath = "src/main/java/it/polimi/ingsw/view/gui/img/buttons/";

    private final int panelHeight = 600;
    private final int panelWidth = 600;

    private final Image loadingBack = ImageIO.read(new File(backgroundsPath.concat("WelcomeBackground.png")));
    private final Image img = loadingBack.getScaledInstance(panelWidth, panelHeight, Image.SCALE_SMOOTH);

    public Welcome() throws IOException {
        super();
        int logoWidth = 400;
        int logoHeight = 130;
        JLabel logo;
        JLabel welcome;
        JLabel nickname;
        JTextField nicknameTextField;
        JButton okButton;

        welcome = new JLabel("WELCOME!!");
        welcome.setForeground(Color.BLACK);
        welcome.setBounds(270,40 + logoHeight,300,30);

        nickname = new JLabel("Chose a nickname: ");
        nickname.setForeground(Color.BLACK);
        nickname.setBounds(60,90 + logoHeight,200,30);
        nicknameTextField = new JTextField();
        nicknameTextField.setBounds(200,90 + logoHeight,200,30);
        nicknameTextField.setOpaque(false);

        int okWidth = 100;
        int okHeight = 150;

        okButton = new JButton();
        Image buttonPng = ImageIO.read(new File(buttonsPath.concat("PlayButton.png")));
        ImageIcon buttonImg = new ImageIcon(buttonsPath.concat("PlayButton.png"));
        okButton.setIcon(buttonImg);
        okButton.setBounds(250,150 + logoHeight,okWidth,okHeight);
        okButton.setOpaque(false);
        okButton.setBorderPainted(false);
        okButton.setBackground(Color.DARK_GRAY);

        okButton.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {

                if(nicknameTextField.getText().isEmpty()){
                    System.out.println("Chose a nickname");
                }else{
                    PanelManager.getInstance().asyncWriteToSocket(nicknameTextField.getText());
                    okButton.setEnabled(false);
                }

            }

            @Override
            public void mousePressed(MouseEvent e) {

            }

            @Override
            public void mouseReleased(MouseEvent e) {

            }

            @Override
            public void mouseEntered(MouseEvent e) {
                ImageIcon buttonImg2 = new ImageIcon(buttonPng.getScaledInstance(okWidth + 10, okHeight + 10, Image.SCALE_SMOOTH));
                okButton.setIcon(buttonImg2);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                okButton.setIcon(buttonImg);

            }
        });

        ImageIcon img = new ImageIcon(backgroundsPath.concat("SantoriniLogo.png"));
        logo = new JLabel(img);
        logo.setBounds(100,20,logoWidth,logoHeight);
        logo.setOpaque(false);

        setLayout(null);
        add(welcome);
        add(nickname);
        add(nicknameTextField);
        add(okButton);
        add(logo);
        setVisible(true);
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        super.paintComponent(g2d);
        g2d.drawImage(img, 0, 0, null);
    }

}