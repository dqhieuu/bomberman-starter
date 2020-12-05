package uet.oop.bomberman.utils;

import javafx.util.Pair;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.entities.stillobjects.*;

import java.util.ArrayDeque;
import java.util.Deque;

public class AlgorithmicProcessor {
  public static final int INFINITY = -1;

  private static boolean isValidTile(int i, int j, int[][] map) {
    return i >= 0 && j >= 0 && i < map.length && j < map[0].length;
  }

  /**
   * Chuẩn hoá map thành đồ thị có trọng số. Thứ tự ưu tiên: cỏ < vật có thể đi qua < lửa bom sẽ nổ
   * < lửa bom đang nổ < tường (bom, tường, tường gạch) Trọng số: 0: cỏ 1: vật có thể đi qua 2: lửa
   * bom sẽ nổ 3: lửa bom đang nổ 100: tường (bom, tường, tường gạch)
   *
   * @param map map chứa các vật thể chưa được chuẩn hoá
   * @return đường cần đi
   */
  public static int[][] getProcessedGraph(Entity[][] map) {
    if (map == null) {
      return null;
    }

    int[][] newMap = new int[map.length][map[0].length];

    for (int i = 0; i < map.length; i++) {
      for (int j = 0; j < map[0].length; j++) {
        if (map[i][j] instanceof Wall
            || map[i][j] instanceof BrickWall
            || map[i][j] instanceof Bomb) {
          newMap[i][j] = 100;
        } else if (map[i][j] instanceof Flame) {
          newMap[i][j] = 3;
        } else if (map[i][j] instanceof Grass) {
          newMap[i][j] = 0;
        } else {
          newMap[i][j] = 1;
        }
      }
    }

    for (int i = 0; i < map.length; i++) {
      for (int j = 0; j < map[0].length; j++) {
        if (map[i][j] instanceof Bomb) {
          int blastRadius = ((Bomb) map[i][j]).getBlastRadius();
          for (int k = 1; k < blastRadius; k++) {
            if (isValidTile(i, j + k, newMap) && newMap[i][j + k] == 0) {
              newMap[i][j + k] = 2;
            } else break;
          }
          for (int k = 1; k < blastRadius; k++) {
            if (isValidTile(i, j - k, newMap) && newMap[i][j - k] == 0) {
              newMap[i][j - k] = 2;
            } else break;
          }
          for (int k = 1; k < blastRadius; k++) {
            if (isValidTile(i + k, j, newMap) && newMap[i + k][j] == 0) {
              newMap[i + k][j] = 2;
            } else break;
          }
          for (int k = 1; k < blastRadius; k++) {
            if (isValidTile(i - k, j, newMap) && newMap[i - k][j] == 0) {
              newMap[i - k][j] = 2;
            } else break;
          }
        }
      }
    }
    return newMap;
  }

  private static final int[][] cases = {{0, 1}, {0, -1}, {1, 0}, {-1, 0}};

  public static Deque<Pair<Integer, Integer>> getPathFromGraph(
      int[][] graph,
      int evadeThreshold,
      int maxEvadeWeight,
      int maxSeekWeight,
      int BFSRange,
      int iFrom,
      int jFrom,
      int iTo,
      int jTo) {
    if (graph == null || (iFrom == iTo && jFrom == jTo)) {
      return null;
    }

    boolean evadeMode = false;
    if (graph[iFrom][jFrom] >= evadeThreshold) {
      evadeMode = true;
    }

    boolean[][] visited = new boolean[graph.length][graph[0].length];
    Deque<Deque<Pair<Integer, Integer>>> queue = new ArrayDeque<>();
    Deque<Pair<Integer, Integer>> first = new ArrayDeque<>();

    first.add(new Pair<>(iFrom, jFrom));
    queue.add(first);

    while (queue.size() > 0) {
      Deque<Pair<Integer, Integer>> cur = queue.removeFirst();
      if(BFSRange != INFINITY && cur.size() - 1 >= BFSRange) {
        return null;
      }
      assert cur.peekLast() != null;
      int curI = cur.peekLast().getKey();
      assert cur.peekLast() != null;
      int curJ = cur.peekLast().getValue();
      for (int[] c : cases) {
        int nextI = curI + c[0];
        int nextJ = curJ + c[1];
        if (isValidTile(nextI, nextJ, graph)
            && !visited[nextI][nextJ]
            && ((evadeMode && graph[nextI][nextJ] <= maxEvadeWeight)
                || graph[nextI][nextJ] <= maxSeekWeight)) {
          visited[nextI][nextJ] = true;
          Deque<Pair<Integer, Integer>> insert = new ArrayDeque<>(cur);
          insert.add(new Pair<>(nextI, nextJ));
          if ((evadeMode && graph[nextI][nextJ] < evadeThreshold)
              || (nextI == iTo && nextJ == jTo)) {
            insert.removeFirst(); // remove current pos
            return insert;
          }
          queue.add(insert);
        }
      }
    }

    return null;
  }
}
