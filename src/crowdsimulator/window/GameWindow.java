package crowdsimulator.window;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 * La fenêtre contenant l'application.
 * @author Arnaud
 */
public class GameWindow extends JFrame {
    
    private final Dimension size = new Dimension(150, 20);
    private final JButton start = new JButton("LANCER");
    private JLabel nbTours = new JLabel("0");
    private JLabel nbMoves = new JLabel("0");
    private JLabel mouseArrived = new JLabel("0");
    private JLabel mouseMoving = new JLabel("0");
    private final JPanel center_container = new JPanel();
    private final JPanel bottom_container = new JPanel();
    private final JPanel bottom_left_container = new JPanel();
    private final JPanel bottom_right_container = new JPanel();
    private final JPanel main_container = new JPanel();
    private final JTextField inputNBMouse1 = new JTextField();
    private final JTextField inputNBMouse2 = new JTextField();
    private final JTextField inputTime = new JTextField();
    
    public GameWindow() {
        
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent event) {
                System.exit(0);
            }
        });
        
        this.inputNBMouse1.setPreferredSize(size);
        this.inputNBMouse2.setPreferredSize(size);
        this.inputTime.setPreferredSize(size);
        
        this.main_container.setLayout(new BorderLayout());
        this.bottom_container.setLayout(new BorderLayout());
        this.main_container.add(center_container, BorderLayout.CENTER);
        this.main_container.add(bottom_container, BorderLayout.SOUTH);
        this.bottom_container.add(bottom_left_container, BorderLayout.WEST);
        this.bottom_container.add(bottom_right_container, BorderLayout.EAST);
        
        this.bottom_left_container.add(new JLabel("TOURS"));
        this.bottom_left_container.add(nbTours);
        this.bottom_left_container.add(new JLabel("DÉPLACEMENTS"));
        this.bottom_left_container.add(nbMoves);
        this.bottom_left_container.add(new JLabel("SOURIS EN DÉPLACEMENT"));
        this.bottom_left_container.add(mouseMoving);
        this.bottom_left_container.add(new JLabel("SOURIS ARRIVÉES"));
        this.bottom_left_container.add(mouseArrived);
        
        this.bottom_right_container.add(new JLabel("PORTE 1"));
        this.bottom_right_container.add(inputNBMouse1);
        this.bottom_right_container.add(new JLabel("PORTE 2"));
        this.bottom_right_container.add(inputNBMouse2);
        this.bottom_right_container.add(new JLabel("VITESSE"));
        this.bottom_right_container.add(inputTime);
        this.bottom_right_container.add(start);        
        
        this.setTitle("Simulateur de foule");
        this.setContentPane(main_container);
        this.setVisible(true);
        this.setResizable(false);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}