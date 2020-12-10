package uet.oop.bomberman.entities;

import javafx.scene.image.Image;
import uet.oop.bomberman.ai.AIComponent;
import uet.oop.bomberman.scenes.MainGameScene;

public abstract class AIControlledObject extends MovableObject {
    protected AIComponent objectsAI;

    public AIControlledObject(MainGameScene scene, double x, double y, Image img) {
        super(scene, x, y, img);
    }

    public void setAIComponent(AIComponent AI) {
        objectsAI = AI;
    }
}
