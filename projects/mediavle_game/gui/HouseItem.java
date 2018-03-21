package projects.mediavle_game.gui;

import engine.core.exceptions.CoreException;
import engine.core.system.Sys;
import engine.linear.gui.GuiButton;
import engine.linear.material.GuiElement;
import org.lwjgl.util.vector.Vector2f;
import projects.mediavle_game.map.entities.abs.UniqueGameEntity;
import projects.mediavle_game.map.entities.obs.Townhall;
import projects.mediavle_game.player.Player;

/**
 * Created by finne on 21.03.2018.
 */
public enum HouseItem {

    TOWNHALL(new Townhall(0,0), 100,100, "raw");

    private UniqueGameEntity uniqueGameEntity;
    private int guiX;
    private int guiY;
    private String guiTexture;

    public GuiButton guiButton;

    static void generateButtons() {
        TOWNHALL.generateButton();
    }

    private void generateButton(){
        HouseItem i = this;
        guiButton = new GuiButton(guiTexture) {
            @Override
            public void onClick() {
                Player.setItem(i);
            }
        };
        guiButton.setDisplayMode(GuiElement.DISPLAY_MODE_PIXEL_SPACE);
        guiButton.setLocation(new Vector2f(guiX,guiY));
        guiButton.setScale(new Vector2f(100,100));
        try {
            Sys.OVERLAY_SYSTEM.addElement(guiButton);
        } catch (CoreException e) {
            e.printStackTrace();
        }
    }

    private HouseItem(UniqueGameEntity uniqueGameEntity, int guiX, int guiY, String guiTexture) {
        this.uniqueGameEntity = uniqueGameEntity;
        this.guiX = guiX;
        this.guiY = guiY;
        this.guiTexture = guiTexture;
    }

    public UniqueGameEntity getUniqueGameEntity() {
        return uniqueGameEntity;
    }

    public void setUniqueGameEntity(UniqueGameEntity uniqueGameEntity) {
        this.uniqueGameEntity = uniqueGameEntity;
    }

}
