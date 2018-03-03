package projects.newgame.crafts.emittingsystem.msystem;

import engine.linear.entities.Entity;
import projects.newgame.crafts.emittingsystem.Bullet;
import org.lwjgl.util.vector.Vector3f;

/**
 * Created by finne on 20.09.2017.
 */
public class Missile extends Bullet {

    private Vector3f target;
    private Entity entity;

    private float turnSpeed;
    private float speed;
    private float maxDistance;

    private float currentDistance = 0;

    public Missile(Vector3f position, Vector3f target, Entity entity, float turnSpeed, float speed, float maxDistance) {
        super(position);
        this.target = target;
        this.entity = entity;
        this.turnSpeed = turnSpeed;
        this.speed = speed;
        this.maxDistance = maxDistance;
    }

    protected void processLocation(double time) {
        try{
            Vector3f con = Vector3f.sub(target, entity.getAbsolutePosition(), null);
            Vector3f rot = Vector3f.cross(entity.getZAxis(), con, null);
            if(rot == null || rot.length() == 0){
                this.entity.increaseRotation((float)time * turnSpeed,(float)time * turnSpeed,(float)time * turnSpeed);
            }else{
                this.entity.increaseRotation(Vector3f.cross(entity.getZAxis(), Vector3f.sub(target, entity.getPosition(),  null), null), -(float)time * turnSpeed);
            }
            this.entity.increasePosition((Vector3f) this.entity.getZAxis().negate(null).normalise(null).scale(speed * time < con.length()? (float) (speed * time) :con.length()));
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    @Override
    protected void dataChangedNotification() {

    }
}
