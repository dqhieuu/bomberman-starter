package uet.oop.bomberman.scenes;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.util.Duration;
import uet.oop.bomberman.BombermanGame;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.entities.mobileobjects.Ballom;
import uet.oop.bomberman.entities.MovableObject;
import uet.oop.bomberman.entities.mobileobjects.Bomber;
import uet.oop.bomberman.entities.stillobjects.*;
import uet.oop.bomberman.entities.Text;
import uet.oop.bomberman.entities.stillobjects.powerups.PowerUpBomb;
import uet.oop.bomberman.entities.stillobjects.powerups.PowerUpFlame;
import uet.oop.bomberman.entities.stillobjects.powerups.PowerUpSpeed;
import uet.oop.bomberman.graphics.Sprite;
import uet.oop.bomberman.utils.ErrorDialog;
import uet.oop.bomberman.utils.Camera;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainGameScene implements GameScene {
    private String level;
    private int gridWidth;
    private int gridHeight;
    private List<String> mapData;

    private List<MovableObject> animateObjects = new ArrayList<>();
    private Entity[][] stillObjects;

    protected List<Flame> flames = new ArrayList<>();

    public static Camera camera;

    public static Bomber bomberman;

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

        textTime = new Text(this, 0.5, 1.5, true);
        textPoints = new Text(this, 8, 1.5, true);
        textLives = new Text(this, 11.5, 1.5, true);

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

        if (!readMapFile(path)) {
            ErrorDialog.displayAndExit("Lỗi khi load cảnh", "Load cảnh chính lỗi.");
        }

        bomberman = new Bomber(this, 1, 1);

        camera =
                new Camera(
                        BombermanGame.CANVAS_WIDTH,
                        BombermanGame.CANVAS_HEIGHT - BombermanGame.CANVAS_OFFSET_Y,
                        getRealWidth(),
                        getRealHeight());
        camera.attachCamera(bomberman);
        bomberman.setCamera(camera);
        animateObjects.forEach(e -> e.setCamera(camera));

        for (Entity[] arr : stillObjects) {
            for (Entity e : arr) {
                e.setCamera(camera);
            }
        }

        bomberman.bindInput(BombermanGame.primaryStage.getScene());
    }

    public List<Flame> getFlames() {
        return flames;
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

            level = matcher.group(1);
            gridHeight = Integer.parseInt(matcher.group(2));
            gridWidth = Integer.parseInt(matcher.group(3));

            while ((currentLine = br.readLine()) != null) {
                if (currentLine.length() != 0) {
                    if (currentLine.length() < gridWidth) {
                        return false;
                    }
                    mapData.add(currentLine);
                }
            }

            if (mapData.size() < gridHeight) {
                return false;
            }

            stillObjects = new Entity[gridHeight][gridWidth];

            processMapData();

        } catch (IOException e) {
            e.printStackTrace();
        }
        return true;
    }

    private void processMapData() {
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
                        animateObjects.add(new Ballom(this, j, i));
                        stillObjects[i][j] = new Grass(this, j, i);
                        break;
                    case '*':
                        stillObjects[i][j] = new BrickWall(this, j, i);
                        break;
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

    /**
     * This is just for testing also.
     */
    public void testMap() {
        for (String texture : mapData) {
            System.out.println(texture);
        }
    }

    public Entity getStillObjectAt(int x, int y) {
        if (x < 0 || y < 0 || x >= stillObjects[0].length || y >= stillObjects.length) {
            return null;
        }
        return stillObjects[y][x];
    }

    public void setStillObjectAt(Entity object, int x, int y) {
        if (x < 0 || y < 0 || x >= stillObjects[0].length || y >= stillObjects.length || object == null) {
            return;
        }
        stillObjects[y][x] = object;
    }

    public void addPoints(int points) {
        playerPoints += points;
        textPoints.setText(String.valueOf(playerPoints));
    }

    @Override
    public void update() {
        for (int i = 0; i < gridHeight; i++) {
            for (int j = 0; j < gridWidth; j++) {
                if (stillObjects[i][j].isDestroyed()) {
                    Entity grass = new Grass(this, j, i);
                    grass.setCamera(camera);
                    stillObjects[i][j] = grass;
                }
            }
        }
        if (animateObjects.size() > 0) {
            animateObjects.removeIf(Entity::isDestroyed);
        }

        animateObjects.forEach(Entity::update);
        bomberman.update();
        camera.updateCamera();
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

        animateObjects.forEach(g -> g.render(gc));
        bomberman.render(gc);
        System.out.printf("pos: %f %f\n", bomberman.getGridX(), bomberman.getGridY());
        textPoints.render(gc);
        textLives.render(gc);
        textTime.render(gc);
    }
}
