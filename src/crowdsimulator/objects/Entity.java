package crowdsimulator.objects;

import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;

/**
 *
 * @author Arnaud
 */
public abstract class Entity {
    
    public int positionX;
    public int positionY;
    public boolean isOccupied;
    public BufferedImage image = null;

    /**
     * Crée un objet composé d'un sprite et de sa position horizontale et verticale dans la fenêtre.
     * @param imageLink Le lien de l'image associée à l'objet.
     * @param x La position horizontale de départ de l'objet.
     * @param y La position verticale de départ de l'objet.
     */
    public Entity(BufferedImage image, int x, int y) {
        this.positionX = x;
        this.positionY = y;
        this.image = image;
    }
}