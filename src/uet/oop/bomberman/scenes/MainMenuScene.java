package uet.oop.bomberman.scenes;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.Scene;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.util.Duration;
import uet.oop.bomberman.BombermanGame;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.entities.PlainImage;
import uet.oop.bomberman.entities.Text;
import uet.oop.bomberman.graphics.Sprite;

import java.util.ArrayList;
import java.util.List;

public class MainMenuScene extends GameScene {
    private final Text menuText;
    private final Text highScore;
    private boolean blink = false;
    private final List<PlainImage> menu = new ArrayList<>();
    private boolean keyPressed = false;

    public MainMenuScene() {
        menuText = new Text(this, 2.5, 12, false);
        highScore = new Text(this, 6, 2, false);
        menuText.setText("Press Enter to start");
        highScore.setText(String.format("High score: %d", BombermanGame.getHighScore()));
        Sprite.loadMenuScreen();
        int i = 0;
        for (int x = 0; x < Sprite.MENU_WIDTH; x++) {
            for (int y = 0; y < Sprite.MENU_HEIGHT; y++) {
                menu.add(new PlainImage(this, 4 + x, 3 + y,
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
        addObservableAnimation(blinkingWords);
        blinkingWords.setCycleCount(Timeline.INDEFINITE);
        blinkingWords.play();
        keyEvent(BombermanGame.primaryStage.getScene());
    }

    public void keyEvent(Scene currentScene) {
        currentScene.setOnKeyPressed(event -> {
            if (event != null && !keyPressed) {
                KeyCode key = event.getCode();
                if (key == KeyCode.F11 || key == KeyCode.ESCAPE) {
                    if (key == KeyCode.F11) {
                        BombermanGame.primaryStage.setFullScreen(!BombermanGame.primaryStage.isFullScreen());
                    }
                } else if (key == KeyCode.SPACE || key == KeyCode.ENTER || key == KeyCode.SHIFT || key == KeyCode.Z) {
                    keyPressed = true;
                    BombermanGame.setCurrentGameScene(new IntermissionScene(IntermissionScene.IntermissionType.FIRST_LEVEL));
                }
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
            item.renderAsUI(gc);
        }
        if (!blink) {
            menuText.renderAsUI(gc);
        }
        highScore.renderAsUI(gc);
    }
}
