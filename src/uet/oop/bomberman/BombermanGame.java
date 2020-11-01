package uet.oop.bomberman;

import com.sun.javafx.perf.PerformanceTracker;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import uet.oop.bomberman.entities.*;
import uet.oop.bomberman.graphics.Maps;
import uet.oop.bomberman.graphics.Sprite;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class BombermanGame extends Application {
    public static final Maps allMaps = new Maps("res/levels/Level1.txt");
    public static final int WIDTH = allMaps.getMAP_WIDTH();
    public static final int HEIGHT = allMaps.getMAP_HEIGHT();

    public static GraphicsContext gc;
    public static Canvas canvas;

    private static PerformanceTracker tracker;

    private static int frameCount = 0;

    public static void main(String[] args) {
        Application.launch(BombermanGame.class);
    }

    @Override
    public void start(Stage stage) {
        /*allMaps.printMap();*/

        Entity bomberman = new Bomber(1, 1, Sprite.player_right.getFxImage());
        Maps.maps.get(0).getEntities().add(bomberman);

        // Tao Canvas
        canvas = new Canvas(Sprite.SCALED_SIZE * WIDTH, Sprite.SCALED_SIZE * HEIGHT);
        gc = canvas.getGraphicsContext2D();

        // Tao root container
        Group root = new Group();
        root.getChildren().add(canvas);

        // Tao scene
        Scene scene = new Scene(root);

        scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
            /**
             * Don't invest much in the SPACE case, it's still in progress.
             *
             * @param keyEvent
             */
            @Override
            public void handle(KeyEvent keyEvent) {
                switch (keyEvent.getCode()) {
                    case A:
                        ((Bomber) bomberman).setMoveWest(true);
                        ((Bomber) bomberman).checkMovement();
                        break;
                    case D:
                        ((Bomber) bomberman).setMoveEast(true);
                        ((Bomber) bomberman).checkMovement();
                        break;
                    case W:
                        ((Bomber) bomberman).setMoveNorth(true);
                        ((Bomber) bomberman).checkMovement();
                        break;
                    case S:
                        ((Bomber) bomberman).setMoveSouth(true);
                        ((Bomber) bomberman).checkMovement();
                        break;
                    case SPACE:
                        Bomb bomb = new Bomb(bomberman.getX(), bomberman.getY(), Sprite.bomb.getFxImage());
                        bomb.setExplodeLimit(((Bomber) bomberman).getExplodeRange());
                        bomb.checkSurrounding();
                        bomb.setExplodeAnimation();
                        Maps.maps.get(0).getEntities().add(bomb);
                        bomb.detonate();
                        break;
                }
            }
        });

        scene.setOnKeyReleased(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
                switch (keyEvent.getCode()){
                    case A:
                        if(((Bomber) bomberman).isMoveAble()){
                            bomberman.setX(bomberman.getX() - 1);
                        }
                        bomberman.setImg(Sprite.player_left.getFxImage());
                        ((Bomber) bomberman).setMoveWest(false);
                        break;
                    case D:
                        if(((Bomber) bomberman).isMoveAble()){
                            bomberman.setX(bomberman.getX() + 1);
                        }
                        bomberman.setImg(Sprite.player_right.getFxImage());
                        ((Bomber) bomberman).setMoveEast(false);
                        break;
                    case W:
                        if(((Bomber) bomberman).isMoveAble()){
                            bomberman.setY(bomberman.getY() - 1);
                        }
                        bomberman.setImg(Sprite.player_up.getFxImage());
                        ((Bomber) bomberman).setMoveNorth(false);
                        break;
                    case S:
                        if(((Bomber) bomberman).isMoveAble()){
                            bomberman.setY(bomberman.getY() + 1);
                        }
                        bomberman.setImg(Sprite.player_down.getFxImage());
                        ((Bomber) bomberman).setMoveSouth(false);
                        break;
                }
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
                allMaps.drawLevel(0, gc);
                update();
                updateAndPrintFps();
            }
        };
        timer.start();

        allMaps.createLevel(0);
    }

    public void updateAndPrintFps() {
        if (frameCount >= 100) {
            double fps = tracker.getAverageFPS();
            System.out.printf("Current fps: %f\n", fps);
            tracker.resetAverageFPS();
            frameCount=0;
        } else {
            frameCount++;
        }
    }

    public void update() {
        Maps.maps.get(0).getEntities().forEach(Entity::update);
    }

    public boolean isMoveAble(Entity bomber) {
        int x = bomber.getX();
        int y = bomber.getY();
        return !Entity.isColliding(bomber, Maps.maps.get(0).getStillObjects().get(y * WIDTH + x));
    }
}
