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
import uet.oop.bomberman.entities.mobileobjects.Bomber;
import uet.oop.bomberman.graphics.Sprite;
import uet.oop.bomberman.scenes.GameScene;
import uet.oop.bomberman.scenes.MainGameScene;
import uet.oop.bomberman.utils.GameMediaPlayer;

public class Bomb extends CollidableObject {
    protected Bomber bombermanContext;
    protected int blastRadius;

    protected int spriteIndex;
    protected boolean isAnimationReverse = false;

    private static final Image[] spriteList = {
            Sprite.bomb.getFxImage(), Sprite.bomb_1.getFxImage(), Sprite.bomb_2.getFxImage(),
    };

    public Bomb(GameScene scene, double x, double y, int blastRadius, Bomber bomber) {
        super(scene, x, y, Sprite.bomb.getFxImage());

        this.isSolid = true;

        this.blastRadius = blastRadius;
        bombermanContext = bomber;
        spriteIndex = 0;
        Animation spriteChanger =
                new Timeline(
                        new KeyFrame(
                                Duration.millis(300),
                                e -> {
                                    if (spriteList.length < 2) return;

                                    if (!isAnimationReverse) {
                                        if (spriteIndex == spriteList.length - 1) {
                                            spriteIndex--;
                                            isAnimationReverse = true;
                                        } else {
                                            spriteIndex++;
                                        }
                                    } else {
                                        if (spriteIndex == 0) {
                                            spriteIndex++;
                                            isAnimationReverse = false;
                                        } else {
                                            spriteIndex--;
                                        }
                                    }

                                    setCurrentImg(spriteList[spriteIndex]);
                                }));
        spriteChanger.setCycleCount(Animation.INDEFINITE);
        spriteChanger.play();
        Animation countdown =
                new Timeline(
                        new KeyFrame(
                                Duration.seconds(3),
                                e -> {
                                    spriteChanger.stop();
                                    destroy();
                                }));
        countdown.play();
    }

    @Override
    public void update() {
    }

    private boolean replaceTile(int curX, int curY, String flameType) {
        Entity objectToBeDestroyed = ((MainGameScene) sceneContext).getStillObjectAt(curX, curY);
        if (objectToBeDestroyed != null) {
            if (objectToBeDestroyed instanceof Grass) {
                Flame flame = new Flame(sceneContext, curX, curY, flameType);
                flame.setCamera(camera);
                ((MainGameScene) sceneContext).setStillObjectAt(flame, curX, curY);
                return true;
            } else {
                if ( !(objectToBeDestroyed instanceof Flame)) {
                    objectToBeDestroyed.destroy();
                }
            }
        }
        return false;
    }

    private void addFlameRight(int curX, int curY, int blastRadius) {
        String type;
        for (int i = 1; i < blastRadius; i++) {
            if (i == blastRadius - 1) {
                type = "right";
            } else {
                type = "horizontal";
            }
            if (!replaceTile(curX + i, curY, type)) {
                return;
            }
        }
    }

    private void addFlameLeft(int curX, int curY, int blastRadius) {
        String type;
        for (int i = 1; i < blastRadius; i++) {
            if (i == blastRadius - 1) {
                type = "left";
            } else {
                type = "horizontal";
            }
            if (!replaceTile(curX - i, curY, type)) {
                return;
            }
        }
    }

    private void addFlameUp(int curX, int curY, int blastRadius) {
        String type;
        for (int i = 1; i < blastRadius; i++) {
            if (i == blastRadius - 1) {
                type = "top";
            } else {
                type = "vertical";
            }
            if (!replaceTile(curX, curY - i, type)) {
                return;
            }
        }
    }

    private void addFlameDown(int curX, int curY, int blastRadius) {
        String type;
        for (int i = 1; i < blastRadius; i++) {
            if (i == blastRadius - 1) {
                type = "bottom";
            } else {
                type = "vertical";
            }
            if (!replaceTile(curX, curY + i, type)) {
                return;
            }
        }
    }

    public void setTilesOnFire() {
        int intX = (int) gridX;
        int intY = (int) gridY;
        if (blastRadius >= 1) {
            Flame flame = new Flame(sceneContext, gridX, gridY, "center");
            flame.setCamera(camera);
            ((MainGameScene) sceneContext).setStillObjectAt(flame, intX, intY);

            if (blastRadius > 1) {
                addFlameRight(intX, intY, blastRadius);
                addFlameLeft(intX, intY, blastRadius);
                addFlameUp(intX, intY, blastRadius);
                addFlameDown(intX, intY, blastRadius);
            }
        }
    }

    public int getBlastRadius() {
        return blastRadius;
    }

    @Override
    public void destroy() {
        if (!isDestroyed()) {
            GameMediaPlayer.explosion.play();
            super.destroy();
            bombermanContext.retrieveBomb();
            setTilesOnFire();
        }
    }

    @Override
    public void render(GraphicsContext gc) {
        if (exists && currentImg != null) {
            if (camera != null) {
                gc.drawImage(
                        Sprite.grass.getFxImage(),
                        getRealX() - camera.getX(),
                        getRealY() - camera.getY() + BombermanGame.CANVAS_OFFSET_Y);
                gc.drawImage(
                        currentImg,
                        getRealX() - camera.getX(),
                        getRealY() - camera.getY() + BombermanGame.CANVAS_OFFSET_Y);
            } else {
                gc.drawImage(
                        Sprite.grass.getFxImage(), getRealX(), getRealY() + BombermanGame.CANVAS_OFFSET_Y);
                gc.drawImage(currentImg, getRealX(), getRealY() + BombermanGame.CANVAS_OFFSET_Y);
            }
        }
    }
}
