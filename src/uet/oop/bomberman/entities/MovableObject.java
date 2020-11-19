package uet.oop.bomberman.entities;

import javafx.scene.image.Image;
import uet.oop.bomberman.BombermanGame;
import uet.oop.bomberman.entities.stillobjects.Wall;
import uet.oop.bomberman.misc.Direction;

public abstract class MovableObject extends CollidableObject {
    protected Direction facingDirection;
    protected int animationTracker;

    protected static final double EPSILON = 0.0001;

    protected static final double SLIDING_CONSTANT = 0.7;

    public MovableObject(double x, double y, Image img) {
        super(x,y,img);
        facingDirection = Direction.EAST;
        animationTracker = 0;
    }

    public Direction getFacingDirection() {
        return facingDirection;
    }

    public void setFacingDirection(Direction facingDirection) {
        this.facingDirection = facingDirection;
    }

    public boolean movable() {
        double offsetX;
        double offsetY;
        int corY = (int) gridY;
        int corX = (int) gridX;
        switch (this.getFacingDirection()) {
            case EAST:
                offsetY = gridY - corY;
                if (offsetY == 0) {
                    Entity object = BombermanGame.getNextStillObjects(gridX + 1, gridY);
                    if (object instanceof Wall) {
                        return false;
                    }
                } else if (offsetY <= 0.5) {
                    Entity firstObject = BombermanGame.getNextStillObjects(gridX + 1, corY);
                    Entity secondObject = BombermanGame.getNextStillObjects(gridX + 1, corY + 1);
                    if (firstObject instanceof Wall) {
                        return false;
                    }
                    if (secondObject instanceof Wall) {
                        gridY -= offsetY * SLIDING_CONSTANT;
                    }
                    if (gridY - corY <= EPSILON) {
                        gridY = corY;
                    }

                } else {
                    Entity firstObject = BombermanGame.getNextStillObjects(gridX + 1, corY);
                    Entity secondObject = BombermanGame.getNextStillObjects(gridX + 1, corY + 1);
                    if (secondObject instanceof Wall) {
                        return false;
                    }
                    if (firstObject instanceof Wall) {
                        gridY += (1 - offsetY) * SLIDING_CONSTANT;
                    }

                    if (gridY - corY + 1 <= EPSILON) {
                        gridY = corY + 1;
                    }
                }
                break;
            case WEST:
                offsetY = gridY - corY;
                if (offsetY == 0) {
                    Entity object = BombermanGame.getNextStillObjects(gridX, gridY);
                    if (object instanceof Wall) {
                        return false;
                    }
                } else if (offsetY <= 0.5) {
                    Entity firstObject = BombermanGame.getNextStillObjects(gridX, corY);
                    Entity secondObject = BombermanGame.getNextStillObjects(gridX, corY + 1);
                    if (firstObject instanceof Wall) {
                        return false;
                    }
                    if (secondObject instanceof Wall) {
                        gridY -= offsetY * SLIDING_CONSTANT;
                    }
                    if (gridY - corY <= EPSILON) {
                        gridY = corY;
                    }
                } else {
                    Entity firstObject = BombermanGame.getNextStillObjects(gridX, corY);
                    Entity secondObject = BombermanGame.getNextStillObjects(gridX, corY + 1);
                    if (secondObject instanceof Wall) {
                        return false;
                    }
                    if (firstObject instanceof Wall) {
                        gridY += (1 - offsetY) * SLIDING_CONSTANT;
                    }

                    if (gridY - corY + 1 <= EPSILON) {
                        gridY = corY + 1;
                    }
                }
                break;
            case SOUTH:
                offsetX = gridX - corX;
                if (offsetX == 0) {
                    Entity object = BombermanGame.getNextStillObjects(gridX, gridY + 1);
                    if (object instanceof Wall) {
                        return false;
                    }
                } else if (offsetX <= 0.5) {
                    Entity firstObject = BombermanGame.getNextStillObjects(corX, gridY + 1);
                    Entity secondObject = BombermanGame.getNextStillObjects(corX + 1, gridY + 1);
                    if (firstObject instanceof Wall) {
                        return false;
                    }
                    if (secondObject instanceof Wall) {
                        gridX -= offsetX * SLIDING_CONSTANT;
                    }
                    if (gridX - corX <= EPSILON) {
                        gridX = corX;
                    }
                } else {
                    Entity firstObject = BombermanGame.getNextStillObjects(corX, gridY + 1);
                    Entity secondObject = BombermanGame.getNextStillObjects(corX + 1, gridY + 1);
                    if (secondObject instanceof Wall) {
                        return false;
                    }
                    if (firstObject instanceof Wall) {
                        gridX += (1 - offsetX) * SLIDING_CONSTANT;
                    }

                    if (gridX - corX + 1 <= EPSILON) {
                        gridX = corX + 1;
                    }
                }
                break;
            case NORTH:
                offsetX = gridX - corX;
                if (offsetX == 0) {
                    Entity object = BombermanGame.getNextStillObjects(gridX, gridY);
                    if (object instanceof Wall) {
                        return false;
                    }
                } else if (offsetX <= 0.5) {
                    Entity firstObject = BombermanGame.getNextStillObjects(corX, gridY);
                    Entity secondObject = BombermanGame.getNextStillObjects(corX + 1, gridY);
                    if (firstObject instanceof Wall) {
                        return false;
                    }
                    if (secondObject instanceof Wall) {
                        gridX -= offsetX * SLIDING_CONSTANT;
                    }
                    if (gridX - corX <= EPSILON) {
                        gridX = corX;
                    }
                } else {
                    Entity firstObject = BombermanGame.getNextStillObjects(corX, gridY);
                    Entity secondObject = BombermanGame.getNextStillObjects(corX + 1, gridY);
                    if (secondObject instanceof Wall) {
                        return false;
                    }

                    if (firstObject instanceof Wall) {
                        gridX += (1 - offsetX) * SLIDING_CONSTANT;
                    }

                    if (gridX - corX + 1 <= EPSILON) {
                        gridX = corX + 1;
                    }
                }
                break;
        }
        return true;
    }

    public void updateAnimationIndex() {

    }

}
