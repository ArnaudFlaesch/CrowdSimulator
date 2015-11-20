package crowdsimulator.objects;

import java.awt.image.BufferedImage;

/**
 *
 * @author Arnaud Flaesch, Maxime Mollard, Yohan Lamerand
 */
public class Wall extends Edge {

    public Wall(BufferedImage image, int x, int y) {
        super(image, x, y);
        this.isOccupied = true;
    }
}