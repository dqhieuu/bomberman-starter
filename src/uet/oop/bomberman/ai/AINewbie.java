package uet.oop.bomberman.ai;

import uet.oop.bomberman.entities.AIControlledObject;
import uet.oop.bomberman.scenes.MainGameScene;

public class AINewbie extends AIComponent {
    public AINewbie(AIControlledObject object, MainGameScene scene) {
        super(object, scene);
    }


    @Override
    public void update() {
        moveRandomly();
    }
}
