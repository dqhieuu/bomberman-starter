package uet.oop.bomberman.entities;

import javafx.scene.SnapshotParameters;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import uet.oop.bomberman.BombermanGame;
import uet.oop.bomberman.graphics.Maps;

public class Bomber extends Entity {
    protected boolean moveEast;
    protected boolean moveWest;
    protected boolean moveSouth;
    protected boolean moveNorth;
    protected boolean moveAble;
    protected boolean hasPlantedBomb;
    protected int explodeRange;

    public Bomber(int x, int y, Image img) {
        super(x, y, img);
        explodeRange = 1;
        hasPlantedBomb = false;
        moveNorth = false;
        moveSouth = false;
        moveWest = false;
        moveEast = false;
        moveAble = true;
    }

    @Override
    public void update() {
        this.render(BombermanGame.gc);
    }

    public boolean isHasPlantedBomb() {
        return hasPlantedBomb;
    }

    public void setHasPlantedBomb(boolean hasPlantedBomb) {
        this.hasPlantedBomb = hasPlantedBomb;
    }

    public boolean isMoveEast() {
        return moveEast;
    }

    public boolean isMoveWest() {
        return moveWest;
    }

    public boolean isMoveSouth() {
        return moveSouth;
    }

    public boolean isMoveNorth() {
        return moveNorth;
    }

    public void setMoveEast(boolean moveEast) {
        this.moveEast = moveEast;
    }

    public void setMoveWest(boolean moveWest) {
        this.moveWest = moveWest;
    }

    public void setMoveSouth(boolean moveSouth) {
        this.moveSouth = moveSouth;
    }

    public void setMoveNorth(boolean moveNorth) {
        this.moveNorth = moveNorth;
    }

    public boolean isMoveAble(){
        return moveAble;
    }

    public void setExplodeRange(int explodeRange){
        this.explodeRange = explodeRange;
    }

    public int getExplodeRange(){
        return explodeRange;
    }

    /**
     * Check if bomberman can move to the destined location.
     */
    public void checkMovement() {
        int corX;
        int corY;
        if (moveEast) {
            corX = this.x + 1;
            corY = this.y;
            Entity entity = Entity.getNextEntity(corX, corY);
            if(entity instanceof Grass){
                moveAble = true;
                return;
            }
            moveAble = !Entity.isColliding(this, entity);
        } else if (moveWest) {
            corX = this.x - 1;
            corY = this.y;
            Entity entity = Entity.getNextEntity(corX, corY);
            if(entity instanceof Grass){
                moveAble = true;
                return;
            }
            moveAble = !Entity.isColliding(this, entity);
        } else if (moveSouth) {
            corX = this.x;
            corY = this.y + 1;
            Entity entity = Entity.getNextEntity(corX, corY);
            if(entity instanceof Grass){
                moveAble = true;
                return;
            }
            moveAble = !Entity.isColliding(this, entity);
        } else if (moveNorth) {
            corX = this.x;
            corY = this.y - 1;
            Entity entity = Entity.getNextEntity(corX, corY);
            if(entity instanceof Grass){
                moveAble = true;
                return;
            }
            moveAble = !Entity.isColliding(this, entity);
        }
    }
}
