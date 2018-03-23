package projects.mediavle_game.gui;

import engine.core.exceptions.CoreException;
import engine.core.system.Sys;
import engine.linear.gui.GuiButton;
import engine.linear.material.GuiElement;
import org.lwjgl.util.vector.Vector2f;
import projects.mediavle_game.map.entities.abs.GameEntity;
import projects.mediavle_game.map.entities.abs.UniqueGameEntity;
import projects.mediavle_game.map.entities.obs.Street;
import projects.mediavle_game.map.entities.obs.Townhall;
import projects.mediavle_game.player.Player;

/**
 * Created by finne on 21.03.2018.
 */
public enum HouseItem {

    TOWNHALL(new Townhall(0,0), 100,100, "raw"),
    STREET(new Street(0,0), 220, 100, "raw");

    private GameEntity gameEntity;
    private int guiX;
    private int guiY;
    private String guiTexture;

    public GuiButton guiButton;

    static void generateButtons() {
        TOWNHALL.generateButton();
        STREET.generateButton();
    }

    private void generateButton(){
        final HouseItem i = this;
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

    HouseItem(GameEntity gameEntity, int guiX, int guiY, String guiTexture) {
        this.gameEntity = gameEntity;
        this.guiX = guiX;
        this.guiY = guiY;
        this.guiTexture = guiTexture;
    }

    public GameEntity getGameEntity() {
        return gameEntity;
    }

    public void setGameEntity(GameEntity gameEntity) {
        this.gameEntity = gameEntity;
    }

}
