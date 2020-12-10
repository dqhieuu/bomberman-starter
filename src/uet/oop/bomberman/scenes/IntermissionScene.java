package uet.oop.bomberman.scenes;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.util.Duration;
import uet.oop.bomberman.BombermanGame;
import uet.oop.bomberman.entities.Text;
import uet.oop.bomberman.utils.ErrorDialog;
import uet.oop.bomberman.utils.GameMediaPlayer;
import uet.oop.bomberman.utils.GameVars;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class IntermissionScene extends GameScene {
    public enum IntermissionType {
        FIRST_LEVEL,
        REPLAY_LEVEL,
        NEXT_LEVEL,
        GAME_OVER
    }

    private final Text intermissionText;

    private String levelLabel;
    private int mapWidth;
    private int mapHeight;
    private List<String> mapData;
    private int bomberX;
    private int bomberY;

    public IntermissionScene(IntermissionType intermissionType) {
        intermissionText = new Text(this, 5.5, 8, false);
        if (intermissionType == IntermissionType.FIRST_LEVEL
                || intermissionType == IntermissionType.NEXT_LEVEL
                || intermissionType == IntermissionType.REPLAY_LEVEL) {
            GameMediaPlayer.playBackgroundMusic(GameMediaPlayer.STAGE_START, false);
            if (intermissionType == IntermissionType.FIRST_LEVEL) {
                GameVars.currentLevel = 1;
                GameVars.playerLives = 3;
                GameVars.playerPoints = 0;
                GameVars.playerPowerUpBomb = 0;
                GameVars.playerPowerUpFlame = 0;
                GameVars.playerPowerUpSpeed = 0;
            } else if (intermissionType == IntermissionType.NEXT_LEVEL) {
                GameVars.currentLevel++;
            } else {
                GameVars.playerPowerUpBomb = 0;
                GameVars.playerPowerUpFlame = 0;
                GameVars.playerPowerUpSpeed = 0;
            }

            if (GameVars.currentLevel > BombermanGame.levelPaths.length) {
                BombermanGame.setHighScore(GameVars.playerPoints);
                GameMediaPlayer.playBackgroundMusic(GameMediaPlayer.ENDING, false);
                intermissionText.setText("YOU WIN");
            } else {
                if (!readMapFile(BombermanGame.levelPaths[GameVars.currentLevel - 1])) {
                    ErrorDialog.displayAndExit("Lỗi load map", "Map không đúng định dạng");
                }

                intermissionText.setText(levelLabel);

                Animation countdown =
                        new Timeline(
                                new KeyFrame(
                                        Duration.seconds(3),
                                        e ->
                                                BombermanGame.setCurrentGameScene(
                                                        new MainGameScene(mapWidth, mapHeight, mapData, bomberX, bomberY))));
                addObservableAnimation(countdown);
                countdown.play();
            }
        } else if (intermissionType == IntermissionType.GAME_OVER) {
            BombermanGame.setHighScore(GameVars.playerPoints);
            GameMediaPlayer.playBackgroundMusic(GameMediaPlayer.GAME_OVER, false);
            intermissionText.setText("GAME OVER");
            Animation countdown =
                    new Timeline(
                            new KeyFrame(
                                    Duration.seconds(6),
                                    e ->
                                            BombermanGame.setCurrentGameScene(
                                                    new MainMenuScene())));
            addObservableAnimation(countdown);
            countdown.play();
        }
    }

    private boolean readMapFile(String path) {
        try {
            mapData = new ArrayList<>();
            BufferedReader br =
                    new BufferedReader(new InputStreamReader(this.getClass().getResourceAsStream(path)));

            String currentLine = br.readLine();
            Pattern pattern = Pattern.compile("(^.+)\\s+(\\d+)\\s+(\\d+$)");
            Matcher matcher = pattern.matcher(currentLine);

            if (!matcher.find()) {
                return false;
            }

            levelLabel = matcher.group(1);
            mapHeight = Integer.parseInt(matcher.group(2));
            mapWidth = Integer.parseInt(matcher.group(3));

            while ((currentLine = br.readLine()) != null) {
                if (currentLine.length() != 0) {
                    if (currentLine.length() < mapWidth) {
                        return false;
                    }
                    mapData.add(currentLine);
                }
            }

            if (mapData.size() < mapHeight) {
                return false;
            }

            for (int i = 0; i < mapHeight; i++) {
                for (int j = 0; j < mapWidth; j++) {
                    if (mapData.get(i).charAt(j) == 'p') {
                        bomberX = j;
                        bomberY = i;
                        return true;
                    }
                }
            }

            return false;


        } catch (IOException e) {
            e.printStackTrace();
        }
        return true;
    }

    @Override
    public void update() {
    }

    @Override
    public void render(GraphicsContext gc) {
        gc.setFill(Color.BLACK);
        gc.fillRect(0, 0, BombermanGame.canvas.getWidth(), BombermanGame.canvas.getHeight());

        intermissionText.render(gc);
    }
}
