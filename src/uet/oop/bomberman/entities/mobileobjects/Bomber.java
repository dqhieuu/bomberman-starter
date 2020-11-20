package uet.oop.bomberman.entities.mobileobjects;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.util.Duration;
import uet.oop.bomberman.BombermanGame;
import uet.oop.bomberman.entities.stillobjects.Bomb;
import uet.oop.bomberman.entities.UserControlledObject;
import uet.oop.bomberman.graphics.Sprite;
import uet.oop.bomberman.misc.Direction;
import uet.oop.bomberman.misc.MovePad;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Bomber extends UserControlledObject {
  private static Map<String, Image[]> spriteLists;
  private final Animation spriteChanger;
  private int spriteIndex;

  protected int bombsCanPlant;
  protected int bombsBlastRadius;

  protected int bombsPlanted;

  protected ArrayList<Bomb> bombs = new ArrayList<>();
  private double baseSpeed = 0.04;

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
                  spriteIndex =
                      (spriteIndex + 1) % spriteLists.get(facingDirection.toString()).length;
                  setCurrentImg(spriteLists.get(facingDirection.toString())[spriteIndex]);
                }));
    spriteChanger.setCycleCount(Animation.INDEFINITE);

    bombsBlastRadius = 3;
    bombsCanPlant = 1;
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

  private void setBombsCanPlant(int bombsCanPlant) {
    this.bombsCanPlant = bombsCanPlant;
  }

  private int getBombsCanPlant() {
    return bombsCanPlant;
  }

  public void setBombsBlastRadius(int bombsBlastRadius) {
    this.bombsBlastRadius = bombsBlastRadius;
  }

  public int getBombsBlastRadius() {
    return bombsBlastRadius;
  }

  public ArrayList<Bomb> getBombs() {
    return bombs;
  }

  public void setBombs() {
    if (bombsCanPlant > bombs.size()) {
      for (int i = 0; i < bombsCanPlant - bombs.size(); i++) {
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

  public void bindInput() {
    BombermanGame.primaryStage
        .getScene()
        .setOnKeyPressed(
            keyEvent -> {
              KeyCode key = keyEvent.getCode();
              switch (key) {
                case LEFT:
                case A:
                  movePad.left = true;
                  break;
                case RIGHT:
                case D:
                  movePad.right = true;
                  break;
                case UP:
                case W:
                  movePad.up = true;
                  break;
                case DOWN:
                case S:
                  movePad.down = true;
                  break;
                case SPACE:
                  Bomb bomb = searchBomb();
                  if (bomb != null) {
                    bomb.detonate();
                  }
                  break;
              }
            });

    BombermanGame.primaryStage
        .getScene()
        .setOnKeyReleased(
            keyEvent -> {
              KeyCode key = keyEvent.getCode();
              switch (key) {
                case LEFT:
                case A:
                  movePad.left = false;
                  break;
                case RIGHT:
                case D:
                  movePad.right = false;
                  break;
                case UP:
                case W:
                  movePad.up = false;
                  break;
                case DOWN:
                case S:
                  movePad.down = false;
                  break;
              }
            });
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

  public void increaseBaseSpeed() {
    if (baseSpeed <= 0.06) {
      baseSpeed += 0.005;
    }
  }

  public void increaseNumberOfBombs() {
    bombsCanPlant++;
  }

  public void increaseBombsBlastRadius() {
    bombsBlastRadius++;
  }
}
