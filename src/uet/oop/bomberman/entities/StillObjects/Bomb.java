package uet.oop.bomberman.entities.StillObjects;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.image.Image;
import javafx.util.Duration;
import uet.oop.bomberman.BombermanGame;
import uet.oop.bomberman.entities.CollidableObject;
import uet.oop.bomberman.graphics.Sprite;

import java.util.ArrayList;

public class Bomb extends CollidableObject {
    protected Timeline timeline = new Timeline();
    protected boolean hasPlanted;
    protected boolean hasExploded;
    protected boolean exploding;
    protected int numberOfFlames;

    protected int explodeLimit;
    protected int explodeLimitNorth;
    protected int explodeLimitSouth;
    protected int explodeLimitEast;
    protected int explodeLimitWest;

    public Bomb(double x, double y, Image img) {
        super(x, y, img);
        hasPlanted = false;
        hasExploded = false;
        exploding = false;
        numberOfFlames = 0;
        setAnimation();
    }

    public boolean isHasPlanted() {
        return hasPlanted;
    }

    public void setHasPlanted(boolean hasPlanted) {
        this.hasPlanted = hasPlanted;
    }

    public boolean isHasExploded() {
        return hasExploded;
    }

    public void setHasExploded(boolean hasExploded) {
        this.hasExploded = hasExploded;
    }

    public void setExploding(boolean exploding) {
        this.exploding = exploding;
    }

    public ArrayList<Explosion> getExplodeAnimation() {
        return BombermanGame.allMaps.getMaps().get(BombermanGame.level - 1).getExplodeAnimation();
    }

    public void setAnimation() {
        timeline.setCycleCount(4);
        timeline.getKeyFrames().addAll(
                new KeyFrame(Duration.millis(200), e -> {
                    if (exploding) {
                        for (Explosion explosion : this.getExplodeAnimation()) {
                            explosion.setExplosionImage();
                        }
                        for (Explosion explosion : this.getExplodeAnimation()) {
                            explosion.updateAnimationIndex();
                        }
                        if (this.getExplodeAnimation().get(0).getAnimationIndex() == 4) {
                            this.setHasPlanted(false);
                            this.setExploding(false);
                            this.getExplodeAnimation().clear();
                        }
                    }
                })
        );
    }

    public void setExplodeLimit(int explodeLimit) {
        this.explodeLimit = explodeLimit;
        this.explodeLimitEast = explodeLimit;
        this.explodeLimitNorth = explodeLimit;
        this.explodeLimitSouth = explodeLimit;
        this.explodeLimitWest = explodeLimit;
    }

    public void setAttribute() {
        this.setX(BombermanGame.bomberman.getBombX());
        this.setY(BombermanGame.bomberman.getBombY());
        this.setExplodeLimit(BombermanGame.bomberman.getExplodeRange());
        this.checkSurrounding();
        this.setExplodeAnimation();
    }

    @Override
    public void update() {
        if (hasPlanted) {
            if (exploding) {
                for (Explosion explosion : this.getExplodeAnimation()) {
                    explosion.update();
                }
            }
            if (hasExploded) {
                timeline.play();
                hasExploded = false;
                exploding = true;
            } else if (!exploding) {
                this.render(BombermanGame.gc);
            }
        }
    }

    /**
     * Check the surrounding for walls in order to draw the required explosion sprite.
     */
    public void checkSurrounding() {
        int limit = BombermanGame.allMaps.getMaps().get(BombermanGame.level - 1).getStillObjects().size();
        for (int i = -1; i >= -explodeLimit; i--) {
            int check = (int) y * BombermanGame.WIDTH + (int) (x + i);
            if (check <= limit && check >= 0) {
                if (BombermanGame.getNextStillObjects(x + i, y) instanceof Wall) {
                    explodeLimitWest = Math.abs(i) - 1;
                    break;
                }
            }
        }
        for (int i = -1; i >= -explodeLimit; i--) {
            int check = (int) (y + i) * BombermanGame.WIDTH + (int) x;
            if (check <= limit && check >= 0) {
                if (BombermanGame.getNextStillObjects(x, y + i) instanceof Wall) {
                    explodeLimitNorth = Math.abs(i) - 1;
                    break;
                }
            }
        }

        for (int i = 1; i <= explodeLimit; i++) {
            int check = (int) y * BombermanGame.WIDTH + (int) (x + i);
            if (check <= limit && check >= 0) {
                if (BombermanGame.getNextStillObjects(x + i, y) instanceof Wall) {
                    explodeLimitEast = i - 1;
                    break;
                }
            }
        }
        for (int i = 1; i <= explodeLimit; i++) {
            int check = (int) (y + i) * BombermanGame.WIDTH + (int) x;
            if (check <= limit && check >= 0) {
                if (BombermanGame.getNextStillObjects(x, y + i) instanceof Wall) {
                    explodeLimitSouth = i - 1;
                    break;
                }
            }
        }
    }

    /**
     * Add all explosion sprite for later rendering.
     */
    public void setExplodeAnimation() {
        Explosion centerExplosion = new Explosion(x, y,
                Sprite.bomb_exploded,
                Sprite.bomb_exploded1,
                Sprite.bomb_exploded2);
        this.getExplodeAnimation().add(centerExplosion);
        numberOfFlames++;
        for (int i = 1; i <= explodeLimitWest; i++) {
            if (i == explodeLimitWest) {
                Explosion explosion = new Explosion(x - i, y,
                        Sprite.explosion_horizontal_left_last,
                        Sprite.explosion_horizontal_left_last1,
                        Sprite.explosion_horizontal_left_last2);
                this.getExplodeAnimation().add(explosion);
                numberOfFlames++;
            } else {
                Explosion explosionLeft = new Explosion(x - i, y,
                        Sprite.explosion_horizontal,
                        Sprite.explosion_horizontal1,
                        Sprite.explosion_horizontal2);
                this.getExplodeAnimation().add(explosionLeft);
                numberOfFlames++;
            }
        }
        for (int i = 1; i <= explodeLimitEast; i++) {
            if (i == explodeLimitEast) {
                Explosion explosion = new Explosion(x + i, y,
                        Sprite.explosion_horizontal_right_last,
                        Sprite.explosion_horizontal_right_last1,
                        Sprite.explosion_horizontal_right_last2);
                this.getExplodeAnimation().add(explosion);
                numberOfFlames++;
            } else {
                Explosion explosionRight = new Explosion(x + i, y,
                        Sprite.explosion_horizontal,
                        Sprite.explosion_horizontal1,
                        Sprite.explosion_horizontal2);
                this.getExplodeAnimation().add(explosionRight);
                numberOfFlames++;
            }
        }
        for (int i = 1; i <= explodeLimitNorth; i++) {
            if (i == explodeLimitNorth) {
                Explosion explosion = new Explosion(x, y - i,
                        Sprite.explosion_vertical_top_last,
                        Sprite.explosion_vertical_top_last1,
                        Sprite.explosion_vertical_top_last2);
                this.getExplodeAnimation().add(explosion);
                numberOfFlames++;
            } else {
                Explosion explosionTop = new Explosion(x, y - i,
                        Sprite.explosion_vertical,
                        Sprite.explosion_vertical1,
                        Sprite.explosion_vertical2);
                this.getExplodeAnimation().add(explosionTop);
                numberOfFlames++;
            }
        }
        for (int i = 1; i <= explodeLimitSouth; i++) {
            if (i == explodeLimitSouth) {
                Explosion explosion = new Explosion(x, y + i,
                        Sprite.explosion_vertical_down_last,
                        Sprite.explosion_vertical_down_last1,
                        Sprite.explosion_vertical_down_last2);
                this.getExplodeAnimation().add(explosion);
                numberOfFlames++;
            } else {
                Explosion explosionDown = new Explosion(x, y + i,
                        Sprite.explosion_vertical,
                        Sprite.explosion_vertical1,
                        Sprite.explosion_vertical2);
                this.getExplodeAnimation().add(explosionDown);
                numberOfFlames++;
            }
        }
    }

    public void detonate() {
        Timeline timeline = new Timeline(new KeyFrame(Duration.millis(5000), e -> {
            hasExploded = true;
        })
        );
        timeline.play();
    }
}
