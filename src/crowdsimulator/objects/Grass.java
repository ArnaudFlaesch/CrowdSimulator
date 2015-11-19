package crowdsimulator.objects;

import java.awt.image.BufferedImage;

/**
 *
 * @author Arnaud
 */
public class Grass extends Path {
    
    public Grass(BufferedImage image, int x, int y) {
        super(image, x, y);
        this.cost = 2;
    }
}