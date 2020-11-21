package uet.oop.bomberman.entities.stillobjects.powerups;

import uet.oop.bomberman.graphics.Sprite;
import uet.oop.bomberman.scenes.GameScene;

public class PowerUpBomb extends PowerUp {
    public PowerUpBomb(GameScene scene, double x, double y) {
        super(scene, x, y, Sprite.powerup_bombs.getFxImage());
    }

    @Override
    public void update() {

    }
}
