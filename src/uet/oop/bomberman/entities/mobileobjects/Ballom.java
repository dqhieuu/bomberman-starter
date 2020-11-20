package uet.oop.bomberman.entities.mobileobjects;

import uet.oop.bomberman.entities.AIControlledObject;
import uet.oop.bomberman.graphics.Sprite;
import uet.oop.bomberman.scenes.GameScene;

public class Ballom extends AIControlledObject {

  public Ballom(GameScene scene, double x, double y) {
    super(scene, x, y, Sprite.balloom_left1.getFxImage());
  }

  @Override
  public void update() {

  }
}
