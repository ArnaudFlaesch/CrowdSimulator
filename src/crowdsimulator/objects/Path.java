package crowdsimulator.objects;

import java.awt.image.BufferedImage;

/**
 *
 * @author Arnaud
 */
public abstract class Path extends Edge {
    
    protected int cost;
    protected int poids;
    public boolean hasBeenVisited;
    public Path antecedent = null;
    
    public Path(BufferedImage image, int x, int y) {
        super(image, x, y);
        this.isOccupied = false;
        this.hasBeenVisited = false;
        this.poids = -1;
    }
}