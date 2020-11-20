package uet.oop.bomberman.scenes;

import javafx.scene.canvas.GraphicsContext;

public interface GameScene {
  void update();
  void render(GraphicsContext gc);
}
