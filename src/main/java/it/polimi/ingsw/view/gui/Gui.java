package it.polimi.ingsw.view.gui;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Gui{

    /*
    public static void main(String[] args) {
        JFrame frame = createFrame();

        JLayeredPane layeredPane = new JLayeredPane();

        //adding a button to the JLayeredPane
        JButton button = new JButton("Show message");
        //need to do absolute positioning because by default LayeredPane has null layout,
        button.setBounds(100, 50, 150, 30);
        layeredPane.add(button, JLayeredPane.DEFAULT_LAYER);//depth 0

        //adding an initially invisible JLabel in an upper layer
        JLabel label = new JLabel("Test message");
        label.setOpaque(true);
        label.setHorizontalAlignment(SwingConstants.CENTER);
        //setting background with transparency value to see though the label
        label.setBackground(new Color(50, 210, 250, 200));
        //just set the size for now
        label.setSize(200, 50);
        label.setBorder(new LineBorder(Color.gray));
        label.setVisible(false);
        layeredPane.add(label, JLayeredPane.POPUP_LAYER);//depth 300

        //to make label visible
        button.addActionListener(e -> {
            JComponent source = (JComponent) e.getSource();
            //set the  popup label location at center of the source component
            label.setLocation(new Point(source.getX() + source.getWidth() / 2,
                    source.getY() + source.getHeight() / 2));
            label.setVisible(true);
        });

        //to hide the label
        frame.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                label.setVisible(false);
            }
        });

        frame.add(layeredPane);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    private static JFrame createFrame() {
        JFrame frame = new JFrame("JLayeredPane Basic Example");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(new Dimension(500, 400));
        return frame;
    }

     */


    public static void main(String[] args) {
        EventQueue.invokeLater(Gui::start);
    }

    /*
    private static void start() {
        JFrame window = new JFrame("Test");
        window.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        JLayeredPane pane = window.getLayeredPane();

        JPanel content = new JPanel(new GridBagLayout());
        content.setBorder(new EmptyBorder(100, 100, 100, 100));
        content.setSize(1200, 1200);
        window.setContentPane(content);



        ImageIcon img = new ImageIcon("C:\\Users\\tomma\\Desktop\\Progect\\Risorse grafiche Santorini\\SantoriniBoard.png");
        Image image = img.getImage();
        Image modifiedImage = image.getScaledInstance(1440, 810, Image.SCALE_SMOOTH);
        img = new ImageIcon(modifiedImage);
        JLabel BackGround = new JLabel(img);
        pane.add(BackGround, 3);

        JPanel grid = new JPanel(new GridLayout(5, 5));
        for(int i = 0; i < 25; i++) {
            JButton pulsante = new JButton(String.valueOf(i));
            pulsante.setEnabled(true);
            pulsante.setOpaque(true);
            int finalI = i;
            pulsante.addActionListener(e -> System.out.println(finalI));
            grid.add(pulsante);
        }
        pane.add(grid,2);

        window.pack();
        window.setLocationRelativeTo(null);
        window.setVisible(true);
    }

     */


    private static void start() {
        JButton next = new JButton("Successivo");
        JButton prev = new JButton("Precedente");
        JPanel first = new JPanel(new BorderLayout());
        JPanel second = new JPanel(new BorderLayout());
        JPanel third = new JPanel(new BorderLayout());
        Dimension cardSize = new Dimension(400, 300);
        first.add(new JLabel("Primo", JLabel.CENTER));
        second.add(new JLabel("Secondo", JLabel.CENTER));
        third.add(new JLabel("Terzo", JLabel.CENTER));
        first.setPreferredSize(cardSize);
        second.setPreferredSize(cardSize);
        third.setPreferredSize(cardSize);
        final JPanel screen = new JPanel();
        final CardLayout layout = new CardLayout();
        screen.setLayout(layout);
        screen.add(first, "first");
        screen.add(second, "second");
        screen.add(third, "third");
        next.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                layout.next(screen);
            }
        });
        prev.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                layout.previous(screen);
            }
        });
        JPanel buttonContainer = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonContainer.add(prev);
        buttonContainer.add(next);
        JPanel container = new JPanel(new BorderLayout());
        container.add(screen, BorderLayout.CENTER);
        container.add(buttonContainer, BorderLayout.SOUTH);
        JFrame window = new JFrame("Test");
        window.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        window.add(container);
        window.pack();
        window.setLocationRelativeTo(null);
        window.setVisible(true);
    }


}