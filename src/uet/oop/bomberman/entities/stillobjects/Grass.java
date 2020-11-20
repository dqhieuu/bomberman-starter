package uet.oop.bomberman.entities.stillobjects;

import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.graphics.Sprite;
import uet.oop.bomberman.scenes.GameScene;

public class Grass extends Entity {

  public Grass(GameScene scene, double x, double y) {
    super(scene, x, y, Sprite.grass.getFxImage());
  }

  @Override
  public void update() {}
}
