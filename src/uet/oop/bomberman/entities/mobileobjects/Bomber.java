package uet.oop.bomberman.entities.mobileobjects;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.util.Duration;
import uet.oop.bomberman.BombermanGame;
import uet.oop.bomberman.entities.stillobjects.Bomb;
import uet.oop.bomberman.entities.UserControlledObject;
import uet.oop.bomberman.graphics.Sprite;
import uet.oop.bomberman.misc.Direction;
import uet.oop.bomberman.scenes.GameScene;
import uet.oop.bomberman.scenes.MainGameScene;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Bomber extends UserControlledObject {
    private static Map<String, Image[]> spriteLists;
    private final Animation spriteChanger;
    private int spriteIndex = 0;

    protected int bombsCanPlant;
    protected int bombBlastRadius;

    protected int bombsPlanted = 0;

    private double baseSpeed = 0.05;

    public Bomber(GameScene scene, double x, double y) {
        super(scene, x, y, Sprite.player_right.getFxImage());
        if (spriteLists == null) {
            spriteLists = new HashMap<>();
            spriteLists.put(
                    Direction.WEST.toString(),
                    new Image[]{
                            Sprite.player_left.getFxImage(),
                            Sprite.player_left_1.getFxImage(),
                            Sprite.player_left_2.getFxImage(),
                    });
            spriteLists.put(
                    Direction.EAST.toString(),
                    new Image[]{
                            Sprite.player_right.getFxImage(),
                            Sprite.player_right_1.getFxImage(),
                            Sprite.player_right_2.getFxImage(),
                    });
            spriteLists.put(
                    Direction.NORTH.toString(),
                    new Image[]{
                            Sprite.player_up.getFxImage(),
                            Sprite.player_up_1.getFxImage(),
                            Sprite.player_up_2.getFxImage(),
                    });
            spriteLists.put(
                    Direction.SOUTH.toString(),
                    new Image[]{
                            Sprite.player_down.getFxImage(),
                            Sprite.player_down_1.getFxImage(),
                            Sprite.player_down_2.getFxImage(),
                    });
        }

        spriteChanger =
                new Timeline(
                        new KeyFrame(
                                Duration.millis(80),
                                e -> {
                                    spriteIndex =
                                            (spriteIndex + 1) % spriteLists.get(facingDirection.toString()).length;
                                    setCurrentImg(spriteLists.get(facingDirection.toString())[spriteIndex]);
                                }));
        spriteChanger.setCycleCount(Animation.INDEFINITE);

        bombBlastRadius = 2;
        bombsCanPlant = 1;
//        powerUpFire();        powerUpFire();        powerUpFire();        powerUpFire();
        powerUpBomb();        powerUpBomb();        powerUpBomb();        powerUpBomb();
//        powerUpSpeed();        powerUpSpeed();        powerUpSpeed();        powerUpSpeed();        powerUpSpeed();
    }

    @Override
    public void update() {
        super.update();
        updatePosition();
    }

    private void setBombsCanPlant(int bombsCanPlant) {
        this.bombsCanPlant = bombsCanPlant;
    }

    private int getBombsCanPlant() {
        return bombsCanPlant;
    }

    public void setBombBlastRadius(int bombBlastRadius) {
        this.bombBlastRadius = bombBlastRadius;
    }

    public int getBombBlastRadius() {
        return bombBlastRadius;
    }

    public void plantBomb() {
        int bombX = (int) Math.round(gridX);
        int bombY = (int) Math.round(gridY);

        if (bombsPlanted >= bombsCanPlant ||
                ((MainGameScene) sceneContext).getStillObjectAt(bombX, bombY) instanceof Bomb) {
            return;
        }
        bombsPlanted++;

        Bomb newBomb = new Bomb(sceneContext, bombX, bombY, bombBlastRadius, this);
        newBomb.setCamera(camera);

        ((MainGameScene) sceneContext).setStillObjectAt(newBomb, bombX, bombY);
    }

    public void bindInput(Scene currentScene) {
        currentScene.setOnKeyPressed(
                keyEvent -> {
                    KeyCode key = keyEvent.getCode();
                    switch (key) {
                        case LEFT:
                        case A:
                            movePad.left = true;
                            break;
                        case RIGHT:
                        case D:
                            movePad.right = true;
                            break;
                        case UP:
                        case W:
                            movePad.up = true;
                            break;
                        case DOWN:
                        case S:
                            movePad.down = true;
                            break;
                        case SPACE:
                            plantBomb();
                            break;
                    }
                });

        BombermanGame.primaryStage
                .getScene()
                .setOnKeyReleased(
                        keyEvent -> {
                            KeyCode key = keyEvent.getCode();
                            switch (key) {
                                case LEFT:
                                case A:
                                    movePad.left = false;
                                    break;
                                case RIGHT:
                                case D:
                                    movePad.right = false;
                                    break;
                                case UP:
                                case W:
                                    movePad.up = false;
                                    break;
                                case DOWN:
                                case S:
                                    movePad.down = false;
                                    break;
                            }
                        });
    }

    public void moveLeft() {
        if (facingDirection != Direction.WEST) {
            setFacingDirection(Direction.WEST);
            spriteIndex = 0;
            setCurrentImg(spriteLists.get(Direction.WEST.toString())[0]);
        }
        if (spriteChanger.getCurrentRate() == 0.0) {
            spriteChanger.play();
        }

        gridX -= baseSpeed * BombermanGame.gameSpeed;
        if (!movable()) {
            gridX += baseSpeed * BombermanGame.gameSpeed;
        }
    }

    public void moveRight() {
        if (facingDirection != Direction.EAST) {
            setFacingDirection(Direction.EAST);
            spriteIndex = 0;
            setCurrentImg(spriteLists.get(Direction.EAST.toString())[0]);
        }
        if (spriteChanger.getCurrentRate() == 0.0) {
            spriteChanger.play();
        }

        gridX += baseSpeed * BombermanGame.gameSpeed;
        if (!movable()) {
            gridX -= baseSpeed * BombermanGame.gameSpeed;
        }
    }

    public void moveUp() {
        if (facingDirection != Direction.NORTH) {
            setFacingDirection(Direction.NORTH);
            spriteIndex = 0;
            setCurrentImg(spriteLists.get(Direction.NORTH.toString())[0]);
        }
        if (spriteChanger.getCurrentRate() == 0.0) {
            spriteChanger.play();
        }

        gridY -= baseSpeed * BombermanGame.gameSpeed;
        if (!movable()) {
            gridY += baseSpeed * BombermanGame.gameSpeed;
        }
    }

    public void moveDown() {
        if (facingDirection != Direction.SOUTH) {
            setFacingDirection(Direction.SOUTH);
            spriteIndex = 0;
            setCurrentImg(spriteLists.get(Direction.SOUTH.toString())[0]);
        }
        if (spriteChanger.getCurrentRate() == 0.0) {
            spriteChanger.play();
        }

        gridY += baseSpeed * BombermanGame.gameSpeed;
        if (!movable()) {
            gridY -= baseSpeed * BombermanGame.gameSpeed;
        }
    }

    /**
     * Check if bomberman can move to the destined location.
     */
    public void updatePosition() {
        if (!movePad.left
                && !movePad.right
                && !movePad.up
                && !movePad.down
                && spriteChanger.getCurrentRate() != 0.0) {
            spriteChanger.pause();
        }
        if (movePad.left && !movePad.right) {
            moveLeft();
        } else if (movePad.right && !movePad.left) {
            moveRight();
        } else if (movePad.up && !movePad.down) {
            moveUp();
        } else if (movePad.down && !movePad.up) {
            moveDown();
        }
    }

    public void powerUpSpeed() {
        if (baseSpeed < 0.10) {
            baseSpeed += 0.01;
        }
    }

    public void powerUpBomb() {
        bombsCanPlant++;
    }

    public void powerUpFire() {
        if (bombBlastRadius < 5) {
            bombBlastRadius++;
        }
    }

    public void retrieveBomb() {
        if (bombsPlanted > 0) {
            bombsPlanted--;
        }
    }
}
