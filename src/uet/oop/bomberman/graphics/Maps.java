package uet.oop.bomberman.graphics;

import javafx.scene.canvas.GraphicsContext;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Maps {
    private int numberOfMaps;
    private int MAP_WIDTH;
    private int MAP_HEIGHT;
    private ArrayList<Level> maps = new ArrayList<>();

    public Maps(String path) {
        readFile(path);
    }

    public int getMAP_WIDTH() {
        return MAP_WIDTH;
    }

    public int getMAP_HEIGHT() {
        return MAP_HEIGHT;
    }

    public ArrayList<Level> getMaps(){
        return maps;
    }

    /**
     * Generate a list of maps from the path file. Read the Note.txt in res/levels for details on how the path file is structured.
     *
     * @param path
     */
    public void readFile(String path) {
        try {
            BufferedReader br = new BufferedReader(new FileReader(path));
            String firstLine;
            firstLine = br.readLine();
            Scanner scanner = new Scanner(firstLine);
            numberOfMaps = scanner.nextInt();
            MAP_HEIGHT = scanner.nextInt();
            MAP_WIDTH = scanner.nextInt();
            String currentLine;
            int counter = 0;
            int level = 0;
            boolean mapCreated = true;
            while ((currentLine = br.readLine()) != null) {
                level++;
                ArrayList<String> mapStructure = new ArrayList<>();
                if (currentLine.length() != MAP_WIDTH) {
                    System.out.println("There's a false formatted map (incorrect width): " + level);
                    while ((currentLine = br.readLine()) != null) {
                        if (currentLine.equals("")) {
                            break;
                        }
                    }
                    mapCreated = false;
                } else {
                    mapStructure.add(currentLine);
                    counter++;
                    while ((currentLine = br.readLine()) != null) {
                        if (currentLine.equals("")) {
                            if (counter != MAP_HEIGHT) {
                                System.out.println("There's a false formatted map (incorrect height): " + level);
                                mapCreated = false;
                            }
                            break;
                        } else if (currentLine.length() != MAP_WIDTH) {
                            System.out.println("There's a false formatted map (incorrect width): " + level);
                            while ((currentLine = br.readLine()) != null) {
                                if (currentLine.equals("")) {
                                    break;
                                }
                            }
                            mapCreated = false;
                            break;
                        }
                        mapStructure.add(currentLine);
                        counter++;
                    }
                }
                if (!mapCreated) {
                    level--;
                    counter = 0;
                    mapCreated = true; //reset the requirement for the next level.
                } else {
                    Level newLevel = new Level(level, mapStructure);
                    maps.add(newLevel);
                    counter = 0;
                }
            }
            if (numberOfMaps > level) {
                System.out.printf("There are %d false formatted maps%n", numberOfMaps - level);
            } else {
                System.out.println("All maps created successfully");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * This is just for testing.
     */
    public void printMap() {
        System.out.println(numberOfMaps);
        System.out.println(MAP_HEIGHT);
        System.out.println(MAP_WIDTH);
        for (Level map : maps) {
            map.testMap();
        }
    }

    public void createLevel(int level) {
        maps.get(level).createMap();
    }

    public void drawLevel(int level, GraphicsContext gc) {
        maps.get(level).drawMap(gc);
    }
}
