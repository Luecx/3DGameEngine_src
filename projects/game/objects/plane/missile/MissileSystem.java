package projects.game.objects.plane.missile;

import engine.core.components.Light;
import engine.core.components.PerspectiveCamera;
import engine.core.master.DisplayManager;
import engine.core.system.Sys;
import engine.linear.loading.Loader;
import engine.linear.material.ParticleMaterial;
import engine.linear.maths.VectorOperations;
import engine.linear.particles.ParticleEmitter;
import org.lwjgl.opengl.Display;
import org.lwjgl.util.vector.Vector3f;

import java.util.ArrayList;

/**
 * Created by Luecx on 22.02.2017.
 */
public abstract class MissileSystem {

    protected static ArrayList<Missile> missiles = new ArrayList<>();

    protected static final String SMOKE_FILE = "textures/atlases/smoke_atlas";
    protected static final String FIRE_FILE = "textures/atlases/triebwerk";
    protected static final String EXPLOSION_FILE = "textures/atlases/triebwerk";
    protected static ParticleEmitter globalFireEmitter;
    protected static ParticleEmitter globalExplosionEmitter;
    protected static ParticleEmitter globalSmokeEmitter;

    private static boolean enabled = false;

    public static boolean isEnabled(){
        return enabled;
    }

    public static void enableSystem(){
        if(enabled) return;
        if(Sys.PARTICLE_SYSTEM.isEnabled() == false || Sys.ENTITY_SYSTEM.isEnabled() == false){
            return;
        }
        try{
            globalFireEmitter = new ParticleEmitter(new Vector3f(0,0,0), new ParticleMaterial(Loader.loadTexture(FIRE_FILE),4));
            globalSmokeEmitter = new ParticleEmitter(new Vector3f(0,0,0), new ParticleMaterial(Loader.loadTexture(SMOKE_FILE),4));
            globalExplosionEmitter = new ParticleEmitter(new Vector3f(0,0,0), new ParticleMaterial(Loader.loadTexture(EXPLOSION_FILE),4));
            Sys.PARTICLE_SYSTEM.addElement(globalFireEmitter);
            Sys.PARTICLE_SYSTEM.addElement(globalSmokeEmitter);
            Sys.PARTICLE_SYSTEM.addElement(globalExplosionEmitter);
            MissileEnum.generateAllMissiles();
        }catch (Exception e){
            e.printStackTrace();
        }
        enabled = true;

    }

    public static void addMissile(MissileEnum missileEnum, Vector3f position, Vector3f rotation, Vector3f target) {
        if(!enabled) return;
        Missile m = missileEnum.createMissile(new Vector3f(position), new Vector3f(rotation), target);
        missiles.add(m);
    }

    public static void createExplosion(Vector3f position, int particles, float size) {
        for(int i = 0; i < particles; i++){
            globalExplosionEmitter.emittParticle(position, VectorOperations.randomPointOnSphereSurface(1), 150,40,size / 150);
        }
    }

    public static void update(double prossedTime){
        if(!enabled) return;
        for(int i = 0; i < missiles.size(); i++){
            if(missiles.get(i).process(prossedTime)) {
                createExplosion(missiles.get(i).getPosition(), 100, 50);
                Sys.ENTITY_SYSTEM.removeElement(missiles.get(i).getEntity());
                missiles.remove(i);
            }
        }

    }

    public static void main(String[] args){
        DisplayManager.createDisplay();

        Sys.enableAll();
        MissileSystem.enableSystem();
        MissileSystem.addMissile(MissileEnum.POWER_B, new Vector3f(), new Vector3f(), new Vector3f());

        PerspectiveCamera c = new PerspectiveCamera();
        ArrayList<Light> lights = new ArrayList<>();
        lights.add(new Light(10000,10000,10000));
        lights.get(0).setColor(new Vector3f(0.9f,0.8f,0.8f));

        while(Display.isCloseRequested() == false){
            MissileSystem.update(DisplayManager.processedFrameTime());
            DisplayManager.updateDisplay();
            c.move();
            Sys.ENTITY_SYSTEM.render(lights, c);
            Sys.PARTICLE_SYSTEM.render(c);
        }


        DisplayManager.closeDisplay();
    }
}
