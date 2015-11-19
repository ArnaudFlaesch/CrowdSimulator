package crowdsimulator.objects;

import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;

/**
 *
 * @author Arnaud
 */
public class Sprites {
    public static BufferedImage pathImage = null;
    public static BufferedImage mouseImage = null;
    public static BufferedImage grassImage = null;
    public static BufferedImage cheeseImage = null;
    public static BufferedImage startImage = null;
    public static BufferedImage wallImage = null;
    public static BufferedImage red = null;
    
    public static void initSprites() {
        try {
            Sprites.pathImage =  ImageIO.read(Sprites.class.getClassLoader().getResource("resources/path.png"));
            Sprites.mouseImage =  ImageIO.read(Sprites.class.getClassLoader().getResource("resources/mouse.png"));
            Sprites.grassImage =  ImageIO.read(Sprites.class.getClassLoader().getResource("resources/grass.png"));
            Sprites.cheeseImage =  ImageIO.read(Sprites.class.getClassLoader().getResource("resources/cheese.png"));
            Sprites.startImage =  ImageIO.read(Sprites.class.getClassLoader().getResource("resources/start.png"));
            Sprites.wallImage =  ImageIO.read(Sprites.class.getClassLoader().getResource("resources/wall.png"));
        }
        catch (IOException error) {
            System.out.println(error);
        }
    }    
}