package uet.oop.bomberman.ai;

import javafx.util.Pair;
import uet.oop.bomberman.entities.AIControlledObject;
import uet.oop.bomberman.entities.CollidableObject;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.entities.stillobjects.Grass;
import uet.oop.bomberman.misc.Direction;
import uet.oop.bomberman.scenes.MainGameScene;
import uet.oop.bomberman.utils.AlgorithmicProcessor;

import java.util.Deque;
import java.util.Random;

public abstract class AIComponent {
    public static final double ROUND_DISTANCE = 0.1;

    protected AIControlledObject vehicle;
    protected MainGameScene gameScene;
    protected boolean isMoving = false;

    public AIComponent(AIControlledObject object, MainGameScene scene) {
        vehicle = object;
        gameScene = scene;
    }

    public int countUnblockedPaths() {
        int x = (int) Math.round(vehicle.getGridX());
        int y = (int) Math.round(vehicle.getGridY());
        int unblockedPaths = 0;
        int[][] pos = {{0, 1}, {0, -1}, {1, 0}, {-1, 0}};
        for (int[] c : pos) {
            int newX = x + c[0];
            int newY = y + c[1];
            Entity obj = gameScene.getStillObjectAt(newX, newY);
            if (obj instanceof Grass
                    || (obj instanceof CollidableObject && !((CollidableObject) obj).isSolid())) {
                unblockedPaths++;
            }
        }
        return unblockedPaths;
    }

    private void setFacingDirectionRandomly() {
        int rand = new Random().nextInt(4);
        switch (rand) {
            case 0:
                vehicle.setFacingDirection(Direction.EAST);
                break;
            case 1:
                vehicle.setFacingDirection(Direction.WEST);
                break;
            case 2:
                vehicle.setFacingDirection(Direction.NORTH);
                break;
            case 3:
                vehicle.setFacingDirection(Direction.SOUTH);
                break;
        }
    }

    protected Deque<Pair<Integer, Integer>> BFSToBomber(int evadeThreshold, int maxEvadeWeight, int maxSeekWeight, int BFSRange) {
        return AlgorithmicProcessor.getBFSPathFromGraph(
                gameScene.getStillObjectGraph(),
                evadeThreshold,
                maxEvadeWeight,
                maxSeekWeight,
                BFSRange,
                (int) Math.round(vehicle.getGridY()),
                (int) Math.round(vehicle.getGridX()),
                (int) Math.round(gameScene.bomberman.getGridY()),
                (int) Math.round(gameScene.bomberman.getGridX()));
    }

    protected Deque<Pair<Integer, Integer>> searchStraightPathToBomber(int searchRange) {
        return AlgorithmicProcessor.getStraightPathFromGraph(
                gameScene.getStillObjectGraph(),
                2,
                searchRange,
                (int) Math.round(vehicle.getGridY()),
                (int) Math.round(vehicle.getGridX()),
                (int) Math.round(gameScene.bomberman.getGridY()),
                (int) Math.round(gameScene.bomberman.getGridX()));
    }

    public void moveAccordingToCalculatedPath(Deque<Pair<Integer, Integer>> path) {
        if (path != null) {
            assert path.peekFirst() != null;
            double nextX = path.peekFirst().getValue();
            assert path.peekFirst() != null;
            double nextY = path.peekFirst().getKey();

            if (nextX - vehicle.getGridX() > ROUND_DISTANCE) {
                vehicle.moveRight();
            } else if (nextX - vehicle.getGridX() < -ROUND_DISTANCE) {
                vehicle.moveLeft();
            } else if (nextY - vehicle.getGridY() > ROUND_DISTANCE) {
                vehicle.moveDown();
            } else if (nextY - vehicle.getGridY() < -ROUND_DISTANCE) {
                vehicle.moveUp();
            }
        }
    }

    public void moveRandomly() {
        double gridX = vehicle.getGridX();
        double gridY = vehicle.getGridY();

        // 1 in 6 chance of AI changing the direction at any more-than-3-way junction when standing
        // perfectly inside a tile
        if (gridX == (int) gridX
                && gridY == (int) gridY
                && countUnblockedPaths() >= 3
                && new Random().nextInt(3) == 0) {
            setFacingDirectionRandomly();
        } else {
            if (isMoving) {
                switch (vehicle.getFacingDirection()) {
                    case WEST:
                        isMoving = vehicle.moveLeft();
                        break;
                    case EAST:
                        isMoving = vehicle.moveRight();
                        break;
                    case SOUTH:
                        isMoving = vehicle.moveDown();
                        break;
                    case NORTH:
                        isMoving = vehicle.moveUp();
                        break;
                }
            } else {
                setFacingDirectionRandomly();
                isMoving = true;
            }
        }
    }

    public void moveToRoundCoords() {
        double gridX = vehicle.getGridX();
        double gridY = vehicle.getGridY();

        if (Math.round(gridX) - vehicle.getGridX() > ROUND_DISTANCE) {
            vehicle.moveRight();
        } else if (Math.round(gridX) - vehicle.getGridX() < -ROUND_DISTANCE) {
            vehicle.moveLeft();
        } else if (Math.round(gridY) - vehicle.getGridY() > ROUND_DISTANCE) {
            vehicle.moveDown();
        } else if (Math.round(gridY) - vehicle.getGridY() < -ROUND_DISTANCE) {
            vehicle.moveUp();
        }
    }

    public abstract void update();
}
