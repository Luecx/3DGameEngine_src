package engine.linear.gui;

import engine.linear.material.GuiElement;
import org.lwjgl.input.Mouse;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;

/**
 * Created by Luecx on 05.03.2017.
 */
public abstract class GuiButton extends GuiElement {


    private Vector3f hoverColor = new Vector3f(0.3f,0.3f,0.3f);
    private float hoverBlendFactor = 0.4f;
    private boolean hoverColorEnabled = true;

    private Vector3f clickColor = new Vector3f(0,0,0);;
    private float clickBlendFactor = 0.7f;
    private boolean clickColorEnabled = true;

    private boolean enabled = true;

    private Vector3f blendCOld = new Vector3f(0,0,0);
    private float blendFOld;

    public GuiButton(int colorMap) {
        super(colorMap);
    }

    public GuiButton(String colorMap) {
        super(colorMap);
    }

    public GuiButton(int colorMap, int displayMode) {
        super(colorMap, displayMode);
    }

    public GuiButton(String colorMap, int displayMode) {
        super(colorMap, displayMode);
    }

    public boolean clickOnScreen(int x, int y){
        Vector2f loc = GuiElement.pixelToClipLocation(new Vector2f(x,y));
        return Math.abs(loc.x - this.getLocation().x) < this.getScale().x &&
                Math.abs(loc.y - this.getLocation().y) < this.getScale().y;
    }

    @Override @Deprecated
    public void setRotation(float rotation) {
    }

    public Vector3f getHoverColor() {
        return hoverColor;
    }

    public void setHoverColor(Vector3f hoverColor) {
        this.hoverColor = hoverColor;
    }

    public float getHoverBlendFactor() {
        return hoverBlendFactor;
    }

    public void setHoverBlendFactor(float hoverBlendFactor) {
        this.hoverBlendFactor = hoverBlendFactor;
    }

    public boolean isHoverColorEnabled() {
        return hoverColorEnabled;
    }

    public void setHoverColorEnabled(boolean hoverColorEnabled) {
        this.hoverColorEnabled = hoverColorEnabled;
    }

    public Vector3f getClickColor() {
        return clickColor;
    }

    public void setClickColor(Vector3f clickColor) {
        this.clickColor = clickColor;
    }

    public float getClickBlendFactor() {
        return clickBlendFactor;
    }

    public void setClickBlendFactor(float clickBlendFactor) {
        this.clickBlendFactor = clickBlendFactor;
    }

    public boolean isClickColorEnabled() {
        return clickColorEnabled;
    }

    public void setClickColorEnabled(boolean clickColorEnabled) {
        this.clickColorEnabled = clickColorEnabled;
    }

    @Override
    public void setBlendColor(Vector3f blendColor) {
        this.blendCOld = blendColor;
    }

    @Override
    public void setBlendFactor(float blendFactor) {
        this.blendFOld = blendFactor;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    @Override
    public void update() {
        if (this.clickOnScreen(Mouse.getX(), Mouse.getY()) && this.enabled) {
            if(Mouse.isButtonDown(0)) {
                if(clickColorEnabled){
                    super.setBlendColor(this.clickColor);
                    super.setBlendFactor(this.clickBlendFactor);
                }
            }else{
                if(hoverColorEnabled){
                    super.setBlendColor(this.hoverColor);
                    super.setBlendFactor(this.hoverBlendFactor);
                }
            }
        }else{
            super.setBlendColor(blendCOld);
            super.setBlendFactor(blendFOld);
        }
    }

    public abstract void onClick();
}
