package uet.oop.bomberman.scenes;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.util.Duration;
import uet.oop.bomberman.BombermanGame;
import uet.oop.bomberman.entities.CollidableObject;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.entities.mobileobjects.Ballom;
import uet.oop.bomberman.entities.MovableObject;
import uet.oop.bomberman.entities.mobileobjects.Bomber;
import uet.oop.bomberman.entities.stillobjects.Explosion;
import uet.oop.bomberman.entities.stillobjects.Grass;
import uet.oop.bomberman.entities.stillobjects.Wall;
import uet.oop.bomberman.entities.Text;
import uet.oop.bomberman.graphics.Sprite;
import uet.oop.bomberman.misc.ErrorDialog;
import uet.oop.bomberman.utils.Camera;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainGameScene implements Scene {
  private String level;
  private int gridWidth;
  private int gridHeight;
  private List<String> mapData;

  private List<MovableObject> animateObjects = new ArrayList<>();
  private List<Entity> stillObjects = new ArrayList<>();
  protected List<Explosion> flames = new ArrayList<>();

  public static Camera camera;

  public static Bomber bomberman = new Bomber(1, 1);

  private boolean[][] solidTiles;

  private int playerPoints;
  private int playerLives;
  private int timeLeft;

  private final Text textPoints;
  private final Text textTime;
  private final Text textLives;

  private Animation countdownTimer;

  public MainGameScene(String path) {
    playerPoints = 0;
    playerLives = 3;
    timeLeft = 200;

    textTime = new Text(0.5, 1.5, true);
    textPoints = new Text(8, 1.5, true);
    textLives = new Text(11.5, 1.5, true);

    textPoints.setText(String.valueOf(playerPoints));
    textLives.setText(String.format("LEFT %d", playerLives));
    textTime.setText(String.format("TIME %d", timeLeft));

    // decreases time by 1 every 1 second
    countdownTimer =
        new Timeline(
            new KeyFrame(
                Duration.seconds(1),
                e -> {
                  if (timeLeft > 0) {
                    timeLeft--;
                  }
                  textTime.setText(String.format("TIME %d", timeLeft));
                }));
    countdownTimer.setCycleCount(Timeline.INDEFINITE);
    countdownTimer.play();

    if (readData(path)) {
      loadData();
    } else {
      ErrorDialog.displayAndExit("Lỗi khi load cảnh", "Load cảnh chính lỗi.");
    }
    camera =
        new Camera(
            BombermanGame.CANVAS_WIDTH,
            BombermanGame.CANVAS_HEIGHT - BombermanGame.CANVAS_OFFSET_Y,
            getRealWidth(),
            getRealHeight());
    camera.attachCamera(bomberman);
  }

  public List<Explosion> getFlames() {
    return flames;
  }

  private boolean readData(String path) {
    try {
      BufferedReader br =
          new BufferedReader(new InputStreamReader(this.getClass().getResourceAsStream(path)));
      String firstLine;
      firstLine = br.readLine();
      Pattern pattern = Pattern.compile("(^.+)\\s+(\\d+)\\s+(\\d+$)");
      Matcher matcher = pattern.matcher(firstLine);

      if (matcher.find()) {

        level = matcher.group(1);
        gridHeight = Integer.parseInt(matcher.group(2));
        gridWidth = Integer.parseInt(matcher.group(3));
        String currentLine;
        List<String> mapStructure = new ArrayList<>();
        while ((currentLine = br.readLine()) != null) {
          if (currentLine.length() != 0) {
            if (currentLine.length() != gridWidth) {
              return false;
            }
            mapStructure.add(currentLine);
          }
        }

        if (mapStructure.size() == gridHeight) {
          this.mapData = mapStructure;
          return true;
        }
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
    return false;
  }

  private void loadData() {
    for (int i = 0; i < mapData.size(); i++) {
      String texture = mapData.get(i);
      for (int j = 0; j < texture.length(); j++) {
        switch (texture.charAt(j)) {
          case '#':
            CollidableObject wall = new Wall(j, i);
            //      solidTiles[j][i] = true;
            stillObjects.add(wall);
            break;
          case '1':
            MovableObject enemy = new Ballom(j, i);
            animateObjects.add(enemy);
            stillObjects.add(new Grass(j, i));
            break;
          default:
            Entity object = new Grass(j, i);
            stillObjects.add(object);
            break;
        }
      }
    }
  }

  public void addCamera() {
    for (Entity e : stillObjects) {
      e.setCamera(camera);
    }
    for (Entity e : animateObjects) {
      e.setCamera(camera);
    }
  }

  public int getGridWidth() {
    return gridWidth;
  }

  public int getGridHeight() {
    return gridHeight;
  }

  public int getRealWidth() {
    return gridWidth * Sprite.SCALED_SIZE;
  }

  public int getRealHeight() {
    return gridHeight * Sprite.SCALED_SIZE;
  }

  /** This is just for testing also. */
  public void testMap() {
    for (String texture : mapData) {
      System.out.println(texture);
    }
  }

  public void removeFlames(int numberOfFlames) {
    for (int i = 0; i < numberOfFlames; i++) {
      flames.remove(i);
    }
  }

  public List<MovableObject> getAnimateObjects() {
    return this.animateObjects;
  }

  public List<Entity> getStillObjects() {
    return this.stillObjects;
  }


  @Override
  public void update() {
    //        if(stillObjects.size()>0)
    //        stillObjects.removeIf(Entity::isDestroyed);
    //        if(animateObjects.size()>0)
    //        animateObjects.removeIf(Entity::isDestroyed);
    camera.updateCamera();
  }

  @Override
  public void render(GraphicsContext gc) {
    gc.setFill(Color.rgb(173, 173, 173));
    gc.fillRect(0, 0, BombermanGame.canvas.getWidth(), BombermanGame.canvas.getHeight());

    stillObjects.forEach(g -> g.render(gc));
    animateObjects.forEach(g -> g.render(gc));
    textPoints.render(gc);
    textLives.render(gc);
    textTime.render(gc);
  }
}
