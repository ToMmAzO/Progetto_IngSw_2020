package it.polimi.ingsw.view.gui;

import javax.swing.*;
import java.awt.*;

public class WelcomeFirst extends JPanel {

    
    /*For thread safety,
     * this method should be invoked from the
     * event-dispatching thread.

     */

     public WelcomeFirst(){


        setLayout(new BorderLayout(0, 0));
        JLabel welcomeFirst = new JLabel();
        welcomeFirst.setText("You are the first player to connect to the lobby!!");
        add(welcomeFirst, BorderLayout.NORTH);
        JLabel chosePlayers = new JLabel();
        chosePlayers.setText("Chose the number of players: ");
        chosePlayers.setBounds(45,45,100,20);
        add(chosePlayers, BorderLayout.WEST);
        JPanel choices = new JPanel();
        choices.setLayout(new FlowLayout());
        add(choices, BorderLayout.CENTER);
        JButton button2 = new JButton();
        button2.setText("2");
        button2.setBounds(48,45,100,20);
        JButton button3 = new JButton();
        button3.setText("3");
        button3.setBounds(151,45,100,20);
        choices.add(button2,BorderLayout.WEST);
        choices.add(button3,BorderLayout.EAST);

        setSize(700,400);


        setVisible(true);

    }


    public static void main(String[] args) {

        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                JFrame mainFrame = new JFrame();
                mainFrame.add(new WelcomeFirst());
                mainFrame.setVisible(true);
                mainFrame.pack();
            }
        });
    }




}
