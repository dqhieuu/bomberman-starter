package uet.oop.bomberman.entities.mobileobjects;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;
import uet.oop.bomberman.entities.AIControlledObject;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.entities.stillobjects.Flame;
import uet.oop.bomberman.entities.stillobjects.powerups.PowerUp;
import uet.oop.bomberman.entities.stillobjects.powerups.PowerUpBomb;
import uet.oop.bomberman.entities.stillobjects.powerups.PowerUpFlame;
import uet.oop.bomberman.entities.stillobjects.powerups.PowerUpSpeed;
import uet.oop.bomberman.graphics.Sprite;
import uet.oop.bomberman.scenes.GameScene;
import uet.oop.bomberman.scenes.MainGameScene;

public class Ballom extends AIControlledObject {
  private int spriteIndex = 0;

  public Ballom(GameScene scene, double x, double y) {
    super(scene, x, y, Sprite.balloom_left1.getFxImage());
  }

  @Override
  public void update() {
    if (!isDestroyed()) {
      super.update();
      Entity objectStandingOn = ((MainGameScene) sceneContext)
              .getStillObjectAt((int) Math.round(gridX), (int) Math.round(gridY));
      if(objectStandingOn != null) {
        if(objectStandingOn instanceof Flame) {
          destroy();
        }
      }
    }
  }

  @Override
  public void destroy() {
//    setCurrentImg(Sprite.balloom_dead.getFxImage());
//    Animation spriteChanger =
//            new Timeline(
//                    new KeyFrame(
//                            Duration.millis(100),
//                            e -> {
//                              spriteIndex++;
//                              if (spriteIndex < destroyedBrickWall.length) {
//                                setCurrentImg(destroyedBrickWall[spriteIndex]);
//                              } else {
//                                exists = false;
//                                if (objectUnderneath != null) {
//                                  if(objectUnderneath instanceof PowerUp) {
//                                    ((PowerUp) objectUnderneath).activateDespawnTimer();
//                                  }
//                                  objectUnderneath.setCamera(camera);
//                                  ((MainGameScene) sceneContext).setStillObjectAt(objectUnderneath, (int) gridX, (int) gridY);
//                                }
//                              }
//                            }));
//    spriteChanger.setCycleCount(destroyedBrickWall.length);
//    spriteChanger.play();
    super.destroy();
    ((MainGameScene)sceneContext).addPoints(100);
  }
}
