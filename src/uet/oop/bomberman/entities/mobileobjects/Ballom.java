package uet.oop.bomberman.entities.mobileobjects;

import uet.oop.bomberman.entities.AIControlledObject;
import uet.oop.bomberman.graphics.Sprite;

public class Ballom extends AIControlledObject {

  public Ballom(double x, double y) {
    super(x, y, Sprite.balloom_left1.getFxImage());
  }

  @Override
  public void update() {

  }
}
