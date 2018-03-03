package projects.game.core;

import engine.core.master.Time;

/**
 * Created by Luecx on 28.02.2017.
 */
public abstract class GameCore extends Thread{

    public static final double MODE_16_TICK = 1d / 16d;
    public static final double MODE_64_TICK = 1d / 64d;
    public static final double MODE_128_TICK = 1d / 128d ;

    private double updateMode;
    private boolean isRunning = false;

    private Time timer = new Time();
    private double processedTime  = 0.1d;

    public GameCore(double updateMode) {
        this.updateMode = updateMode;
    }

    public void run() {
        while(!this.isInterrupted()){
            try{
                Thread.sleep((int)(1000 * updateMode), (int) (updateMode * 1000000000 - (int) (updateMode * 1000) * 1000000));
                processedTime = Math.min(0.1d,timer.getPassedCheckPointTime());
                timer.setCheckPoint();
                if(this.isRunning()) {
                    this.update();
                }
            } catch (Exception e){
                this.interrupt();
            }
        }
        this.interrupt();
    }

    protected abstract void update();

    public double getProcessedTime() {
        return processedTime;
    }

    public void setRunning(boolean running) {
        this.isRunning = running;
    }

    public boolean isRunning() {
        return this.isRunning;
    }

    public void close() {
        this.interrupt();
    }


}
