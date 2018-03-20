package projects.mediavle_game.player;

import engine.core.master.DisplayManager;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.util.vector.Vector3f;
import projects.mediavle_game.map.GroundMap;

public class Player {
    private PlayerCamera perspectiveCamera = new PlayerCamera(500, 2, 500);
    private float mouseSens = 0.1f;

    public void move(GroundMap groundMap) {
        perspectiveCamera.increaseRotation(Mouse.getDY() * mouseSens, Mouse.getDX() * -1 * mouseSens, 0);
        if (perspectiveCamera.getRotation().x > 90)
            perspectiveCamera.getRotation().x = 90;
        else if (perspectiveCamera.getRotation().x < -90)
            perspectiveCamera.getRotation().x = -90;

        Vector3f forward = (Vector3f) (perspectiveCamera.getZAxis().negate());
        Vector3f sideward = (Vector3f) (perspectiveCamera.getXAxis().negate());
        Vector3f direction = (Vector3f) (new Vector3f(forward.x, 0, forward.z).normalise().scale(4 * (float) DisplayManager.processedFrameTime()));
        Vector3f directionSide = (Vector3f) (new Vector3f(forward.x, 0, forward.z).normalise().scale(4 * (float) DisplayManager.processedFrameTime()));

        if (Keyboard.isKeyDown(Keyboard.KEY_W))
            perspectiveCamera.increasePosition(direction);
        if (Keyboard.isKeyDown(Keyboard.KEY_S))
            perspectiveCamera.increasePosition(new Vector3f(direction.x * -.2f, 0, direction.z * -.2f));
        if (Keyboard.isKeyDown(Keyboard.KEY_A))
            perspectiveCamera.increasePosition(directionSide);
        if (Keyboard.isKeyDown(Keyboard.KEY_D))
            perspectiveCamera.increasePosition(new Vector3f(direction.z * .5f, 0, direction.x * .5f));
    }

    public PlayerCamera getPerspectiveCamera() {
        return perspectiveCamera;
    }

    public void setPerspectiveCamera(PlayerCamera perspectiveCamera) {
        this.perspectiveCamera = perspectiveCamera;
    }
}
