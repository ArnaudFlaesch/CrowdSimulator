package crowdsimulator.objects;

import java.awt.image.BufferedImage;

/**
 *
 * @author Arnaud Flaesch, Maxime Mollard, Yohan Lamerand
 */
public abstract class Path extends Edge {
    
    public int cost;
    public int poids;
    public boolean hasBeenVisited;
    public Path antecedent = null;
    
    public Path(BufferedImage image, int x, int y) {
        super(image, x, y);
        this.isOccupied = false;
        this.hasBeenVisited = false;
        this.poids = -1;
    }
}