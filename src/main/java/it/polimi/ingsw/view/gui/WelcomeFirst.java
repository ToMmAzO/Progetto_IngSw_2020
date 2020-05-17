package it.polimi.ingsw.view.gui;

import javax.swing.*;

public class WelcomeFirst extends JFrame{
    private JPanel mainPanel;
    private JLabel firstPlayer;
    private JLabel number;
    private JPanel numberChoice;
    private JButton a2Button;
    private JButton a3Button;

    /*For thread safety,
     * this method should be invoked from the
     * event-dispatching thread.

     */

    public WelcomeFirst(){
        add(mainPanel);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
        setVisible(true);
    }

    public static void main(String[] args) {

        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new WelcomeFirst();
            }
        });

    }
}
