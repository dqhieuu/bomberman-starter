package uet.oop.bomberman.entities.mobileobjects.enemies;

import javafx.scene.image.Image;
import uet.oop.bomberman.ai.AIGod;
import uet.oop.bomberman.graphics.Sprite;
import uet.oop.bomberman.misc.Direction;
import uet.oop.bomberman.scenes.GameScene;
import uet.oop.bomberman.scenes.MainGameScene;

import java.util.HashMap;
import java.util.Map;

public class Oneal extends Mob {
  public Oneal(GameScene scene, double x, double y) {
    super(scene, x, y, Sprite.oneal_right1.getFxImage());
    setFirstDeadSprite(Sprite.oneal_dead.getFxImage());
    Map<String, Image[]> sprites = new HashMap<>();
    sprites.put(
        Direction.WEST.toString(),
        new Image[] {
          Sprite.oneal_left1.getFxImage(),
          Sprite.oneal_left2.getFxImage(),
          Sprite.oneal_left3.getFxImage()
        });
    sprites.put(
        Direction.EAST.toString(),
        new Image[] {
          Sprite.oneal_right1.getFxImage(),
          Sprite.oneal_right2.getFxImage(),
          Sprite.oneal_right3.getFxImage()
        });
    sprites.put(
        Direction.SOUTH.toString(),
        new Image[] {
          Sprite.oneal_left1.getFxImage(),
          Sprite.oneal_left2.getFxImage(),
          Sprite.oneal_left3.getFxImage()
        });
    sprites.put(
        Direction.NORTH.toString(),
        new Image[] {
          Sprite.oneal_right1.getFxImage(),
          Sprite.oneal_right2.getFxImage(),
          Sprite.oneal_right3.getFxImage()
        });
    setMovingSpriteLists(sprites);

    baseSpeed = 0.05;
    setAIComponent(new AIGod(this, (MainGameScene) sceneContext));
  }

  @Override
  public void destroy() {
    super.destroy();
    ((MainGameScene) sceneContext).addPoints(100);
  }
}
