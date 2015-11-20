package crowdsimulator;

import crowdsimulator.objects.Mouse;
import crowdsimulator.objects.Path;
import crowdsimulator.objects.Start;
import crowdsimulator.objects.Wall;

/**
 *
 * @author Arnaud Flaesch, Maxime Mollard, Yohan Lamerand
 */
public class Dijkstra {
    public static void calcPath(CrowdSimulator simulator) {
        for (Path edge : Mouse.edgesVisited) {
            for (int row = edge.positionY -1; row<edge.positionY + 2; row++) {
                for (int col = edge.positionX -1; col<edge.positionX + 2; col++) {        
                    if (!(simulator.edgesList[row][col] instanceof Start || simulator.edgesList[row][col] instanceof Wall)) {
                        Path father = (Path)simulator.edgesList[edge.positionY][edge.positionX];
                        Path fils = (Path)simulator.edgesList[row][col];
                        if (!(fils.hasBeenVisited) && (((father.poids +fils.cost) < fils.poids) || (fils.poids == -1))) {
                            if (fils.isOccupied) {
                                fils.cost += 0.001;
                            }
                            ((Path)simulator.edgesList[row][col]).poids = father.poids + fils.cost;
                            ((Path)simulator.edgesList[row][col]).antecedent = father;
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
            calcPath(simulator);
        }
    }
}