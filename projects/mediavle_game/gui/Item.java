package projects.mediavle_game.gui;

import engine.linear.gui.GuiButton;
import engine.linear.material.GuiElement;
import org.lwjgl.util.vector.Vector2f;
import projects.mediavle_game.map.GroundMap;
import projects.mediavle_game.map.entities.abs.UniqueGameEntity;
import projects.mediavle_game.player.Player;

/**
 * Created by finne on 21.03.2018.
 */
public abstract class Item {


    private Player player;
    private GroundMap groundMap;

    public static final int TYPE_UTIL = 1;
    public static final int TYPE_HOUSE = 2;

    private int type;
    private UniqueGameEntity uniqueGameEntity;

    public GuiButton guiButton;

    public Item(GroundMap groundMap, Player player, int guiX, int guiY, String guiTexture, int type, UniqueGameEntity entity) {

        this.uniqueGameEntity = entity;
        this.type = type;
        this.player = player;
        this.groundMap = groundMap;

        Item i = this;

        guiButton = new GuiButton(guiTexture) {
            @Override
            public void onClick() {
                player.setItem(i);
            }
        };
        guiButton.setDisplayMode(GuiElement.DISPLAY_MODE_PIXEL_SPACE);
        guiButton.setLocation(new Vector2f(guiX,guiY));
        guiButton.setScale(new Vector2f(100,100));
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public abstract void onAction_Util();

    public UniqueGameEntity getUniqueGameEntity() {
        return uniqueGameEntity;
    }

    public void setUniqueGameEntity(UniqueGameEntity uniqueGameEntity) {
        this.uniqueGameEntity = uniqueGameEntity;
    }

}
