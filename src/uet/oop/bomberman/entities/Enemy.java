package uet.oop.bomberman.entities;

import javafx.scene.image.Image;

public class Enemy extends Entity {
    protected int moveLimit;

    public Enemy(int x, int y, Image img, int moveLimit) {
        super(x, y, img);
        this.moveLimit = moveLimit;
    }

    @Override
    public void update() {

    }

    public void autoMove(){

    }
}
