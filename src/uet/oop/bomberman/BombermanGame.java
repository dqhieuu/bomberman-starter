package uet.oop.bomberman;

import com.sun.javafx.perf.PerformanceTracker;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import uet.oop.bomberman.graphics.Sprite;
import uet.oop.bomberman.scenes.GameScene;
import uet.oop.bomberman.scenes.MainMenuScene;

import java.awt.*;
import java.util.prefs.Preferences;

public class BombermanGame extends Application {
    public static final int CANVAS_OFFSET_Y = 2 * Sprite.DEFAULT_SIZE * Sprite.SCALE_RATIO;
    public static final int CANVAS_WIDTH = 16 * Sprite.DEFAULT_SIZE * Sprite.SCALE_RATIO;
    public static final int CANVAS_HEIGHT = 13 * Sprite.DEFAULT_SIZE * Sprite.SCALE_RATIO + CANVAS_OFFSET_Y;
    public static final double TARGET_FRAME_RATE = 60.0;
    public static final String[] levelPaths = {
            "/levels/Level1.txt", "/levels/Level2.txt"
    };

    private static double gameSpeed;

    private static Font pixelFont;

    public static GraphicsContext gc;
    public static Stage primaryStage;
    public static Canvas canvas;

    private static final Preferences prefs = Preferences.userNodeForPackage(BombermanGame.class);
    private static GameScene currentGameScene;

    private static PerformanceTracker tracker;
    private static int frameCount;

    public static void main(String[] args) {
        Application.launch(BombermanGame.class, args);
    }

    @Override
    public void init() {
        pixelFont = Font.loadFont(getClass().getResourceAsStream("/fonts/Pixel_NES.otf"), 12 * Sprite.SCALE_RATIO);
        int refreshRate =
                GraphicsEnvironment.getLocalGraphicsEnvironment()
                        .getScreenDevices()[0]
                        .getDisplayMode()
                        .getRefreshRate();
        gameSpeed = (refreshRate <= 5.0) ? 1.0 : TARGET_FRAME_RATE / refreshRate;
        System.out.printf("Gane is running at %.2fx speed\n", gameSpeed);
    }

    @Override
    public void start(Stage stage) {

        // Cap nhat bien toan cuc
        primaryStage = stage;

        // Tao Canvas
        canvas = new Canvas(CANVAS_WIDTH, CANVAS_HEIGHT);
        gc = canvas.getGraphicsContext2D();

        // Set font mặc định
        gc.setFont(pixelFont);

        // Tạo root container
        Pane root = new Pane();
        root.getChildren().add(canvas);

        // Tự động resize nếu thay đổi kích thước
        ChangeListener<Number> stageSizeListener = (observable, oldValue, newValue) -> {
            double resizeScale = Math.min(root.getWidth() / CANVAS_WIDTH, root.getHeight() / CANVAS_HEIGHT);
            if (resizeScale <= 0) resizeScale = 1;

            canvas.setTranslateX((root.getWidth() - (double) CANVAS_WIDTH) / 2);
            canvas.setTranslateY((root.getHeight() - (double) CANVAS_HEIGHT) / 2);
            canvas.setScaleX(resizeScale);
            canvas.setScaleY(resizeScale);
        };

        root.heightProperty().addListener(stageSizeListener);
        root.widthProperty().addListener(stageSizeListener);


        // Tao scene
        Scene scene = new Scene(root, Color.BLACK);

        // Them scene vao stage
        stage.setScene(scene);
        stage.setTitle("Bomberman");

        // Load game scene mac dinh
        currentGameScene = new MainMenuScene();
        stage.show();

        // Do fps
        tracker = PerformanceTracker.getSceneTracker(scene);

        AnimationTimer timer =
                new AnimationTimer() {
                    @Override
                    public void handle(long l) {
                        update();
                        render();
                        printFps();
                    }
                };
        timer.start();
    }

    public void printFps() {
        if (frameCount >= 100) {
            double fps = tracker.getAverageFPS();
            System.out.printf("Current fps: %f\n", fps);
            tracker.resetAverageFPS();
            frameCount = 0;

        } else {
            frameCount++;
        }
    }

    public void update() {
        currentGameScene.update();
    }

    public void render() {
        currentGameScene.render(gc);
    }

    public static void setCurrentGameScene(GameScene currentGameScene) {
        BombermanGame.currentGameScene.setInactive();
        BombermanGame.currentGameScene = currentGameScene;
    }

    public static double getGameSpeed() {
        return gameSpeed;
    }

    public static void setHighScore(int score) {
        if (getHighScore() < score) {
            prefs.putInt("highScore", score);
        }
    }

    public static int getHighScore() {
        return prefs.getInt("highScore", 0);
    }
}
