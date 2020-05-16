package it.polimi.ingsw.view.gui;

import javax.swing.*;

public class Gui {
    private static void createAndShowGUI(){
        JFrame frame = new JFrame("Santorini");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);   //termina quando chiudo
        JLabel label = new JLabel("WOW");
        frame.getContentPane().add(label);
        frame.pack();
        frame.setVisible(true);
    }

    public static void main(String[] args){
        createAndShowGUI();
    }
}
