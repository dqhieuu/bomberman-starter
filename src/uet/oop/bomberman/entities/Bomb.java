package uet.oop.bomberman.entities;

import javafx.scene.image.Image;
import uet.oop.bomberman.BombermanGame;
import uet.oop.bomberman.graphics.Maps;
import uet.oop.bomberman.graphics.Sprite;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class Bomb extends Entity {
    protected boolean hasExploded;
    protected ArrayList<Entity> explodeAnimation = new ArrayList<>();
    protected int explodeLimit;
    protected int explodeLimitNorth;
    protected int explodeLimitSouth;
    protected int explodeLimitEast;
    protected int explodeLimitWest;

    public Bomb(int x, int y, Image img) {
        super(x, y, img);
        hasExploded = false;
    }

    public boolean isHasExploded() {
        return hasExploded;
    }

    public void setHasExploded(boolean hasExploded) {
        this.hasExploded = hasExploded;
    }

    public void setExplodeLimit(int explodeLimit) {
        this.explodeLimit = explodeLimit;
        this.explodeLimitEast = explodeLimit;
        this.explodeLimitNorth = explodeLimit;
        this.explodeLimitSouth = explodeLimit;
        this.explodeLimitWest = explodeLimit;
    }

    @Override
    public void update() {
        if (hasExploded) {
            for (Entity explosion : explodeAnimation) {
                explosion.render(BombermanGame.gc);
            }
            /*Maps.maps.get(0).getEntities().removeIf(entity -> entity instanceof Bomb);*/
        } else {
            this.render(BombermanGame.gc);
        }
    }

    /**
     * Check the surrounding for walls in order to draw the required explosion sprite.
     */
    public void checkSurrounding() {
        for (int i = -1; i >= -explodeLimit; i--) {
            if (Entity.getNextEntity(x + i, y) instanceof Wall) {
                explodeLimitWest = Math.abs(i) - 1;
            }
            if (Entity.getNextEntity(x, y + i) instanceof Wall) {
                explodeLimitNorth = Math.abs(i) - 1;
            }
        }
        for (int i = 1; i <= explodeLimit; i++) {
            if (Entity.getNextEntity(x + i, y) instanceof Wall) {
                explodeLimitEast = i - 1;
            }
            if (Entity.getNextEntity(x, y + i) instanceof Wall) {
                explodeLimitSouth = i - 1;
            }
        }
    }

    /**
     * Add all explosion sprite for later rendering.
     */
    public void setExplodeAnimation() {
        Entity centerExplosion = new Explosion(x, y, Sprite.bomb_exploded.getFxImage());
        explodeAnimation.add(centerExplosion);
        for (int i = 1; i <= explodeLimitWest; i++) {
            if (i == explodeLimitWest) {
                Entity explosion = new Explosion(x - i, y, Sprite.explosion_horizontal_left_last.getFxImage());
                explodeAnimation.add(explosion);
            } else {
                Entity explosionLeft = new Explosion(x - i, y, Sprite.explosion_horizontal.getFxImage());
                explodeAnimation.add(explosionLeft);
            }
        }
        for (int i = 1; i <= explodeLimitEast; i++) {
            if (i == explodeLimitEast) {
                Entity explosion = new Explosion(x + i, y, Sprite.explosion_horizontal_right_last.getFxImage());
                explodeAnimation.add(explosion);
            } else {
                Entity explosionRight = new Explosion(x + i, y, Sprite.explosion_horizontal.getFxImage());
                explodeAnimation.add(explosionRight);
            }
        }
        for (int i = 1; i <= explodeLimitNorth; i++) {
            if (i == explodeLimitNorth) {
                Entity explosion = new Explosion(x, y - i, Sprite.explosion_vertical_top_last.getFxImage());
                explodeAnimation.add(explosion);
            } else {
                Entity explosionTop = new Explosion(x, y - i, Sprite.explosion_vertical.getFxImage());
                explodeAnimation.add(explosionTop);
            }
        }
        for (int i = 1; i <= explodeLimitSouth; i++) {
            if (i == explodeLimitSouth) {
                Entity explosion = new Explosion(x, y + i, Sprite.explosion_vertical_down_last.getFxImage());
                explodeAnimation.add(explosion);
            } else {
                Entity explosionDown = new Explosion(x, y + i, Sprite.explosion_vertical.getFxImage());
                explodeAnimation.add(explosionDown);
            }
        }
    }

    public void detonate() {
        Timer t = new Timer();
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                hasExploded = true;
            }
        };
        t.schedule(task, 5000L);
    }
}
