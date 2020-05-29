package it.polimi.ingsw.view.gui;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.IOException;

public class Welcome extends JPanel {
    private final int panelHeight = 600;
    private final int panelWidth = 600;

    private final static String imagePath = "src/main/java/it/polimi/ingsw/view/gui/img/";
    private final Image loadingBack = ImageIO.read(new File(imagePath.concat("Odyssey-Olympus.png")));
    Image img = loadingBack.getScaledInstance(panelWidth, panelHeight, Image.SCALE_SMOOTH);





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

        JLabel nicknameLine = new JLabel("__________________________________________ ");
        nicknameLine.setBounds(200,90 + logoHeight,200,30);

        int okHeight = 150;
        int okWidth = 100;
        okButton = new JButton();
        Image buttonPng = ImageIO.read(new File(imagePath.concat("button-play-normal.png")));
        ImageIcon buttonImg = new ImageIcon(buttonPng.getScaledInstance(okWidth, okHeight, Image.SCALE_SMOOTH));
        okButton.setIcon(buttonImg);
        okButton.setBounds(250,150 + logoHeight,okWidth,okHeight);
        okButton.setOpaque(false);
        okButton.setBorderPainted(false);
        okButton.setBackground(null);
        okButton.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {

                if(nicknameTextField.getText().isEmpty()){
                    System.out.println("chose a nickname");

                }else{
                    //manda al server la richiesta con il nickmane inserito
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
                ImageIcon buttonImg = new ImageIcon(buttonPng.getScaledInstance(okWidth + 10, okHeight + 10, Image.SCALE_SMOOTH));
                okButton.setIcon(buttonImg);

            }

            @Override
            public void mouseExited(MouseEvent e) {
                ImageIcon buttonImg = new ImageIcon(buttonPng.getScaledInstance(okWidth, okHeight, Image.SCALE_SMOOTH));
                okButton.setIcon(buttonImg);

            }
        });




        Image santoriniLogo = ImageIO.read(new File(imagePath.concat("santorini-logo.png")));
        ImageIcon img = new ImageIcon(santoriniLogo.getScaledInstance(logoWidth, logoHeight, Image.SCALE_REPLICATE));
        logo = new JLabel(img);
        logo.setBounds(100,20,logoWidth,logoHeight);
        logo.setOpaque(false);

        setLayout(null);
        add(welcome);
        add(nickname);
        add(nicknameTextField);
        add(okButton);
        add(logo);
        //setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //pack();
        setVisible(true);
        setBackground(Color.DARK_GRAY);

    }

    @Override
    protected void paintComponent(Graphics g) {

        super.paintComponent(g);
        g.drawImage(img, 0, 0, null);
    }

    public static void main(String[] args) {

        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                JFrame mainFrame = new JFrame();
                try {
                    mainFrame.add(new Welcome());
                } catch (IOException e) {
                    e.printStackTrace();
                }
                mainFrame.setVisible(true);
                mainFrame.setSize(600,600);
            }
        });
    }
}