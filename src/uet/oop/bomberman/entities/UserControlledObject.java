package uet.oop.bomberman.entities;

import javafx.scene.Scene;
import javafx.scene.image.Image;
import uet.oop.bomberman.misc.MovePad;
import uet.oop.bomberman.scenes.MainGameScene;

public abstract class UserControlledObject extends MovableObject {
    protected final MovePad movePad;

    public UserControlledObject(MainGameScene scene, double x, double y, Image img) {
        super(scene, x, y, img);
        movePad = new MovePad();
    }

    public abstract void bindInput(Scene currentScene);
}
