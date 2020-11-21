package uet.oop.bomberman.entities.stillobjects.powerups;

import uet.oop.bomberman.graphics.Sprite;
import uet.oop.bomberman.scenes.GameScene;

public class PowerUpSpeed extends PowerUp{
    public PowerUpSpeed(GameScene scene, double x, double y) {
        super(scene, x, y, Sprite.powerup_speed.getFxImage());
    }

    @Override
    public void update() {

    }
}
