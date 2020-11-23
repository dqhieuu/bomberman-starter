package uet.oop.bomberman.scenes;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.util.Duration;
import uet.oop.bomberman.BombermanGame;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.entities.mobileobjects.Balloon;
import uet.oop.bomberman.entities.MovableObject;
import uet.oop.bomberman.entities.mobileobjects.Bomber;
import uet.oop.bomberman.entities.stillobjects.*;
import uet.oop.bomberman.entities.Text;
import uet.oop.bomberman.entities.stillobjects.powerups.PowerUpBomb;
import uet.oop.bomberman.entities.stillobjects.powerups.PowerUpFlame;
import uet.oop.bomberman.entities.stillobjects.powerups.PowerUpSpeed;
import uet.oop.bomberman.graphics.Sprite;
import uet.oop.bomberman.utils.Camera;
import uet.oop.bomberman.utils.GameMediaPlayer;
import uet.oop.bomberman.utils.GameVars;

import java.util.ArrayList;
import java.util.List;

public class MainGameScene implements GameScene {
  private final int gridWidth;
  private final int gridHeight;

  private final List<MovableObject> enemies;
  private final Entity[][] stillObjects;

  public static Camera camera;

  public static Bomber bomberman;

  private int timeLeft;

  private final Text textPoints;
  private final Text textTime;
  private final Text textLives;

  private boolean stageCompleted = false;

  public MainGameScene(int levelWidth, int levelHeight, List<String> mapData) {
    gridWidth = levelWidth;
    gridHeight = levelHeight;

    timeLeft = 200;

    stillObjects = new Entity[levelHeight][levelWidth];
    enemies = new ArrayList<>();

    processMapData(mapData);

    bomberman = new Bomber(this, 1, 1);

    textTime = new Text(this, 0.5, 1.5, true);
    textPoints = new Text(this, 8, 1.5, true);
    textLives = new Text(this, 11.5, 1.5, true);

    textPoints.setText(String.valueOf(GameVars.playerPoints));
    textLives.setText(String.format("LEFT %d", GameVars.playerLives));
    textTime.setText(String.format("TIME %d", timeLeft));

    // decreases time by 1 every 1 second
    Animation countdownTimer =
        new Timeline(
            new KeyFrame(
                Duration.seconds(1),
                e -> {
                  if (!stageCompleted) {
                    if (timeLeft > 0) {
                      timeLeft--;
                    }
                    textTime.setText(String.format("TIME %d", timeLeft));
                  }
                }));
    countdownTimer.setCycleCount(Timeline.INDEFINITE);
    countdownTimer.play();

    GameMediaPlayer.playBackgroundMusic(GameMediaPlayer.STAGE_THEME, true);

    camera =
        new Camera(
            BombermanGame.CANVAS_WIDTH,
            BombermanGame.CANVAS_HEIGHT - BombermanGame.CANVAS_OFFSET_Y,
            getRealWidth(),
            getRealHeight());
    camera.attachCamera(bomberman);
    bomberman.setCamera(camera);
    enemies.forEach(e -> e.setCamera(camera));

    for (Entity[] arr : stillObjects) {
      for (Entity e : arr) {
        e.setCamera(camera);
      }
    }

    bomberman.bindInput(BombermanGame.primaryStage.getScene());
  }

  private void processMapData(List<String> mapData) {
    for (int i = 0; i < gridHeight; i++) {
      String texture = mapData.get(i);
      for (int j = 0; j < gridWidth; j++) {
        BrickWall tempBrick;

        switch (texture.charAt(j)) {
          case '#':
            stillObjects[i][j] = new Wall(this, j, i);
            break;
          case 'b':
            tempBrick = new BrickWall(this, j, i);
            tempBrick.addObjectUnderneath(new PowerUpBomb(this, j, i));
            stillObjects[i][j] = tempBrick;
            break;
          case 'f':
            tempBrick = new BrickWall(this, j, i);
            tempBrick.addObjectUnderneath(new PowerUpFlame(this, j, i));
            stillObjects[i][j] = tempBrick;
            break;
          case 's':
            tempBrick = new BrickWall(this, j, i);
            tempBrick.addObjectUnderneath(new PowerUpSpeed(this, j, i));
            stillObjects[i][j] = tempBrick;
            break;
          case 'x':
            tempBrick = new BrickWall(this, j, i);
            tempBrick.addObjectUnderneath(new Portal(this, j, i));
            stillObjects[i][j] = tempBrick;
            break;
          case '1':
            enemies.add(new Balloon(this, j, i));
            stillObjects[i][j] = new Grass(this, j, i);
            break;
          case '*':
            stillObjects[i][j] = new BrickWall(this, j, i);
            break;
            //          case 'p':
            //            bomberman = new Bomber(this, j, i);
            //            break;
          default:
            stillObjects[i][j] = new Grass(this, j, i);
            break;
        }
      }
    }
  }

  public int getRealWidth() {
    return gridWidth * Sprite.SCALED_SIZE;
  }

  public int getRealHeight() {
    return gridHeight * Sprite.SCALED_SIZE;
  }

  public Entity getStillObjectAt(int x, int y) {
    if (x < 0 || y < 0 || x >= stillObjects[0].length || y >= stillObjects.length) {
      return null;
    }
    return stillObjects[y][x];
  }

  public void setStillObjectAt(Entity object, int x, int y) {
    if (x < 0
        || y < 0
        || x >= stillObjects[0].length
        || y >= stillObjects.length
        || object == null) {
      return;
    }
    stillObjects[y][x] = object;
  }

  public void addPoints(int points) {
    GameVars.playerPoints += points;
    textPoints.setText(String.valueOf(GameVars.playerPoints));
  }

  public List<MovableObject> getEnemies() {
    return enemies;
  }

  public void setStageCompleted() {
    stageCompleted = true;
    GameMediaPlayer.playBackgroundMusic(GameMediaPlayer.STAGE_COMPLETE, false);
    Animation countdownTimer =
        new Timeline(
            new KeyFrame(
                Duration.seconds(3),
                e -> {
                  BombermanGame.currentGameScene =
                      new IntermissionScene(IntermissionScene.IntermissionType.NEXT_LEVEL);
                }));
    countdownTimer.play();
  }

  public boolean isStageCompleted() {
    return this.stageCompleted;
  }

  @Override
  public void update() {
    if (!stageCompleted) {
      if (!bomberman.isDestroyed() && timeLeft > 0) {
        for (int i = 0; i < gridHeight; i++) {
          for (int j = 0; j < gridWidth; j++) {
            if (stillObjects[i][j].isDestroyed()) {
              Entity grass = new Grass(this, j, i);
              grass.setCamera(camera);
              stillObjects[i][j] = grass;
            }
          }
        }
        if (enemies.size() > 0) {
          enemies.removeIf(Entity::isDestroyed);
          if (enemies.size() == 0) {
            GameMediaPlayer.portalOpen.play();
          }
        }

        enemies.forEach(Entity::update);
        bomberman.update();
        camera.updateCamera();
      } else {
        GameVars.playerLives--;
        if (GameVars.playerLives > 0) {
          BombermanGame.currentGameScene =
              new IntermissionScene(IntermissionScene.IntermissionType.REPLAY_LEVEL);
        } else {
          BombermanGame.currentGameScene =
              new IntermissionScene(IntermissionScene.IntermissionType.GAME_OVER);
        }
      }
    }
  }

  @Override
  public void render(GraphicsContext gc) {
    gc.setFill(Color.rgb(173, 173, 173));
    gc.fillRect(0, 0, BombermanGame.canvas.getWidth(), BombermanGame.canvas.getHeight());

    for (Entity[] arr : stillObjects) {
      for (Entity e : arr) {
        e.render(gc);
      }
    }

    enemies.forEach(g -> g.render(gc));
    bomberman.render(gc);
    //        System.out.printf("pos: %f %f\n", bomberman.getGridX(), bomberman.getGridY());
    textPoints.render(gc);
    textLives.render(gc);
    textTime.render(gc);
  }
}
