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

    public static GuiPanel item_util;
    public static GuiPanel item_house;

    private static ArrayList<Item> house_item_list = new ArrayList<>();
    private static ArrayList<Item> util_item_list = new ArrayList<>();

    public static void generateTextures(GroundMap map, Player player) {
        crosshair = new GuiPanel("crosshair");
        crosshair.setLocation(new Vector2f(0,0));

        crosshair.setDisplayMode(GuiElement.DISPLAY_MODE_PIXEL_SPACE);
        crosshair.setScale(new Vector2f(48,48));

        item_util = new GuiPanel("textures/colormaps/redpng");
        item_util.setDisplayMode(GuiElement.DISPLAY_MODE_PIXEL_SPACE);
        item_util.setLocation(new Vector2f(org.lwjgl.opengl.Display.getDisplayMode().getWidth() - 300,100));
        item_util.setScale(new Vector2f(100,100));

        item_house = new GuiPanel("textures/colormaps/redpng");
        item_house.setDisplayMode(GuiElement.DISPLAY_MODE_PIXEL_SPACE);
        item_house.setLocation(new Vector2f(org.lwjgl.opengl.Display.getDisplayMode().getWidth() - 190,100));
        item_house.setScale(new Vector2f(100,100));


        Item townHallItem = new Item(map, player, 100,100,"raw",Item.TYPE_HOUSE, new Townhall(0,0)) {
            @Override
            public void onAction_Util() {

            }
        };

        house_item_list.add(townHallItem);

        try{
            Sys.OVERLAY_SYSTEM.addElement(crosshair);
            for(Item i:house_item_list){
                Sys.OVERLAY_SYSTEM.addElement(i.guiButton);
            } for(Item i:util_item_list){
                Sys.OVERLAY_SYSTEM.addElement(i.guiButton);
            }
            Sys.OVERLAY_SYSTEM.addElement(item_util);
            Sys.OVERLAY_SYSTEM.addElement(item_house);
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    public static void setVisible(boolean val) {
        for(Item i:house_item_list) {
            i.guiButton.setVisible(val);
        }for(Item i:util_item_list) {
            i.guiButton.setVisible(val);
        }
        Mouse.setGrabbed(!val);
    }

    public static void setItem(Item i) {
        System.out.println(i);
        if(i.getType() == Item.TYPE_HOUSE) item_house.setColorMap(i.guiButton.getColorMap());
        else{
            item_util.setColorMap(i.guiButton.getColorMap());
        }
    }



}
