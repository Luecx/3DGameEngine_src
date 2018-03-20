package projects.game.objects;

import engine.core.components.Group;
import engine.core.exceptions.CoreException;
import engine.core.system.Sys;
import engine.linear.entities.Entity;
import engine.linear.entities.TexturedModel;
import engine.linear.loading.Loader;
import engine.linear.loading.OBJLoader;
import engine.linear.material.EntityMaterial;
import engine.linear.material.ParticleMaterial;
import engine.linear.maths.VectorOperations;
import engine.linear.particles.ParticleEmitter;
import org.lwjgl.opengl.Display;
import org.lwjgl.util.vector.Vector3f;

/**
 * Created by Luecx on 20.02.2017.
 */
public class Missile extends Group{

    private static ParticleEmitter globalEmitter;
    private static ParticleEmitter globalSmokeEmitter;
    private static boolean emitterConnected;

    private static float ROTATION_SPEED = 2 * 360;
    private static TexturedModel texturedModel;

    private Entity entity;

    private float speed;
    private Vector3f target;
    private float turnSpeed;

    public Missile(Vector3f start, Plane plane, Vector3f target, float speed, float turnSpeed) {
        super();
        try {
            if(Sys.PARTICLE_SYSTEM.isEnabled() && emitterConnected == false){
                globalEmitter = new ParticleEmitter(new Vector3f(0,0,0), new ParticleMaterial(Loader.loadTexture("textures/atlases/triebwerk"),4));
                globalSmokeEmitter = new ParticleEmitter(new Vector3f(0,0,0), new ParticleMaterial(Loader.loadTexture("textures/atlases/smoke_atlas"),4));
                Sys.PARTICLE_SYSTEM.addElement(globalEmitter);
                Sys.PARTICLE_SYSTEM.addElement(globalSmokeEmitter);
                emitterConnected = true;
            }
            if(texturedModel == null && Display.isCreated()){
                texturedModel = new TexturedModel(OBJLoader.loadOBJ("objectWithTextures/missile/missile", false), new EntityMaterial(Loader.loadTexture("objectWithTextures/missile/texture")));
            }
            this.entity = new Entity(texturedModel);
            this.entity.increaseRotation(0,0,90);
            //this.entity.setScale(plane.getScale().x, plane.getScale().z, plane.getScale().z);
            this.entity.setPosition(new Vector3f(start));
            this.target = target;
            this.speed = speed;
            this.turnSpeed = turnSpeed;

            if(Sys.ENTITY_SYSTEM.isEnabled()){
                Sys.ENTITY_SYSTEM.addElement(this.entity);
            }


        } catch (CoreException e) {
            e.printStackTrace();
        }

    }

    public void process(double passedTime) {
        for(int i = 0; i < 2; i++){
            Vector3f dir = VectorOperations.randomKegelVector(this.entity.getZAxis(), 80);
            Vector3f start = Vector3f.add(entity.getPosition(), (Vector3f)this.entity.getZAxis().normalise(null).scale(16 + i * 0.3f), null);
            Missile.globalEmitter.emittParticle(start, dir, 40,7,0.06f);
            Missile.globalSmokeEmitter.emittParticle(start, dir, 3,4,0.3f);
        }
        try{
            Vector3f con = Vector3f.sub(target, entity.getAbsolutePosition(), null);
            Vector3f rot = Vector3f.cross(entity.getZAxis(), con, null);
            if(rot == null || rot.length() == 0){
                this.entity.increaseRotation((float)passedTime * turnSpeed,(float)passedTime * turnSpeed,(float)passedTime * turnSpeed);
            }else{
                this.entity.increaseRotation(Vector3f.cross(entity.getZAxis(), Vector3f.sub(target, entity.getPosition(),  null), null), -(float)passedTime * turnSpeed);
            }
            this.entity.increasePosition((Vector3f) this.entity.getZAxis().negate(null).normalise(null).scale(speed * passedTime < con.length()? (float) (speed * passedTime) :con.length()));
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public Entity getEntity() {
        return entity;
    }

    public void setEntity(Entity entity) {
        this.entity = entity;
    }

    public float getSpeed() {
        return speed;
    }

    public void setSpeed(float speed) {
        this.speed = speed;
    }

    public Vector3f getTarget() {
        return target;
    }

    public void setTarget(Vector3f target) {
        this.target = target;
    }

    public float getTurnSpeed() {
        return turnSpeed;
    }

    public void setTurnSpeed(float turnSpeed) {
        this.turnSpeed = turnSpeed;
    }
}
