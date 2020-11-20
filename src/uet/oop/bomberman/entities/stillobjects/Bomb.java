package uet.oop.bomberman.entities.stillobjects;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;
import uet.oop.bomberman.BombermanGame;
import uet.oop.bomberman.entities.CollidableObject;
import uet.oop.bomberman.entities.mobileobjects.Bomber;
import uet.oop.bomberman.graphics.Sprite;

import java.util.List;

public class Bomb extends CollidableObject {
  protected Bomber bombermanContext;
  protected int blastRadius;
  
  protected Timeline timeline;
  protected boolean hasExploded;
  protected boolean exploding;
  protected int numberOfFlames;


  protected int explodeLimit;
  protected int explodeLimitNorth;
  protected int explodeLimitSouth;
  protected int explodeLimitEast;
  protected int explodeLimitWest;

  public Bomb(double x, double y, int blastRadius, Bomber bomber) {
    super(x, y, Sprite.bomb.getFxImage());
    this.blastRadius = blastRadius;
    bombermanContext = bomber;
    timeline =
        new Timeline(
            new KeyFrame(
                Duration.millis(200),
                e -> {
                  if (exploding) {
                    for (Explosion explosion : this.getExplodeAnimation()) {
                      explosion.setExplosionImage();
                    }
                    for (Explosion explosion : this.getExplodeAnimation()) {
                      explosion.updateAnimationIndex();
                    }
                    if (this.getExplodeAnimation().get(0).getAnimationIndex() == 4) {
                      this.setExploding(false);
                      this.getExplodeAnimation().clear();
                    }
                  }
                }));
    timeline.setCycleCount(4);
    exploding = false;
    numberOfFlames = 0;
  }

  public void setExploding(boolean exploding) {
    this.exploding = exploding;
  }

  public List<Explosion> getExplodeAnimation() {
    return BombermanGame.currentScene.getFlames();
  }

  public void setExplodeLimit(int explodeLimit) {
    this.explodeLimit = explodeLimit;
    this.explodeLimitEast = explodeLimit;
    this.explodeLimitNorth = explodeLimit;
    this.explodeLimitSouth = explodeLimit;
    this.explodeLimitWest = explodeLimit;
  }

  public void setAttribute() {
    this.setGridX(bombermanContext.getBombX());
    this.setGridY(bombermanContext.getBombY());
    this.setExplodeLimit(bombermanContext.getBombsBlastRadius());
    this.checkSurrounding();
    this.setExplodeAnimation();
  }

  @Override
  public void update() {
    if (exploding) {
      for (Explosion explosion : this.getExplodeAnimation()) {
        explosion.update();
      }
    }
    if (hasExploded) {
      timeline.play();
      hasExploded = false;
      exploding = true;
    } else if (!exploding) {
      this.render(BombermanGame.gc);
    }
  }

  /** Check the surrounding for walls in order to draw the required explosion sprite. */
  public void checkSurrounding() {
    int limit = BombermanGame.currentScene.getStillObjects().size();
    for (int i = -1; i >= -explodeLimit; i--) {
      int check = (int) gridY * BombermanGame.mapWidth + (int) (gridX + i);
      if (check <= limit && check >= 0) {
        if (BombermanGame.getNextStillObjects(gridX + i, gridY) instanceof Wall) {
          explodeLimitWest = Math.abs(i) - 1;
          break;
        }
      }
    }
    for (int i = -1; i >= -explodeLimit; i--) {
      int check = (int) (gridY + i) * BombermanGame.mapWidth + (int) gridX;
      if (check <= limit && check >= 0) {
        if (BombermanGame.getNextStillObjects(gridX, gridY + i) instanceof Wall) {
          explodeLimitNorth = Math.abs(i) - 1;
          break;
        }
      }
    }

    for (int i = 1; i <= explodeLimit; i++) {
      int check = (int) gridY * BombermanGame.mapWidth + (int) (gridX + i);
      if (check <= limit && check >= 0) {
        if (BombermanGame.getNextStillObjects(gridX + i, gridY) instanceof Wall) {
          explodeLimitEast = i - 1;
          break;
        }
      }
    }
    for (int i = 1; i <= explodeLimit; i++) {
      int check = (int) (gridY + i) * BombermanGame.mapWidth + (int) gridX;
      if (check <= limit && check >= 0) {
        if (BombermanGame.getNextStillObjects(gridX, gridY + i) instanceof Wall) {
          explodeLimitSouth = i - 1;
          break;
        }
      }
    }
  }

  /** Add all explosion sprite for later rendering. */
  public void setExplodeAnimation() {
    Explosion centerExplosion =
        new Explosion(
            gridX, gridY, Sprite.bomb_exploded, Sprite.bomb_exploded1, Sprite.bomb_exploded2);
    this.getExplodeAnimation().add(centerExplosion);
    numberOfFlames++;
    for (int i = 1; i <= explodeLimitWest; i++) {
      if (i == explodeLimitWest) {
        Explosion explosion =
            new Explosion(
                gridX - i,
                gridY,
                Sprite.explosion_horizontal_left_last,
                Sprite.explosion_horizontal_left_last1,
                Sprite.explosion_horizontal_left_last2);
        this.getExplodeAnimation().add(explosion);
        numberOfFlames++;
      } else {
        Explosion explosionLeft =
            new Explosion(
                gridX - i,
                gridY,
                Sprite.explosion_horizontal,
                Sprite.explosion_horizontal1,
                Sprite.explosion_horizontal2);
        this.getExplodeAnimation().add(explosionLeft);
        numberOfFlames++;
      }
    }
    for (int i = 1; i <= explodeLimitEast; i++) {
      if (i == explodeLimitEast) {
        Explosion explosion =
            new Explosion(
                gridX + i,
                gridY,
                Sprite.explosion_horizontal_right_last,
                Sprite.explosion_horizontal_right_last1,
                Sprite.explosion_horizontal_right_last2);
        this.getExplodeAnimation().add(explosion);
        numberOfFlames++;
      } else {
        Explosion explosionRight =
            new Explosion(
                gridX + i,
                gridY,
                Sprite.explosion_horizontal,
                Sprite.explosion_horizontal1,
                Sprite.explosion_horizontal2);
        this.getExplodeAnimation().add(explosionRight);
        numberOfFlames++;
      }
    }
    for (int i = 1; i <= explodeLimitNorth; i++) {
      if (i == explodeLimitNorth) {
        Explosion explosion =
            new Explosion(
                gridX,
                gridY - i,
                Sprite.explosion_vertical_top_last,
                Sprite.explosion_vertical_top_last1,
                Sprite.explosion_vertical_top_last2);
        this.getExplodeAnimation().add(explosion);
        numberOfFlames++;
      } else {
        Explosion explosionTop =
            new Explosion(
                gridX,
                gridY - i,
                Sprite.explosion_vertical,
                Sprite.explosion_vertical1,
                Sprite.explosion_vertical2);
        this.getExplodeAnimation().add(explosionTop);
        numberOfFlames++;
      }
    }
    for (int i = 1; i <= explodeLimitSouth; i++) {
      if (i == explodeLimitSouth) {
        Explosion explosion =
            new Explosion(
                gridX,
                gridY + i,
                Sprite.explosion_vertical_down_last,
                Sprite.explosion_vertical_down_last1,
                Sprite.explosion_vertical_down_last2);
        this.getExplodeAnimation().add(explosion);
        numberOfFlames++;
      } else {
        Explosion explosionDown =
            new Explosion(
                gridX,
                gridY + i,
                Sprite.explosion_vertical,
                Sprite.explosion_vertical1,
                Sprite.explosion_vertical2);
        this.getExplodeAnimation().add(explosionDown);
        numberOfFlames++;
      }
    }
  }

  public void detonate() {
    Timeline timeline = new Timeline(new KeyFrame(Duration.millis(5000), e -> hasExploded = true));
    timeline.play();
  }
}
