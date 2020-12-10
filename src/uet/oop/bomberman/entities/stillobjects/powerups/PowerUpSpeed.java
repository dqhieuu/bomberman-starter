package uet.oop.bomberman.entities.stillobjects.powerups;

import uet.oop.bomberman.graphics.Sprite;
import uet.oop.bomberman.scenes.MainGameScene;

public class PowerUpSpeed extends PowerUp{
    public PowerUpSpeed(MainGameScene scene, double x, double y) {
        super(scene, x, y, Sprite.powerup_speed.getFxImage());
    }

    @Override
    public void update() {

    }
}
