package crowdsimulator.objects;

import java.awt.image.BufferedImage;

/**
 *
 * @author Arnaud
 */
public class Path extends Entity {

    public Path(BufferedImage image, int x, int y) {
        super(image, x, y);
        this.isOccupied = false;
    }
}