package crowdsimulator;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import crowdsimulator.objects.Cheese;
import crowdsimulator.objects.Edge;
import crowdsimulator.objects.Mouse;
import crowdsimulator.objects.Sprites;
import crowdsimulator.objects.Start;
import crowdsimulator.objects.Wall;

/**
 *
 * @author Arnaud Flaesch, Maxime Mollard , Yohan Lamerand
 */
public class CrowdSimulator {

    public GameWindow window;
    protected int nbTours = 0;
    protected int nbMoves = 0;
    public int nbMoving = 0;
    public int nbArrived = 0;
    protected int roundTime = 500;
    public Edge edgesList[][];
    public List<Mouse> mouseList = new ArrayList();
    public List<Start> startList = new ArrayList();
    public List<Cheese> cheeseList = new ArrayList();

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        CrowdSimulator simulator = new CrowdSimulator();

        JFileChooser dialogue = new JFileChooser(new File("."));

        if (dialogue.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
            try {
                String fichier = dialogue.getSelectedFile().getCanonicalPath();
                simulator.window = new GameWindow(fichier, simulator);
                int i = 1;
                for (Start start : simulator.startList) {
                    start.index = i;
                    start.nbSouris = Integer.parseInt(JOptionPane.showInputDialog(null,
                                    "Nombre de souris pour la porte " + i, "Porte " + i, JOptionPane.QUESTION_MESSAGE));
                    i++;
                    simulator.window.bottom_center_container.add(new JLabel("Porte " + i + " :"+ start.getNbSouris()));
                }
                simulator.roundTime = Integer.parseInt(JOptionPane.showInputDialog(null,
                                "Veuillez saisir la vitesse souhaitée en millisecondes", "Vitesse du jeu",
                                JOptionPane.QUESTION_MESSAGE));
                simulator.window.bottom_right_container.add(new JLabel("VITESSE : " + simulator.roundTime));
                CrowdSimulator.start(simulator);
            }
            catch (IOException error) {
                System.out.println("Erreur " + error);
            }
        }
    }

    public static void start(CrowdSimulator simulator) {
        int nbRounds = 0;
        do {
            // On bouge les souris
            for (int i = 0; i < simulator.mouseList.size(); i++) {
                for (Cheese cheese : simulator.cheeseList) {
                        simulator.mouseList.get(i).getClosestCheese(cheese, simulator);
                }
                if (simulator.mouseList.get(i).move(simulator.mouseList.get(i).arrival.positionX, simulator.mouseList.get(i).arrival.positionY, simulator)) {
                    simulator.nbMoves++;
                    simulator.window.nbMoves.setText("" + simulator.nbMoves);
                }
            }

            // On fait apparaitre les souris
            simulator.window.bottom_center_container.removeAll();
            for (Start start : simulator.startList) {
                if (start.nbSouris > 0) {
                    for (int row = start.positionY - 1; row < start.positionY + 2 && start.nbSouris > 0; row++) {
                        for (int col = start.positionX - 1; col < start.positionX + 2 && start.nbSouris > 0; col++) {
                            if (!(simulator.edgesList[row][col].isOccupied || simulator.edgesList[row][col] instanceof Wall ||simulator.edgesList[row][col] instanceof Start)) {
                                simulator.mouseList.add(new Mouse(Sprites.mouseImage, col, row));
                                simulator.edgesList[row][col].isOccupied = true;
                                start.nbSouris -= 1;
                                simulator.nbMoving++;
                                simulator.window.mouseMoving.setText("" + simulator.nbMoving);
                            }
                        }
                    }
                }
                simulator.window.bottom_center_container.add(new JLabel("PORTE " + start.index + " : "+start.getNbSouris()));
            }
            nbRounds++;
            simulator.window.nbTours.setText("" + nbRounds);
            simulator.window.repaint();

            try {
                Thread.sleep(simulator.roundTime);
            }
            catch (InterruptedException error) {
                System.out.println(error);
            }
        } while (!(simulator.mouseList.isEmpty()));
    }
}