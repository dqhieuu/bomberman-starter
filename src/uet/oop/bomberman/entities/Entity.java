package uet.oop.bomberman.entities;

import javafx.scene.SnapshotParameters;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Shape;
import uet.oop.bomberman.BombermanGame;
import uet.oop.bomberman.entities.StillObjects.Grass;
import uet.oop.bomberman.graphics.Maps;
import uet.oop.bomberman.graphics.Sprite;

import javafx.scene.shape.Rectangle;

public abstract class Entity {
    protected double x;
    protected double y;
    protected Image img;
    private Image base;

    public Entity(double x, double y, Image img) {
        this.x = x;
        this.y = y;
        this.img = img;
        updateImg();
    }

    private void updateImg() {
        SnapshotParameters params = new SnapshotParameters();
        params.setFill(Color.TRANSPARENT);
        ImageView iv = new ImageView(img);
        base = iv.snapshot(params, null);
    }

    public void render(GraphicsContext gc) {

        gc.drawImage(base, x * Sprite.SCALED_SIZE, y * Sprite.SCALED_SIZE);
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public void setX(double x) {
        this.x = x;
    }

    public void setY(double y) {
        this.y = y;
    }

    public double getEntityWidth() {
        return img.getWidth();
    }

    public double getEntityHeight() {
        return img.getHeight();
    }

    public Image getImg() {
        return img;
    }

    public void setImg(Image img) {
        this.img = img;
        updateImg();
    }

    public abstract void update();
}
