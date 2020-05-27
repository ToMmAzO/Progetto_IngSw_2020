package it.polimi.ingsw.view.gui;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class Gui{

    private final static Dimension TABLE_DIMENSION = new Dimension(1440,810);

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                start();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    private static void start() throws IOException {
        JFrame gameFrame = new JFrame("SANTORINI");
        gameFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        Table table = new Table();
        gameFrame.add(table);

        //gameFrame.setLocationRelativeTo(null);
        //gameFrame.pack();     //la finestra assuma le dimensioni minime necessarie e sufficienti affinchè ciò che contiene sia visualizzato secondo le sue dimensioni ottimali

        gameFrame.setVisible(true);
        gameFrame.validate();
        //gameFrame.setResizable(true);   se vogliamo impedire che la finestra venga ridimensionata
    }

}