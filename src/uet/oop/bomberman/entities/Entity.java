package uet.oop.bomberman.entities;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import uet.oop.bomberman.BombermanGame;
import uet.oop.bomberman.graphics.Sprite;
import uet.oop.bomberman.scenes.GameScene;
import uet.oop.bomberman.utils.Camera;

public abstract class Entity {
  protected double gridX;
  protected double gridY;
  protected GameScene sceneContext;
  protected Image currentImg;
  protected Camera camera;
  protected boolean exists;

  public Entity(GameScene scene, double gridX, double gridY) {
    sceneContext = scene;
    exists = true;
    this.gridX = gridX;
    this.gridY = gridY;
  }

  public Entity(GameScene scene, double gridX, double gridY, Image image) {
    sceneContext = scene;
    exists = true;
    this.gridX = gridX;
    this.gridY = gridY;
    setCurrentImg(image);
  }

  public Entity(GameScene scene, double gridX, double gridY, Image image, Camera camera) {
    sceneContext = scene;
    exists = true;
    this.gridX = gridX;
    this.gridY = gridY;
    setCurrentImg(image);
    this.camera = camera;
  }

  public void render(GraphicsContext gc) {
    if (exists && currentImg != null) {
      if (camera != null) {
        gc.drawImage(
            currentImg,
            getRealX() - camera.getX(),
            getRealY() - camera.getY() + BombermanGame.CANVAS_OFFSET_Y);
      } else {
        gc.drawImage(currentImg, getRealX(), getRealY() + BombermanGame.CANVAS_OFFSET_Y);
      }
    }
  }

  public void renderAsUI(GraphicsContext gc) {
    if (exists && currentImg != null) {
      gc.drawImage(currentImg, getRealX(), getRealY());
    }
  }

  public double getGridX() {
    return gridX;
  }

  public double getGridY() {
    return gridY;
  }

  public void setGridX(double gridX) {
    this.gridX = gridX;
  }

  public void setGridY(double gridY) {
    this.gridY = gridY;
  }

  public int getRealX() {
    return (int) (gridX * Sprite.SCALED_SIZE);
  }

  public int getRealY() {
    return (int) (gridY * Sprite.SCALED_SIZE);
  }

  public double getEntityWidth() {
    return currentImg.getWidth();
  }

  public double getEntityHeight() {
    return currentImg.getHeight();
  }

  public void setCurrentImg(Image img) {
    this.currentImg = img;
  }

  public Image getCurrentImg() {
    return currentImg;
  }

  public void setCamera(Camera camera) {
    this.camera = camera;
  }

  public void destroy() {
    exists = false;
  }

  public boolean isDestroyed() {
    return !exists;
  }

  public abstract void update();
}
