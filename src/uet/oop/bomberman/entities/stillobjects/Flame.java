package uet.oop.bomberman.entities.stillobjects;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.util.Duration;
import uet.oop.bomberman.BombermanGame;
import uet.oop.bomberman.entities.CollidableObject;
import uet.oop.bomberman.graphics.Sprite;
import uet.oop.bomberman.scenes.GameScene;

import java.util.HashMap;
import java.util.Map;

public class Flame extends CollidableObject {
    protected String type;
    protected int spriteIndex;
    protected int spriteLength;
    protected boolean isAnimationReverse;

    private static Map<String, Image[]> spriteLists;

    public Flame(GameScene scene, double x, double y, String type) {
        super(scene, x, y, Sprite.bomb_exploded.getFxImage());

        this.type = type;

        if (spriteLists == null) {
            spriteLists = new HashMap<>();
            spriteLists.put(
                    "left",
                    new Image[]{
                            Sprite.explosion_horizontal_left_last.getFxImage(),
                            Sprite.explosion_horizontal_left_last1.getFxImage(),
                            Sprite.explosion_horizontal_left_last2.getFxImage(),
                    });
            spriteLists.put(
                    "right",
                    new Image[]{
                            Sprite.explosion_horizontal_right_last.getFxImage(),
                            Sprite.explosion_horizontal_right_last1.getFxImage(),
                            Sprite.explosion_horizontal_right_last2.getFxImage(),
                    });
            spriteLists.put(
                    "top",
                    new Image[]{
                            Sprite.explosion_vertical_top_last.getFxImage(),
                            Sprite.explosion_vertical_top_last1.getFxImage(),
                            Sprite.explosion_vertical_top_last2.getFxImage(),
                    });
            spriteLists.put(
                    "bottom",
                    new Image[]{
                            Sprite.explosion_vertical_down_last.getFxImage(),
                            Sprite.explosion_vertical_down_last1.getFxImage(),
                            Sprite.explosion_vertical_down_last2.getFxImage(),
                    });
            spriteLists.put(
                    "horizontal",
                    new Image[]{
                            Sprite.explosion_horizontal.getFxImage(),
                            Sprite.explosion_horizontal1.getFxImage(),
                            Sprite.explosion_horizontal2.getFxImage(),
                    });
            spriteLists.put(
                    "vertical",
                    new Image[]{
                            Sprite.explosion_vertical.getFxImage(),
                            Sprite.explosion_vertical1.getFxImage(),
                            Sprite.explosion_vertical2.getFxImage(),
                    });
            spriteLists.put(
                    "center",
                    new Image[]{
                            Sprite.bomb_exploded.getFxImage(),
                            Sprite.bomb_exploded1.getFxImage(),
                            Sprite.bomb_exploded2.getFxImage(),
                    });
        }

        spriteLength = spriteLists.get(type).length;

        setCurrentImg(spriteLists.get(type)[0]);

        spriteIndex = 0;
        Animation spriteChanger =
                new Timeline(
                        new KeyFrame(
                                Duration.millis(100),
                                e -> {
                                    if (spriteLists.get(type).length < 2) return;

                                    if (!isAnimationReverse) {
                                        if (spriteIndex == spriteLength - 1) {
                                            spriteIndex--;
                                            isAnimationReverse = true;
                                        } else {
                                            spriteIndex++;
                                        }
                                    } else {
                                        if (spriteIndex == -1) {
                                            destroy();
                                        } else {
                                            spriteIndex--;
                                        }
                                    }
                                    if (spriteIndex != -1) {
                                        setCurrentImg(spriteLists.get(type)[spriteIndex]);
                                    }
                                }));
        spriteChanger.setCycleCount(Animation.INDEFINITE);
        spriteChanger.play();
    }

    @Override
    public void update() {
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
                gc.drawImage(Sprite.grass.getFxImage(), getRealX(), getRealY() + BombermanGame.CANVAS_OFFSET_Y);
                gc.drawImage(currentImg, getRealX(), getRealY() + BombermanGame.CANVAS_OFFSET_Y);
            }
        }
    }
}
