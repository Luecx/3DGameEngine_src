package projects.game.objects;

import engine.core.components.Camera;
import engine.core.components.Group;
import engine.core.components.PerspectiveCamera;
import engine.core.master.Time;
import engine.linear.entities.Entity;
import engine.linear.entities.TexturedModel;
import engine.linear.material.ParticleMaterial;
import engine.linear.particles.ParticleEmitter;
import projects.game.objects.plane.missile.MissileEnum;
import projects.game.objects.plane.missile.MissileSystem;
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
    private ParticleEmitter emitter;
    private ParticleEmitter left;
    private ParticleEmitter right;

    private float forwardSpeed = 400;

    public Plane(TexturedModel model) {
        super();

        this.setPosition(new Vector3f(0, 200, 0));
        entity = new Entity(model);
        entity.increaseRotation(new Vector3f(-90, 0, 0));
        entity.getModel().getMaterial().setTransparency(true);
        emitter = new ParticleEmitter(new Vector3f(0, 0, 0), new ParticleMaterial("textures/atlases/expl", 2));
        left = new ParticleEmitter(new Vector3f(0, -0.3f, 2f), new ParticleMaterial("textures/atlases/triebwerk", 4));
        right = new ParticleEmitter(new Vector3f(0, -0.3f, 3), new ParticleMaterial("textures/atlases/smoke_atlas", 4));

        frontCamera = new PerspectiveCamera(new Vector3f(0, 8, 23), new Vector3f());
        //frontCamera = new PerspectiveCamera(new Vector3f(0, 0.76f, -5.7f), new Vector3f());
        backCamera = new PerspectiveCamera(new Vector3f(0, 0, -20), new Vector3f());
        backCamera.increaseRotation(new Vector3f(0, 180, 0));
        frontCamera.setFOV(90);
        backCamera.setFOV(110);

        activeCamera = frontCamera;

        this.addChild(left);
        this.addChild(right);
        this.addChild(emitter);
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

    public ParticleEmitter getEmitter() {
        return this.emitter;
    }

    public ParticleEmitter getLeft() {
        return left;
    }

    public ParticleEmitter getRight() {
        return right;
    }

    private boolean engineOff = false;


    Time fire = new Time();
    int count = 0;
    boolean fires = true;

    public void move(double passedTime) {


        if(Keyboard.isKeyDown(Keyboard.KEY_Q)){
            forwardSpeed -= passedTime * 1000;
        }
        if(Keyboard.isKeyDown(Keyboard.KEY_E)){
            forwardSpeed += passedTime * 1000;
        }
        if(Keyboard.isKeyDown(Keyboard.KEY_F)) {
            MissileSystem.addMissile(MissileEnum.POWER_B, this.getPosition(), this.getRotation(), this.getPosition());
        }

        Vector3f forward = this.getZAxis();
        forward.scale((float) -(passedTime * forwardSpeed / (forward.length() * 3.6)));
        this.increasePosition(forward);

        for(int i = 0; i < 2; i++){

            left.emittParticle(new Vector3f((float) (-forward.x + Math.random() * 0.1 - 0.05),
                            (float) (-forward.y + Math.random() * 0.1 - 0.05),-forward.z), -120f,
                    3f, 0.06f);
            right.emittParticle(new Vector3f((float) (-forward.x + Math.random() * 0.1 - 0.05),
                            (float) (-forward.y + Math.random() * 0.1 - 0.05),-forward.z), -120f,
                    1.5f, 0.3f);
        }


        if (Keyboard.isKeyDown(Keyboard.KEY_LSHIFT)) {
            this.activeCamera = backCamera;
        }else{
            this.activeCamera = frontCamera;
        }
        if (Keyboard.isKeyDown(Keyboard.KEY_SPACE)) {
            Vector3f h = new Vector3f(forward);
            h.scale(1f / (h.length() * 3));
            if (fire.getPassedCheckPointTime() > 0.1) {
                for (int i = 0; i < 25; i++) {
                    h = new Vector3f(forward);
                    h.scale(i * 0.6f / (h.length()));
                    emitter.emittParticle(h, forward, 900, 1, 2);
                }

                fire.setCheckPoint();
                fires = true;
            }
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
}
