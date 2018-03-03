package projects.game.objects.plane;

import engine.core.components.Group;
import engine.core.exceptions.CoreException;
import engine.core.system.Sys;
import engine.linear.entities.Entity;
import org.lwjgl.util.vector.Vector3f;

/**
 * Created by Luecx on 22.02.2017.
 */
public class Aircraft extends Group{


    protected Entity entity;
    protected float rotation_speed;
    protected float forward_speed;

    public Aircraft(Vector3f position, Vector3f rotation, Entity entity, float rotation_speed, float forward_speed) {
        super(position, rotation);
        this.entity = entity;
        this.addChild(entity);
        this.rotation_speed = rotation_speed;
        this.forward_speed = forward_speed;
        if(Sys.ENTITY_SYSTEM.isEnabled()){
            try {
                Sys.ENTITY_SYSTEM.addElement(entity);
            } catch (CoreException e) {
                e.printStackTrace();
            }
        }
    }

    public Aircraft(Aircraft other, Entity entity, float rotation_speed, float forward_speed) {
        super(new Vector3f(other.getPosition()), new Vector3f(other.getRotation()));
        this.entity = entity;
        this.addChild(entity);
        this.rotation_speed = rotation_speed;
        this.forward_speed = forward_speed;
        if(Sys.ENTITY_SYSTEM.isEnabled()){
            try {
                Sys.ENTITY_SYSTEM.addElement(entity);
            } catch (CoreException e) {
                e.printStackTrace();
            }
        }
    }

    protected void rotate(){
        this.increaseRotation(this.getZAxis(), this.rotation_speed);
    }

    protected void forward() {
        this.increasePosition((Vector3f) this.getZAxis().negate(null).scale(forward_speed));
    }

    public Entity getEntity() {
        return entity;
    }

    public void setEntity(Entity entity) {
        this.entity = entity;
    }

    public float getRotation_speed() {
        return rotation_speed;
    }

    public void setRotation_speed(float rotation_speed) {
        this.rotation_speed = rotation_speed;
    }

    public float getForward_speed() {
        return forward_speed;
    }

    public void setForward_speed(float forward_speed) {
        this.forward_speed = forward_speed;
    }
}
