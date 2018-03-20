package engine.debugging.entityeditor;

import engine.core.master.DisplayManager;
import engine.core.system.Sys;
import engine.linear.loading.Loader;
import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.AWTGLCanvas;
import org.lwjgl.opengl.DisplayMode;

/**
 * Created by finne on 15.03.2018.
 */
public class MyAWTGLCanvas extends AWTGLCanvas {

    public MyAWTGLCanvas() throws LWJGLException {

    }

    public void start( ){
        DisplayManager.createDisplay(this, 800,600);
        Sys.enableAll();

        while(DisplayManager.isCloseRequested() == false) {
            DisplayManager.updateDisplay();
        }

        Sys.disableAll();
        Loader.cleanUp();
        DisplayManager.closeDisplay();
    }
}
