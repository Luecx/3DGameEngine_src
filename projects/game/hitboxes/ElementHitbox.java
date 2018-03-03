package projects.game.hitboxes;

import org.lwjgl.util.vector.Vector3f;

/**
 * Created by Luecx on 16.01.2017.
 */
public class ElementHitbox extends Hitbox {

    public static final int STATIC = 0;
    public static final int DYNAMIC = 1;


    private int type = 0;

    public ElementHitbox(Vector3f position, float width, float height, float length) {
        super(position, width, height, length);
    }

    public ElementHitbox(Vector3f position, float width, float height, float length, int type) {
        super(position, width, height, length);
        this.type = type;
    }

    @Override
    public RayIntersection intersectsRay(Ray r) {
        return null;
    }

    @Override
    protected void absoluteDataChangedNotification() {

    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
