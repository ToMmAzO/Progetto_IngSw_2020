package it.polimi.ingsw.view.gui;

import javax.swing.*;
import java.awt.*;

public class Welcome extends JPanel {

    JLabel welcome;
    JLabel nickname;
    JTextField nicknameTextField;
    JButton okButton;

    public Welcome() {

        this.welcome = new JLabel("WELCOME TO SANTORINI!!");
        this.nickname = new JLabel("Chose a nickname: ");
        this.nicknameTextField = new JTextField();
        this.okButton = new JButton("OK");
        setLayout(new BorderLayout());
        add(welcome, BorderLayout.NORTH);
        add(nickname, BorderLayout.WEST);
        add(nicknameTextField, BorderLayout.CENTER);
        add(okButton, BorderLayout.SOUTH);
        //setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //pack();
        setVisible(true);

    }

    public static void main(String[] args) {

        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                JFrame mainFrame = new JFrame();
                mainFrame.add(new Welcome());
                mainFrame.setVisible(true);
                mainFrame.pack();
            }
        });
    }
}