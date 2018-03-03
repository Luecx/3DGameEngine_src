package projects.game.hitboxes;

import engine.core.components.GroupableGameObject;
import org.lwjgl.util.vector.Vector3f;

/**
 * Created by Luecx on 16.01.2017.
 */
public abstract class Hitbox extends GroupableGameObject{

    protected float width, height, length;

    public Hitbox(Vector3f position, float width, float height, float length) {
        super(position);
        this.width = width;
        this.height = height;
        this.length = length;
    }

    public abstract RayIntersection intersectsRay(Ray r);

    public float getWidth() {
        return width;
    }

    public void setWidth(float width) {
        this.width = width;
    }

    public float getHeight() {
        return height;
    }

    public void setHeight(float height) {
        this.height = height;
    }

    public float getLength() {
        return length;
    }

    public void setLength(float length) {
        this.length = length;
    }
}
