package map;

import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;
import javax.swing.Timer;

import main.TestAStar;
import aStar.Node;
import java.io.File;
import java.util.Scanner;

public class Game extends JPanel {
    private Map map;
    List<Node> listMousePath = new ArrayList<Node>();
    int m = 0;

    public Game() {
        this.setSize(1268, 520);

        new Timer(500, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                    moveNext(getGraphics());
            }
        }).start();
    }

    public void mapGenerator(String path) {
        List<Entity> listCol = new ArrayList();
        List<List<Entity>> mapEnt = new ArrayList();

        try {
            Scanner scanner = new Scanner(new File(path));
            String strLine;
            map = new Map(null);
            int i = 0;
            int j = 0;

            while (scanner.hasNextLine()) {
                strLine = scanner.nextLine();
                for (i = 0; i < strLine.length(); i++) {
                    switch (strLine.charAt(i)) {
                        case '*':
                            listCol.add(new Entity(j, i, Sprites.wallImage));
                            TestAStar.obstacleMap[i][j] = 1;
                            break;

                        case 'A':
                            listCol.add(new Entity(j, i, Sprites.cheeseImage));
                            TestAStar.obstacleMap[i][j] = 0;
                            break;

                        case 'G':
                            listCol.add(new Entity(j, i, Sprites.grassImage));
                            TestAStar.obstacleMap[i][j] = 0;
                            break;

                        case 'D':
                            listCol.add(new Entity(j, i, Sprites.startImage));
                            TestAStar.obstacleMap[i][j] = 0;
                            break;

                        case ' ':
                            listCol.add(new Entity(j, i, Sprites.pathImage));
                            TestAStar.obstacleMap[i][j] = 0;
                            break;

                        default: //Erreur, si le symbole n'est pas reconnu il faut envoyer un message d'erreur ou mettre un mur
                            listCol.add(new Entity(j, i, Sprites.pathImage));
                            TestAStar.obstacleMap[i][j] = 0;
                            break;
                            }
                        }
                    mapEnt.add(listCol);
                    listCol = new ArrayList<Entity>();
                    j++;
                }
                map = new Map(mapEnt);
            }
            catch (FileNotFoundException e) {
                e.printStackTrace();
            }
	}

        @Override
	public void paintComponent(Graphics g) {
            super.paintComponent(g);

            for (int l = 0; l < this.map.getMap().size(); l++) {
                for (int c = 0; c < this.map.getMap().get(l).size(); c++) {
                    g.drawImage(this.map.getMap().get(l).get(c).image, this.map.getMap().get(l).get(c).posX * 26, this.map
                                    .getMap().get(l).get(c).posY * 26, 26, 26, this);
                }
            }
	}

	public void moveNext(Graphics g) {

            if (listMousePath.size() > 0 && m < listMousePath.size()) {
                if (m == 0) {
                    g.drawImage(Sprites.mouseImage, this.listMousePath.get(m).getX() * 26,
                                            this.listMousePath.get(m).getY() * 26, 26, 26, this);
                }
                else {
                	g.drawImage(map.getMap().get(this.listMousePath.get(m-1).getY()).get(this.listMousePath.get(m-1).getX()).image, this.listMousePath.get(m-1).getX() * 26, this.listMousePath.get(m-1).getY() * 26, 26, 26, this); 
                    g.drawImage(Sprites.mouseImage, this.listMousePath.get(m).getX() * 26, this.listMousePath.get(m).getY() * 26, 26, 26, this); 
                }
                m++;
            }
	}

    public void setListMousePath(List<Node> listMousePath) {
        this.listMousePath = listMousePath;
    }
}