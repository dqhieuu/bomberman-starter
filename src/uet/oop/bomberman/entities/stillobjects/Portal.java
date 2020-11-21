package uet.oop.bomberman.entities.stillobjects;

import javafx.scene.canvas.GraphicsContext;
import uet.oop.bomberman.BombermanGame;
import uet.oop.bomberman.entities.CollidableObject;
import uet.oop.bomberman.graphics.Sprite;
import uet.oop.bomberman.scenes.GameScene;

public class Portal extends CollidableObject {
    public Portal(GameScene scene, double x, double y) {
        super(scene, x, y, Sprite.portal.getFxImage());
    }

    @Override
    public void update() {

    }

    @Override
    public void destroy() {

    }
}
