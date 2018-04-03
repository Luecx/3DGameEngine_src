package projects.vier_gewinnt_v2.visual;

import engine.core.components.PerspectiveCamera;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.util.vector.Vector3f;

/**
 * Created by finne on 30.03.2018.
 */
public class GameCamera extends PerspectiveCamera{

    private float lookAtDistance = 5;

    public GameCamera() {
    }

    public GameCamera(float x, float y, float z, float rx, float ry, float rz) {
        super(x, y, z, rx, ry, rz);
    }

    public GameCamera(float x, float y, float z) {
        super(x, y, z);
    }

    public GameCamera(Vector3f position, Vector3f rotation) {
        super(position, rotation);
    }

    public Vector3f lookAt(int stretch) {
        Vector3f l = (Vector3f) Vector3f.add(
                (Vector3f) this.getZAxis().negate().normalise().scale(lookAtDistance),
                this.getAbsolutePosition(),
                null
        ).scale(1 / (float) stretch);
        return l;
    }

    public void move() {
        super.move();
        lookAtDistance += (float)Mouse.getDWheel() / 100;
        lookAtDistance = Math.min(40, Math.max(lookAtDistance, 0));
    }
}
