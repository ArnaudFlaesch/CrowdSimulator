package crowdsimulator.window;

import java.awt.Graphics;
import java.awt.Image;

/**
 *
 * @author Arnaud
 */

public class Sprite {

    private Image image;

    /**
     * Crée un sprite à partir d'une image.
     * @param image L'image associée au sprite.
     */
    public Sprite(Image image) {
        this.image = image;
    }

    /**
     * Dessine le sprite dans la fenêtre.
     * @param g 
     * @param x La position horizontale du sprite
     * @param y La position verticale du sprite
     */
    public void draw(Graphics g, int x, int y) {
        g.drawImage(image, x, y, null);
    }
}