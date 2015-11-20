package crowdsimulator.objects;

import java.awt.image.BufferedImage;

/**
 *
 * @author Arnaud Flaesch, Maxime Mollard, Yohan Lamerand
 */
public abstract class Edge {
    
    public int positionX;
    public int positionY;
    public boolean isOccupied;
    public BufferedImage image = null;

    /**
     * Cree un objet compose d'un sprite et de sa position horizontale et verticale dans la fenetre.
     * @param image L'image associee a� l'objet.
     * @param x La position horizontale de depart de l'objet.
     * @param y La position verticale de depart de l'objet.
     */
    public Edge(BufferedImage image, int x, int y) {
        this.positionX = x;
        this.positionY = y;
        this.image = image;
    }
}