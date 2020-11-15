package uet.oop.bomberman.entities.StillObjects;

import javafx.scene.image.Image;
import uet.oop.bomberman.entities.CollidableObject;
import uet.oop.bomberman.entities.Entity;

public class BrickWall extends CollidableObject {
    public BrickWall(double x, double y, Image img) {
        super(x, y, img);
    }

    @Override
    public void update() {

    }
}

