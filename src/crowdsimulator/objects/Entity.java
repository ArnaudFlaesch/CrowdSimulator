package crowdsimulator.objects;

import crowdsimulator.window.Sprite;
import java.awt.Graphics;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsEnvironment;
import java.awt.Image;
import java.awt.Transparency;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;

/**
 *
 * @author Arnaud
 */
public abstract class Entity {
    
    protected int positionX;
    protected int positionY;
    protected Sprite sprite;

    /**
     * Crée un objet composé d'un sprite et de sa position horizontale et verticale dans la fenêtre.
     * @param imageLink Le lien de l'image associée à l'objet.
     * @param x La position horizontale de départ de l'objet.
     * @param y La position verticale de départ de l'objet.
     */
    public Entity(String imageLink, int x, int y) {
        BufferedImage sourceImage = null;
        try {
            sourceImage = ImageIO.read(this.getClass().getClassLoader().getResource(imageLink));
        }
        catch (IOException error) {
            System.out.println("Impossible d'accèder à l'image : "+imageLink+ " "+error);
        }
        GraphicsConfiguration gc = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDefaultConfiguration();
        Image image = gc.createCompatibleImage(sourceImage.getWidth(), sourceImage.getHeight(), Transparency.BITMASK);
        image.getGraphics().drawImage(sourceImage, 0, 0, null);
        this.sprite = new Sprite(image);
        this.positionX = x * 26;
        this.positionY = y * 26;
    }

    /**
     * Dessine le sprite dans la fenêtre.
     * @param g
     */
    public void draw(Graphics g) {
        sprite.draw(g, positionX, positionY);
    }
}