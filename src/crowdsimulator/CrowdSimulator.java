package crowdsimulator;

import crowdsimulator.objects.Cheese;
import crowdsimulator.objects.Entity;
import crowdsimulator.objects.Grass;
import crowdsimulator.objects.Mouse;
import crowdsimulator.objects.Path;
import crowdsimulator.objects.Start;
import crowdsimulator.objects.Wall;
import crowdsimulator.window.GameWindow;
import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.image.BufferStrategy;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import javax.swing.JFileChooser;
import javax.swing.JPanel;

/**
 * 
 * @author Arnaud
 */
public class CrowdSimulator extends Canvas {
    private BufferStrategy strategy;
    private boolean gameRunning = true;
    private ArrayList<Entity> entities = new ArrayList();
    private ArrayList<Mouse> mouseList = new ArrayList();

    /**
     * Demande à l'utilisateur de choisir un fichier texte pour charger la carte, puis
     * initialise le simulateur avec.
     * @param argv 
     */
    public static void main(String argv[]) {
        
        JFileChooser dialogue = new JFileChooser(new File("."));
	
	if (dialogue.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
            try {
                String fichier = dialogue.getSelectedFile().getCanonicalPath();
                CrowdSimulator simulator = new CrowdSimulator(fichier);
                simulator.start();
            }
            catch (IOException error) {
                System.out.println("Erreur " + error);
            }
	}
    }

    /**
     * Lance l'application et affiche la fenêtre contenant la carte.
     * @param configFile Fichier contenant la carte.
     */
    public CrowdSimulator(String configFile) {
        GameWindow container = new GameWindow();
        
        JPanel panel = (JPanel) container.getContentPane();
        panel.add(this);

        setIgnoreRepaint(true);
        
        createBufferStrategy(2);
        strategy = getBufferStrategy();
        
        Dimension taille = initEntities(configFile);
        setBounds(0, 0, (int)taille.getWidth() * 26, (int)taille.getHeight() * 26);
        container.pack();
    }

    /**
     * A partir du fichier contenant la map en ASCII, crée la liste des éléments devant être
     * déssinés dans la fenêtre Java.
     * @return La résolution de la fenêtre dans laquelle sera affiché le simulateur.
     */
    private Dimension initEntities(String configFile) {
        try {
            Scanner scanner = new Scanner(new File(configFile));
            int row = 0;
            String line = " ";
            while (scanner.hasNextLine()) {
                line = scanner.nextLine();
                for (int i = 0; i<line.length(); i++) {
                    switch (line.charAt(i)) {
                        case 'A' : {
                            entities.add(new Cheese("resources/cheese.png", i, row));
                            break;
                        }
                        case 'D' : {
                            entities.add(new Start("resources/start.png", i, row));
                            break;
                        }
                        case '*' : {
                            entities.add(new Wall("resources/wall.png", i, row));
                            break;
                        }
                        case 32 : {
                            entities.add(new Path("resources/path.png", i, row));
                            break;
                        }
                        case 'G' : {
                            entities.add(new Grass("resources/grass.png", i, row));
                            break;
                        }
                        default : {
                            // Si un caractère n'est pas reconnu, on met un mur
                            entities.add(new Wall("resources/wall.png", i, row));
                            break;
                        }
                    }
                }
                row++;
            }
            return(new Dimension(line.length(), row));
        }
        catch (FileNotFoundException error) {
            System.out.println(error);
            return(null);
        }
    }

    /**
     * Fonction dans laquelle vont être exécutés les déplacements des souris et la mise à jour de leur position
     * sur la carte.
     */
    public void start() {
        long lastLoopTime = System.currentTimeMillis();
        long time = 500; // Nombre de millisecondes après lesquels il faut faire une action (à modifier)
        
        while (gameRunning) {
            time += System.currentTimeMillis() - lastLoopTime;
            lastLoopTime = System.currentTimeMillis();
            if (time >= 500) {
                Graphics2D g = (Graphics2D) strategy.getDrawGraphics();
                
                // A partir de la liste des souris (à ajouter), on les bouge une à une vers le plus proche fromage
                for (Mouse mouse : mouseList) {
                    mouse.move(1, 0);
                }

                // Affiche les sprites correspondant aux objets sur la carte
                for (Entity entity : entities) {
                    entity.draw(g);
                }

                g.dispose();
                strategy.show();
                time = 0;
            }
        }
    }
}