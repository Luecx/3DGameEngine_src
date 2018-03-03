package engine.render.shadowsystem;

import org.lwjgl.util.vector.Matrix4f;

/**
 * Created by Luecx on 05.12.2016.
 */
public class ShadowData {
    private Matrix4f shadowMatrix = new Matrix4f();
    private ShadowFrameBuffer frameBuffer;

    public static final int BUFFER_POWER = 12;

    private boolean active;

    public ShadowData(Matrix4f shadowMatrix, ShadowFrameBuffer frameBuffer) {
        this.shadowMatrix = shadowMatrix;
        this.frameBuffer = frameBuffer;
    }

    public ShadowData(Matrix4f shadowMatrix) {
        this.shadowMatrix = shadowMatrix;
        this.frameBuffer = new ShadowFrameBuffer((int)Math.pow(2,BUFFER_POWER),(int)Math.pow(2,BUFFER_POWER));
    }

    public ShadowData() {
        this.frameBuffer = new ShadowFrameBuffer((int)Math.pow(2,BUFFER_POWER),(int)Math.pow(2,BUFFER_POWER));
    }


    public Matrix4f getShadowMatrix() {
        return this.shadowMatrix;
    }

    public void setShadowMatrix(Matrix4f shadowMatrix) {
        this.shadowMatrix = shadowMatrix;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public ShadowFrameBuffer getFrameBuffer() {
        return frameBuffer;
    }

}
