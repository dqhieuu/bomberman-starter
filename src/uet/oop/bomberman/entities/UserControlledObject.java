package uet.oop.bomberman.entities;

import javafx.scene.image.Image;

public abstract class UserControlledObject extends MovableObject {
    public UserControlledObject(double x, double y, Image img) {
        super(x, y, img);
    }
}
