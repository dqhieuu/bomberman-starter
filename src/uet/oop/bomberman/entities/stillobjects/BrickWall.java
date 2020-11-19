package uet.oop.bomberman.entities.stillobjects;

import uet.oop.bomberman.entities.CollidableObject;
import uet.oop.bomberman.graphics.Sprite;

public class BrickWall extends CollidableObject {
    public BrickWall(double x, double y) {
        super(x, y, Sprite.brick.getFxImage());
    }

    @Override
    public void update() {

    }
}

