package uet.oop.bomberman.ai;

import javafx.util.Pair;
import uet.oop.bomberman.entities.AIControlledObject;
import uet.oop.bomberman.entities.mobileobjects.Bomber;
import uet.oop.bomberman.scenes.MainGameScene;
import uet.oop.bomberman.utils.AlgorithmicProcessor;

import java.util.Deque;

public class AIGod extends AIComponent {
  public AIGod(AIControlledObject object, MainGameScene scene) {
    super(object, scene);
  }

  @Override
  public void update() {
    Bomber bomber = gameScene.bomberman;

    int vehicleX = (int) Math.round(vehicle.getGridX());
    int vehicleY = (int) Math.round(vehicle.getGridY());

    int bomberX = (int) Math.round(bomber.getGridX());
    int bomberY = (int) Math.round(bomber.getGridY());

    Deque<Pair<Integer, Integer>> res =
        AlgorithmicProcessor.getPathFromGraph(
            gameScene.getStillObjectAdjacencyMatrix(),
            2,
            2,
            1,
            AlgorithmicProcessor.INFINITY,
            vehicleY,
            vehicleX,
            bomberY,
            bomberX);
    if (res != null) {
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
      moveToRoundCoords();
    }
  }
}
