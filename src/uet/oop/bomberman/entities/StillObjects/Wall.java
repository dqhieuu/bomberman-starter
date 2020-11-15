package uet.oop.bomberman.entities.StillObjects;

import javafx.scene.image.Image;
import uet.oop.bomberman.entities.CollidableObject;
import uet.oop.bomberman.entities.Entity;

public class Wall extends CollidableObject {

    public Wall(double x, double y, Image img) {
        super(x, y, img);
    }

    @Override
    public void update() {

    }
}
