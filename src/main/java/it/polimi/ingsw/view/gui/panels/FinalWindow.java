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

public class FinalWindow  extends JWindow {

    Image winImage = ImageIO.read(new File("src/main/java/it/polimi/ingsw/view/gui/img/Win.png"));
    Image loseImage = ImageIO.read(new File("src/main/java/it/polimi/ingsw/view/gui/img/Lose.png"));
    Image invalidationImage = ImageIO.read(new File("src/main/java/it/polimi/ingsw/view/gui/img/questionBackground2.png"));

    public FinalWindow(GameState gameState) throws IOException {
        JLabel background = new JLabel();
        switch (gameState) {
            case WIN -> {
                background.setIcon(new ImageIcon(winImage));
                background.setBounds(0,0,600,300);
            }
            case LOSE -> {
                background.setIcon(new ImageIcon(loseImage));
                background.setBounds(0,0,600,300);
            }
            default -> {
                background.setIcon(new ImageIcon(invalidationImage));
                background.setBounds(0,0,580,300);

                Message message;
                if(gameState == GameState.INVALIDATION){
                    message = new Message_Invalidation();
                }else{
                    message = new Message_Error();
                }

                JTextPane info = new JTextPane();
                info.setText(message.getMessage());
                StyledDocument doc = info.getStyledDocument();
                SimpleAttributeSet center = new SimpleAttributeSet();
                StyleConstants.setAlignment(center, StyleConstants.ALIGN_CENTER);
                doc.setParagraphAttributes(0, doc.getLength(), center, false);
                Font f = new Font(Font.SANS_SERIF, Font.BOLD | Font.ITALIC,13);
                info.setFont(f);
                info.setEditable(false);
                info.setOpaque(false);
                info.setBounds(230,50,200,80);
                add(info);
            }
        }

        setLayout(null);
        setVisible(true);
        add(background);
    }
}

