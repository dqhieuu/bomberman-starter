package uet.oop.bomberman.entities.StillObjects;

import javafx.scene.image.Image;
import uet.oop.bomberman.BombermanGame;
import uet.oop.bomberman.entities.CollidableObject;
import uet.oop.bomberman.graphics.Sprite;

public class Explosion extends CollidableObject {
    protected int animationIndex;
    protected Sprite firstExplosion;
    protected Sprite secondExplosion;
    protected Sprite thirdExplosion;

    public int getAnimationIndex() {
        return animationIndex;
    }

    public void updateAnimationIndex(){
        animationIndex++;
    }

    public Explosion(double x, double y,
                     Sprite firstExplosion, Sprite secondExplosion, Sprite thirdExplosion) {
        super(x, y, firstExplosion.getFxImage());
        this.firstExplosion = firstExplosion;
        this.secondExplosion = secondExplosion;
        this.thirdExplosion = thirdExplosion;
        this.animationIndex = 0;
    }

    @Override
    public void update() {
        this.render(BombermanGame.gc);
    }

    public void setExplosionImage() {
        this.setImg(Sprite.animation(firstExplosion, secondExplosion, thirdExplosion, animationIndex).getFxImage());
    }
}
