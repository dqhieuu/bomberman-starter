package uet.oop.bomberman.entities.mobileobjects;

import javafx.scene.image.Image;
import uet.oop.bomberman.entities.AIControlledObject;
import uet.oop.bomberman.scenes.GameScene;

public class Mob extends AIControlledObject {

  public Mob(GameScene scene, double x, double y, Image img) {
    super(scene, x, y, img);
  }
}
