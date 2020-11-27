package uet.oop.bomberman.entities.mobileobjects;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.image.Image;
import javafx.util.Duration;
import uet.oop.bomberman.ai.AIComponent;
import uet.oop.bomberman.entities.AIControlledObject;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.entities.stillobjects.Flame;
import uet.oop.bomberman.graphics.Sprite;
import uet.oop.bomberman.scenes.GameScene;
import uet.oop.bomberman.scenes.MainGameScene;

public abstract class Mob extends AIControlledObject {
  private static final Image[] deadSprites = {
    Sprite.mob_dead1.getFxImage(), Sprite.mob_dead2.getFxImage(), Sprite.mob_dead3.getFxImage()
  };

  protected Image firstDeadSprite;

  private int deadSpriteIndex;

  public Mob(GameScene scene, double x, double y, Image img) {
    super(scene, x, y, img);
  }

  @Override
  public void destroy() {
    isDead = true;
    deadSpriteIndex = -1;

    if (firstDeadSprite != null) {
      setCurrentImg(firstDeadSprite);
    } else {
      setCurrentImg(deadSprites[0]);
      deadSpriteIndex++;
    }

    Animation deadAnimation =
        new Timeline(
            new KeyFrame(
                Duration.millis(300),
                e -> {
                  deadSpriteIndex++;
                  if (deadSpriteIndex < deadSprites.length) {
                    setCurrentImg(deadSprites[deadSpriteIndex]);
                  } else {
                    super.destroy();
                  }
                }));
    deadAnimation.setCycleCount(deadSprites.length - deadSpriteIndex + 1);
    deadAnimation.play();
  }

  @Override
  public void update() {
    super.update();
    if (!isDead && !isDestroyed() && objectsAI != null) {
      objectsAI.update();

      Entity objectStandingOn =
          ((MainGameScene) sceneContext)
              .getStillObjectAt((int) Math.round(gridX), (int) Math.round(gridY));
      if (objectStandingOn instanceof Flame) {
        destroy();
      }
    }
  }
}
