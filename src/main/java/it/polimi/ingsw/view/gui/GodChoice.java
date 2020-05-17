package it.polimi.ingsw.view.gui;

import javax.swing.*;

public class GodChoice extends JFrame{
    private JPanel mainPanel;
    private JLabel godChoice;
    private JPanel god1Panel;
    private JPanel god2Panel;
    private JPanel god3Panel;
    private JLabel godPicture1;
    private JLabel godName1;
    private JLabel godPower1;
    private JLabel godName2;
    private JLabel godPicture;
    private JLabel godPower2;
    private JLabel godName3;
    private JLabel godPicture3;
    private JLabel godPower3;
    private JCheckBox checkBox1;
    private JCheckBox checkBox2;
    private JCheckBox checkBox3;
    private JButton okButton;

    public GodChoice(){
        add(mainPanel);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
        setVisible(true);
    }

    public static void main(String[] args) {

        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new GodChoice();
            }
        });

    }
}
