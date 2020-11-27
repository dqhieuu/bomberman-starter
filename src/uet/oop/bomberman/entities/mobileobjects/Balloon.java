package uet.oop.bomberman.entities.mobileobjects;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.image.Image;
import javafx.util.Duration;
import uet.oop.bomberman.BombermanGame;
import uet.oop.bomberman.ai.AIGod;
import uet.oop.bomberman.graphics.Sprite;
import uet.oop.bomberman.misc.Direction;
import uet.oop.bomberman.scenes.GameScene;
import uet.oop.bomberman.scenes.MainGameScene;

import java.util.*;

public class Balloon extends Mob {
    private int spriteIndex = 0;
    protected Map<String, Image[]> movingSpriteLists;
    protected Animation spriteChanger;

    public Balloon(GameScene scene, double x, double y) {
        super(scene, x, y, Sprite.balloom_right1.getFxImage());
        firstDeadSprite = Sprite.balloom_dead.getFxImage();
        baseSpeed = 0.03;
        setAIComponent(new AIGod(this, (MainGameScene)sceneContext));

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
                                    Duration.millis(200),
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
    public void destroy() {
        spriteChanger.stop();
        super.destroy();
        ((MainGameScene) sceneContext).addPoints(100);
    }

    public boolean moveRight() {
        if (facingDirection != Direction.EAST) {
            setFacingDirection(Direction.EAST);
            spriteIndex = 0;
            setCurrentImg(movingSpriteLists.get(Direction.EAST.toString())[0]);
        }
        if (spriteChanger.getCurrentRate() == 0.0) {
            spriteChanger.play();
        }

        gridX += baseSpeed * BombermanGame.getGameSpeed();
        if (!movable()) {
            gridX -= baseSpeed * BombermanGame.getGameSpeed();
            return false;
        } else {
            return true;
        }
    }

    public boolean moveLeft() {
        if (facingDirection != Direction.WEST) {
            setFacingDirection(Direction.WEST);
            spriteIndex = 0;
            setCurrentImg(movingSpriteLists.get(Direction.WEST.toString())[0]);
        }
        if (spriteChanger.getCurrentRate() == 0.0) {
            spriteChanger.play();
        }

        gridX -= baseSpeed * BombermanGame.getGameSpeed();
        if (!movable()) {
            gridX += baseSpeed * BombermanGame.getGameSpeed();
            return false;
        } else {
            return true;
        }
    }

    public boolean moveUp() {
        if (facingDirection != Direction.NORTH) {
            setFacingDirection(Direction.NORTH);
            spriteIndex = 0;
            setCurrentImg(movingSpriteLists.get(Direction.NORTH.toString())[0]);
        }
        if (spriteChanger.getCurrentRate() == 0.0) {
            spriteChanger.play();
        }

        gridY -= baseSpeed * BombermanGame.getGameSpeed();
        if (!movable()) {
            gridY += baseSpeed * BombermanGame.getGameSpeed();
            return false;
        } else {
            return true;
        }
    }

    public boolean moveDown() {
        if (facingDirection != Direction.SOUTH) {
            setFacingDirection(Direction.SOUTH);
            spriteIndex = 0;
            setCurrentImg(movingSpriteLists.get(Direction.SOUTH.toString())[0]);
        }
        if (spriteChanger.getCurrentRate() == 0.0) {
            spriteChanger.play();
        }

        gridY += baseSpeed * BombermanGame.getGameSpeed();
        if (!movable()) {
            gridY -= baseSpeed * BombermanGame.getGameSpeed();
            return false;
        } else {
            return true;
        }
    }
}
