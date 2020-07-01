package it.polimi.ingsw.view.gui.panels;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;

public class Welcome extends JPanel {

    private final static String backgroundsPath = "/img/backgrounds/";
    private final static String buttonsPath = "/img/buttons/";

    private final int panelHeight = 600;
    private final int panelWidth = 600;

    private final Image loadingBack = ImageIO.read(getClass().getResource(backgroundsPath.concat("WelcomeBackground.png")));
    private final Image img = loadingBack.getScaledInstance(panelWidth, panelHeight, Image.SCALE_SMOOTH);

    private final JTextPane welcome;
    private Font f;

    /**
     * create the JPanel for the Welcome phase of the game.
     * it let the player insert a nickname
     * @throws IOException for errors when reading from file
     */
    public Welcome() throws IOException {
        super();
        int logoWidth = 400;
        int logoHeight = 130;
        JLabel logo;
        JLabel nickname;
        JLabel flag;
        JTextField nicknameTextField;
        JButton okButton;

        welcome = new JTextPane();
        welcome.setText("WELCOME!!");
        f = new Font(Font.SANS_SERIF, Font.BOLD | Font.ITALIC,30);
        welcome.setFont(f);
        welcome.setEditable(false);
        welcome.setBounds(110,30 + logoHeight,390,50);
        welcome.setForeground(Color.WHITE);
        welcome.setOpaque(false);
        StyledDocument doc = welcome.getStyledDocument();
        SimpleAttributeSet center = new SimpleAttributeSet();
        StyleConstants.setAlignment(center, StyleConstants.ALIGN_CENTER);
        doc.setParagraphAttributes(0, doc.getLength(), center, false);

        nickname = new JLabel("Chose a nickname:");
        nickname.setForeground(Color.BLACK);
        nickname.setBounds(80,140 + logoHeight,200,30);

        flag = new JLabel();
        flag.setIcon(new ImageIcon(getClass().getResource(backgroundsPath.concat("Flag.png"))));
        flag.setBounds(52, 136, 493, 145);

        nicknameTextField = new JTextField();
        nicknameTextField.setBounds(200,140 + logoHeight,200,30);
        nicknameTextField.setOpaque(false);

        int okWidth = 100;
        int okHeight = 150;

        okButton = new JButton();
        Image buttonPng = ImageIO.read(getClass().getResource(buttonsPath.concat("PlayButton.png")));
        ImageIcon buttonImg = new ImageIcon(buttonPng);
        okButton.setIcon(buttonImg);
        okButton.setBounds(250,180 + logoHeight,okWidth,okHeight);
        okButton.setOpaque(false);
        okButton.setBorderPainted(false);
        okButton.setBackground(Color.DARK_GRAY);

        okButton.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {

                if(nicknameTextField.getText().isEmpty()){
                    f = new Font(Font.SANS_SERIF, Font.BOLD | Font.ITALIC,18);
                    welcome.setFont(f);
                    welcome.setText("You must choose a \nnickname to play!");
                } else{
                    PanelManager.getInstance().asyncWriteToSocket(nicknameTextField.getText());
                }

            }

            @Override
            public void mousePressed(MouseEvent e) {

            }

            @Override
            public void mouseReleased(MouseEvent e) {

            }

            @Override
            public void mouseEntered(MouseEvent e) {
                ImageIcon buttonImg2 = new ImageIcon(buttonPng.getScaledInstance(okWidth + 10, okHeight + 10, Image.SCALE_SMOOTH));
                okButton.setIcon(buttonImg2);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                okButton.setIcon(buttonImg);

            }
        });

        ImageIcon img = new ImageIcon(getClass().getResource(backgroundsPath.concat("SantoriniLogo.png")));
        logo = new JLabel(img);
        logo.setBounds(100,20,logoWidth,logoHeight);
        logo.setOpaque(false);

        setLayout(null);
        add(welcome);
        add(nickname);
        add(flag);
        add(nicknameTextField);
        add(okButton);
        add(logo);
        setVisible(true);
    }

    /**
     * notifies the player if it's chosen nickname it's not acceptable (ex: missing nickname or already chosen by another player)
     * @param message the message you want to be notified
     */
    public void changeNickname(String message){
        f = new Font(Font.SANS_SERIF, Font.BOLD | Font.ITALIC,20);
        welcome.setFont(f);
        welcome.setText(message);
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        super.paintComponent(g2d);
        g2d.drawImage(img, 0, 0, null);
    }

}