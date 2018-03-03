package engine.render.shadowsystem;

import engine.core.components.OrthographicCamera;
import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector3f;

/**
 * Created by Luecx on 12.01.2017.
 */
public class ShadowCamera extends OrthographicCamera {

    public static final int STATIC_DIRECTION = 1;
    public static final int STATIC_POSITION = 2;

    private int modus = 1;
    private Vector3f lookAt = new Vector3f();
    private float distanceToTarget = 50f;

    public ShadowCamera(Vector3f position, Vector3f lookAt) {
        super(position);
        this.lookAt = lookAt;
    }

    public ShadowCamera(Vector3f position) {
        super(position);
    }

    public ShadowCamera(Vector3f position, Vector3f lookAt, int modus) {
        super(position);
        this.modus = modus;
        this.lookAt = lookAt;
    }

    public ShadowCamera(Vector3f lookAt, int modus){
        this.modus = modus;
        this.lookAt = lookAt;
    }

    public float getDistanceToTarget() {
        return distanceToTarget;
    }

    public void setDistanceToTarget(float distanceToTarget) {
        this.distanceToTarget = distanceToTarget;
    }

    public Vector3f getLookAt() {
        return lookAt;
    }

    public void setLookAt(Vector3f lookAt) {
        this.lookAt = lookAt;
    }

    public void update() {
        if(this.modus == ShadowCamera.STATIC_DIRECTION){
            Vector3f direction = (Vector3f)Vector3f.sub(this.getPosition(),lookAt,null).negate();
            direction.normalise(direction);
            this.setPosition(this.lookAt.x - distanceToTarget * direction.x,this.lookAt.y - distanceToTarget * direction.y,this.lookAt.z - distanceToTarget * direction.z);
        }
    }

    public Matrix4f getProViewMatrix() {
        return Matrix4f.mul(this.getProjectionMatrix(), this.lookAtViewMatrix(lookAt, new Vector3f(0,1,0)), null);
    }
}
