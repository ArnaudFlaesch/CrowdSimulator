package crowdsimulator;

import crowdsimulator.objects.Cheese;
import crowdsimulator.objects.Edge;
import crowdsimulator.objects.Grass;
import crowdsimulator.objects.Mouse;
import crowdsimulator.objects.Sand;
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
        CrowdSimulator.edgesList = new Edge[this.size.height][this.size.width];
    }
    
    public Dimension getMapSize() {
        try {
            Scanner scanner = new Scanner(new File(configFile));
            int rows = 0, cols;
            String line;
            line = scanner.nextLine();
            rows++;
            cols = line.length();
            while (scanner.hasNextLine()) {
                scanner.nextLine();
                rows++;
            }
            return (new Dimension(cols, rows));
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
                    if (line.charAt(0) != '*' || line.charAt(this.size.width - 1) != '*') {
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
                for (int col = 0; col<line.length(); col++) {
                    switch (line.charAt(col)) {
                        case 'A' : {
                            CrowdSimulator.edgesList[row][col] = new Cheese(Sprites.cheeseImage, col, row);
                            CrowdSimulator.cheeseList.add(new Cheese(Sprites.cheeseImage, col, row));
                            break;
                        }
                        case 'D' : {
                            CrowdSimulator.edgesList[row][col] = new Start(Sprites.startImage, col, row);
                            CrowdSimulator.startList.add(new Start(Sprites.startImage, col, row));
                            break;
                        }
                        case '*' : {
                            CrowdSimulator.edgesList[row][col] = new Wall(Sprites.wallImage, col, row);
                            break;
                        }
                        case 32 : {
                            CrowdSimulator.edgesList[row][col] = new Sand(Sprites.pathImage, col, row);
                            break;
                        }
                        case 'G' : {
                            CrowdSimulator.edgesList[row][col] = new Grass(Sprites.grassImage, col, row);
                            break;
                        }
                        default : {
                            // Si un caractère n'est pas reconnu, on met un mur
                            CrowdSimulator.edgesList[row][col] = new Wall(Sprites.wallImage, col, row);
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
        for (int i = 0; i<CrowdSimulator.edgesList.length; i++) {
            for (int j = 0; j<CrowdSimulator.edgesList[i].length; j++) {
                Edge entity = CrowdSimulator.edgesList[i][j];
                g.drawImage(entity.image, entity.positionX*26, entity.positionY*26, 26, 26, this);
            }
        }

        for(Mouse mouse : CrowdSimulator.mouseList) {
            g.drawImage(mouse.image, mouse.positionX*26, mouse.positionY*26, 26, 26, this);
        }
    }
}
