package uet.oop.bomberman.entities;

import javafx.scene.image.Image;
import uet.oop.bomberman.BombermanGame;
import uet.oop.bomberman.misc.Direction;
import uet.oop.bomberman.scenes.GameScene;
import uet.oop.bomberman.scenes.MainGameScene;

public abstract class MovableObject extends CollidableObject {
    protected Direction facingDirection;
    protected int animationTracker;

    protected static final double EPSILON = 0.0001;

    protected static final double SLIDING_CONSTANT = 1.0;
    protected static final double IGNORE_COLLISION_DISTANCE = 0.85;

    protected double baseSpeed = 0.05;

    public MovableObject(GameScene scene, double x, double y, Image img) {
        super(scene, x,y,img);
        facingDirection = Direction.EAST;
        animationTracker = 0;
    }

    public Direction getFacingDirection() {
        return facingDirection;
    }

    public void setFacingDirection(Direction facingDirection) {
        this.facingDirection = facingDirection;
    }

    protected boolean movable() {
        double offsetX;
        double offsetY;
        int corY = (int) gridY;
        int corX = (int) gridX;
        switch (this.getFacingDirection()) {
            case EAST:
                offsetY = gridY - corY;
                if (offsetY == 0) {
                    Entity object = ((MainGameScene)sceneContext).getStillObjectAt(corX + 1, corY);
                    if (object instanceof CollidableObject && ((CollidableObject) object).isSolid()) {
                        return Math.abs(gridX - object.gridX) < IGNORE_COLLISION_DISTANCE;
                    }
                } else if (offsetY <= 0.5) {
                    Entity firstObject = ((MainGameScene)sceneContext).getStillObjectAt(corX + 1, corY);
                    Entity secondObject = ((MainGameScene)sceneContext).getStillObjectAt(corX + 1, corY + 1);
                    if (firstObject instanceof CollidableObject && ((CollidableObject) firstObject).isSolid()) {
                        return false;
                    }
                    if (secondObject instanceof CollidableObject && ((CollidableObject) secondObject).isSolid()) {
                        gridY -= baseSpeed * BombermanGame.gameSpeed *  SLIDING_CONSTANT;
                    }

                } else {
                    Entity firstObject = ((MainGameScene)sceneContext).getStillObjectAt(corX + 1, corY);
                    Entity secondObject = ((MainGameScene)sceneContext).getStillObjectAt(corX + 1, corY + 1);
                    if (secondObject instanceof CollidableObject && ((CollidableObject) secondObject).isSolid()) {
                        return false;
                    }
                    if (firstObject instanceof CollidableObject && ((CollidableObject) firstObject).isSolid()) {
                        gridY += baseSpeed * BombermanGame.gameSpeed *  SLIDING_CONSTANT;
                    }
                }
                break;
            case WEST:
                offsetY = gridY - corY;
                if (offsetY == 0) {
                    Entity object = ((MainGameScene)sceneContext).getStillObjectAt(corX, corY);
                    if (object instanceof CollidableObject && ((CollidableObject) object).isSolid()) {
                        return Math.abs(gridX - object.gridX) < IGNORE_COLLISION_DISTANCE;
                    }
                } else if (offsetY <= 0.5) {
                    Entity firstObject = ((MainGameScene)sceneContext).getStillObjectAt(corX, corY);
                    Entity secondObject = ((MainGameScene)sceneContext).getStillObjectAt(corX, corY + 1);
                    if (firstObject instanceof CollidableObject && ((CollidableObject) firstObject).isSolid()) {
                        return false;
                    }
                    if (secondObject instanceof CollidableObject && ((CollidableObject) secondObject).isSolid()) {
                        gridY -= baseSpeed * BombermanGame.gameSpeed *  SLIDING_CONSTANT;
                    }
                } else {
                    Entity firstObject = ((MainGameScene)sceneContext).getStillObjectAt(corX, corY);
                    Entity secondObject = ((MainGameScene)sceneContext).getStillObjectAt(corX, corY + 1);
                    if (secondObject instanceof CollidableObject && ((CollidableObject) secondObject).isSolid()) {
                        return false;
                    }
                    if (firstObject instanceof CollidableObject && ((CollidableObject) firstObject).isSolid()) {
                        gridY += baseSpeed * BombermanGame.gameSpeed *  SLIDING_CONSTANT;
                    }
                }
                break;
            case SOUTH:
                offsetX = gridX - corX;
                if (offsetX == 0) {
                    Entity object = ((MainGameScene)sceneContext).getStillObjectAt(corX, corY + 1);
                    if (object instanceof CollidableObject && ((CollidableObject) object).isSolid()) {
                        return Math.abs(gridY - object.gridY) < IGNORE_COLLISION_DISTANCE;
                    }
                } else if (offsetX <= 0.5) {
                    Entity firstObject = ((MainGameScene)sceneContext).getStillObjectAt(corX, corY + 1);
                    Entity secondObject = ((MainGameScene)sceneContext).getStillObjectAt(corX + 1, corY + 1);
                    if (firstObject instanceof CollidableObject && ((CollidableObject) firstObject).isSolid()) {
                        return false;
                    }
                    if (secondObject instanceof CollidableObject && ((CollidableObject) secondObject).isSolid()) {
                        gridX -= baseSpeed * BombermanGame.gameSpeed *  SLIDING_CONSTANT;
                    }
                } else {
                    Entity firstObject = ((MainGameScene)sceneContext).getStillObjectAt(corX, corY + 1);
                    Entity secondObject = ((MainGameScene)sceneContext).getStillObjectAt(corX + 1, corY + 1);
                    if (secondObject instanceof CollidableObject && ((CollidableObject) secondObject).isSolid()) {
                        return false;
                    }
                    if (firstObject instanceof CollidableObject && ((CollidableObject) firstObject).isSolid()) {
                        gridX += baseSpeed * BombermanGame.gameSpeed *  SLIDING_CONSTANT;
                    }
                }
                break;
            case NORTH:
                offsetX = gridX - corX;
                if (offsetX == 0) {
                    Entity object = ((MainGameScene)sceneContext).getStillObjectAt(corX, corY);
                    if (object instanceof CollidableObject && ((CollidableObject) object).isSolid()) {
                        return Math.abs(gridY - object.gridY) < IGNORE_COLLISION_DISTANCE;
                    }
                } else if (offsetX <= 0.5) {
                    Entity firstObject = ((MainGameScene)sceneContext).getStillObjectAt(corX, corY);
                    Entity secondObject = ((MainGameScene)sceneContext).getStillObjectAt(corX + 1, corY);
                    if (firstObject instanceof CollidableObject && ((CollidableObject) firstObject).isSolid()) {
                        return false;
                    }
                    if (secondObject instanceof CollidableObject && ((CollidableObject) secondObject).isSolid()) {
                        gridX -= baseSpeed * BombermanGame.gameSpeed *  SLIDING_CONSTANT;
                    }
                } else {
                    Entity firstObject = ((MainGameScene)sceneContext).getStillObjectAt(corX, corY);
                    Entity secondObject = ((MainGameScene)sceneContext).getStillObjectAt(corX + 1, corY);
                    if (secondObject instanceof CollidableObject && ((CollidableObject) secondObject).isSolid()) {
                        return false;
                    }

                    if (firstObject instanceof CollidableObject && ((CollidableObject) firstObject).isSolid()) {
                        gridX += baseSpeed * BombermanGame.gameSpeed * SLIDING_CONSTANT;
                    }
                }
                break;
        }
        return true;
    }

    public void roundGridCoords() {
        int roundedX = (int)Math.round(gridX);
        int roundedY = (int)Math.round(gridY);
        double roundRange = (baseSpeed * BombermanGame.gameSpeed * SLIDING_CONSTANT)/2 + EPSILON;
        if(Math.abs(roundedX-gridX)<=roundRange) {
            gridX = roundedX;
        }
        if(Math.abs(roundedY-gridY)<=roundRange) {
            gridY = roundedY;
        }
    }

    @Override
    public void update() {
      roundGridCoords();
    }
}
