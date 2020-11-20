package uet.oop.bomberman.entities.stillobjects;

import uet.oop.bomberman.entities.CollidableObject;
import uet.oop.bomberman.entities.MovableObject;
import uet.oop.bomberman.graphics.Sprite;
import uet.oop.bomberman.scenes.GameScene;

public class Portal extends CollidableObject {
    Portal(GameScene scene, double x, double y) {
        super(scene, x, y, Sprite.portal.getFxImage());
    }

    @Override
    public void update() {

    }
}
