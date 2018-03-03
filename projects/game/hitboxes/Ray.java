package projects.game.hitboxes;

import org.lwjgl.util.vector.Vector3f;

/**
 * Created by Luecx on 16.01.2017.
 */
public class Ray {

    private Vector3f root;
    private Vector3f direction;

    public Ray(Vector3f root, Vector3f direction) {
        this.root = root;
        direction.normalise(this.direction);
    }

    public Vector3f getRoot() {
        return root;
    }

    public void setRoot(Vector3f root) {
        this.root = root;
    }

    public Vector3f getDirection() {
        return direction;
    }

    public void setDirection(Vector3f direction) {
        direction.normalise(this.direction);
    }
}