package crowdsimulator.objects;

import crowdsimulator.CrowdSimulator;
import crowdsimulator.Dijkstra;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

/**
 *
 * @author Arnaud
 */
public class Mouse extends Edge {
    
    public static ArrayList<Path> edgesVisitedtmp = new ArrayList();
    public static ArrayList<Path> edgesVisited = new ArrayList();
    public Path arrival = null;
    public boolean cannotMove = false;

    public Mouse(BufferedImage image, int x, int y) {
        super(image, x, y);
        this.isOccupied = true;
    }
    
    /**
     * Déplace la souris.
     * @param destinationX
     * @param destinationY
     * @return True si la souris a bougé (on incrémente le nombre de déplacements), False sinon.
     */
    public boolean move(int destinationX, int destinationY) {
        if (this.cannotMove) {
            this.cannotMove = false;
            return (false);
        }
        ((Path)CrowdSimulator.edgesList[this.positionY][this.positionX]).poids = 0;
        ((Path)CrowdSimulator.edgesList[this.positionY][this.positionX]).hasBeenVisited = true;
        Mouse.edgesVisited.add((Path)CrowdSimulator.edgesList[this.positionY][this.positionX]);
        Dijkstra.calcPath();
        Path edge = ((Path)CrowdSimulator.edgesList[destinationY][destinationX]);
        try {
            while ((edge.antecedent.positionX != this.positionX || edge.antecedent.positionY != this.positionY) && edge.antecedent != null) {
                edge = edge.antecedent;
            }
            if (edge.isOccupied) {
                return (false);
            }
        }
        
        catch (NullPointerException error) {
            // Lorsqu'il n'y a aucun antécédent, c'est que la souris est bloquée (elle doit attendre son tour)
            // Il faut prévoir dans l'algo que les chemins occupés rajoutent +1 au coût, et que si malgré cela la souris doit
            // emprunter un chemin embouteillé, elle doit passer son tour et la fonction move() renvoie false
            return (false);
        }
        
        CrowdSimulator.edgesList[this.positionY][this.positionX].isOccupied = false;
        this.positionX = edge.positionX;
        this.positionY = edge.positionY;
        if (CrowdSimulator.edgesList[this.positionY][this.positionX] instanceof Grass) {
            this.cannotMove = true;
        }
        CrowdSimulator.edgesList[this.positionY][this.positionX].isOccupied = true;
        if (this.positionX == destinationX && this.positionY == destinationY) {
            CrowdSimulator.mouseList.remove(this);
            CrowdSimulator.edgesList[destinationY][destinationX].isOccupied = false;
            CrowdSimulator.nbMoving -= 1;
            CrowdSimulator.nbArrived += 1;
            CrowdSimulator.window.mouseMoving.setText("" +CrowdSimulator.nbMoving);
            CrowdSimulator.window.mouseArrived.setText("" +CrowdSimulator.nbArrived);
        }
        
        ((Path)CrowdSimulator.edgesList[this.positionY][this.positionX]).hasBeenVisited = true;
        for (int row = 0; row<CrowdSimulator.edgesList.length; row++) {
            for (int col = 0; col<CrowdSimulator.edgesList[row].length; col++) {
                if (!(CrowdSimulator.edgesList[row][col] instanceof Wall || CrowdSimulator.edgesList[row][col] instanceof Start)) {
                    ((Path)CrowdSimulator.edgesList[row][col]).antecedent = null;
                    ((Path)CrowdSimulator.edgesList[row][col]).poids = -1;
                    ((Path)CrowdSimulator.edgesList[row][col]).hasBeenVisited = false;
                }
            }
        }
        return (true);
    }
    
    public void getClosestCheese(Cheese cheese) {
        ((Path)CrowdSimulator.edgesList[this.positionY][this.positionX]).poids = 0;
        ((Path)CrowdSimulator.edgesList[this.positionY][this.positionX]).hasBeenVisited = true;
        Mouse.edgesVisited.add((Path)CrowdSimulator.edgesList[this.positionY][this.positionX]);
        Dijkstra.calcPath();
        if (this.arrival == null) {
            this.arrival = (Path)CrowdSimulator.edgesList[cheese.positionY][cheese.positionX];
        }
        if (((Path)CrowdSimulator.edgesList[cheese.positionY][cheese.positionX]).poids < this.arrival.poids) {
            this.arrival = (Path)CrowdSimulator.edgesList[cheese.positionY][cheese.positionX];
        }
        
        for (int row = 0; row<CrowdSimulator.edgesList.length; row++) {
            for (int col = 0; col<CrowdSimulator.edgesList[row].length; col++) {
                if (!(CrowdSimulator.edgesList[row][col] instanceof Wall || CrowdSimulator.edgesList[row][col] instanceof Start)) {
                    ((Path)CrowdSimulator.edgesList[row][col]).antecedent = null;
                    ((Path)CrowdSimulator.edgesList[row][col]).poids = -1;
                    ((Path)CrowdSimulator.edgesList[row][col]).hasBeenVisited = false;
                }
            }
        }
    }
}