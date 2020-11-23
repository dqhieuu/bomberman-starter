package uet.oop.bomberman.entities.mobileobjects;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.image.Image;
import javafx.util.Duration;
import uet.oop.bomberman.BombermanGame;
import uet.oop.bomberman.entities.AIControlledObject;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.entities.stillobjects.Flame;
import uet.oop.bomberman.entities.stillobjects.powerups.PowerUp;
import uet.oop.bomberman.entities.stillobjects.powerups.PowerUpBomb;
import uet.oop.bomberman.entities.stillobjects.powerups.PowerUpFlame;
import uet.oop.bomberman.entities.stillobjects.powerups.PowerUpSpeed;
import uet.oop.bomberman.graphics.Sprite;
import uet.oop.bomberman.misc.Direction;
import uet.oop.bomberman.scenes.GameScene;
import uet.oop.bomberman.scenes.MainGameScene;

import java.security.Key;
import java.util.*;

public class Balloon extends AIControlledObject {
    private int spriteIndex = 0;
    private boolean remove;
    private boolean isDead;
    private Map<String, Image[]> movingSpriteLists;
    private Animation spriteChanger;
    private boolean isMoving;
    private int pathsCanTake;
    private Direction currentDirection;
    private int randomGenerator = new Random().nextInt(4) + 1;

    public Balloon(GameScene scene, double x, double y) {
        super(scene, x, y, Sprite.balloom_left1.getFxImage());
        remove = false;
        isMoving = true;
        isDead = false;
        if (canMoveDown()) {
            currentDirection = Direction.SOUTH;
        } else if (canMoveUp()) {
            currentDirection = Direction.NORTH;
        } else if (canMoveRight()) {
            currentDirection = Direction.EAST;
        } else if (canMoveLeft()) {
            currentDirection = Direction.WEST;
        } else {
            isMoving = false;
        }
        if (movingSpriteLists == null) {
            movingSpriteLists = new HashMap<>();
            movingSpriteLists.put(
                    Direction.WEST.toString(),
                    new Image[]{
                            Sprite.balloom_left1.getFxImage(),
                            Sprite.balloom_left2.getFxImage(),
                            Sprite.balloom_left3.getFxImage()
                    });
            movingSpriteLists.put(
                    Direction.EAST.toString(),
                    new Image[]{
                            Sprite.balloom_right1.getFxImage(),
                            Sprite.balloom_right2.getFxImage(),
                            Sprite.balloom_right3.getFxImage()
                    });
            movingSpriteLists.put(
                    Direction.SOUTH.toString(),
                    new Image[]{
                            Sprite.balloom_left1.getFxImage(),
                            Sprite.balloom_left2.getFxImage(),
                            Sprite.balloom_left3.getFxImage()
                    });
            movingSpriteLists.put(
                    Direction.NORTH.toString(),
                    new Image[]{
                            Sprite.balloom_right1.getFxImage(),
                            Sprite.balloom_right2.getFxImage(),
                            Sprite.balloom_right3.getFxImage()
                    });
            spriteChanger =
                    new Timeline(
                            new KeyFrame(
                                    Duration.millis(80),
                                    e -> {
                                        if (!isDestroyed()) {
                                            spriteIndex =
                                                    (spriteIndex + 1) % movingSpriteLists.get(facingDirection.toString()).length;
                                            setCurrentImg(movingSpriteLists.get(facingDirection.toString())[spriteIndex]);
                                        }
                                    }));
            spriteChanger.setCycleCount(Animation.INDEFINITE);
            spriteChanger.play();
        }
    }

    @Override
    public void update() {
        if (!isDestroyed() && !isDead) {
            super.update();
            if (gridX == (int) gridX && gridY == (int) gridY) {
                if (canMoveDown()) {
                    pathsCanTake++;
                }
                if (canMoveUp()) {
                    pathsCanTake++;
                }
                if (canMoveRight()) {
                    pathsCanTake++;
                }
                if (canMoveLeft()) {
                    pathsCanTake++;
                }
            }
            autoMove();
            if (!isMoving) {
                if (randomGenerator >= 10003) {
                    randomGenerator = 0;
                } else {
                    randomGenerator++;
                }
            }
            Entity objectStandingOn = ((MainGameScene) sceneContext)
                    .getStillObjectAt((int) Math.round(gridX), (int) Math.round(gridY));
            if (objectStandingOn != null) {
                if (objectStandingOn instanceof Flame) {
                    destroy();
                }
            }
            if (pathsCanTake > 0) {
                pathsCanTake = 0;
            }
        }
        if (remove) {
            super.destroy();
        }
    }

    @Override
    public void destroy() {
        isDead = true;
        setCurrentImg(Sprite.balloom_dead.getFxImage());
        spriteChanger.pause();
        Animation countDownTillRemoval = new Timeline(new KeyFrame(Duration.millis(400), e -> remove = true));
        countDownTillRemoval.play();
        ((MainGameScene) sceneContext).addPoints(100);
    }

    public void moveRight() {
        if (facingDirection != Direction.EAST) {
            setFacingDirection(Direction.EAST);
            spriteIndex = 0;
            setCurrentImg(movingSpriteLists.get(Direction.EAST.toString())[0]);
        }
        if (spriteChanger.getCurrentRate() == 0.0) {
            spriteChanger.play();
        }

        gridX += baseSpeed * BombermanGame.gameSpeed;
        if (!movable()) {
            isMoving = false;
            gridX -= baseSpeed * BombermanGame.gameSpeed;
        } else {
            randomGenerator = new Random().nextInt(4) + 1;
            isMoving = true;
        }
    }

    public void moveLeft() {
        if (facingDirection != Direction.WEST) {
            setFacingDirection(Direction.WEST);
            spriteIndex = 0;
            setCurrentImg(movingSpriteLists.get(Direction.WEST.toString())[0]);
        }
        if (spriteChanger.getCurrentRate() == 0.0) {
            spriteChanger.play();
        }

        gridX -= baseSpeed * BombermanGame.gameSpeed;
        if (!movable()) {
            isMoving = false;
            gridX += baseSpeed * BombermanGame.gameSpeed;
        } else {
            randomGenerator = new Random().nextInt(4) + 1;
            isMoving = true;
        }
    }

    public void moveUp() {
        if (facingDirection != Direction.NORTH) {
            setFacingDirection(Direction.NORTH);
            spriteIndex = 0;
            setCurrentImg(movingSpriteLists.get(Direction.NORTH.toString())[0]);
        }
        if (spriteChanger.getCurrentRate() == 0.0) {
            spriteChanger.play();
        }

        gridY -= baseSpeed * BombermanGame.gameSpeed;
        if (!movable()) {
            isMoving = false;
            gridY += baseSpeed * BombermanGame.gameSpeed;
        } else {
            randomGenerator = new Random().nextInt(4) + 1;
            isMoving = true;
        }
    }

    public void moveDown() {
        if (facingDirection != Direction.SOUTH) {
            setFacingDirection(Direction.SOUTH);
            spriteIndex = 0;
            setCurrentImg(movingSpriteLists.get(Direction.SOUTH.toString())[0]);
        }
        if (spriteChanger.getCurrentRate() == 0.0) {
            spriteChanger.play();
        }

        gridY += baseSpeed * BombermanGame.gameSpeed;
        if (!movable()) {
            isMoving = false;
            gridY -= baseSpeed * BombermanGame.gameSpeed;
        } else {
            randomGenerator = new Random().nextInt(4) + 1;
            isMoving = true;
        }
    }

    public boolean canMoveRight() {
        boolean result = false;
        gridX += baseSpeed * BombermanGame.gameSpeed;
        setFacingDirection(Direction.EAST);
        if (movable()) {
            result = true;
        }
        gridX -= baseSpeed * BombermanGame.gameSpeed;
        setFacingDirection(currentDirection);
        return result;
    }

    public boolean canMoveLeft() {
        boolean result = false;
        gridX -= baseSpeed * BombermanGame.gameSpeed;
        setFacingDirection(Direction.WEST);
        if (movable()) {
            result = true;
        }
        gridX += baseSpeed * BombermanGame.gameSpeed;
        setFacingDirection(currentDirection);
        return result;
    }

    public boolean canMoveUp() {
        boolean result = false;
        gridY -= baseSpeed * BombermanGame.gameSpeed;
        setFacingDirection(Direction.NORTH);
        if (movable()) {
            result = true;
        }
        gridY += baseSpeed * BombermanGame.gameSpeed;
        setFacingDirection(currentDirection);
        return result;
    }

    public boolean canMoveDown() {
        boolean result = false;
        gridY += baseSpeed * BombermanGame.gameSpeed;
        setFacingDirection(Direction.SOUTH);
        if (movable()) {
            result = true;
        }
        gridY -= baseSpeed * BombermanGame.gameSpeed;
        setFacingDirection(currentDirection);
        return result;
    }

    /**
     * Select a path.
     */
    public void selectPath(int test) {
        switch (test % 4) {
            case 0:
                currentDirection = Direction.WEST;
                break;
            case 1:
                currentDirection = Direction.EAST;
                break;
            case 2:
                currentDirection = Direction.NORTH;
                break;
            case 3:
                currentDirection = Direction.SOUTH;
                break;
        }
    }

    @Override
    public void autoMove() {
        if (!isMoving) {
            selectPath(randomGenerator);
        } else {
            if (pathsCanTake > 2) {
                randomGenerator = new Random().nextInt(4) + 1;
                selectPath(randomGenerator);
            }
        }
        switch (currentDirection) {
            case WEST:
                moveLeft();
                break;
            case EAST:
                moveRight();
                break;
            case SOUTH:
                moveDown();
                break;
            case NORTH:
                moveUp();
                break;
        }

    }
}
