package projects.mediavle_game.gui;

import engine.core.system.Sys;
import engine.linear.gui.GuiPanel;
import engine.linear.material.GuiElement;
import org.lwjgl.util.vector.Vector2f;
import projects.mediavle_game.player.items.Item;

import java.util.ArrayList;

/**
 * Created by finne on 21.03.2018.
 */
public class GuiInit {

    public static GuiPanel crosshair;

    public static GuiPanel item_util;
    public static GuiPanel item_house;

    private ArrayList<Item> house_item_list = new ArrayList<>();
    private ArrayList<Item> util_item_list = new ArrayList<>();

    public static void generateTextures() {
        crosshair = new GuiPanel("crosshair");
        crosshair.setLocation(new Vector2f(0,0));

        crosshair.setDisplayMode(GuiElement.DISPLAY_MODE_PIXEL_SPACE);
        crosshair.setScale(new Vector2f(48,48));
    }

    public static void setItem(Item i) {
        if(i.getType() == Item.TYPE_HOUSE) item_house.setColorMap(i.guiButton.getColorMap());
        else{
            item_util.setColorMap(i.guiButton.getColorMap());
        }
    }

    public static void initGui() {
        try{
            Sys.OVERLAY_SYSTEM.addElement(crosshair);

        }catch (Exception e){
            e.printStackTrace();
        }
    }




}
