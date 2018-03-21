package projects.mediavle_game.gui;

import engine.core.system.Sys;
import engine.linear.gui.GuiPanel;
import engine.linear.material.GuiElement;
import org.lwjgl.util.vector.Vector2f;

/**
 * Created by finne on 21.03.2018.
 */
public class GuiInit {

    static GuiPanel crosshair;

    public static void generateTextures() {
        crosshair = new GuiPanel("crosshair");
        crosshair.setLocation(new Vector2f(0,0));

        crosshair.setDisplayMode(GuiElement.DISPLAY_MODE_PIXEL_SPACE);
        crosshair.setScale(new Vector2f(48,48));
    }

    public static void initGui() {
        try{
            Sys.OVERLAY_SYSTEM.addElement(crosshair);

        }catch (Exception e){
            e.printStackTrace();
        }
    }

}
