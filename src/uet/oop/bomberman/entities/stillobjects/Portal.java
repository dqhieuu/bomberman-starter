package uet.oop.bomberman.entities.stillobjects;

import uet.oop.bomberman.entities.CollidableObject;
import uet.oop.bomberman.graphics.Sprite;
import uet.oop.bomberman.scenes.MainGameScene;

public class Portal extends CollidableObject {
    public Portal(MainGameScene scene, double x, double y) {
        super(scene, x, y, Sprite.portal.getFxImage());
    }

    @Override
    public void update() {
    }

    public void checkLevelFinished() {
        if (((MainGameScene) sceneContext).getEnemies().size() == 0) {
            ((MainGameScene) sceneContext).setStageCompleted();
        }
    }

    @Override
    public void destroy() {

    }
}
