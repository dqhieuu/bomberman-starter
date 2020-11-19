package uet.oop.bomberman.entities.mobileobjects;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.image.Image;
import javafx.util.Duration;
import uet.oop.bomberman.BombermanGame;
import uet.oop.bomberman.entities.stillobjects.Bomb;
import uet.oop.bomberman.entities.UserControlledObject;
import uet.oop.bomberman.graphics.Sprite;
import uet.oop.bomberman.misc.Direction;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Bomber extends UserControlledObject {
  private static Map<String, Image[]> spriteLists;
  private final Animation spriteChanger;

  protected int numberOfBombs;
  protected int bombsPlanted;
  protected int explodeRange;
  protected ArrayList<Bomb> bombs = new ArrayList<>();
  private double baseSpeed = 0.04;

  private int spriteIndex;

  public Bomber(double x, double y) {
    super(x, y, Sprite.player_right.getFxImage());
    if (spriteLists == null) {
      spriteLists = new HashMap<>();
      spriteLists.put(
          Direction.WEST.toString(),
          new Image[] {
            Sprite.player_left.getFxImage(),
            Sprite.player_left_1.getFxImage(),
            Sprite.player_left_2.getFxImage(),
          });
      spriteLists.put(
          Direction.EAST.toString(),
          new Image[] {
            Sprite.player_right.getFxImage(),
            Sprite.player_right_1.getFxImage(),
            Sprite.player_right_2.getFxImage(),
          });
      spriteLists.put(
          Direction.NORTH.toString(),
          new Image[] {
            Sprite.player_up.getFxImage(),
            Sprite.player_up_1.getFxImage(),
            Sprite.player_up_2.getFxImage(),
          });
      spriteLists.put(
          Direction.SOUTH.toString(),
          new Image[] {
            Sprite.player_down.getFxImage(),
            Sprite.player_down_1.getFxImage(),
            Sprite.player_down_2.getFxImage(),
          });
    }

    spriteChanger =
        new Timeline(
            new KeyFrame(
                Duration.millis(100),
                e -> {
                  if (facingDirection == Direction.EAST) {
                    spriteIndex =
                        (spriteIndex + 1) % spriteLists.get(Direction.EAST.toString()).length;
                    setCurrentImg(spriteLists.get(Direction.EAST.toString())[spriteIndex]);
                  } else if (facingDirection == Direction.WEST) {
                    spriteIndex =
                        (spriteIndex + 1) % spriteLists.get(Direction.WEST.toString()).length;
                    setCurrentImg(spriteLists.get(Direction.WEST.toString())[spriteIndex]);
                  } else if (facingDirection == Direction.NORTH) {
                    spriteIndex =
                        (spriteIndex + 1) % spriteLists.get(Direction.NORTH.toString()).length;
                    setCurrentImg(spriteLists.get(Direction.NORTH.toString())[spriteIndex]);
                  } else {
                    spriteIndex =
                        (spriteIndex + 1) % spriteLists.get(Direction.SOUTH.toString()).length;
                    setCurrentImg(spriteLists.get(Direction.SOUTH.toString())[spriteIndex]);
                  }
                }));
    spriteChanger.setCycleCount(Animation.INDEFINITE);

    explodeRange = 3;
    numberOfBombs = 1;
    bombsPlanted = 0;
  }


  @Override
  public void update() {
    if (bombs.size() > 0) {
      for (Bomb bomb : bombs) {
        bomb.update();
      }
    }
    updatePosition();
    this.render(BombermanGame.gc);
  }

  private void setNumberOfBombs(int numberOfBombs) {
    this.numberOfBombs = numberOfBombs;
  }

  private int getNumberOfBombs() {
    return numberOfBombs;
  }

  public void setExplodeRange(int explodeRange) {
    this.explodeRange = explodeRange;
  }

  public int getExplodeRange() {
    return explodeRange;
  }

  public ArrayList<Bomb> getBombs() {
    return bombs;
  }

  public void setBombs() {
    if (numberOfBombs > bombs.size()) {
      for (int i = 0; i < numberOfBombs - bombs.size(); i++) {
        Bomb newBomb = new Bomb(0, 0);
        bombs.add(newBomb);
      }
    }
  }

  public Bomb searchBomb() {
    for (Bomb bomb : bombs) {
      bomb.setAttribute();
      return bomb;
    }
    return null;
  }

  public double getBombX() {
    return Math.round(gridX);
  }

  public double getBombY() {
    return Math.round(gridY);
  }

  public void moveLeft() {
    if (facingDirection != Direction.WEST) {
      setFacingDirection(Direction.WEST);
      spriteIndex = 0;
      setCurrentImg(spriteLists.get(Direction.WEST.toString())[0]);
    }
    if (spriteChanger.getCurrentRate() == 0.0) {
      spriteChanger.play();
    }
    gridX -= baseSpeed;
    if (!movable()) {
      gridX += baseSpeed;
    }
  }

  public void moveRight() {
    if (facingDirection != Direction.EAST) {
      setFacingDirection(Direction.EAST);
      spriteIndex = 0;
      setCurrentImg(spriteLists.get(Direction.EAST.toString())[0]);
    }
    if (spriteChanger.getCurrentRate() == 0.0) {
      spriteChanger.play();
    }
    gridX += baseSpeed;
    if (!movable()) {
      gridX -= baseSpeed;
    }
  }

  public void moveUp() {
    if (facingDirection != Direction.NORTH) {
      setFacingDirection(Direction.NORTH);
      spriteIndex = 0;
      setCurrentImg(spriteLists.get(Direction.NORTH.toString())[0]);
    }
    if (spriteChanger.getCurrentRate() == 0.0) {
      spriteChanger.play();
    }
    gridY -= baseSpeed;
    if (!movable()) {
      gridY += baseSpeed;
    }
  }

  public void moveDown() {
    if (facingDirection != Direction.SOUTH) {
      setFacingDirection(Direction.SOUTH);
      spriteIndex = 0;
      setCurrentImg(spriteLists.get(Direction.SOUTH.toString())[0]);
    }
    if (spriteChanger.getCurrentRate() == 0.0) {
      spriteChanger.play();
    }
    gridY += baseSpeed;
    if (!movable()) {
      gridY -= baseSpeed;
    }
  }

  /** Check if bomberman can move to the destined location. */
  public void updatePosition() {
    if (!movePad.left
        && !movePad.right
        && !movePad.up
        && !movePad.down
        && spriteChanger.getCurrentRate() != 0.0) {
      spriteChanger.stop();
    }
    if (movePad.left && !movePad.right) {
      moveLeft();
    } else if (movePad.right && !movePad.left) {
      moveRight();
    } else if (movePad.up && !movePad.down) {
      moveUp();
    } else if (movePad.down && !movePad.up) {
      moveDown();
    }
  }
}
