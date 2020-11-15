package uet.oop.bomberman.entities.MobileObjects;

import javafx.scene.image.Image;
import uet.oop.bomberman.BombermanGame;
import uet.oop.bomberman.entities.CollidableObject;
import uet.oop.bomberman.entities.StillObjects.Bomb;
import uet.oop.bomberman.entities.StillObjects.Wall;
import uet.oop.bomberman.entities.UserControlledObject;
import uet.oop.bomberman.graphics.Sprite;

import java.util.ArrayList;

public class Bomber extends UserControlledObject {
    protected int numberOfBombs;
    protected boolean hasPlantedBomb;
    protected int explodeRange;
    protected ArrayList<Bomb> bombs = new ArrayList<>();

    public Bomber(double x, double y, Image img) {
        super(x, y, img);
        explodeRange = 3;
        numberOfBombs = 1;
        hasPlantedBomb = false;
        setBombs();
    }

    @Override
    public void update() {
        if (bombs.size() > 0) {
            for (Bomb bomb : bombs) {
                bomb.update();
            }
        }
        ticks();
        this.render(BombermanGame.gc);
    }

    public void setNumberOfBombs(int numberOfBombs) {
        this.numberOfBombs = numberOfBombs;
    }

    public int getNumberOfBombs() {
        return numberOfBombs;
    }

    public boolean isHasPlantedBomb() {
        return hasPlantedBomb;
    }

    public void setHasPlantedBomb(boolean hasPlantedBomb) {
        this.hasPlantedBomb = hasPlantedBomb;
    }

    public void setExplodeRange(int explodeRange) {
        this.explodeRange = explodeRange;
    }

    public int getExplodeRange() {
        return explodeRange;
    }

    public ArrayList<Bomb> getBombs() {
        return bombs;
    }

    public void setBombs() {
        if (numberOfBombs > bombs.size()) {
            for (int i = 0; i < numberOfBombs - bombs.size(); i++) {
                Bomb newBomb = new Bomb(0, 0, Sprite.bomb.getFxImage());
                bombs.add(newBomb);
            }
        }
    }

    public Bomb searchBomb() {
        for (Bomb bomb : bombs) {
            if (!bomb.isHasPlanted()) {
                bomb.setHasPlanted(true);
                bomb.setAttribute();
                return bomb;
            }
        }
        return null;
    }

    public double getBombX() {
        if (x != (int) x) {
            double temp = x - (int) x;
            if (temp <= 0.5) {
                return Math.floor(x);
            } else {
                return Math.ceil(x);
            }
        }
        return x;
    }

    public double getBombY() {
        if (y != (int) y) {
            double temp = y - (int) y;
            if (temp <= 0.5) {
                return Math.floor(y);
            } else {
                return Math.ceil(y);
            }
        }
        return y;
    }

    /**
     * Check if bomberman can move to the destined location.
     */
    public void checkMovement() {
        if (!isMoving()) {
            return;
        }

        double offsetX;
        double offsetY;
        int corY = (int) y;
        int corX = (int) x;
        switch (this.getFacingDirection()) {
            case EAST:
                offsetY = y - corY;
                if (offsetY == 0) {
                    CollidableObject object = BombermanGame.getNextStillObjects(x + 1, y);
                    if (object instanceof Wall) {
                        moveAble = false;
                    }
                } else if (offsetY <= 0.5) {
                    CollidableObject firstObject = BombermanGame.getNextStillObjects(x + 1, corY);
                    CollidableObject secondObject = BombermanGame.getNextStillObjects(x + 1, corY + 1);
                    if (firstObject instanceof Wall) {
                        moveAble = false;
                    }
                    if (moveAble) {
                        if (secondObject instanceof Wall) {
                            y -= offsetY * SLIDING_CONSTANT;
                        }
                        if (y - corY <= EPSILON) {
                            y = corY;
                        }
                    }
                } else {
                    CollidableObject firstObject = BombermanGame.getNextStillObjects(x + 1, corY);
                    CollidableObject secondObject = BombermanGame.getNextStillObjects(x + 1, corY + 1);
                    if (secondObject instanceof Wall) {
                        moveAble = false;
                    }
                    if (moveAble) {
                        if (firstObject instanceof Wall) {
                            y += (1 - offsetY) * SLIDING_CONSTANT;
                        }

                        if (y - corY + 1 <= EPSILON) {
                            y = corY + 1;
                        }
                    }
                }
                break;
            case WEST:
                offsetY = y - corY;
                if (offsetY == 0) {
                    CollidableObject object = BombermanGame.getNextStillObjects(x, y);
                    if (object instanceof Wall) {
                        moveAble = false;
                    }
                } else if (offsetY <= 0.5) {
                    CollidableObject firstObject = BombermanGame.getNextStillObjects(x, corY);
                    CollidableObject secondObject = BombermanGame.getNextStillObjects(x, corY + 1);
                    if (firstObject instanceof Wall) {
                        moveAble = false;
                    }
                    if (moveAble) {
                        if (secondObject instanceof Wall) {
                            y -= offsetY * SLIDING_CONSTANT;
                        }
                        if (y - corY <= EPSILON) {
                            y = corY;
                        }
                    }
                } else {
                    CollidableObject firstObject = BombermanGame.getNextStillObjects(x, corY);
                    CollidableObject secondObject = BombermanGame.getNextStillObjects(x, corY + 1);
                    if (secondObject instanceof Wall) {
                        moveAble = false;
                    }
                    if (moveAble) {
                        if (firstObject instanceof Wall) {
                            y += (1 - offsetY) * SLIDING_CONSTANT;
                        }

                        if (y - corY + 1 <= EPSILON) {
                            y = corY + 1;
                        }
                    }
                }
                break;
            case SOUTH:
                offsetX = x - corX;
                if (offsetX == 0) {
                    CollidableObject object = BombermanGame.getNextStillObjects(x, y + 1);
                    if (object instanceof Wall) {
                        moveAble = false;
                    }
                } else if (offsetX <= 0.5) {
                    CollidableObject firstObject = BombermanGame.getNextStillObjects(corX, y + 1);
                    CollidableObject secondObject = BombermanGame.getNextStillObjects(corX + 1, y + 1);
                    if (firstObject instanceof Wall) {
                        moveAble = false;
                    }
                    if (moveAble) {
                        if (secondObject instanceof Wall) {
                            x -= offsetX * SLIDING_CONSTANT;
                        }
                        if (x - corX <= EPSILON) {
                            x = corX;
                        }
                    }
                } else {
                    CollidableObject firstObject = BombermanGame.getNextStillObjects(corX, y + 1);
                    CollidableObject secondObject = BombermanGame.getNextStillObjects(corX + 1, y + 1);
                    if (secondObject instanceof Wall) {
                        moveAble = false;
                    }
                    if (moveAble) {
                        if (firstObject instanceof Wall) {
                            x += (1 - offsetX) * SLIDING_CONSTANT;
                        }

                        if (x - corX + 1 <= EPSILON) {
                            x = corX + 1;
                        }
                    }
                }
                break;
            case NORTH:
                offsetX = x - corX;
                if (offsetX == 0) {
                    CollidableObject object = BombermanGame.getNextStillObjects(x, y);
                    if (object instanceof Wall) {
                        moveAble = false;
                    }
                } else if (offsetX <= 0.5) {
                    CollidableObject firstObject = BombermanGame.getNextStillObjects(corX, y);
                    CollidableObject secondObject = BombermanGame.getNextStillObjects(corX + 1, y);
                    if (firstObject instanceof Wall) {
                        moveAble = false;
                    }

                    if (moveAble) {
                        if (secondObject instanceof Wall) {
                            x -= offsetX * SLIDING_CONSTANT;
                        }
                        if (x - corX <= EPSILON) {
                            x = corX;
                        }
                    }
                } else {
                    CollidableObject firstObject = BombermanGame.getNextStillObjects(corX, y);
                    CollidableObject secondObject = BombermanGame.getNextStillObjects(corX + 1, y);
                    if (secondObject instanceof Wall) {
                        moveAble = false;
                    }

                    if (moveAble) {
                        if (firstObject instanceof Wall) {
                            x += (1 - offsetX) * SLIDING_CONSTANT;
                        }

                        if (x - corX + 1 <= EPSILON) {
                            x = corX + 1;
                        }
                    }
                }
                break;
        }
    }

    public void ticks() {
        if (velX != 0 || velY != 0) {
            x += velX;
            y += velY;
            checkMovement();
            if (!moveAble) {
                animationTracker = 0;
                x -= velX;
                y -= velY;
                moveAble = true;
            }
        } else {
            animationTracker = 0;
        }
    }
}
