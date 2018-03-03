package engine.linear.gui;

import engine.linear.material.GuiElement;

/**
 * Created by Luecx on 05.03.2017.
 */
public class GuiPanel extends GuiElement {
    public GuiPanel(int colorMap) {
        super(colorMap);
    }

    public GuiPanel(String colorMap) {
        super(colorMap);
    }

    public GuiPanel(int colorMap, int displayMode) {
        super(colorMap, displayMode);
    }

    public GuiPanel(String colorMap, int displayMode) {
        super(colorMap, displayMode);
    }

    @Override
    public void update() {

    }
}
