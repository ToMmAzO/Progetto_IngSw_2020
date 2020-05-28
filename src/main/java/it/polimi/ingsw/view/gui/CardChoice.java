package it.polimi.ingsw.view.gui;

import it.polimi.ingsw.model.cards.God;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;
import java.awt.Graphics;

public class CardChoice extends JPanel {
    private final static String ImagePath = "src/main/java/it/polimi/ingsw/view/gui/img/cards/";
    private final static String loadingImagePath = "src/main/java/it/polimi/ingsw/view/gui/img/Odyssey_UI_Backdrop.png";
    private final int imageWidth = 150;
    private final int imageHeight = 250;
    private final int panelHeight = 600;
    private final int panelWidth = 600;
    private final Image loadingBack = ImageIO.read(new File(loadingImagePath));
    Image img = loadingBack.getScaledInstance(panelWidth, panelHeight, Image.SCALE_SMOOTH);



    public CardChoice(God god1, God god2, God god3, boolean[] availability) throws IOException {   //DA FARE: costruttore per 2 god
        super();
        //setBackground(Color.RED);
        JLabel l1 = new JLabel("Choose a God, here's your options:");
        l1.setBounds(30,30,400,20);
        l1.setForeground(Color.WHITE);
        JButton card1 = new JButton();
        card1.setBounds(30,60,imageWidth,imageHeight);
        card1.setIcon(getImage(god1));
        if(!availability[0]) {
            card1.setEnabled(false);
            card1.setToolTipText("this God has already been chosen by another player");
        }
        JButton card2 = new JButton();
        card2.setBounds(30 + imageWidth + 20,60,imageWidth,imageHeight);
        card2.setIcon(getImage(god2));
        if(!availability[1]) {
            card2.setEnabled(false);
            card2.setToolTipText("this God has already been chosen by another player");
        }
        JButton card3 = new JButton();
        card3.setBounds(30 + 2*imageWidth + 20 + 20,60,imageWidth,imageHeight);
        card3.setIcon(getImage(god3));
        if(!availability[2]) {
            card3.setEnabled(false);
            card3.setToolTipText("this God has already been chosen by another player");
        }
        JTextArea info1 = new JTextArea(God.getGodDescription(god1));
        info1.setWrapStyleWord(true);
        info1.setLineWrap(true);
        info1.setEditable(false);
        info1.setBounds(30,60 + imageHeight + 20,imageWidth,150);
        info1.setOpaque(false);
        info1.setForeground(Color.WHITE);

        JTextArea info2 = new JTextArea(God.getGodDescription(god2));
        info2.setWrapStyleWord(true);
        info2.setLineWrap(true);
        info2.setEditable(false);
        info2.setBounds(30 + imageWidth + 20,60 + imageHeight + 20,imageWidth,150);
        info2.setOpaque(false);
        info2.setForeground(Color.WHITE);

        JTextArea info3 = new JTextArea(God.getGodDescription(god3));
        info3.setWrapStyleWord(true);
        info3.setLineWrap(true);
        info3.setEditable(false);
        info3.setBounds(30 + 2*imageWidth + 20 + 20,60 + imageHeight + 20,imageWidth,150);
        info3.setOpaque(false);
        info3.setForeground(Color.WHITE);

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


        setSize(panelWidth,panelHeight);
        setLayout(null);
        setVisible(true);
    }

    public CardChoice(God god1, God god2, boolean[] availability) throws IOException {
        super();


        JLabel l1 = new JLabel("Choose a God, here's your options:");
        l1.setBounds(30,30,400,20);
        JButton card1 = new JButton();
        card1.setBounds(30,60,imageWidth,imageHeight);
        card1.setIcon(getImage(god1));
        if(!availability[0]) {
            card1.setEnabled(false);
            card1.setToolTipText("this God has already been chosen by another player");
        }
        JButton card2 = new JButton();
        card2.setBounds(30 + imageWidth + 20,60,imageWidth,imageHeight);
        card2.setIcon(getImage(god2));
        if(!availability[1]) {
            card2.setEnabled(false);
            card2.setToolTipText("this God has already been chosen by another player");
        }

        JTextArea info1 = new JTextArea(God.getGodDescription(god1));
        info1.setWrapStyleWord(true);
        info1.setLineWrap(true);
        info1.setEditable(false);
        info1.setBounds(30,60 + imageHeight + 20,imageWidth,150);
        info1.setOpaque(false);
        info1.setForeground(Color.WHITE);

        JTextArea info2 = new JTextArea(God.getGodDescription(god2));
        info2.setWrapStyleWord(true);
        info2.setLineWrap(true);
        info2.setEditable(false);
        info2.setBounds(30 + imageWidth + 20,60 + imageHeight + 20,imageWidth,150);
        info2.setOpaque(false);
        info2.setForeground(Color.WHITE);

        add(l1);
        add(card1);
        add(card2);
        add(info1);
        add(info2);


        card1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //segnala al server che è stato scelto god1
                System.out.println("god1");
                card1.setEnabled(false);    //disabilita i bottoni cosi da non poter mandare più di una richiesta
                card2.setEnabled(false);
            }
        });


        card2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //segnala al server che è stato scelto god2
                System.out.println("god2");
                card1.setEnabled(false);    //disabilita i bottoni cosi da non poter mandare più di una richiesta
                card2.setEnabled(false);
            }
        });


        setSize(panelWidth,panelHeight);
        setLayout(null);
        setVisible(true);
    }

    @Override
    protected void paintComponent(Graphics g) {

        super.paintComponent(g);
        g.drawImage(img, 0, 0, null);
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

        return new ImageIcon(image.getScaledInstance(imageWidth, imageHeight, Image.SCALE_AREA_AVERAGING));
    }


    public static void main(String[] args) {

        //javax.swing.SwingUtilities.invokeLater(new Runnable() {
          //  public void run() {

                try {
                    System.out.println(ImagePath.concat("/Apollo.png"));
                    JFrame mainFrame = new JFrame();
                    boolean[] availability = {true,true,true};
                    JPanel cardChoice =new CardChoice(God.ATLAS,God.MINOTAUR,God.ZEUS,availability);
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
