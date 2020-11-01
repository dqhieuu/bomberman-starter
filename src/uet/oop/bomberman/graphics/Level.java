package uet.oop.bomberman.graphics;

import javafx.scene.canvas.GraphicsContext;
import uet.oop.bomberman.BombermanGame;
import uet.oop.bomberman.entities.*;

import java.util.ArrayList;
import java.util.List;

public class Level {
    private int level;
    private ArrayList<String> mapStrcuture;
    private List<Entity> entities = new ArrayList<>();
    private List<Entity> stillObjects = new ArrayList<>();

    public Level(int level, ArrayList<String> mapStrcuture) {
        this.level = level;
        this.mapStrcuture = mapStrcuture;
    }

    public List<Entity> getStillObjects(){
        return stillObjects;
    }

    public List<Entity> getEntities(){
        return entities;
    }

    public void createMap() {
        for (int i = 0; i < mapStrcuture.size(); i++) {
            String texture = mapStrcuture.get(i);
            for (int j = 0; j < texture.length(); j++) {
                Entity object;
                switch (texture.charAt(j)) {
                    case '#':
                        object = new Wall(j, i, Sprite.wall.getFxImage());
                        stillObjects.add(object);
                        break;
                    case '1':
                        object = new Enemy(j, i, Sprite.balloom_dead.getFxImage(), 3);
                        entities.add(object);
                    default:
                        object = new Grass(j, i, Sprite.grass.getFxImage());
                        stillObjects.add(object);
                        break;
                }
            }
        }
    }

    /**
     * This is just for testing also.
     */
    public void testMap() {
        for (String texture : mapStrcuture) {
            System.out.println(texture);
        }
    }

    /**
     * Draw the level, recycled from the previous code.
     *
     * @param gc
     */
    public void drawMap(GraphicsContext gc) {
        gc.clearRect(0, 0, BombermanGame.canvas.getWidth(), BombermanGame.canvas.getHeight());
        stillObjects.forEach(g -> g.render(gc));
        entities.forEach(g -> g.render(gc));
    }
}
