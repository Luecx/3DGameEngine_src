package projects.newgame.crafts.planes;

import engine.core.components.Group;
import engine.core.components.PerspectiveCamera;
import engine.core.exceptions.CoreException;
import engine.core.master.Time;
import engine.core.system.Sys;
import engine.linear.entities.Entity;
import engine.linear.material.ParticleMaterial;
import engine.linear.particles.ParticleEmitter;
import org.lwjgl.input.Keyboard;
import org.lwjgl.util.vector.Vector3f;

/**
 * Created by Luecx on 24.02.2017.
 */
public class Jet extends Group {


    private Entity entity;

    private int currentCamera = 0;
    private PerspectiveCamera activeCamera;
    private Time cameraTimer = new Time();
    private PerspectiveCamera[] cameras;


    private float fowardSpeed = 0;
    private ParticleEmitter[] turbines;
    private ParticleEmitter[] guns;

    public Jet(
            Entity entity,
            Vector3f[] cameraLocations,
            Vector3f[] cameraRotations,
            Vector3f[] turbinePositions,
            Vector3f[] gunPositions) {

        this.entity = entity;
        this.addChild(entity);

        this.cameraTimer.setTimer(0.3f);

        this.cameras = new PerspectiveCamera[cameraLocations.length];
        this.turbines = new ParticleEmitter[turbinePositions.length * 2];

        for (int i = 0; i < cameras.length; i++) {
            cameras[i] = new PerspectiveCamera();
            cameras[i].setPosition(cameraLocations[i]);
            cameras[i].setRotation(cameraRotations[i]);
            this.addChild(cameras[i]);
        }

        for (int i = 0; i < turbines.length; i += 2) {
            turbines[i] = new ParticleEmitter(turbinePositions[i / 2], new ParticleMaterial("textures/atlases/triebwerk", 4));
            turbines[i + 1] = new ParticleEmitter(new Vector3f(turbinePositions[i / 2].x, turbinePositions[i / 2].y, turbinePositions[i / 2].z + 3), new ParticleMaterial("textures/atlases/smoke_atlas", 4));
            try {
                Sys.PARTICLE_SYSTEM.addElement(turbines[i]);
                Sys.PARTICLE_SYSTEM.addElement(turbines[i + 1]);
            } catch (CoreException e) {
                e.printStackTrace();
            }
            this.addChild(turbines[i]);
            this.addChild(turbines[i + 1]);
        }

        for(int i = 0; i < gunPositions.length; i++) {
            guns[i] = new ParticleEmitter(turbinePositions[i / 2], new ParticleMaterial("textures/atlases/triebwerk", 4));
            try {
                Sys.PARTICLE_SYSTEM.addElement(turbines[i]);
            } catch (CoreException e) {
                e.printStackTrace();
            }
            this.addChild(turbines[i]);
        }

        this.activeCamera = cameras[0];
    }

    public void setFowardSpeed(float fowardSpeed) {
        this.fowardSpeed = fowardSpeed;
    }

    public void shoot() {

    }

    public void processVision(boolean emit, double passedTime) {

        Vector3f forward = this.getZAxis();
        forward.scale((float) -(passedTime * this.fowardSpeed / (forward.length() * 3.6)));

        if(emit) {
            for(int n = 0; n < turbines.length; n+=2) {
                for(int k = 0; k < 5; k++) {
                    turbines[n].emittParticle(new Vector3f((float) (-forward.x + Math.random() * 0.1 - 0.05),
                                    (float) (-forward.y + Math.random() * 0.1 - 0.05),-forward.z), -120f + (float)Math.random() * 20f,
                            1.5f, 0.04f);
                }
                turbines[n+1].emittParticle(new Vector3f((float) (-forward.x + Math.random() * 0.1 - 0.05),
                                (float) (-forward.y + Math.random() * 0.1 - 0.05),-forward.z), -80f + (float) Math.random() * 10,
                        2.5f, 0.3f);
                turbines[n+1].emittParticle(new Vector3f((float) (-forward.x + Math.random() * 0.1 - 0.05),
                                (float) (-forward.y + Math.random() * 0.1 - 0.05),-forward.z), -70f + (float) Math.random() * 20,
                        1.5f, 0.3f);
            }
        }

        if (Keyboard.isKeyDown(Keyboard.KEY_LSHIFT)) {
            if(cameraTimer.timerIsUp()) {
                this.currentCamera = (currentCamera + 1) % cameras.length;
                this.activeCamera = cameras[currentCamera];
                cameraTimer.setTimer(0.3f);
            }
        }

    }
}
