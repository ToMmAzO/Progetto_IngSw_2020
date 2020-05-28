package it.polimi.ingsw.view.gui;

import it.polimi.ingsw.model.cards.God;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

public class CardChoice extends JPanel {
    private final static String ImagePath = "src/main/java/it/polimi/ingsw/view/gui/img/cards";
    private final int imageWidth = 150;
    private final int imageHeight = 250;

    public CardChoice(God god1, God god2, God god3) throws IOException {
        super();
        //setBackground(Color.RED);
        JLabel l1 = new JLabel("Choose a God, here's your options:");
        l1.setBounds(30,30,150,20);
        JButton card1 = new JButton();
        card1.setBounds(30,60,imageWidth,imageHeight);
        card1.setIcon(getImage(god1));
        JButton card2 = new JButton();
        card2.setBounds(30 + imageWidth + 20,60,imageWidth,imageHeight);
        card2.setIcon(getImage(god2));
        JButton card3 = new JButton();
        card3.setBounds(30 + 2*imageWidth + 20 + 20,60,imageWidth,imageHeight);
        card3.setIcon(getImage(god3));
        JLabel info1 = new JLabel(God.getGodDescription(god1));
        info1.setBounds(30,300,imageWidth,60);
        JLabel info2 = new JLabel(God.getGodDescription(god2));
        info2.setBounds(30 + imageWidth + 20,300,imageWidth,60);
        JLabel info3 = new JLabel(God.getGodDescription(god3));
        info1.setBounds(30 + 2*imageWidth + 20,300,imageWidth,60);
        add(l1);
        add(card1);
        add(card2);
        add(card3);
        add(info1);
        add(info2);
        add(info3);


        card1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //segnala al server che è stato scelto god1
                System.out.println("god1");
                card1.setEnabled(false);    //disabilita i bottoni cosi da non poter mandare più di una richiesta
                card2.setEnabled(false);
                card3.setEnabled(false);
            }
        });

        card2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //segnala al server che è stato scelto god2
                System.out.println("god2");
                card1.setEnabled(false);    //disabilita i bottoni cosi da non poter mandare più di una richiesta
                card2.setEnabled(false);
                card3.setEnabled(false);
            }
        });

        card3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //segnala al server che è stato scelto god3
                System.out.println("god3");
                card1.setEnabled(false);    //disabilita i bottoni cosi da non poter mandare più di una richiesta
                card2.setEnabled(false);
                card3.setEnabled(false);
            }
        });

        setSize(600,800);
        setLayout(null);
        setVisible(true);
    }

    public ImageIcon getImage(God god) throws IOException {
        Image image;

        image = ImageIO.read(new File(ImagePath + god.toString() + ".png"));

        /*switch (god){
            case APOLLO -> image = ImageIO.read(new File(ImagePath.concat("/Apollo.png")));
            case ARTEMIS -> image = ImageIO.read(new File(ImagePath.concat("/Artemis.png")));
            case ATHENA -> image = ImageIO.read(new File(ImagePath.concat("/Athena.png")));
            case ATLAS -> image = ImageIO.read(new File(ImagePath.concat("/Atlas.png")));
            case CHRONUS -> image = ImageIO.read(new File(ImagePath.concat("/Chronus.png")));
            case DEMETER -> image = ImageIO.read(new File(ImagePath.concat("/Demeter.png")));
            case HEPHAESTUS -> image = ImageIO.read(new File(ImagePath.concat("/Hephaestus.png")));
            case HERA -> image = ImageIO.read(new File(ImagePath.concat("/Hera.png")));
            case HESTIA -> image = ImageIO.read(new File(ImagePath.concat("/Hestia.png")));
            case MINOTAUR -> image = ImageIO.read(new File(ImagePath.concat("/Minotaur.png")));
            case PAN -> image = ImageIO.read(new File(ImagePath.concat("/Pan.png")));
            case PROMETHEUS -> image = ImageIO.read(new File(ImagePath.concat( "/Prometheus.png")));
            case TRITON -> image = ImageIO.read(new File(ImagePath.concat("/Triton.png")));
            default -> image = ImageIO.read(new File(ImagePath.concat("/Zeus.png")));
        }*/

        return new ImageIcon(image.getScaledInstance(imageWidth, imageHeight, Image.SCALE_SMOOTH));
    }


    public static void main(String[] args) {

        //javax.swing.SwingUtilities.invokeLater(new Runnable() {
          //  public void run() {

                try {
                    System.out.println(ImagePath.concat("/Apollo.png"));
                    JFrame mainFrame = new JFrame();
                    JPanel cardChoice =new CardChoice(God.ATLAS,God.MINOTAUR,God.PROMETHEUS);
                    mainFrame.add(cardChoice);
                    mainFrame.setVisible(true);
                    mainFrame.pack();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            //}
      //  });
    }
}
