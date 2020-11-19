package uet.oop.bomberman.entities;

import javafx.scene.image.Image;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;


public abstract class CollidableObject extends Entity {
    protected boolean isSolid;

    public CollidableObject(double x, double y, Image img){
        super(x, y, img);
        this.isSolid  = true;
    }

    public boolean isSolid() {
        return isSolid;
    }

    public void setIsSolid(boolean isSolid){
        this.isSolid = isSolid;
    }

    public boolean isColliding(CollidableObject other) {
        if (!other.isSolid()) {
            return false;
        }
        Rectangle otherCollisionBox =
                new Rectangle(other.gridX, other.gridY,
                        other.getEntityWidth(),
                        other.getEntityHeight());
        Rectangle thisCollisionBox =
                new Rectangle(this.gridX, this.gridY,
                        this.getEntityWidth(),
                        this.getEntityHeight());
        Shape checker = Shape.intersect(otherCollisionBox, thisCollisionBox);
        return checker.getBoundsInLocal().getWidth() != -1 || checker.getBoundsInLocal().getHeight() != -1;
    }
}
