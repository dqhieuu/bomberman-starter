package uet.oop.bomberman.entities.stillobjects;

import uet.oop.bomberman.entities.CollidableObject;
import uet.oop.bomberman.graphics.Sprite;
import uet.oop.bomberman.scenes.GameScene;

public class Wall extends CollidableObject {

    public Wall(GameScene scene, double x, double y) {
        super(scene, x, y, Sprite.wall.getFxImage());
        this.isSolid = true;
    }

    @Override
    public void update() {
    }

    @Override
    public void destroy() {
    }
}
