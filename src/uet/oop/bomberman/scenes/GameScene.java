package uet.oop.bomberman.scenes;

import javafx.animation.Animation;
import javafx.scene.canvas.GraphicsContext;

import java.util.ArrayList;
import java.util.List;

public abstract class GameScene {
    private final List<Animation> threads = new ArrayList<>();

    public abstract void update();

    public abstract void render(GraphicsContext gc);

    /**
     * Animation không bị garbage collector dọn nên cần phải dừng bằng tay.
     */
    public void setInactive() {
        threads.forEach(Animation::stop);
    }

    public void addObservableAnimation(Animation animation) {
        if (animation != null) {
            threads.add(animation);
        }
    }

}
