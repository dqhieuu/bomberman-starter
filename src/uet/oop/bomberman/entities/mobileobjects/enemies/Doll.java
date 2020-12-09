package uet.oop.bomberman.entities.mobileobjects.enemies;

import javafx.scene.image.Image;
import uet.oop.bomberman.ai.AIIntermediate;
import uet.oop.bomberman.ai.AINewbie;
import uet.oop.bomberman.graphics.Sprite;
import uet.oop.bomberman.misc.Direction;
import uet.oop.bomberman.scenes.GameScene;
import uet.oop.bomberman.scenes.MainGameScene;
import uet.oop.bomberman.utils.AlgorithmicProcessor;

import java.util.HashMap;
import java.util.Map;

public class Doll extends Mob {
    public Doll(GameScene scene, double x, double y) {
        super(scene, x, y, Sprite.doll_right1.getFxImage());
        setFirstDeadSprite(Sprite.doll_dead.getFxImage());
        Map<String, Image[]> sprites = new HashMap<>();
        sprites.put(
                Direction.WEST.toString(),
                new Image[]{
                        Sprite.doll_left1.getFxImage(),
                        Sprite.doll_left2.getFxImage(),
                        Sprite.doll_left3.getFxImage()
                });
        sprites.put(
                Direction.EAST.toString(),
                new Image[]{
                        Sprite.doll_right1.getFxImage(),
                        Sprite.doll_right2.getFxImage(),
                        Sprite.doll_right3.getFxImage()
                });
        sprites.put(
                Direction.SOUTH.toString(),
                new Image[]{
                        Sprite.doll_left1.getFxImage(),
                        Sprite.doll_left2.getFxImage(),
                        Sprite.doll_left3.getFxImage()
                });
        sprites.put(
                Direction.NORTH.toString(),
                new Image[]{
                        Sprite.doll_right1.getFxImage(),
                        Sprite.doll_right2.getFxImage(),
                        Sprite.doll_right3.getFxImage()
                });
        setMovingSpriteLists(sprites);

        baseSpeed = 0.04;
        setAIComponent(new AIIntermediate(this, (MainGameScene) sceneContext, 1));
    }

    @Override
    public void destroy() {
        super.destroy();
        ((MainGameScene) sceneContext).addPoints(200);
    }
}
