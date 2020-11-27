package uet.oop.bomberman.entities;

import javafx.scene.image.Image;
import uet.oop.bomberman.scenes.GameScene;


public abstract class CollidableObject extends Entity {
    protected boolean isSolid;

    public CollidableObject(GameScene scene, double x, double y, Image img){
        super(scene, x, y, img);
        this.isSolid = false;
    }

    public boolean isSolid() {
        return isSolid;
    }

    public void setIsSolid(boolean isSolid){
        this.isSolid = isSolid;
    }

    public boolean isColliding(CollidableObject other) {
        return Math.abs(this.gridX - other.gridX) < 0.6 && Math.abs(this.gridY - other.gridY) < 0.6;
    }
}
