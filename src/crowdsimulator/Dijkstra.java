package crowdsimulator;

import crowdsimulator.objects.Mouse;
import crowdsimulator.objects.Path;
import crowdsimulator.objects.Start;
import crowdsimulator.objects.Wall;
import java.awt.Point;
import java.util.ArrayList;

/**
 *
 * @author Arnaud Flaesch, Maxime Mollard, Yohan Lamerand
 */
public class Dijkstra {
    
    public static ArrayList<Point> listCoordNeighbours = new ArrayList();
    
    public static void initNeighbours() {
        Dijkstra.listCoordNeighbours.add(new Point(-1, 0));
        Dijkstra.listCoordNeighbours.add(new Point(1, 0));
        Dijkstra.listCoordNeighbours.add(new Point(0, -1));
        Dijkstra.listCoordNeighbours.add(new Point(0, 1));
    }
    
    public static void calcPath(CrowdSimulator simulator) {
        for (Path edge : Mouse.edgesVisited) {
            for (Point coordonnee : Dijkstra.listCoordNeighbours) {        
                if (!(simulator.edgesList[edge.positionY + coordonnee.y][edge.positionX + coordonnee.x] instanceof Start || simulator.edgesList[edge.positionY + coordonnee.y][edge.positionX + coordonnee.x] instanceof Wall)) {
                    Path father = (Path)simulator.edgesList[edge.positionY][edge.positionX];
                    Path fils = (Path)simulator.edgesList[edge.positionY + coordonnee.y][edge.positionX + coordonnee.x];
                    if (!(fils.hasBeenVisited) && (((father.poids +fils.cost) < fils.poids) || (fils.poids == -1))) {
                        if (fils.isOccupied) {
                            fils.cost += 1;
                        }
                        ((Path)simulator.edgesList[edge.positionY + coordonnee.y][edge.positionX + coordonnee.x]).poids = father.poids + fils.cost;
                        ((Path)simulator.edgesList[edge.positionY + coordonnee.y][edge.positionX + coordonnee.x]).antecedent = father;
                        Mouse.edgesVisitedtmp.add(fils);                        
                    }
                }
            }
        }
        Mouse.edgesVisited.clear();
        Mouse.edgesVisited.addAll(Mouse.edgesVisitedtmp);
        Mouse.edgesVisitedtmp.clear();
        if (!Mouse.edgesVisited.isEmpty()) {
            calcPath(simulator);
        }
    }
}