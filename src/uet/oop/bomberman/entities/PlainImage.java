package uet.oop.bomberman.entities;

import javafx.scene.image.Image;
import uet.oop.bomberman.scenes.GameScene;

public class PlainImage extends Entity {
    public PlainImage(GameScene scene, double gridX, double gridY, Image image) {
        super(scene, gridX, gridY, image);
    }

    @Override
    public void update() {

    }
}
