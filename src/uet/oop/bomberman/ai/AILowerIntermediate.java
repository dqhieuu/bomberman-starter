package uet.oop.bomberman.ai;

import javafx.util.Pair;
import uet.oop.bomberman.entities.AIControlledObject;
import uet.oop.bomberman.scenes.MainGameScene;

import java.util.Deque;

public class AILowerIntermediate extends AIComponent {
    public AILowerIntermediate(AIControlledObject object, MainGameScene scene) {
        super(object, scene);
    }

    @Override
    public void update() {
        Deque<Pair<Integer, Integer>> path = searchStraightPathToBomber(5);
        if (path != null) {
            moveAccordingToCalculatedPath(path);
        } else {
            moveRandomly();
        }
    }
}
