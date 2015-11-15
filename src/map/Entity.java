package map;

import java.awt.image.BufferedImage;

public class Entity {
    public int posX;
    public int posY;
    public BufferedImage image = null;

    public Entity(int y, int x, BufferedImage image){		
        this.posY = y;
        this.posX = x;
        this.image = image;
    }

    public int getPosX() {
        return posX;
    }


    public int getPosY() {
        return posY;
    }

}
