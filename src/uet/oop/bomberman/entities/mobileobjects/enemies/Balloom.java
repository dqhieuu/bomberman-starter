package uet.oop.bomberman.entities.mobileobjects.enemies;

import javafx.scene.image.Image;
import uet.oop.bomberman.ai.AINewbie;
import uet.oop.bomberman.graphics.Sprite;
import uet.oop.bomberman.misc.Direction;
import uet.oop.bomberman.scenes.MainGameScene;

import java.util.HashMap;
import java.util.Map;

public class Balloom extends Mob {
    public Balloom(MainGameScene scene, double x, double y) {
        super(scene, x, y, Sprite.balloom_right1.getFxImage());
        setFirstDeadSprite(Sprite.balloom_dead.getFxImage());
        Map<String, Image[]> sprites = new HashMap<>();
        sprites.put(
                Direction.WEST.toString(),
                new Image[]{
                        Sprite.balloom_left1.getFxImage(),
                        Sprite.balloom_left2.getFxImage(),
                        Sprite.balloom_left3.getFxImage()
                });
        sprites.put(
                Direction.EAST.toString(),
                new Image[]{
                        Sprite.balloom_right1.getFxImage(),
                        Sprite.balloom_right2.getFxImage(),
                        Sprite.balloom_right3.getFxImage()
                });
        sprites.put(
                Direction.SOUTH.toString(),
                new Image[]{
                        Sprite.balloom_left1.getFxImage(),
                        Sprite.balloom_left2.getFxImage(),
                        Sprite.balloom_left3.getFxImage()
                });
        sprites.put(
                Direction.NORTH.toString(),
                new Image[]{
                        Sprite.balloom_right1.getFxImage(),
                        Sprite.balloom_right2.getFxImage(),
                        Sprite.balloom_right3.getFxImage()
                });
        setMovingSpriteLists(sprites);

        baseSpeed = 0.03;
        setAIComponent(new AINewbie(this, (MainGameScene) sceneContext));
    }

    @Override
    public void destroy() {
        super.destroy();
        ((MainGameScene) sceneContext).addPoints(100);
    }
}
