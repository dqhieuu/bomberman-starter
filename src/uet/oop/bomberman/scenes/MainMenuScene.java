package uet.oop.bomberman.scenes;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.Scene;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.util.Duration;
import uet.oop.bomberman.BombermanGame;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.entities.PlainImage;
import uet.oop.bomberman.entities.Text;
import uet.oop.bomberman.graphics.Sprite;
import uet.oop.bomberman.utils.GameVars;

import java.util.ArrayList;
import java.util.List;

public class MainMenuScene implements GameScene {
    private final Text menuText;
    private final Text highScore;
    private boolean blink = false;
    private final List<PlainImage> menu = new ArrayList<>();
    private boolean keyPressed = false;

    public MainMenuScene() {
        menuText = new Text(this, 2, 12, false);
        highScore = new Text(this, 6, 2, false);
        menuText.setText("Press any key to start");
        highScore.setText(String.format("High score: %d", BombermanGame.getHighScore()));
        Sprite.loadMenuScreen();
        int i = 0;
        for (int x = 0; x < Sprite.MENU_WIDTH; x++) {
            for (int y = 0; y < Sprite.MENU_HEIGHT; y++) {
                menu.add(new PlainImage(this, 4 + x, 1 + y,
                        Sprite.menuTitle.get(i).getFxImage()));
                i++;
            }
        }
        Animation blinkingWords =
                new Timeline(
                        new KeyFrame(
                                Duration.millis(800),
                                e -> blink = !blink
                        ));
        blinkingWords.setCycleCount(Timeline.INDEFINITE);
        blinkingWords.play();
        keyEvent(BombermanGame.primaryStage.getScene());
    }

    public void keyEvent(Scene currentScene) {
        currentScene.setOnKeyPressed(event -> {
            if (event != null && !keyPressed) {
                keyPressed = true;
                BombermanGame.setCurrentGameScene(new IntermissionScene(IntermissionScene.IntermissionType.FIRST_LEVEL));
            }
        });
    }

    @Override
    public void update() {

    }

    @Override
    public void render(GraphicsContext gc) {
        gc.setFill(Color.BLACK);
        gc.fillRect(0, 0, BombermanGame.canvas.getWidth(), BombermanGame.canvas.getHeight());
        for (Entity item : menu) {
            item.render(gc);
        }
        if (blink) {
            menuText.render(gc);
        }
        highScore.render(gc);
    }
}
