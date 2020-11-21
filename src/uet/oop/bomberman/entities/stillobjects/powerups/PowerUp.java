package uet.oop.bomberman.entities.stillobjects.powerups;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.image.Image;
import javafx.util.Duration;
import uet.oop.bomberman.entities.CollidableObject;
import uet.oop.bomberman.scenes.GameScene;

public abstract class PowerUp extends CollidableObject {
    public PowerUp(GameScene scene, double x, double y, Image img) {
        super(scene, x, y, img);
    }

    public void activateDespawnTimer() {
        Animation countdown = new Timeline(new KeyFrame(Duration.seconds(15), e -> destroy()));
        countdown.play();
    }
}
