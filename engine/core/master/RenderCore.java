package engine.core.master;

import engine.core.system.Sys;
import engine.linear.loading.Loader;

/**
 * Created by Luecx on 03.01.2017.
 */
public abstract class RenderCore extends Thread {

    private boolean stopped = false;

    public RenderCore() {
        this.start();
    }

    public void run() {
        DisplayManager.createDisplay();
        onEnable();
        while(DisplayManager.isCloseRequested() == false && !stopped){
            DisplayManager.updateDisplay();
            render();
        }
        Sys.disableAll();
        onDisable();
        Loader.cleanUp();
        DisplayManager.closeDisplay();
    }

    public void stopRenderThread(){
        stopped = true;
    }

    protected abstract void onEnable();

    protected abstract void onDisable();

    protected abstract void render();

}
