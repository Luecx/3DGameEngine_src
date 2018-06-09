package projects.jet_game.client;

import engine.core.components.Camera;
import engine.core.components.Group;
import engine.core.components.PerspectiveCamera;
import engine.linear.entities.Entity;
import engine.linear.entities.TexturedModel;
import engine.linear.particles.ParticleEmitter;
import org.lwjgl.input.Keyboard;
import org.lwjgl.util.vector.Vector3f;

/**
 * Created by Luecx on 14.02.2017.
 */



public class Plane extends Group {


    public PerspectiveCamera activeCamera;

    private PerspectiveCamera frontCamera;
    private PerspectiveCamera backCamera;

    private Entity entity;

    private float forwardSpeed = 400;
    private long player_id;

    public Plane(TexturedModel model, long player_id) {
        super();

        this.player_id = player_id;
        this.setPosition(new Vector3f(0, 200, 0));
        entity = new Entity(model);
        entity.increaseRotation(new Vector3f(-90, 0, 0));
        entity.getModel().getMaterial().setTransparency(true);

        frontCamera = new PerspectiveCamera(new Vector3f(0, 8, 23), new Vector3f());
        //frontCamera = new PerspectiveCamera(new Vector3f(0, 0.76f, -5.7f), new Vector3f());
        backCamera = new PerspectiveCamera(new Vector3f(0, 0, -20), new Vector3f());
        backCamera.increaseRotation(new Vector3f(0, 180, 0));
        frontCamera.setFOV(90);
        backCamera.setFOV(110);

        activeCamera = frontCamera;

        this.addChild(entity);
        this.addChild(frontCamera);
        this.addChild(backCamera);
    }

    public Camera getActiveCamera() {
        return activeCamera;
    }

    public Camera getFrontCamera() {
        return frontCamera;
    }

    public Camera getBackCamera() {
        return backCamera;
    }

    public Entity getEntity() {
        return entity;
    }

    public void move(double passedTime) {

        Vector3f forward = this.getZAxis();
        forward.scale((float) -(passedTime * forwardSpeed / (forward.length() * 3.6)));
        this.increasePosition(forward);

        if (Keyboard.isKeyDown(Keyboard.KEY_LSHIFT)) {
            this.activeCamera = backCamera;
        }else{
            this.activeCamera = frontCamera;
        }
        if (Keyboard.isKeyDown(Keyboard.KEY_RIGHT)) {
            this.increaseRotation(getZAxis(), -0.7f);
        }
        if (Keyboard.isKeyDown(Keyboard.KEY_LEFT)) {
            this.increaseRotation(getZAxis(), 0.7f);
        }
        if (Keyboard.isKeyDown(Keyboard.KEY_UP)) {
            this.increaseRotation(getXAxis(), -0.4f);
        }
        if (Keyboard.isKeyDown(Keyboard.KEY_DOWN)) {
            this.increaseRotation(getXAxis(), 0.4f);
        }
        if (Keyboard.isKeyDown(Keyboard.KEY_A)) {
            this.increaseRotation(getYAxis(), 0.1f);
        }
        if (Keyboard.isKeyDown(Keyboard.KEY_D)) {
            this.increaseRotation(getYAxis(), -0.1f);
        }
    }

    public float getForwardSpeed() {
        return forwardSpeed;
    }

    public void setForwardSpeed(float forwardSpeed) {
        this.forwardSpeed = forwardSpeed;
    }

    public long getPlayer_id() {
        return player_id;
    }

    public void setPlayer_id(long player_id) {
        this.player_id = player_id;
    }
}
