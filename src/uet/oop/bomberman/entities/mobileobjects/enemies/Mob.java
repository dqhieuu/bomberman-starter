package uet.oop.bomberman.entities.mobileobjects.enemies;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.image.Image;
import javafx.util.Duration;
import uet.oop.bomberman.entities.AIControlledObject;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.entities.stillobjects.Flame;
import uet.oop.bomberman.graphics.Sprite;
import uet.oop.bomberman.misc.Direction;
import uet.oop.bomberman.scenes.MainGameScene;

import java.util.Map;

public abstract class Mob extends AIControlledObject {
    private static final Image[] deadSprites = {
            Sprite.mob_dead1.getFxImage(), Sprite.mob_dead2.getFxImage(), Sprite.mob_dead3.getFxImage()
    };

    private Image firstDeadSprite;

    private int deadSpriteIndex;

    private final Animation spriteChanger;

    private int spriteIndex = 0;
    private Map<String, Image[]> movingSpriteLists;

    public Mob(MainGameScene scene, double x, double y, Image img) {
        super(scene, x, y, img);
        spriteChanger =
                new Timeline(
                        new KeyFrame(
                                Duration.millis(200),
                                e -> {
                                    if (!isDestroyed()) {
                                        spriteIndex =
                                                (spriteIndex + 1)
                                                        % movingSpriteLists.get(facingDirection.toString()).length;
                                        setCurrentImg(movingSpriteLists.get(facingDirection.toString())[spriteIndex]);
                                    }
                                }));
        sceneContext.addObservableAnimation(spriteChanger);
        spriteChanger.setCycleCount(Animation.INDEFINITE);

    }

    public void playAnimation() {
        if (spriteChanger.getCurrentRate() == 0.0) {
            spriteChanger.play();
        }
    }

    public void stopAnimation() {
        spriteChanger.stop();
    }

    public void setMovingSpriteLists(Map<String, Image[]> movingSpriteLists) {
        this.movingSpriteLists = movingSpriteLists;
        playAnimation();
    }

    public void setFirstDeadSprite(Image firstDeadSprite) {
        this.firstDeadSprite = firstDeadSprite;
    }

    public boolean moveRight() {
        boolean res = super.moveRight();

        if (facingDirection != Direction.EAST) {
            setFacingDirection(Direction.EAST);
            spriteIndex = 0;
            setCurrentImg(movingSpriteLists.get(Direction.EAST.toString())[0]);
        }
        playAnimation();

        return res;
    }

    public boolean moveLeft() {
        boolean res = super.moveLeft();

        if (facingDirection != Direction.WEST) {
            setFacingDirection(Direction.WEST);
            spriteIndex = 0;
            setCurrentImg(movingSpriteLists.get(Direction.WEST.toString())[0]);
        }
        playAnimation();

        return res;
    }

    public boolean moveUp() {
        boolean res = super.moveUp();

        if (facingDirection != Direction.NORTH) {
            setFacingDirection(Direction.NORTH);
            spriteIndex = 0;
            setCurrentImg(movingSpriteLists.get(Direction.NORTH.toString())[0]);
        }
        playAnimation();

        return res;
    }

    public boolean moveDown() {
        boolean res = super.moveDown();

        if (facingDirection != Direction.SOUTH) {
            setFacingDirection(Direction.SOUTH);
            spriteIndex = 0;
            setCurrentImg(movingSpriteLists.get(Direction.SOUTH.toString())[0]);
        }
        playAnimation();

        return res;
    }

    @Override
    public void destroy() {
        stopAnimation();
        isDead = true;
        deadSpriteIndex = -1;

        if (firstDeadSprite != null) {
            setCurrentImg(firstDeadSprite);
        } else {
            setCurrentImg(deadSprites[0]);
            deadSpriteIndex++;
        }

        Animation deadAnimation =
                new Timeline(
                        new KeyFrame(
                                Duration.millis(300),
                                e -> {
                                    deadSpriteIndex++;
                                    if (deadSpriteIndex < deadSprites.length) {
                                        setCurrentImg(deadSprites[deadSpriteIndex]);
                                    } else {
                                        super.destroy();
                                    }
                                }));
        sceneContext.addObservableAnimation(deadAnimation);
        deadAnimation.setCycleCount(deadSprites.length - deadSpriteIndex + 1);
        deadAnimation.play();
    }

    @Override
    public void update() {
        super.update();
        if (!isDead && !isDestroyed() && objectsAI != null) {
            objectsAI.update();

            Entity objectStandingOn =
                    ((MainGameScene) sceneContext)
                            .getStillObjectAt((int) Math.round(gridX), (int) Math.round(gridY));
            if (objectStandingOn instanceof Flame) {
                destroy();
            }
        }
    }
}
