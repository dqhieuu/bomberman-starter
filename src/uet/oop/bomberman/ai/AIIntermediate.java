package uet.oop.bomberman.ai;

import javafx.util.Pair;
import uet.oop.bomberman.entities.AIControlledObject;
import uet.oop.bomberman.entities.mobileobjects.Bomber;
import uet.oop.bomberman.scenes.MainGameScene;
import uet.oop.bomberman.utils.AlgorithmicProcessor;

import java.util.Deque;

public class AIIntermediate extends AIComponent {
    private int BFSRange;
    private int evadeThreshold;
    private int maxEvadeWeight;
    private final int AILevel;
    private boolean chaseMode;

    public AIIntermediate(AIControlledObject object, MainGameScene scene, int AILevel) {
        super(object, scene);
        this.AILevel = AILevel;
        switch (AILevel){
            case 1:
                BFSRange = 10;
                evadeThreshold = 3;
                maxEvadeWeight = 3;
                break;
            case 2:
                BFSRange = 5;
                evadeThreshold = 3;
                maxEvadeWeight = 3;
                break;
            case 3:
                BFSRange = 5;
                evadeThreshold = 2;
                maxEvadeWeight = 3;
                break;
        }
        chaseMode = true;
    }

    @Override
    public void update() {
        Bomber bomber = gameScene.bomberman;

        if (AILevel == 1) {
            chaseMode = bomber.getGridX() == vehicle.getGridX() || bomber.getGridY() == vehicle.getGridY();
        }

        int vehicleX = (int) Math.round(vehicle.getGridX());
        int vehicleY = (int) Math.round(vehicle.getGridY());

        int bomberX = (int) Math.round(bomber.getGridX());
        int bomberY = (int) Math.round(bomber.getGridY());

        Deque<Pair<Integer, Integer>> res =
                AlgorithmicProcessor.getPathFromGraph(
                        gameScene.getStillObjectAdjacencyMatrix(),
                        evadeThreshold,
                        maxEvadeWeight,
                        1,
                        BFSRange,
                        vehicleY,
                        vehicleX,
                        bomberY,
                        bomberX);
        if (res != null && chaseMode) {
            assert res.peekFirst() != null;
            double nextX = res.peekFirst().getValue();
            assert res.peekFirst() != null;
            double nextY = res.peekFirst().getKey();

            if (nextX - vehicle.getGridX() > ROUND_DISTANCE) {
                vehicle.moveRight();
            } else if (nextX - vehicle.getGridX() < -ROUND_DISTANCE) {
                vehicle.moveLeft();
            } else if (nextY - vehicle.getGridY() > ROUND_DISTANCE) {
                vehicle.moveDown();
            } else if (nextY - vehicle.getGridY() < -ROUND_DISTANCE) {
                vehicle.moveUp();
            }

        } else {
            moveRandomly();
        }
    }
}
