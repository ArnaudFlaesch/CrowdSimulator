package crowdsimulator.objects;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import crowdsimulator.CrowdSimulator;
import crowdsimulator.Dijkstra;

/**
 *
 * @author Arnaud Flaesch, Maxime Mollard, Yohan Lamerand
 */
public class Mouse extends Edge {
    
    public static List<Path> edgesVisitedtmp = new ArrayList();
    public static List<Path> edgesVisited = new ArrayList();
    public Path arrival = null;
    public boolean cannotMove = false;

    public Mouse(BufferedImage image, int x, int y) {
        super(image, x, y);
        this.isOccupied = true;
    }
    
    /**
     * Deplace la souris.
     * @param destinationX
     * @param destinationY
     * @param simulator
     * @return True si la souris a bouge (on incremente le nombre de deplacements), False sinon.
     */
    public boolean move(int destinationX, int destinationY, CrowdSimulator simulator) {
        if (this.cannotMove) {
            this.cannotMove = false;
            return (false);
        }
        ((Path)simulator.edgesList[this.positionY][this.positionX]).poids = 0;
        ((Path)simulator.edgesList[this.positionY][this.positionX]).hasBeenVisited = true;
        Mouse.edgesVisited.add((Path)simulator.edgesList[this.positionY][this.positionX]);
        Dijkstra.calcPath(simulator);
        Path edge = ((Path)simulator.edgesList[destinationY][destinationX]);
        try {
            while ((edge.antecedent.positionX != this.positionX || edge.antecedent.positionY != this.positionY) && edge.antecedent != null) {
                edge = edge.antecedent;
            }
            if (edge.isOccupied) {
                return (false);
            }
        }
        
        catch (NullPointerException error) {
            // Lorsqu'il n'y a aucun antecedent, c'est que la souris est bloque (elle doit attendre son tour)
            // Il faut prevoir dans l'algo que les chemins occupés rajoutent +1 au coût, et que si malgré cela la souris doit
            // emprunter un chemin embouteille, elle doit passer son tour et la fonction move() renvoie false
            return (false);
        }
        
        simulator.edgesList[this.positionY][this.positionX].isOccupied = false;
        this.positionX = edge.positionX;
        this.positionY = edge.positionY;
        if (simulator.edgesList[this.positionY][this.positionX] instanceof Grass) {
            this.cannotMove = true;
        }
        simulator.edgesList[this.positionY][this.positionX].isOccupied = true;
        if (this.positionX == destinationX && this.positionY == destinationY) {
            simulator.mouseList.remove(this);
            simulator.edgesList[destinationY][destinationX].isOccupied = false;
            simulator.nbMoving -= 1;
            simulator.nbArrived += 1;
            simulator.window.mouseMoving.setText("" +simulator.nbMoving);
            simulator.window.mouseArrived.setText("" +simulator.nbArrived);
        }
        
        ((Path)simulator.edgesList[this.positionY][this.positionX]).hasBeenVisited = true;
        for (int row = 0; row<simulator.edgesList.length; row++) {
            for (int col = 0; col<simulator.edgesList[row].length; col++) {
                if (!(simulator.edgesList[row][col] instanceof Wall || simulator.edgesList[row][col] instanceof Start)) {
                    ((Path)simulator.edgesList[row][col]).antecedent = null;
                    ((Path)simulator.edgesList[row][col]).poids = -1;
                    ((Path)simulator.edgesList[row][col]).hasBeenVisited = false;
                }
            }
        }
        return (true);
    }
    
    public void getClosestCheese(Cheese cheese, CrowdSimulator simulator) {
        ((Path)simulator.edgesList[this.positionY][this.positionX]).poids = 0;
        ((Path)simulator.edgesList[this.positionY][this.positionX]).hasBeenVisited = true;
        Mouse.edgesVisited.add((Path)simulator.edgesList[this.positionY][this.positionX]);
        Dijkstra.calcPath(simulator);
        if (this.arrival == null) {
            this.arrival = (Path)simulator.edgesList[cheese.positionY][cheese.positionX];
        }
        if (((Path)simulator.edgesList[cheese.positionY][cheese.positionX]).poids < this.arrival.poids) {
            this.arrival = (Path)simulator.edgesList[cheese.positionY][cheese.positionX];
        }
        
        for (int row = 0; row<simulator.edgesList.length; row++) {
            for (int col = 0; col<simulator.edgesList[row].length; col++) {
                if (!(simulator.edgesList[row][col] instanceof Wall || simulator.edgesList[row][col] instanceof Start)) {
                    ((Path)simulator.edgesList[row][col]).antecedent = null;
                    ((Path)simulator.edgesList[row][col]).poids = -1;
                    ((Path)simulator.edgesList[row][col]).hasBeenVisited = false;
                }
            }
        }
    }
}