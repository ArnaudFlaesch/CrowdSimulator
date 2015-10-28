package crowdsimulator.objects;

import java.awt.image.BufferedImage;

/**
 *
 * @author Arnaud
 */
public class Mouse extends Entity {

    public Mouse(BufferedImage image, int x, int y) {
        super(image, x, y);
        this.isOccupied = true;
    }
    
    /**
     * Déplace la souris.
     * @param h La direction horizontale (-1, 0 ou 1).
     * @param v La direction verticale (-1, 0 ou 1).
     */
    public void move(int h, int v) {
        positionX += 0;
        positionY += -1;
    }
}