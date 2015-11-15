package main;

import heuristics.AStarHeuristic;

import javax.swing.JFrame;

import map.Window;
import aStar.AStar;
import aStar.AreaMap;
import aStar.Path;
import map.Sprites;

public class TestAStar {

    public static int[][] obstacleMap = new int[48][19];

    private static int goalY = 5;
    private static int goalX = 17;
    private static int startY = 5;
    private static int startX = 2;

    public static void main(String[] args) {
        Sprites.initSprites();

        String configFile = "src/resources/map.txt";

        Window window = new Window(configFile);

        Path path = new Path();

        AreaMap map = new AreaMap(48, 19, obstacleMap);

        AStarHeuristic heuristic = new AStarHeuristic();

        AStar pathFinder = new AStar(map, heuristic);

        path = pathFinder.calcShortestPath(goalY, goalX, startY, startX);
        window.getGame().setListMousePath(path.getWayPoint());

        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}