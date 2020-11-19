package uet.oop.bomberman.entities;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class Text extends Entity {
  private String text;
  private final boolean hasShadow;

  public Text(double gridX, double gridY, boolean hasShadow) {
    super(gridX, gridY);
    this.hasShadow = hasShadow;
  }

  public void setText(String text) {
    this.text = text;
  }

  public String getText() {
    return text;
  }

  @Override
  public void render(GraphicsContext gc) {
    if (exists && !"".equals(text)) {
      if(hasShadow) {
        gc.setFill(Color.BLACK);
        gc.fillText(text, getRealX()+2, getRealY()+2);
      }
      gc.setFill(Color.WHITE);
      gc.fillText(text, getRealX(), getRealY());
    }
  }

  @Override
  public void renderAsUI(GraphicsContext gc) {
    render(gc);
  }

  @Override
  public void update() {}
}
