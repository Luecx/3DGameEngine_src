package engine.core.system;

/**
 * Created by Luecx on 03.01.2017.
 */
public abstract class AbstractRenderer<S extends ShaderProgram> {

    protected S shader;

    public AbstractRenderer(S shader) {
        this.shader = shader;
    }

    public S getShader() {
        return shader;
    }

    public void setShader(S shader) {
        this.shader = shader;
    }
}
