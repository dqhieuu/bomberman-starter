package uet.oop.bomberman.entities.mobileobjects.enemies;

import javafx.scene.image.Image;
import uet.oop.bomberman.ai.AIGod;
import uet.oop.bomberman.graphics.Sprite;
import uet.oop.bomberman.misc.Direction;
import uet.oop.bomberman.scenes.MainGameScene;

import java.util.HashMap;
import java.util.Map;

public class Kondoria extends Mob {
    public Kondoria(MainGameScene scene, double x, double y) {
        super(scene, x, y, Sprite.kondoria_right1.getFxImage());
        setFirstDeadSprite(Sprite.kondoria_dead.getFxImage());
        Map<String, Image[]> sprites = new HashMap<>();
        sprites.put(
                Direction.WEST.toString(),
                new Image[]{
                        Sprite.kondoria_left1.getFxImage(),
                        Sprite.kondoria_left2.getFxImage(),
                        Sprite.kondoria_left3.getFxImage()
                });
        sprites.put(
                Direction.EAST.toString(),
                new Image[]{
                        Sprite.kondoria_right1.getFxImage(),
                        Sprite.kondoria_right2.getFxImage(),
                        Sprite.kondoria_right3.getFxImage()
                });
        sprites.put(
                Direction.SOUTH.toString(),
                new Image[]{
                        Sprite.kondoria_left1.getFxImage(),
                        Sprite.kondoria_left2.getFxImage(),
                        Sprite.kondoria_left3.getFxImage()
                });
        sprites.put(
                Direction.NORTH.toString(),
                new Image[]{
                        Sprite.kondoria_right1.getFxImage(),
                        Sprite.kondoria_right2.getFxImage(),
                        Sprite.kondoria_right3.getFxImage()
                });
        setMovingSpriteLists(sprites);

        baseSpeed = 0.04;
        setAIComponent(new AIGod(this, (MainGameScene) sceneContext));
    }

    @Override
    public void destroy() {
        super.destroy();
        ((MainGameScene) sceneContext).addPoints(500);
    }
}
