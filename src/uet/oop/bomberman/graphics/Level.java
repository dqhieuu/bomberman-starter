package uet.oop.bomberman.graphics;

import javafx.scene.canvas.GraphicsContext;
import uet.oop.bomberman.BombermanGame;
import uet.oop.bomberman.entities.*;
import uet.oop.bomberman.entities.MobileObjects.Enemy;
import uet.oop.bomberman.entities.StillObjects.Explosion;
import uet.oop.bomberman.entities.StillObjects.Grass;
import uet.oop.bomberman.entities.StillObjects.Wall;

import java.util.ArrayList;
import java.util.List;

public class Level {
    private int level;
    private ArrayList<String> mapStrcuture;
    private List<MovableObject> entities = new ArrayList<>();
    private List<CollidableObject> stillObjects = new ArrayList<>();
    protected ArrayList<Explosion> explodeAnimation = new ArrayList<>();

    public Level(int level, ArrayList<String> mapStrcuture) {
        this.level = level;
        this.mapStrcuture = mapStrcuture;
    }

    public ArrayList<Explosion> getExplodeAnimation() {
        return explodeAnimation;
    }

    public List<CollidableObject> getStillObjects() {
        return stillObjects;
    }

    public List<MovableObject> getEntities() {
        return entities;
    }

    public void createMap() {
        for (int i = 0; i < mapStrcuture.size(); i++) {
            String texture = mapStrcuture.get(i);
            for (int j = 0; j < texture.length(); j++) {
                switch (texture.charAt(j)) {
                    case '#':
                        CollidableObject wall = new Wall(j, i, Sprite.wall.getFxImage());
                        stillObjects.add(wall);
                        break;
                    case '1':
                        MovableObject enemy = new Enemy(j, i, Sprite.balloom_dead.getFxImage(), 3);
                        entities.add(enemy);
                    default:
                        CollidableObject object = new Grass(j, i, Sprite.grass.getFxImage());
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

    public void removeFlames(int numberOfFlames) {
        for (int i = 0; i < numberOfFlames; i++) {
            explodeAnimation.remove(i);
        }
    }
}
