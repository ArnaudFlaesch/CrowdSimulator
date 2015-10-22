package crowdsimulator.objects;

/**
 *
 * @author Arnaud
 */
public class Mouse extends Entity {

    public Mouse(String imageLink, int x, int y) {
        super(imageLink, x, y);
    }
    
    /**
     * Déplace la souris.
     * @param h La direction horizontale (-1, 0 ou 1).
     * @param v La direction verticale (-1, 0 ou 1).
     */
    public void move(int h, int v) {
        positionX += positionX + -1;
        positionY += positionY + 0;
    }
}