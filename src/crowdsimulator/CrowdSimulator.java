package crowdsimulator;

import crowdsimulator.objects.Cheese;
import crowdsimulator.objects.Edge;
import crowdsimulator.objects.Mouse;
import crowdsimulator.objects.Sprites;
import crowdsimulator.objects.Start;
import crowdsimulator.objects.Wall;
import java.util.ArrayList;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

/**
 *
 * @author Arnaud
 */
public class CrowdSimulator {
    
    public static GameWindow window;
    protected static int nbTours = 0;
    protected static int nbMoves = 0;
    public static int nbMoving = 0;
    public static int nbArrived = 0;
    protected static int roundTime = 500;
    public static Edge edgesList[][];
    public static ArrayList<Mouse> mouseList = new ArrayList();
    public static ArrayList<Start> startList = new ArrayList();
    public static ArrayList<Cheese> cheeseList = new ArrayList();

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        window = new GameWindow("src/resources/map.txt");
        int i = 1;
        for (Start start : startList) {
            start.index = i;
            start.nbSouris = Integer.parseInt(JOptionPane.showInputDialog(null, "Nombre de souris pour la porte "+i, "Porte "+i, JOptionPane.QUESTION_MESSAGE));
            i++;
            CrowdSimulator.window.bottom_center_container.add(new JLabel("Porte " +i+ " :"+start.getNbSouris()));
        }
        CrowdSimulator.roundTime = Integer.parseInt(JOptionPane.showInputDialog(null, "Veuillez saisir la vitesse souhaitée en millisecondes", "Vitesse du jeu", JOptionPane.QUESTION_MESSAGE));
        CrowdSimulator.window.bottom_right_container.add(new JLabel("VITESSE : "+CrowdSimulator.roundTime));
        CrowdSimulator.start();
    }
    
    public static void start() {
        int nbRounds = 0;
        do {            
            // On bouge les souris
            for (int i = 0; i<mouseList.size(); i++) {
                Cheese cheese = CrowdSimulator.cheeseList.get(1);
                if (mouseList.get(i).move(cheese.positionX, cheese.positionY)) {
                    nbMoves++;
                    CrowdSimulator.window.nbMoves.setText("" +nbMoves);
                }
            }
            
            // On fait apparaître les souris
            CrowdSimulator.window.bottom_center_container.removeAll();
            for (Start start : startList) {
                if (start.nbSouris > 0) {
                    for (int row = start.positionY-1; row<start.positionY + 2 && start.nbSouris > 0; row++) {
                        for (int col = start.positionX-1; col<start.positionX + 2 && start.nbSouris > 0; col++) {      
                            if (!(CrowdSimulator.edgesList[row][col].isOccupied || CrowdSimulator.edgesList[row][col] instanceof Wall)) {
                                mouseList.add(new Mouse(Sprites.mouseImage, col, row));
                                edgesList[row][col].isOccupied = true;
                                start.nbSouris -= 1;
                                nbMoving++;
                                CrowdSimulator.window.mouseMoving.setText("" +nbMoving);
                            }
                        }
                    }
                }
                CrowdSimulator.window.bottom_center_container.add(new JLabel("PORTE " +start.index+ " : "+start.getNbSouris()));
            }
            nbRounds++;
            CrowdSimulator.window.nbTours.setText(""+nbRounds);
            CrowdSimulator.window.repaint();
 
            try {
                Thread.sleep(CrowdSimulator.roundTime);
            }
            catch (InterruptedException error) {
                System.out.println(error);
            }
        } while (!(mouseList.isEmpty()));
    }
}