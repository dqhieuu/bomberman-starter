package uet.oop.bomberman;

import com.sun.javafx.perf.PerformanceTracker;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import uet.oop.bomberman.entities.*;
import uet.oop.bomberman.entities.MobileObjects.Bomber;
import uet.oop.bomberman.entities.StillObjects.Bomb;
import uet.oop.bomberman.entities.StillObjects.Grass;
import uet.oop.bomberman.graphics.Maps;
import uet.oop.bomberman.graphics.Sprite;
import uet.oop.bomberman.misc.AnimationCLock;
import uet.oop.bomberman.misc.Direction;

import java.util.Stack;
import java.util.Timer;
import java.util.TimerTask;

public class BombermanGame extends Application {
    public static final Maps allMaps = new Maps("res/levels/Level1.txt");
    public static final int WIDTH = allMaps.getMAP_WIDTH();
    public static final int HEIGHT = allMaps.getMAP_HEIGHT();

    public static GraphicsContext gc;
    public static Canvas canvas;

    private static PerformanceTracker tracker;

    private static int frameCount = 0;

    private static long prevTimestamp = -1;

    private static long curTimestamp;

    public static int level = 1;

    public static Bomber bomberman = new Bomber(1, 1, Sprite.player_right.getFxImage());

    public static void main(String[] args) {
        AnimationCLock newClock = new AnimationCLock(bomberman);
        Thread newThread = new Thread(newClock);
        newThread.start();
        Application.launch(BombermanGame.class);
        newClock.stop();
    }

    @Override
    public void start(Stage stage) {
        allMaps.getMaps().get(level - 1).getEntities().add(bomberman);

        // Tao Canvas
        canvas = new Canvas(Sprite.SCALED_SIZE * WIDTH, Sprite.SCALED_SIZE * HEIGHT);
        gc = canvas.getGraphicsContext2D();

        // Tao root container
        Group root = new Group();
        root.getChildren().add(canvas);

        // Tao scene
        Scene scene = new Scene(root);
        Stack<KeyCode> keysHolding = new Stack<>();
        scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
            /**
             * Don't invest much in the SPACE case, it's still in progress.
             *
             * @param keyEvent
             */

            @Override
            public void handle(KeyEvent keyEvent) {
                double deltaT = getSecondsPassed();
//                if(keysHolding.size() > 0 && keysHolding.contains(keyEvent.getCode())) {
//                    return;
//                }
                //System.out.println(keyEvent);
                switch (keyEvent.getCode()) {
                    case LEFT:
                        bomberman.setFacingDirection(Direction.WEST);
                        bomberman.setVelX(-0.04); //alt speed: -2.5*getSecondsPassed();
                        bomberman.setImg(Sprite.animation(
                                Sprite.player_left,
                                Sprite.player_left_1,
                                Sprite.player_left_2,
                                bomberman.getAnimationTracker()).getFxImage());
//                        keysHolding.push(KeyCode.LEFT);
                        break;
                    case RIGHT:
                        bomberman.setFacingDirection(Direction.EAST);
                        bomberman.setVelX(0.04);
                        bomberman.setImg(Sprite.animation(
                                Sprite.player_right,
                                Sprite.player_right_1,
                                Sprite.player_right_2,
                                bomberman.getAnimationTracker()).getFxImage());
//                        keysHolding.push(KeyCode.RIGHT);
                        break;
                    case UP:
                        bomberman.setFacingDirection(Direction.NORTH);
                        bomberman.setVelY(-0.04);
                        bomberman.setImg(Sprite.animation(
                                Sprite.player_up,
                                Sprite.player_up_1,
                                Sprite.player_up_2,
                                bomberman.getAnimationTracker()).getFxImage());
//                        keysHolding.push(KeyCode.UP);
                        break;
                    case DOWN:
                        bomberman.setFacingDirection(Direction.SOUTH);
                        bomberman.setVelY(0.04);
                        bomberman.setImg(Sprite.animation(
                                Sprite.player_down,
                                Sprite.player_down_1,
                                Sprite.player_down_2,
                                bomberman.getAnimationTracker()).getFxImage());
//                        keysHolding.push(KeyCode.DOWN);
                        break;
                    case SPACE:
                        Bomb bomb = bomberman.searchBomb();
                        if(bomb != null){
                            bomb.detonate();
                        }
                        break;
                }
            }
        });

        scene.setOnKeyReleased(keyEvent -> {
            switch (keyEvent.getCode()) {
                case LEFT:
                case RIGHT:
                case UP:
                case DOWN:
                    bomberman.setVelX(0);
                    bomberman.setVelY(0);
//                    if(keysHolding.size() >0 ) {
//                        keysHolding.pop();
//                    }
//                    if (keysHolding.size() > 0) {
//                        KeyCode previousHoldingKey = keysHolding.pop();
//                        Event.fireEvent(scene, new KeyEvent(KeyEvent.KEY_PRESSED, "", "",
//                                previousHoldingKey,
//                                true, true, true, true));
//                    }
                    break;
            }
        });

        // Them scene vao stage
        stage.setScene(scene);
        stage.setTitle("Bomberman");
        stage.show();

        tracker = PerformanceTracker.getSceneTracker(scene);

        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long l) {
                if (prevTimestamp < 0) {
                    prevTimestamp = curTimestamp = l;
                }
                allMaps.drawLevel(0, gc);
                update();
                updateAndPrintFps();
                prevTimestamp = curTimestamp;
                curTimestamp = l;
            }
        };
        timer.start();

        allMaps.createLevel(level - 1);
    }

    public void updateAndPrintFps() {
        if (frameCount >= 100) {
            double fps = tracker.getAverageFPS();
            System.out.printf("Current fps: %f\n", fps);
            System.out.printf("%f, %f%n", bomberman.getX(), bomberman.getY());
            tracker.resetAverageFPS();
            frameCount = 0;
        } else {
            frameCount++;
        }
    }

    public static double getSecondsPassed() {
        return ((double) (curTimestamp - prevTimestamp)) / 1E9;
    }

    public void update() {
        allMaps.getMaps().get(level - 1).getEntities().forEach(Entity::update);
    }

    public static CollidableObject getNextStillObjects(double x, double y) {
        return allMaps.getMaps().get(level - 1).getStillObjects().get((int) y * WIDTH + (int) x);
    }
}
