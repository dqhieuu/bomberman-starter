package uet.oop.bomberman.entities;

import javafx.scene.image.Image;
import uet.oop.bomberman.misc.MovePad;

public abstract class UserControlledObject extends MovableObject {
    protected MovePad movePad;

    public UserControlledObject(double x, double y, Image img) {
        super(x, y, img);
        movePad = new MovePad();
    }

    public void setMovePad(MovePad movePad) {
        this.movePad = movePad;
    }

    public MovePad getMovePad() {
        return movePad;
    }

//    public abstract void bindInput();
}
