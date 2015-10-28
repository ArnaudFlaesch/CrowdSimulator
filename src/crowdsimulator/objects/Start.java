package crowdsimulator.objects;

import java.awt.image.BufferedImage;

/**
 *
 * @author Arnaud
 */
public class Start extends Entity {

    private int nbSouris = 0;
    
    public Start(BufferedImage image, int x, int y) {
        super(image, x, y);
        this.isOccupied = true;
    }

    public int getNbSouris() {
        return nbSouris;
    }

    public void setNbSouris(int nbSouris) {
        this.nbSouris = nbSouris;
    }
}