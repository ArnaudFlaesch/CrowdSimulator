package crowdsimulator.objects;

import java.awt.image.BufferedImage;

/**
 *
 * @author Arnaud
 */
public abstract class Edge {
    
    public int positionX;
    public int positionY;
    public boolean isOccupied;
    public BufferedImage image = null;

    /**
     * Crée un objet composé d'un sprite et de sa position horizontale et verticale dans la fenêtre.
     * @param image L'image associée à l'objet.
     * @param x La position horizontale de départ de l'objet.
     * @param y La position verticale de départ de l'objet.
     */
    public Edge(BufferedImage image, int x, int y) {
        this.positionX = x;
        this.positionY = y;
        this.image = image;
    }
}