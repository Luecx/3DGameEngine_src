package engine.linear.material;

import engine.core.sourceelements.RawModel;
import engine.core.sourceelements.SourceElement;
import engine.render.overlaysystem.OverlayRenderer;
import org.lwjgl.opengl.Display;
import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;

/**
 * Created by Luecx on 05.03.2017.
 */
public abstract class GuiElement extends Material implements SourceElement{

    public static final int DISPLAY_MODE_PIXEL_SPACE = 1;
    public static final int DISPLAY_MODE_CLIP_SPACE = 2;

    public static final int ANIMATION_NO_ANIMATION = 0;
    public static final int ANIMATION_NO_UPDATE= 1;
    public static final int ANIMATION_PROGRESS_TO_B= 2;
    public static final int ANIMATION_PROGRESS_TO_A= 3;

    private int displayMode;
    private boolean visible = true;

    private Vector2f location = new Vector2f(0,0);
    private Vector2f scale = new Vector2f(0.5f,1);
    private float rotation;

    private boolean trigonometricInterpolation;
    private int animationStatus = 0;
    private float animationFrac = 0f;
    private float animationTime = 1f;
    private Vector2f animationAnchorA = new Vector2f();
    private Vector2f animationAnchorB = new Vector2f();

    protected Vector3f blendColor = new Vector3f();
    protected float blendFactor = 0;

    private boolean blackWhite = false;
    private boolean ignoreAlpha = false;

    private boolean matrixDeprecated = true;
    private Matrix4f transformationMatrix;

    public GuiElement(int colorMap) {
        super(colorMap);
    }

    public GuiElement(String colorMap) {
        super(colorMap);
    }

    public GuiElement(int colorMap, int displayMode) {
        super(colorMap);
        this.displayMode = displayMode;
    }

    public boolean isVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    public GuiElement(String colorMap, int displayMode) {
        super(colorMap);
        this.displayMode = displayMode;
    }

    public void setAnimation(Vector2f pointA, Vector2f pointB, float animationTime){
        if(this.displayMode == DISPLAY_MODE_PIXEL_SPACE){
            pointA = pixelToClipLocation(pointA);
            pointB = pixelToClipLocation(pointB);
        }
        animationStatus = 1;
        this.animationTime = animationTime;
        this.animationAnchorA = pointA;
        this.animationAnchorB = pointB;
    }

    public void setAnimationFrac(float animationFrac) {
        if(animationStatus == ANIMATION_NO_ANIMATION) return;
        this.animationFrac = animationFrac;
    }

    public void updateAnimation(float progressedTime){
        if(animationStatus == ANIMATION_NO_ANIMATION) return;
        if(animationStatus == ANIMATION_NO_UPDATE) {}
        else if(animationStatus == ANIMATION_PROGRESS_TO_B){
            animationFrac += progressedTime / animationTime;
        } else if(animationStatus == ANIMATION_PROGRESS_TO_A){
            animationFrac -= progressedTime / animationTime;
        }
        if(animationFrac > 1) {animationFrac = 1; this.animationStatus = ANIMATION_NO_UPDATE;}
        if(animationFrac < 0) {animationFrac = 0; this.animationStatus = ANIMATION_NO_UPDATE;}
        this.location = trigonometricInterpolation ? trigonometricInterpolation(animationAnchorA, animationAnchorB, animationFrac):
                linearInterpolation(animationAnchorA, animationAnchorB, animationFrac);
        this.matrixDeprecated = true;
    }

    public int getDisplayMode() {
        return displayMode;
    }

    public void setDisplayMode(int displayMode) {
        if(displayMode != DISPLAY_MODE_CLIP_SPACE && displayMode != DISPLAY_MODE_PIXEL_SPACE) return;
        this.displayMode = displayMode;
        this.matrixDeprecated = true;
    }

    public Vector2f getLocation() {
        this.matrixDeprecated = true;
        return location;
    }

    public void setLocation(Vector2f location) {
        if(this.displayMode == DISPLAY_MODE_PIXEL_SPACE) location = pixelToClipLocation(location);
        this.location = location;
        this.matrixDeprecated = true;
    }

    public Vector2f getScale() {
        this.matrixDeprecated = true;
        return scale;
    }

    public void setScale(Vector2f scale) {
        if(this.displayMode == DISPLAY_MODE_PIXEL_SPACE) scale = pixelToClipScale(scale);
            this.scale = scale;
        this.matrixDeprecated = true;
    }

    public float getRotation() {
        return rotation;
    }

    public void setRotation(float rotation) {
        this.rotation = rotation;
        this.matrixDeprecated = true;
    }

    public boolean isBlackWhite() {
        return blackWhite;
    }

    public void setBlackWhite(boolean blackWhite) {
        this.blackWhite = blackWhite;
    }

    public boolean isIgnoreAlpha() {
        return ignoreAlpha;
    }

    public void setIgnoreAlpha(boolean ignoreAlpha) {
        this.ignoreAlpha = ignoreAlpha;
    }

    public void enableTrigonometricInterpolation(){
        this.trigonometricInterpolation = true;
    }

    public void disableTrigonometricInterpolation() {
        this.trigonometricInterpolation = false;
    }

    public boolean isTrigonometricInterpolation() {
        return trigonometricInterpolation;
    }

    public int getAnimationStatus() {
        return animationStatus;
    }

    public void setAnimationStatus(int animationStatus) {
        if(animationStatus != ANIMATION_NO_ANIMATION &&animationStatus != ANIMATION_NO_UPDATE &&
                animationStatus != ANIMATION_PROGRESS_TO_B &&animationStatus != ANIMATION_PROGRESS_TO_A)return;
        this.animationStatus = animationStatus;
    }

    public float getAnimationTime() {
        return animationTime;
    }

    public void setAnimationTime(float animationTime) {
        this.animationTime = animationTime;
    }

    public Vector2f getAnimationAnchorA() {
        return animationAnchorA;
    }

    public void setAnimationAnchorA(Vector2f animationAnchorA) {
        this.animationAnchorA = animationAnchorA;
    }

    public Vector2f getAnimationAnchorB() {
        return animationAnchorB;
    }

    public void setAnimationAnchorB(Vector2f animationAnchorB) {
        this.animationAnchorB = animationAnchorB;
    }

    public float getAnimationFrac() {
        return animationFrac;
    }

    public boolean isMatrixDeprecated() {
        return matrixDeprecated;
    }

    public Vector3f getBlendColor() {
        return blendColor;
    }

    public void setBlendColor(Vector3f blendColor) {
        this.blendColor = blendColor;
    }

    public float getBlendFactor() {
        return blendFactor;
    }

    public void setBlendFactor(float blendFactor) {
        this.blendFactor = blendFactor;
    }

    public static Vector2f pixelToClipLocation(Vector2f in){
        return new Vector2f((2 * in.x) / (float)Display.getDisplayMode().getWidth() - 1, (2 *in.y) /(float) Display.getDisplayMode().getHeight() - 1);
    }

    public static Vector2f pixelToClipScale(Vector2f in){
        return new Vector2f(in.x / (float)Display.getDisplayMode().getWidth(), in.y /(float) Display.getDisplayMode().getHeight());
    }

    public static Vector2f linearInterpolation(Vector2f a, Vector2f b, float frac){
        return Vector2f.add(a, (Vector2f)Vector2f.sub(b, a, null).scale(frac), null);
    }

    public static Vector2f trigonometricInterpolation(Vector2f a, Vector2f b, float frac){
        double c = (float)((Math.cos((frac * Math.PI)-Math.PI) * 0.5 + 0.5));
        return new Vector2f((float)(c * (b.x-a.x) + a.x),
                (float)(c * (b.y-a.y) + a.y));
    }

    @Override
    public RawModel getRawModel() {
        return OverlayRenderer.quad;
    }

    public Matrix4f getTransformationMatrix() {
        if(this.matrixDeprecated == true){
            transformationMatrix = new Matrix4f();
            transformationMatrix.setIdentity();
            Matrix4f.translate(this.location, transformationMatrix, transformationMatrix);
            Matrix4f.scale(new Vector3f(this.scale.x, this.scale.y, 1f), transformationMatrix, transformationMatrix);
            Matrix4f.rotate((float)Math.toRadians(this.rotation), new Vector3f(0,0,1),transformationMatrix, transformationMatrix);
        }
        return this.transformationMatrix;
    }

    public abstract void update();
}

