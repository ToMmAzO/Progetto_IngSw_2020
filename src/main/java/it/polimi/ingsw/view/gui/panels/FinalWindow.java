package it.polimi.ingsw.view.gui.panels;

import it.polimi.ingsw.model.game.GameState;
import it.polimi.ingsw.network.message.Message;
import it.polimi.ingsw.network.message.Message_Error;
import it.polimi.ingsw.network.message.Message_Invalidation;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class FinalWindow extends JWindow {

    private final static String backgroundsPath = "src/main/java/it/polimi/ingsw/view/gui/img/backgrounds/";

    public FinalWindow(JFrame owner, GameState gameState) throws IOException {
        super(owner);
        JLabel background = new JLabel();
        switch (gameState) {
            case WIN -> {
                background.setIcon(new ImageIcon(backgroundsPath.concat("Win.gif")));
                background.setBounds(0,0,600,300);
                setSize(600,300);
            }
            case LOSE -> {
                background.setIcon(new ImageIcon(backgroundsPath.concat("Lose.gif")));
                background.setBounds(0,0,600,300);
                setSize(600,300);
            }
            default -> {
                Image invalidationImage = ImageIO.read(new File(backgroundsPath.concat("ErrorBackground.png")));
                background.setIcon(new ImageIcon(invalidationImage));
                background.setBounds(0,0,500,250);
                setSize(500,250);

                Message message;
                if(gameState == GameState.INVALIDATION){
                    message = new Message_Invalidation();
                } else{
                    message = new Message_Error();
                }

                JTextPane info = new JTextPane();
                info.setText(message.getMessage());
                StyledDocument doc = info.getStyledDocument();
                SimpleAttributeSet center = new SimpleAttributeSet();
                StyleConstants.setAlignment(center, StyleConstants.ALIGN_CENTER);
                doc.setParagraphAttributes(0, doc.getLength(), center, false);
                Font f = new Font(Font.SANS_SERIF, Font.BOLD | Font.ITALIC,17);
                info.setForeground(new Color(0x3C3C3C));
                info.setFont(f);
                info.setEditable(false);
                info.setOpaque(false);
                info.setBounds(100,50,300,150);
                background.add(info);
            }
        }

        setLayout(null);
        setVisible(true);
        add(background);
    }
}

