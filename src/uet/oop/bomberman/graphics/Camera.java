package uet.oop.bomberman.graphics;

import uet.oop.bomberman.entities.Entity;

public class Camera {
    private int x;
    private int y;
    private final int renderWidth;
    private final int renderHeight;
    private final int sceneWidth;
    private final int sceneHeight;
    private Entity objectAttachedTo;

    public Camera(int renderWidth, int renderHeight, int sceneWidth, int sceneHeight) {
        this.renderWidth = renderWidth;
        this.renderHeight = renderHeight;
        this.sceneWidth = sceneWidth;
        this.sceneHeight = sceneHeight;
    }

    public void attachCamera(Entity object) {
        this.objectAttachedTo = object;
    }

    public void updateCamera() {
        if (objectAttachedTo != null) {
            int posX = objectAttachedTo.getRealX() + ((int) objectAttachedTo.getEntityWidth() - renderWidth) / 2;
            int posY = objectAttachedTo.getRealY() + ((int) objectAttachedTo.getEntityHeight() - renderHeight) / 2;
            if (posX < 0) {
                posX = 0;
            } else if (posX + renderWidth > sceneWidth) {
                posX = sceneWidth - renderWidth;
            }
            if (posY < 0) {
                posY = 0;
            } else if (posY + renderHeight > sceneHeight) {
                posY = sceneHeight - renderHeight;
            }
            this.x = posX;
            this.y = posY;
        }
    }

    public int getRenderHeight() {
        return renderHeight;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

}
