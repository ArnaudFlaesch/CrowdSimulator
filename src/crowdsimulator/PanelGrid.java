package crowdsimulator;

import java.awt.Dimension;
import java.awt.Graphics;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import javax.swing.JOptionPane;
import javax.swing.JPanel;

import crowdsimulator.objects.Cheese;
import crowdsimulator.objects.Edge;
import crowdsimulator.objects.Grass;
import crowdsimulator.objects.Mouse;
import crowdsimulator.objects.Sand;
import crowdsimulator.objects.Sprites;
import crowdsimulator.objects.Start;
import crowdsimulator.objects.Wall;

/**
 *
 * @author Arnaud Flaesch, Maxime Mollard, Yohan Lamerand
 */
public class PanelGrid extends JPanel {

    private static final long serialVersionUID = 1L;
    protected String configFile = "";
    protected Dimension size = null;
    private CrowdSimulator simulator;

    public PanelGrid(String configFile , CrowdSimulator simulator) {
        this.configFile = configFile;
        this.size = getMapSize();
        this.simulator = simulator;
        simulator.edgesList = new Edge[this.size.height][this.size.width];
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
                scanner.close();
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
                line = scanner.nextLine(); // Premiere ligne, on verifie qu'il y a des murs partout
                for (int i = 0; i < line.length(); i++) {
                    if (line.charAt(i) != '*') {
                        scanner.close();
                        return (false);
                    }
                }

                while (scanner.hasNextLine()) {
                    line = scanner.nextLine();
                    if (!scanner.hasNextLine()) { // C'est la derniere ligne, on verifie aussi qu'il y a des murs partout
                        for (int i = 0; i < line.length(); i++) {
                            if (line.charAt(i) != '*') {
                                scanner.close();
                                return (false);
                            }
                        }
                    }
                    else {
                        if (line.charAt(0) != '*' || line.charAt(this.size.width - 1) != '*') {
                            scanner.close();
                            return (false);
                        }
                    }
                }
                scanner.close();
                return (true);
		}
            catch (FileNotFoundException error) {
                System.out.println(error);
                return (false);
            }
	}

	public void initEntities(CrowdSimulator simulator) {
            Sprites.initSprites();
            try {
            Scanner scanner = new Scanner(new File(configFile));
            int row = 0;
            String line;
            while (scanner.hasNextLine()) {
                line = scanner.nextLine();
                for (int col = 0; col < line.length(); col++) {
                    switch (line.charAt(col)) {
                        case 'A': {
                            simulator.edgesList[row][col] = new Cheese(Sprites.cheeseImage, col, row);
                            simulator.cheeseList.add(new Cheese(Sprites.cheeseImage, col, row));
                            break;
                        }
                        case 'D': {
                            simulator.edgesList[row][col] = new Start(Sprites.startImage, col, row);
                            simulator.startList.add(new Start(Sprites.startImage, col, row));
                            break;
                        }
                        case '*': {
                            simulator.edgesList[row][col] = new Wall(Sprites.wallImage, col, row);
                            break;
                        }
                        case 32: {
                            simulator.edgesList[row][col] = new Sand(Sprites.pathImage, col, row);
                            break;
                        }
                        case 'G': {
                            simulator.edgesList[row][col] = new Grass(Sprites.grassImage, col, row);
                            break;
                        }
                        default: {
                            // Si un caractere n'est pas reconnu, on informe de l'erreur puis on quite
                            JOptionPane.showMessageDialog(this, "Attention\n La carte chargée n'est pas correcte !", "Erreur", JOptionPane.ERROR_MESSAGE);
                            System.exit(0);
                            break;
                        }
                    }
                }
                row++;
            }
            scanner.close();
        }
        catch (FileNotFoundException error) {
            System.out.println(error);
        }
    }

    @Override
    public void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);
        for (int i = 0; i < simulator.edgesList.length; i++) {
            for (int j = 0; j < simulator.edgesList[i].length; j++) {
                Edge entity = simulator.edgesList[i][j];
                graphics.drawImage(entity.image, entity.positionX * 26, entity.positionY * 26, 26, 26, this);
            }
        }

        for (Mouse mouse : simulator.mouseList) {
            graphics.drawImage(mouse.image, mouse.positionX * 26, mouse.positionY * 26, 26, 26, this);
        }
    }
}