package uet.oop.bomberman.misc;

import uet.oop.bomberman.BombermanGame;
import uet.oop.bomberman.entities.MovableObject;

import java.util.Timer;
import java.util.TimerTask;

public class AnimationCLock implements Runnable {
    private boolean exit;
    private MovableObject object;

    public AnimationCLock(MovableObject object){
        this.object = object;
        exit = false;
    }

    public void run() {
        Timer timer = new Timer();
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                if(exit){
                    timer.cancel();
                    timer.purge();
                    return;
                }
                if (object.isMoving()) {
                    object.updateAnimationIndex();
                }
            }
        };
            timer.scheduleAtFixedRate(task, 0, 200);
    }

    public void stop() {
        exit = true;
    }
}
