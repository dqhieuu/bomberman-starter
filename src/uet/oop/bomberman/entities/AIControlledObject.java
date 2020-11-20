package uet.oop.bomberman.entities;

import javafx.scene.image.Image;
import uet.oop.bomberman.scenes.GameScene;

public abstract class AIControlledObject extends MovableObject {
    public AIControlledObject(GameScene scene, double x, double y, Image img) {
        super(scene, x, y, img);
    }
}
