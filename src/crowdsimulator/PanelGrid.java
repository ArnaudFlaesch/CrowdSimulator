package crowdsimulator;

import crowdsimulator.objects.Cheese;
import crowdsimulator.objects.Entity;
import crowdsimulator.objects.Grass;
import crowdsimulator.objects.Mouse;
import crowdsimulator.objects.Path;
import crowdsimulator.objects.Start;
import crowdsimulator.objects.Wall;
import crowdsimulator.objects.Sprites;
import java.awt.Dimension;
import java.awt.Graphics;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import javax.swing.JPanel;

/**
 *
 * @author Arnaud
 */
public class PanelGrid extends JPanel {
    
    protected String configFile = "";
    protected Dimension size = null;
    
    public PanelGrid(String configFile) {
        this.configFile = configFile;
        this.size = getMapSize();
    }
    
    public Dimension getMapSize() {
        try {
            Scanner scanner = new Scanner(new File(configFile));
            int rows = 0, cols = 0;
            String line;
            line = scanner.nextLine();
            rows++;
            cols = line.length();
            while (scanner.hasNextLine()) {
                scanner.nextLine();
                rows++;
            }
            return (new Dimension(rows, cols));
        }
        catch (FileNotFoundException error) {
            System.out.println(error);
            return (null);
        }
    }
    
    public boolean isValidMap() {
        try {
            Scanner scanner = new Scanner(new File(configFile));
            String line;
            line = scanner.nextLine(); // Première ligne, on vérifie qu'il y a des murs partout
            for (int i = 0; i<line.length(); i++) {
                if (line.charAt(i) != '*') {
                    return (false);
                }
            }
            
            while (scanner.hasNextLine()) {
                line = scanner.nextLine();
                if (!scanner.hasNextLine()) { // C'est la dernière ligne, on vérifie aussi qu'il y a des murs partout
                    for (int i = 0; i<line.length(); i++) {
                        if (line.charAt(i) != '*') {
                            return (false);
                        }
                    }  
                }
                else {
                    if (line.charAt(0) != '*' || line.charAt(this.size.height - 1) != '*') {
                        return (false); 
                    }
                }
            }
            return (true);
        }
        catch (FileNotFoundException error) {
            System.out.println(error);
            return (false);
        }
    }
    
    public void initEntities() {
        Sprites.initSprites();
        try {
            Scanner scanner = new Scanner(new File(configFile));
            int row = 0;
            String line;
            while (scanner.hasNextLine()) {
                line = scanner.nextLine();
                for (int i = 0; i<line.length(); i++) {
                    switch (line.charAt(i)) {
                        case 'A' : {
                            CrowdSimulator.entities.add(new Cheese(Sprites.cheeseImage, i, row));
                            break;
                        }
                        case 'D' : {
                            CrowdSimulator.entities.add(new Start(Sprites.startImage, i, row));
                            CrowdSimulator.startList.add(new Start(Sprites.startImage, i, row));
                            break;
                        }
                        case '*' : {
                            CrowdSimulator.entities.add(new Wall(Sprites.wallImage, i, row));
                            break;
                        }
                        case 32 : {
                            CrowdSimulator.entities.add(new Path(Sprites.pathImage, i, row));
                            break;
                        }
                        case 'G' : {
                            CrowdSimulator.entities.add(new Grass(Sprites.grassImage, i, row));
                            break;
                        }
                        default : {
                            // Si un caractère n'est pas reconnu, on met un mur
                            CrowdSimulator.entities.add(new Wall(Sprites.wallImage, i, row));
                            break;
                        }
                    }
                }
                row++;
            }
        }
        catch (FileNotFoundException error) {
            System.out.println(error);
        }
    }
    
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        for(Entity entity : CrowdSimulator.entities) {
            g.drawImage(entity.image, entity.positionX*26, entity.positionY*26, 26, 26, this);
        }
        for(Mouse mouse : CrowdSimulator.mouseList) {
            g.drawImage(mouse.image, mouse.positionX*26, mouse.positionY*26, 26, 26, this);
        }
    }
}
