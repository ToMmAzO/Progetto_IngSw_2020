package it.polimi.ingsw.view.gui;

import javax.swing.*;

public class WelcomeFirst extends JFrame{
    private JPanel mainPanel;
    private JLabel firstPlayer;
    private JLabel number;
    private JPanel numberChoice;
    private JButton a2Button;
    private JButton a3Button;


    public WelcomeFirst(){
        add(mainPanel);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
        setVisible(true);
    }

    public static void main(String[] args) {
        new WelcomeFirst();
    }
}
