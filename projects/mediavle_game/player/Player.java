package projects.mediavle_game.player;

import engine.core.master.DisplayManager;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;
import projects.mediavle_game.map.GroundMap;

public class Player {
    private PlayerCamera perspectiveCamera = new PlayerCamera(500, 1.8f, 500);
    private float mouseSens = 0.1f;
    private float forwardSpeed = 4;
    private int score;
    private float totalWalked;

    public void move(GroundMap groundMap) {
        perspectiveCamera.increaseRotation(Mouse.getDY() * mouseSens, Mouse.getDX() * -1 * mouseSens, 0);
        if (perspectiveCamera.getRotation().x > 90)
            perspectiveCamera.getRotation().x = 90;
        else if (perspectiveCamera.getRotation().x < -90)
            perspectiveCamera.getRotation().x = -90;

        if (Keyboard.isKeyDown(Keyboard.KEY_LSHIFT))
            forwardSpeed = 20;
        else
            forwardSpeed = 4;

        Vector3f forward = (Vector3f) (perspectiveCamera.getZAxis().negate());
        Vector3f sideward = (Vector3f) (perspectiveCamera.getXAxis().negate());
        Vector3f direction = (Vector3f) (new Vector3f(forward.x, 0, forward.z).normalise().scale(forwardSpeed * (float) DisplayManager.processedFrameTime()));
        Vector3f directionSide = (Vector3f) (new Vector3f(sideward.x, 0, sideward.z).normalise().scale(2 * (float) DisplayManager.processedFrameTime()));

        Vector3f pos = new Vector3f(perspectiveCamera.getPosition());


        if (Keyboard.isKeyDown(Keyboard.KEY_W))
            perspectiveCamera.increasePosition(direction);
        if (Keyboard.isKeyDown(Keyboard.KEY_S))
            perspectiveCamera.increasePosition((Vector3f) direction.scale(-.2f));
        if (Keyboard.isKeyDown(Keyboard.KEY_A))
            perspectiveCamera.increasePosition(directionSide);
        if (Keyboard.isKeyDown(Keyboard.KEY_D))
            perspectiveCamera.increasePosition((Vector3f) directionSide.negate());

        totalWalked += Vector3f.sub(perspectiveCamera.getPosition(),pos,null).length();
        System.out.println(totalWalked);

        perspectiveCamera.getPosition().y = 1.8f + (float)Math.sin(totalWalked * 2) * 0.2f;

        if (groundMap.rigidBody(perspectiveCamera.getAbsolutePosition().x, perspectiveCamera.getAbsolutePosition().z)) {
            perspectiveCamera.setPosition(pos);
        }

        if (Mouse.isButtonDown(0)) {
            Vector2f look = this.perspectiveCamera.lookingAtField();
            if(look != null) {
                if (groundMap.getFields()[(int) look.x][(int) look.y].getGameEntity() != null) {
                    groundMap.getFields()[(int) look.x][(int) look.y].getGameEntity().destroyEntity();
                    groundMap.getFields()[(int) look.x][(int) look.y].setUniqueGameEntity(null);
                    score ++;
                    System.out.println(score);
                }
            }
        }
    }


    public PlayerCamera getPerspectiveCamera() {
        return perspectiveCamera;
    }

    public void setPerspectiveCamera(PlayerCamera perspectiveCamera) {
        this.perspectiveCamera = perspectiveCamera;
    }
}
