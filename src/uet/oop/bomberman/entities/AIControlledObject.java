package uet.oop.bomberman.entities;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.image.Image;
import javafx.util.Duration;
import uet.oop.bomberman.ai.AIComponent;
import uet.oop.bomberman.graphics.Sprite;
import uet.oop.bomberman.scenes.GameScene;
import uet.oop.bomberman.utils.GameMediaPlayer;

public abstract class AIControlledObject extends MovableObject {
    protected AIComponent objectsAI;

    public AIControlledObject(GameScene scene, double x, double y, Image img) {
        super(scene, x, y, img);
    }

    public void setAIComponent(AIComponent AI) {
        objectsAI = AI;
    }
}
