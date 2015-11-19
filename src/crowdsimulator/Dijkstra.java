package crowdsimulator;

import crowdsimulator.objects.Mouse;
import crowdsimulator.objects.Path;
import crowdsimulator.objects.Start;
import crowdsimulator.objects.Wall;

/**
 *
 * @author Arnaud
 */
public class Dijkstra {
    public static void calcPath() {
        for (Path edge : Mouse.edgesVisited) {
            for (int row = edge.positionY -1; row<edge.positionY + 2; row++) {
                for (int col = edge.positionX -1; col<edge.positionX + 2; col++) {        
                    if (!(CrowdSimulator.edgesList[row][col] instanceof Start || CrowdSimulator.edgesList[row][col] instanceof Wall)) {
                        Path father = (Path)CrowdSimulator.edgesList[edge.positionY][edge.positionX];
                        Path fils = (Path)CrowdSimulator.edgesList[row][col];
                        if (!(fils.hasBeenVisited) && (((father.poids +fils.cost) < fils.poids) || (fils.poids == -1))) {
                            if (fils.isOccupied) {
                                fils.cost += 0.001;
                            }
                            ((Path)CrowdSimulator.edgesList[row][col]).poids = father.poids + fils.cost;
                            ((Path)CrowdSimulator.edgesList[row][col]).antecedent = father;
                            Mouse.edgesVisitedtmp.add(fils);                        
                        }
                    }
                }
            }
        }
        Mouse.edgesVisited.clear();
        Mouse.edgesVisited.addAll(Mouse.edgesVisitedtmp);
        Mouse.edgesVisitedtmp.clear();
        if (!Mouse.edgesVisited.isEmpty()) {
            calcPath();
        }
    }
}