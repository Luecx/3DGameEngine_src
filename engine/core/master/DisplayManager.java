/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package engine.core.master;

import org.lwjgl.LWJGLException;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.*;


/**
 *
 * @author Luec
 */
public class DisplayManager {
    
    private static final int WIDTH = 1920;
    private static final int HEIGHT = 1080;
    private static final int FPS_CAP = 120;
    
    
    private static Time time = new Time();
    private static double frameTime = 0;
    
    public static void createDisplay() {

        ContextAttribs attribs = new ContextAttribs(3,3).withForwardCompatible(true).withProfileCore(true);

        try {
            
        	DisplayMode modes[] = Display.getAvailableDisplayModes();
            DisplayMode mode = new DisplayMode(WIDTH,HEIGHT);
            
			for (int i=0; i< modes.length; i++) {
               DisplayMode m = modes[i];
               int bpp = Display.getDisplayMode().getBitsPerPixel();
               if (m.getBitsPerPixel() == bpp
                ) {
                   if ( m.getWidth() <= WIDTH && m.getHeight() <= HEIGHT &&
                        m.getFrequency() <= 144)
                       mode = m;
                   if ( m.getWidth() == WIDTH && m.getHeight() == HEIGHT &&
                        m.getFrequency() == 60)
                       break;
                   }
            }
			 
            Display.setDisplayMode(mode);
            Display.create(new PixelFormat().withSamples(1), attribs);
            Display.setFullscreen(false);
            Mouse.setGrabbed(false);
            GL11.glHint(GL11.GL_PERSPECTIVE_CORRECTION_HINT, GL11.GL_NICEST);
          
        } catch (LWJGLException ex) {
        	ex.printStackTrace();
        }
        
        GL11.glViewport(0, 0, WIDTH, HEIGHT);
        time.setCheckPoint();
        
    }

    public static void createDisplay(AWTGLCanvas canvas, int width, int height) {

        ContextAttribs attribs = new ContextAttribs(3,3).withForwardCompatible(true).withProfileCore(true);

        try {

            DisplayMode modes[] = Display.getAvailableDisplayModes();
            DisplayMode mode = new DisplayMode(width,height);

            for (int i=0; i< modes.length; i++) {
                DisplayMode m = modes[i];
                int bpp = Display.getDisplayMode().getBitsPerPixel();
                if (m.getBitsPerPixel() == bpp
                        ) {
                    if ( m.getWidth() <= WIDTH && m.getHeight() <= HEIGHT &&
                            m.getFrequency() <= 144)
                        mode = m;
                    if ( m.getWidth() == WIDTH && m.getHeight() == HEIGHT &&
                            m.getFrequency() == 60)
                        break;
                }
            }

            Display.setDisplayMode(mode);
            Display.create(new PixelFormat().withSamples(1), attribs);
            Display.setParent(canvas);
            Display.setVSyncEnabled(true);
            Mouse.setGrabbed(true);
            GL11.glHint(GL11.GL_PERSPECTIVE_CORRECTION_HINT, GL11.GL_NICEST);

        } catch (LWJGLException ex) {
            ex.printStackTrace();
        }

        GL11.glViewport(0, 0, WIDTH, HEIGHT);
        time.setCheckPoint();

    }


    public static void updateDisplay() {
        Display.sync(FPS_CAP);
        Display.update();
        clearBuffer();

        frameTime = time.getPassedCheckPointTime();
        time.setCheckPoint();
        
    }
    
    public static void clearBuffer() {

        GL11.glClear(GL11.GL_COLOR_BUFFER_BIT| GL11.GL_DEPTH_BUFFER_BIT);
        GL11.glClearColor(0.2f,0.1f,0.1f , 1f);
        GL11.glClearColor(0,0,0, 1f);
    }
    
    public static void closeDisplay(){
        Display.destroy();
    }
 
    public static boolean isCloseRequested() {
    	return Display.isCloseRequested();
    }
   
    public static double processedFrameTime() {
    	if(frameTime > 0.1){
    		return 0.1;
    	}
    	return frameTime;
    }
}
