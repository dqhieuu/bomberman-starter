package uet.oop.bomberman.entities;

import javafx.scene.image.Image;
import uet.oop.bomberman.misc.Direction;

public abstract class MovableObject extends CollidableObject {
    protected Direction facingDirection;
    protected boolean moveAble;
    protected double velX;
    protected double velY;
    protected int animationTracker;

    protected static final double EPSILON = 0.0001;

    protected static final double SLIDING_CONSTANT = 0.8;

    public MovableObject(double x, double y, Image img){
        super(x,y,img);
        velX = 0;
        velY = 0;
        facingDirection = Direction.EAST;
        moveAble = true;
        animationTracker = 0;
    }

    public Direction getFacingDirection() {
        return facingDirection;
    }

    public void setFacingDirection(Direction facingDirection) {
        this.facingDirection = facingDirection;
    }

    public boolean isMoving() {
        return velX != 0 || velY != 0;
    }

    public boolean isMoveAble(){
        return moveAble;
    }

    public void setVelX(double velX){
        this.velX = velX;
    }

    public void setVelY(double velY){
        this.velY = velY;
    }

    public int getAnimationTracker(){
        return animationTracker;
    }

    public void updateAnimationIndex() {
        if (animationTracker == 2) {
            animationTracker = 1;
        } else {
            animationTracker++;
        }
    }
}
