package crowdsimulator;

import java.awt.BorderLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

/**
 *
 * @author Arnaud Flaesch, Maxime Mollard, Yohan Lamerand
 */
public class GameWindow extends JFrame{

    private static final long serialVersionUID = 1L;
    protected static String configFile;
    protected JLabel nbTours = new JLabel("0");
    protected JLabel nbMoves = new JLabel("0");
    public JLabel mouseArrived = new JLabel("0");
    public JLabel mouseMoving = new JLabel("0");
    protected JPanel main_container = new JPanel();
    protected PanelGrid center_container;
    protected JPanel bottom_container = new JPanel();
    protected JPanel bottom_left_container = new JPanel();
    protected JPanel bottom_center_container = new JPanel();
    protected JPanel bottom_right_container = new JPanel();
    private CrowdSimulator simulator;
    
    public GameWindow(String configFile, CrowdSimulator simulator) {
    	this.simulator = simulator;
        GameWindow.configFile = configFile;
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent event) {
                System.exit(0);
            }
        });
                
        this.center_container = new PanelGrid(configFile, simulator);
        if (center_container.isValidMap()) {
            center_container.setSize(center_container.size.width * 26 + 10, center_container.size.height * 26 + 10);
            center_container.initEntities(simulator);
        }
        else {
            JOptionPane.showMessageDialog(null, "Le fichier texte n'est pas valide !", "Erreur dans le fichier texte", JOptionPane.ERROR_MESSAGE);
            System.exit(0);
        }
                
        this.main_container.setLayout(new BorderLayout());
        this.bottom_container.setLayout(new BorderLayout());
        this.main_container.add(center_container, BorderLayout.CENTER);
        this.main_container.add(bottom_container, BorderLayout.SOUTH);
        this.bottom_container.add(bottom_left_container, BorderLayout.WEST);
        this.bottom_container.add(bottom_center_container, BorderLayout.CENTER);
        this.bottom_container.add(bottom_right_container, BorderLayout.EAST);
        
        this.bottom_left_container.add(new JLabel("TOURS"));
        this.bottom_left_container.add(nbTours);
        this.bottom_left_container.add(new JLabel("DEPLACEMENTS"));
        this.bottom_left_container.add(nbMoves);
        this.bottom_left_container.add(new JLabel("SOURIS EN DEPLACEMENT"));
        this.bottom_left_container.add(mouseMoving);
        this.bottom_left_container.add(new JLabel("SOURIS ARRIVEES"));
        this.bottom_left_container.add(mouseArrived);
        
        this.setTitle("Simulateur de foule");
        this.setContentPane(main_container);
        this.setSize(center_container.getWidth(), center_container.getHeight() + 50);
        this.setVisible(true);
        this.setResizable(false);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public CrowdSimulator getSimulator() {
        return simulator;
    }

    public void setSimulator(CrowdSimulator simulator) {
        this.simulator = simulator;
    }
}