package uet.oop.bomberman.entities.MobileObjects;

import javafx.scene.image.Image;
import uet.oop.bomberman.entities.AIControlledObject;
import uet.oop.bomberman.entities.Entity;

public class Enemy extends AIControlledObject {
    protected double moveLimit;

    public Enemy(double x, double y, Image img, double moveLimit) {
        super(x, y, img);
        this.moveLimit = moveLimit;
    }

    @Override
    public void update() {

    }

    public void autoMove(){

    }
}
