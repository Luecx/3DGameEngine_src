package projects.mediavle_game.player.items;

import engine.linear.gui.GuiButton;
import engine.linear.material.GuiElement;
import org.lwjgl.util.vector.Vector2f;
import projects.mediavle_game.player.Player;

/**
 * Created by finne on 21.03.2018.
 */
public class Item {


    private Player player;

    public static final int TYPE_UTIL = 1;
    public static final int TYPE_HOUSE = 2;

    private int type;

    public GuiButton guiButton;

    public Item(Player player, int guiX, int guiY, String guiTexture, int type) {

        this.type = type;
        this.player = player;

        guiButton = new GuiButton(guiTexture) {
            @Override
            public void onClick() {

            }
        };
        guiButton.setDisplayMode(GuiElement.DISPLAY_MODE_PIXEL_SPACE);
        guiButton.setLocation(new Vector2f(guiX,guiY));
        guiButton.setScale(new Vector2f(32,32));
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
