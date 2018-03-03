package projects.newgame.crafts.emittingsystem;

import engine.core.components.Group;
import engine.core.master.Time;
import engine.linear.advancedterrain.Terrain;

import java.util.ArrayList;

/**
 * Created by finne on 20.09.2017.
 */
public abstract class EmitterSystem<B extends Bullet> extends Group{

    private int currentCharges;
    private double chargeTime;
    private double emitTime;
    private int maxStackedCharges;
    private int stackedCharges = 0;

    private ArrayList<B> bullets = new ArrayList<>();

    private Time timer;
    private Time emitTimer;

    public EmitterSystem(float x, float y, float z, int currentCharges, double chargeTime, double emitTime, int maxStackedCharges) {
        super(x, y, z);
        this.currentCharges = currentCharges;
        this.chargeTime = chargeTime;
        this.maxStackedCharges = maxStackedCharges;
        this.emitTime = emitTime;
        this.timer = new Time();
        this.timer.setTimer(chargeTime);
    }

    public B process(Terrain t, boolean keyDown, double passed) {
        for(int i = 0; i < bullets.size(); i++) {
            if(!bullets.get(i).process(t, passed)) {
                bullets.remove(i);
            }
        }
        if(timer.timerIsUp()) {
            timer.setTimer(chargeTime);
            if(stackedCharges < maxStackedCharges) {
                stackedCharges ++;
                currentCharges --;
            }
        }
        if(keyDown){
            if(emitTimer.timerIsUp() && stackedCharges > 0) {
                emitTimer.setTimer(emitTime);
                B b = createBullet();
                bullets.add(b);
                return b;
            }
        }
        return null;
    }

    protected abstract B createBullet();

    public int getCurrentCharges() {
        return currentCharges;
    }

    public double getChargeTime() {
        return chargeTime;
    }

    public double getEmitTime() {
        return emitTime;
    }

    public int getMaxStackedCharges() {
        return maxStackedCharges;
    }

    public int getStackedCharges() {
        return stackedCharges;
    }

    public ArrayList<B> getBullets() {
        return bullets;
    }

    public Time getTimer() {
        return timer;
    }

    public Time getEmitTimer() {
        return emitTimer;
    }
}
