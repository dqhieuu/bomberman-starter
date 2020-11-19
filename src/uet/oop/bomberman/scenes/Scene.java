package uet.oop.bomberman.scenes;

import javafx.scene.canvas.GraphicsContext;

public interface Scene {
  void update();
  void render(GraphicsContext gc);
}
