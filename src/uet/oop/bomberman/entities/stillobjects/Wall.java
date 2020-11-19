package uet.oop.bomberman.entities.stillobjects;

import uet.oop.bomberman.entities.CollidableObject;
import uet.oop.bomberman.graphics.Sprite;

public class Wall extends CollidableObject {

    public Wall(double x, double y) {
        super(x, y, Sprite.wall.getFxImage());
    }

    @Override
    public void update() {

    }
}
