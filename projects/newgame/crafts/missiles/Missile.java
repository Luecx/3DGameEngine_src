package projects.newgame.crafts.missiles;

import engine.linear.entities.Entity;
import engine.linear.maths.VectorOperations;
import engine.linear.advancedterrain.Terrain;
import projects.game.objects.plane.Aircraft;
import org.lwjgl.util.vector.Vector3f;

/**
 * Created by Luecx on 22.02.2017.
 */
public class Missile extends Aircraft {




    private Vector3f target;
    private float turn_speed;
    private float distanceToFire;

    private float processedDistance = 0;
    private float maxDistance;


    public Missile(Vector3f position, Vector3f rotation, Entity entity, float rotation_speed, float forward_speed, float maxDistance, Vector3f target, float turn_speed, float distanceToFire) {
        super(position, rotation, entity, rotation_speed, forward_speed);
        this.target = target;
        this.maxDistance = maxDistance;
        this.distanceToFire = distanceToFire;
        this.turn_speed = turn_speed;
    }

    public Missile(Aircraft other, Entity entity, float rotation_speed, float forward_speed, Vector3f target, float turn_speed, float distanceToFire) {
        super(other, entity, rotation_speed, forward_speed);
        this.target = target;
        this.distanceToFire = distanceToFire;
        this.turn_speed = turn_speed;
    }


    public boolean process(Terrain t, double passedTime) {
        float terrainHeight = t.getChunks().get(0).height(this.getPosition().x, this.getPosition().z);
        if(terrainHeight < 10000 && this.getPosition().y < terrainHeight) {
            return true;
        }
        for(int i = 0; i < 2; i++){
            Vector3f dir = VectorOperations.randomKegelVector(this.getZAxis(), 80);
            Vector3f start = Vector3f.add(this.getPosition(), (Vector3f)this.getZAxis().normalise(null).scale(distanceToFire + i * 3), null);
            MissileSystem.globalFireEmitter.emittParticle(start, dir, 40,7,0.06f);
            MissileSystem.globalSmokeEmitter.emittParticle(start, dir, 3,4,0.3f);
        }
        try{
            Vector3f con = Vector3f.sub(target, entity.getAbsolutePosition(), null);
            Vector3f rot = Vector3f.cross(entity.getZAxis(), con, null);
            if(rot == null || rot.length() == 0){
                this.increaseRotation(-(float)passedTime * turn_speed,(float)passedTime * turn_speed,(float)passedTime * turn_speed);
            }else{
                this.increaseRotation(Vector3f.cross(this.getZAxis(), Vector3f.sub(target, this.getPosition(),  null), null), -(float)passedTime * turn_speed);
            }

            this.increaseRotation(this.getZAxis(), rotation_speed * (float)passedTime);
            float dist = forward_speed * passedTime < con.length() || con.length() < 25? (float) (forward_speed * passedTime) :con.length();
            this.processedDistance += dist;
            this.increasePosition((Vector3f) this.getZAxis().negate(null).normalise(null).scale(dist));
            return this.processedDistance > this.maxDistance;
        }catch(Exception e){
            e.printStackTrace();
        }
        return true;
    }


}
