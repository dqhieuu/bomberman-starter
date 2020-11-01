package uet.oop.bomberman.entities;

import javafx.scene.SnapshotParameters;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import uet.oop.bomberman.BombermanGame;
import uet.oop.bomberman.graphics.Maps;
import uet.oop.bomberman.graphics.Sprite;

import java.awt.*;

public abstract class Entity {
    protected int x;
    protected int y;
    protected Image img;
    private final Image base;

    public Entity( int x, int y, Image img) {
        this.x = x;
        this.y = y;
        this.img = img;
        SnapshotParameters params = new SnapshotParameters();
        params.setFill(Color.TRANSPARENT);
        ImageView iv = new ImageView(img);
        base = iv.snapshot(params, null);
    }

    public void render(GraphicsContext gc) {




        gc.drawImage(base, x * Sprite.SCALED_SIZE, y * Sprite.SCALED_SIZE);
    }

    public int getX(){
        return x;
    }

    public int getY(){
        return y;
    }

    public void setX(int x){
        this.x = x;
    }

    public void setY(int y){
        this.y = y;
    }

    public double getEntityWidth(){
        return img.getWidth();
    }

    public double getEntityHeight(){
        return img.getHeight();
    }

    public Image getImg(){
        return img;
    }

    public void setImg(Image img){
        this.img = img;
    }

    public static boolean isColliding(Entity first, Entity second){
        if(first instanceof Grass || second instanceof Grass){
            return false;
        }
        Rectangle firstEntity =
                new Rectangle(first.x, first.y,
                        (int) Math.round(first.getEntityWidth()), (int) Math.round(first.getEntityHeight()));
        Rectangle secondEntity =
                new Rectangle(second.x, second.y,
                        (int) Math.round(second.getEntityWidth()), (int) Math.round(second.getEntityHeight()));
        return firstEntity.intersects(secondEntity);
    }

    public static Entity getNextEntity(int x, int y){
        return Maps.maps.get(0).getStillObjects().get(y * BombermanGame.WIDTH + x);
    }

    public abstract void update();
}
