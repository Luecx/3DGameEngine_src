package projects.game.hitboxes;

import org.lwjgl.util.vector.Vector3f;

/**
 * Created by Luecx on 16.01.2017.
 */
public class RayIntersection {

    private Vector3f[] impacts;
    private Hitbox element;

    public RayIntersection(Hitbox element, Vector3f... impacts) {
        this.impacts = impacts;
        this.element = element;
    }

    public Vector3f[] getImpacts() {
        return impacts;
    }

    public void setImpacts(Vector3f[] impacts) {
        this.impacts = impacts;
    }

    public boolean intersects() {
        return impacts.length > 0;
    }

    public Hitbox getElement() {
        return element;
    }

    public void setElement(Hitbox element) {
        this.element = element;
    }
}
