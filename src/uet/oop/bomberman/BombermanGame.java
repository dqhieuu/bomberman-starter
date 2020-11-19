package uet.oop.bomberman;

import com.sun.javafx.perf.PerformanceTracker;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import uet.oop.bomberman.entities.*;
import uet.oop.bomberman.entities.mobileobjects.Bomber;
import uet.oop.bomberman.entities.stillobjects.Bomb;
import uet.oop.bomberman.scenes.MainGameScene;

import uet.oop.bomberman.utils.Camera;
import uet.oop.bomberman.misc.MovePad;

public class BombermanGame extends Application {
  public static final int CANVAS_OFFSET_Y = 64;
  public static final int CANVAS_WIDTH = 512;
  public static final int CANVAS_HEIGHT = 480;
  public static Font pixelFont;
  public static MainGameScene currentLevel = new MainGameScene("/levels/Level1.txt");
  public static int mapWidth = currentLevel.getGridWidth();
  public static int mapHeight = currentLevel.getGridHeight();

  public static MovePad pad = new MovePad();

  public static GraphicsContext gc;
  public static Canvas canvas;
  public static Camera camera;

  private static PerformanceTracker tracker;

  private static int frameCount = 0;

  public static Stage primaryStage;

  // TODO: đem bomber các thứ về class MainGameScene, xử lí logic nằm trong các scene

  public static Bomber bomberman = new Bomber(1, 1);

  public static void main(String[] args) {
    Application.launch(BombermanGame.class);
  }

  @Override
  public void init() {
    pixelFont = Font.loadFont(getClass().getResourceAsStream("/fonts/Pixel_NES.otf"), 24);
    camera =
        new Camera(
            CANVAS_WIDTH,
            CANVAS_HEIGHT - CANVAS_OFFSET_Y,
            currentLevel.getRealWidth(),
            currentLevel.getRealHeight());
  }

  @Override
  public void start(Stage stage) {
    BombermanGame.primaryStage = stage;
    camera.attachCamera(bomberman);
    currentLevel.getAnimateObjects().add(bomberman);
    currentLevel.addCamera();

    // Tao Canvas
    canvas = new Canvas(CANVAS_WIDTH, CANVAS_HEIGHT);
    gc = canvas.getGraphicsContext2D();
    gc.setFont(pixelFont);

    // Tao root container
    Group root = new Group();
    root.getChildren().add(canvas);

    // Tao scene
    Scene scene = new Scene(root);

    scene.setOnKeyPressed(
        keyEvent -> {
          KeyCode key = keyEvent.getCode();
          switch (key) {
            case LEFT:
            case A:
              pad.left = true;
              bomberman.setMovePad(pad);
              break;
            case RIGHT:
            case D:
              pad.right = true;
              bomberman.setMovePad(pad);
              break;
            case UP:
            case W:
              pad.up = true;
              bomberman.setMovePad(pad);
              break;
            case DOWN:
            case S:
              pad.down = true;
              bomberman.setMovePad(pad);
              break;
            case SPACE:
              Bomb bomb = bomberman.searchBomb();
              if (bomb != null) {
                bomb.detonate();
              }
              break;
          }
        });

    scene.setOnKeyReleased(
        keyEvent -> {
          KeyCode key = keyEvent.getCode();
          switch (key) {
            case LEFT:
            case A:
              pad.left = false;
              bomberman.setMovePad(pad);
              break;
            case RIGHT:
            case D:
              pad.right = false;
              bomberman.setMovePad(pad);
              break;
            case UP:
            case W:
              pad.up = false;
              bomberman.setMovePad(pad);
              break;
            case DOWN:
            case S:
              pad.down = false;
              bomberman.setMovePad(pad);
              break;
          }
        });

    // Them scene vao stage
    stage.setScene(scene);
    stage.setTitle("Bomberman");
    stage.setResizable(false);
    stage.show();

    // Do fps
    tracker = PerformanceTracker.getSceneTracker(scene);

    AnimationTimer timer =
        new AnimationTimer() {
          @Override
          public void handle(long l) {
            update();
            camera.updateCamera();
            currentLevel.render(gc);
            printFps();
          }
        };
    timer.start();
  }

  public void printFps() {
    if (frameCount >= 100) {
      double fps = tracker.getAverageFPS();
      System.out.printf("Current fps: %f\n", fps);
      //   System.out.printf("%f, %f%n", bomberman.getX(), bomberman.getY());
      tracker.resetAverageFPS();
      frameCount = 0;
    } else {
      frameCount++;
    }
  }

  public void update() {
    currentLevel.getAnimateObjects().forEach(Entity::update);
  }

  public static Entity getNextStillObjects(double x, double y) {
    return currentLevel.getStillObjects().get((int) y * mapWidth + (int) x);
  }
}
