package projects.mediavle_game.gui;

import engine.core.system.Sys;
import engine.linear.gui.GuiPanel;
import engine.linear.material.GuiElement;
import org.lwjgl.input.Mouse;
import org.lwjgl.util.vector.Vector2f;
import projects.mediavle_game.map.GroundMap;
import projects.mediavle_game.map.entities.obs.Townhall;
import projects.mediavle_game.player.Player;

import java.util.ArrayList;

/**
 * Created by finne on 21.03.2018.
 */
public class GuiInit {

    public static GuiPanel crosshair;

    public static GuiPanel item_house;

    private static ArrayList<HouseItem> houseItems = new ArrayList<>();

    public static void generateTextures() {
        crosshair = new GuiPanel("crosshair");
        crosshair.setLocation(new Vector2f(0,0));
        crosshair.setDisplayMode(GuiElement.DISPLAY_MODE_PIXEL_SPACE);
        crosshair.setScale(new Vector2f(48,48));

        item_house = new GuiPanel("textures/colormaps/redpng");
        item_house.setDisplayMode(GuiElement.DISPLAY_MODE_PIXEL_SPACE);
        item_house.setLocation(new Vector2f(org.lwjgl.opengl.Display.getDisplayMode().getWidth() - 190,100));
        item_house.setScale(new Vector2f(100,100));

        HouseItem.generateButtons();

        houseItems.add(HouseItem.TOWNHALL);
        houseItems.add(HouseItem.STREET);

        try{
            Sys.OVERLAY_SYSTEM.addElement(crosshair);
            Sys.OVERLAY_SYSTEM.addElement(item_house);
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    public static void setVisible(boolean val) {
        for(HouseItem i: houseItems) {
            i.guiButton.setVisible(val);
        }
        Mouse.setGrabbed(!val);
    }

    public static void setItem(HouseItem i) {
        item_house.setColorMap(i.guiButton.getColorMap());
    }
}
