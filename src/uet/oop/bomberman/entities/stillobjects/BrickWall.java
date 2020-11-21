package uet.oop.bomberman.entities.stillobjects;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.util.Duration;
import uet.oop.bomberman.BombermanGame;
import uet.oop.bomberman.entities.CollidableObject;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.entities.stillobjects.powerups.PowerUp;
import uet.oop.bomberman.graphics.Sprite;
import uet.oop.bomberman.scenes.GameScene;
import uet.oop.bomberman.scenes.MainGameScene;

public class BrickWall extends CollidableObject {
    private static final Image[] destroyedBrickWall = {
            Sprite.brick_exploded.getFxImage(),
            Sprite.brick_exploded1.getFxImage(),
            Sprite.brick_exploded2.getFxImage(),
    };

    private int spriteIndex = 0;

    Entity objectUnderneath;

    public BrickWall(GameScene scene, double x, double y) {
        super(scene, x, y, Sprite.brick.getFxImage());
        isSolid = true;
    }

    public void addObjectUnderneath(Entity object) {
        objectUnderneath = object;
    }

    @Override
    public void update() {

    }

    @Override
    public void destroy() {
        setCurrentImg(destroyedBrickWall[0]);
        Animation spriteChanger =
                new Timeline(
                        new KeyFrame(
                                Duration.millis(100),
                                e -> {
                                    spriteIndex++;
                                    if (spriteIndex < destroyedBrickWall.length) {
                                        setCurrentImg(destroyedBrickWall[spriteIndex]);
                                    } else {
                                        super.destroy();
                                        if (objectUnderneath != null) {
                                            if(objectUnderneath instanceof PowerUp) {
                                                ((PowerUp) objectUnderneath).activateDespawnTimer();
                                            }
                                            objectUnderneath.setCamera(camera);
                                            ((MainGameScene) sceneContext).setStillObjectAt(objectUnderneath, (int) gridX, (int) gridY);
                                        }
                                    }
                                }));
        spriteChanger.setCycleCount(destroyedBrickWall.length);
        spriteChanger.play();
    }

    @Override
    public void render(GraphicsContext gc) {
        if (exists && currentImg != null) {
            if (camera != null) {
                if (objectUnderneath == null) {
                    gc.drawImage(
                            Sprite.grass.getFxImage(),
                            getRealX() - camera.getX(),
                            getRealY() - camera.getY() + BombermanGame.CANVAS_OFFSET_Y);
                } else {
                    gc.drawImage(
                            objectUnderneath.getCurrentImg(),
                            getRealX() - camera.getX(),
                            getRealY() - camera.getY() + BombermanGame.CANVAS_OFFSET_Y);
                }
                gc.drawImage(
                        currentImg,
                        getRealX() - camera.getX(),
                        getRealY() - camera.getY() + BombermanGame.CANVAS_OFFSET_Y);
            } else {
                gc.drawImage(Sprite.grass.getFxImage(), getRealX(), getRealY() + BombermanGame.CANVAS_OFFSET_Y);
                gc.drawImage(currentImg, getRealX(), getRealY() + BombermanGame.CANVAS_OFFSET_Y);
            }
        }
    }
}

