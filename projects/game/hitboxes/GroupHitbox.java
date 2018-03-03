package projects.game.hitboxes;

import org.lwjgl.util.vector.Vector3f;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Luecx on 16.01.2017.
 */
public class GroupHitbox extends Hitbox {


    private ArrayList<GroupHitbox> childGroups = new ArrayList<>();
    private ArrayList<ElementHitbox> childElements = new ArrayList<>();

    private Vector3f positionAccess;

    public GroupHitbox(Vector3f position, float width, float height, float length) {
        super(position, width, height, length);
        positionAccess = this.getAbsolutePosition();
    }

    @Override
    protected void absoluteDataChangedNotification() {
        positionAccess = this.getAbsolutePosition();
    }

    @Override
    public RayIntersection intersectsRay(Ray r) {
        Vector3f dirfrac = new Vector3f();
        Vector3f org = r.getRoot();
        Vector3f dir = r.getDirection();
        dirfrac.x = 1.0f / dir.x;
        dirfrac.y = 1.0f / dir.y;
        dirfrac.z = 1.0f / dir.z;
        float t1 = (positionAccess.x - width - org.x)*dirfrac.x;
        float t2 = (positionAccess.x + width - org.x)*dirfrac.x;
        float t3 = (positionAccess.y - height - org.y)*dirfrac.y;
        float t4 = (positionAccess.y + height - org.y)*dirfrac.y;
        float t5 = (positionAccess.z - length - org.z)*dirfrac.z;
        float t6 = (positionAccess.z + length - org.z)*dirfrac.z;

        float tmin = Math.max(Math.max(Math.min(t1, t2), Math.min(t3, t4)), Math.min(t5, t6));
        float tmax = Math.min(Math.min(Math.max(t1, t2), Math.max(t3, t4)), Math.max(t5, t6));

        if (tmax < 0)
        {
            return new RayIntersection(this);
        }

        if (tmin > tmax)
        {
            return new RayIntersection(this);
        }
        return new RayIntersection(this, new Vector3f(0,0,0));
    }


    public void addChildGroup(GroupHitbox child) {
        this.childGroups.add(child);
    }

    public void addChildGElement(ElementHitbox child) {
        this.childElements.add(child);
    }

    public void getAllIntersections(Ray r, List<RayIntersection> listToFill) {
        for(ElementHitbox hitbox:childElements){
            RayIntersection inter = hitbox.intersectsRay(r);
            if(inter.intersects()){
                listToFill.add(inter);
            }
        }
        for(GroupHitbox child:childGroups){
            if(child.intersectsRay(r).intersects()){
                child.getAllIntersections(r, listToFill);
            }
        }
    }


    @Override@Deprecated
    public synchronized Vector3f getRotation() {
        return super.getRotation();
    }

    @Override@Deprecated
    public synchronized void setRotation(Vector3f rotation) {
        super.setRotation(rotation);
    }

    @Override@Deprecated
    public synchronized void setRotation(float x, float y, float z) {
        super.setRotation(x, y, z);
    }

    @Override@Deprecated
    public synchronized void increaseRotation(Vector3f a) {
        super.increaseRotation(a);
    }

    @Override@Deprecated
    public synchronized void increaseRotation(float x, float y, float z) {
        super.increaseRotation(x, y, z);
    }

    @Override@Deprecated
    public synchronized void increaseRotation(Vector3f axis, float value) {
        super.increaseRotation(axis, value);
    }
}
