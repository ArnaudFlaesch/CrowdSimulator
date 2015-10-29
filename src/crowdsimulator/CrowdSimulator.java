package crowdsimulator;

import crowdsimulator.objects.Entity;
import crowdsimulator.objects.Mouse;
import crowdsimulator.objects.Sprites;
import crowdsimulator.objects.Start;
import java.util.ArrayList;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

/**
 *
 * @author Arnaud
 */
public class CrowdSimulator {
    
    protected static GameWindow window;
    protected static int nbTours = 0;
    protected static int nbMoves = 0;
    protected static int nbMoving = 0;
    protected static int roundTime = 500;
    protected static ArrayList<Entity> entities = new ArrayList();
    protected static ArrayList<Mouse> mouseList = new ArrayList();
    protected static ArrayList<Start> startList = new ArrayList();

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        window = new GameWindow("src/resources/map.txt");
        int i = 1;
        for (Start start : startList) {
            start.index = i;
            start.setNbSouris(Integer.parseInt(JOptionPane.showInputDialog(null, "Nombre de souris pour la porte "+i, "Porte "+i, JOptionPane.QUESTION_MESSAGE)));
            i++;
            CrowdSimulator.window.bottom_center_container.add(new JLabel("Porte " +i+ " :"+start.getNbSouris()));
        }
        CrowdSimulator.roundTime = Integer.parseInt(JOptionPane.showInputDialog(null, "Veuillez saisir la vitesse souhaitée en millisecondes", "Vitesse du jeu", JOptionPane.QUESTION_MESSAGE));
        CrowdSimulator.window.bottom_right_container.add(new JLabel("VITESSE : "+CrowdSimulator.roundTime));
        CrowdSimulator.start();
    }
    
    public static void start() {
        int nbRounds = 0;
        while (nbRounds < 15) { //En fait, cela doit être : tant qu'il reste des souris à spawn et que celles en mouvement n'ont pas encore atteint un fromage
            
            // On commence par faire apparaître les souris sur la carte
            CrowdSimulator.window.bottom_center_container.removeAll();
            for (Start start : startList) {
                if (start.getNbSouris() > 0) {
                    mouseList.add(new Mouse(Sprites.mouseImage, start.positionX - 1, start.positionY - 1));
                    start.setNbSouris(start.getNbSouris() - 1);
                    nbMoving++;
                    CrowdSimulator.window.mouseMoving.setText("" +nbMoving);
                }
                CrowdSimulator.window.bottom_center_container.add(new JLabel("PORTE " +start.index+ " : "+start.getNbSouris()));
            }

            // Ensuite on les fait se déplacer
            for (Mouse mouse : mouseList) {
                if (mouse.move(2, 2)) { // variables de test, elles ne changent rien au programme pour l'instant
                    nbMoves++;
                    CrowdSimulator.window.nbMoves.setText("" +nbMoves);
                }
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
        }
    }
}