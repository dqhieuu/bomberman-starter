package uet.oop.bomberman.ai;

import javafx.util.Pair;
import uet.oop.bomberman.entities.AIControlledObject;
import uet.oop.bomberman.scenes.MainGameScene;

import java.util.Deque;

public class AIIntermediate extends AIComponent {
    private final int BFSRange;
    private final int evadeThreshold;
    private final int maxEvadeWeight;
    private final int maxSeekWeight;

    public AIIntermediate(AIControlledObject object, MainGameScene scene, int AILevel) {
        super(object, scene);
        switch (AILevel) {
            default:
            case 1:
                BFSRange = 5;
                evadeThreshold = 999;
                maxEvadeWeight = 0;
                maxSeekWeight = 2;
                break;
            case 2:
                BFSRange = 7;
                evadeThreshold = 999;
                maxEvadeWeight = 0;
                maxSeekWeight = 1;
                break;
        }
    }

    @Override
    public void update() {
        Deque<Pair<Integer, Integer>> path = BFSToBomber(evadeThreshold, maxEvadeWeight, maxSeekWeight, BFSRange);
        if (path != null) {
            moveAccordingToCalculatedPath(path);
        } else {
            moveRandomly();
        }
    }
}
