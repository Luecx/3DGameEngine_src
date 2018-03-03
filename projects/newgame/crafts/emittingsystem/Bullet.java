package projects.newgame.crafts.emittingsystem;

import engine.core.components.ComplexGameObject;
import engine.linear.maths.Ray;
import engine.linear.advancedterrain.Terrain;
import org.lwjgl.util.vector.Vector3f;

/**
 * Created by finne on 20.09.2017.
 */
public abstract class Bullet extends ComplexGameObject{

    private Ray currentRay;

    public Bullet(Vector3f position) {
        super(position);
    }

    boolean process(Terrain t, double time){
        if(!positionCheck(t)) return false;

        currentRay.setOrigin(this.getPosition());

        processLocation(time);

        currentRay.setDirection(Vector3f.sub(this.getPosition(), currentRay.getOrigin(), null));

        return true;
    }

    protected abstract void processLocation(double time);

    private boolean positionCheck(Terrain t) {
        if(t.getChunks().get(0).height(this.getPosition().x, this.getPosition().z) < this.getPosition().y){
            return true;
        }
        return false;
    }

    public Ray getCurrentRay() {
        return currentRay;
    }
}
