package projects.newgame.crafts.emittingsystem.msystem;

import projects.newgame.crafts.emittingsystem.EmitterSystem;
import org.lwjgl.util.vector.Vector3f;

/**
 * Created by finne on 20.09.2017.
 */
public class MissileSystem extends EmitterSystem<Missile> {

    private Vector3f target = new Vector3f(0,0,0);

    public MissileSystem(float x, float y, float z, int currentCharges, double chargeTime, double emitTime, int maxStackedCharges) {
        super(x, y, z, currentCharges, chargeTime, emitTime, maxStackedCharges);
    }

    public MissileSystem(float x, float y, float z, int currentCharges, double chargeTime, double emitTime, int maxStackedCharges, Vector3f target) {
        super(x, y, z, currentCharges, chargeTime, emitTime, maxStackedCharges);
        this.target = target;
    }

    @Override
    protected Missile createBullet() {
        return null;
    }
}
