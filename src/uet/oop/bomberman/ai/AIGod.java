package uet.oop.bomberman.ai;

import javafx.util.Pair;
import uet.oop.bomberman.entities.AIControlledObject;
import uet.oop.bomberman.scenes.MainGameScene;
import uet.oop.bomberman.utils.AlgorithmicProcessor;

import java.util.Deque;

public class AIGod extends AIComponent {
    public AIGod(AIControlledObject object, MainGameScene scene) {
        super(object, scene);
    }

    @Override
    public void update() {
        Deque<Pair<Integer, Integer>> path = BFSToBomber(
                2,
                2,
                1,
                AlgorithmicProcessor.INFINITY);
        if (path != null) {
            moveAccordingToCalculatedPath(path);
        } else {
            moveToRoundCoords();
        }
    }
}
