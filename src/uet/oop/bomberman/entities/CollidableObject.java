package uet.oop.bomberman.entities;

import javafx.scene.image.Image;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import uet.oop.bomberman.BombermanGame;
import uet.oop.bomberman.entities.StillObjects.Grass;
import uet.oop.bomberman.graphics.Maps;


public abstract class CollidableObject extends Entity {
    protected boolean isSolid;
    private Shape hitbox;
    private Shape collisionBox;

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

    public void setHitbox(Shape hitbox) {
        this.hitbox = hitbox;
    }

    public void setCollisionBox(Shape collisionBox) {
        this.collisionBox = collisionBox;
    }

    public boolean isColliding(CollidableObject other) {
        if (!other.isSolid()) {
            return false;
        }
        Rectangle otherCollisionBox =
                new Rectangle(other.x, other.y,
                        other.getEntityWidth(),
                        other.getEntityHeight());
        Rectangle thisCollisionBox =
                new Rectangle(this.x, this.y,
                        this.getEntityWidth(),
                        this.getEntityHeight());
        Shape checker = Shape.intersect(otherCollisionBox, thisCollisionBox);
        return checker.getBoundsInLocal().getWidth() != -1 || checker.getBoundsInLocal().getHeight() != -1;
    }

    @Override
    public void update() {

    }
}
