package it.polimi.ingsw.view.gui;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;

public class Gui{

    public static void main(String[] args) throws IOException {
        start();
    }

    private static void start() throws IOException {
        JFrame gameFrame = new JFrame("SANTORINI");
        gameFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        Table table = new Table();
        gameFrame.add(table);

        gameFrame.setLocationRelativeTo(null);
        gameFrame.pack();     //la finestra assuma le dimensioni minime necessarie e sufficienti affinchè ciò che contiene sia visualizzato secondo le sue dimensioni ottimali
        gameFrame.setVisible(true);
    }


}