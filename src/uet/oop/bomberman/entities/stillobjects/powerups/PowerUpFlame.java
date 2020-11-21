package uet.oop.bomberman.entities.stillobjects.powerups;

import uet.oop.bomberman.graphics.Sprite;
import uet.oop.bomberman.scenes.GameScene;

public class PowerUpFlame extends PowerUp {
    public PowerUpFlame(GameScene scene, double x, double y) {
        super(scene, x, y, Sprite.powerup_flames.getFxImage());
    }

    @Override
    public void update() {

    }
}
