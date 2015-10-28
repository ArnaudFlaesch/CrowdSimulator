package crowdsimulator.objects;

import java.awt.image.BufferedImage;

/**
 *
 * @author Arnaud
 */
public class Wall extends Entity {

    public Wall(BufferedImage image, int x, int y) {
        super(image, x, y);
        this.isOccupied = true;
    }
}