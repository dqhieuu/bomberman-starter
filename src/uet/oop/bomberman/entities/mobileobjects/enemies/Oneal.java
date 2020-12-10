package uet.oop.bomberman.entities.mobileobjects.enemies;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.image.Image;
import javafx.util.Duration;
import uet.oop.bomberman.ai.AILowerIntermediate;
import uet.oop.bomberman.graphics.Sprite;
import uet.oop.bomberman.misc.Direction;
import uet.oop.bomberman.scenes.MainGameScene;

import java.util.HashMap;
import java.util.Map;

public class Oneal extends Mob {
    public Oneal(MainGameScene scene, double x, double y) {
        super(scene, x, y, Sprite.oneal_right1.getFxImage());
        setFirstDeadSprite(Sprite.oneal_dead.getFxImage());
        Map<String, Image[]> sprites = new HashMap<>();
        sprites.put(
                Direction.WEST.toString(),
                new Image[]{
                        Sprite.oneal_left1.getFxImage(),
                        Sprite.oneal_left2.getFxImage(),
                        Sprite.oneal_left3.getFxImage()
                });
        sprites.put(
                Direction.EAST.toString(),
                new Image[]{
                        Sprite.oneal_right1.getFxImage(),
                        Sprite.oneal_right2.getFxImage(),
                        Sprite.oneal_right3.getFxImage()
                });
        sprites.put(
                Direction.SOUTH.toString(),
                new Image[]{
                        Sprite.oneal_left1.getFxImage(),
                        Sprite.oneal_left2.getFxImage(),
                        Sprite.oneal_left3.getFxImage()
                });
        sprites.put(
                Direction.NORTH.toString(),
                new Image[]{
                        Sprite.oneal_right1.getFxImage(),
                        Sprite.oneal_right2.getFxImage(),
                        Sprite.oneal_right3.getFxImage()
                });
        setMovingSpriteLists(sprites);

        baseSpeed = 0.03;
        setAIComponent(new AILowerIntermediate(this, (MainGameScene) sceneContext));

        Timeline speedChanger =
                new Timeline(
                        new KeyFrame(
                                Duration.millis(2000),
                                e -> baseSpeed = 0.03 + 0.02 * Math.random()));
        sceneContext.addObservableAnimation(speedChanger);
        speedChanger.setCycleCount(Animation.INDEFINITE);
        speedChanger.play();
    }

    @Override
    public void destroy() {
        super.destroy();
        ((MainGameScene) sceneContext).addPoints(200);
    }
}
