package projects.vier_gewinnt.logic.server;

import engine.core.components.PerspectiveCamera;
import net.java.games.input.Component;
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
        this.increaseRotation((float)Mouse.getDY() * 0.1f, -(float)Mouse.getDX() * 0.1f,0);
        lookAtDistance += (float)Mouse.getDWheel() / 100;
        this.getPosition().y += Keyboard.isKeyDown(Keyboard.KEY_SPACE) ? 0.1:0;
        this.getPosition().y -= Keyboard.isKeyDown(Keyboard.KEY_LSHIFT) ? 0.1:0;
        lookAtDistance = Math.min(40, Math.max(lookAtDistance, 0));
    }
}
