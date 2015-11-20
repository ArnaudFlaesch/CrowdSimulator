package crowdsimulator.objects;

import java.awt.image.BufferedImage;

/**
 *
 * @author Arnaud Flaesch, Maxime Mollard, Yohan Lamerand
 */
public class Cheese extends Path {

    public Cheese(BufferedImage image, int x, int y) {
        super(image, x, y);
        this.cost = 1;
    }
}