package it.polimi.ingsw.view.gui.panels;

import it.polimi.ingsw.model.game.GameState;
import it.polimi.ingsw.network.message.Message;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class FinalWindow  extends JWindow {

    Image winImage = ImageIO.read(new File("src/main/java/it/polimi/ingsw/view/gui/img/questionBackground2.png"));
    Image loseImage = ImageIO.read(new File("src/main/java/it/polimi/ingsw/view/gui/img/questionBackground2.png"));
    Image invalidationImage = ImageIO.read(new File("src/main/java/it/polimi/ingsw/view/gui/img/questionBackground2.png"));

    public FinalWindow(GameState gameState, Message message) throws IOException {
        JLabel background = new JLabel();
        switch (gameState) {
            case WIN -> background.setIcon(new ImageIcon(winImage));
            case LOSE -> background.setIcon(new ImageIcon(loseImage));
            case INVALIDATION -> background.setIcon(new ImageIcon(invalidationImage));
        }
        background.setBounds(0,0,580,300);

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

        setLayout(null);
        setSize(580,300);
        setVisible(true);
        add(info);
        add(background);
    }
}

