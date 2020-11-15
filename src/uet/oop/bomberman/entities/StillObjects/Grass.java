package uet.oop.bomberman.entities.StillObjects;

import javafx.scene.image.Image;
import uet.oop.bomberman.entities.CollidableObject;
import uet.oop.bomberman.entities.Entity;

public class Grass extends CollidableObject {

    public Grass(double x, double y, Image img) {
        super(x, y, img);
        this.setIsSolid(false);
    }

    @Override
    public void update() {

    }
}
