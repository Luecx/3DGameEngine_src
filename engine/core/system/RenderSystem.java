package engine.core.system;

import engine.core.datastructs.DataStruct;
import engine.core.exceptions.CoreException;
import engine.core.sourceelements.SourceElement;
import engine.core.sourceelements.VAOIdentifier;

/**
 * Created by Luecx on 03.01.2017.
 */
public abstract class RenderSystem<S extends ShaderProgram, R extends  AbstractRenderer<S>, E extends SourceElement, D extends DataStruct> {

    protected VAOIdentifier vaoIdentifier;
    protected boolean enabled;

    protected D data;

    protected R renderer;
    protected S shader;

    public RenderSystem(R renderer, S shader, VAOIdentifier identifier) {
        super();
        this.vaoIdentifier = identifier;
        this.renderer = renderer;
        this.shader = shader;
        enabled = false;
    }

    public void addElement(E element) throws CoreException {
        if (this.isEnabled() == false) throw new CoreException("Error %3x- System is not enabled");
        if (this.checkVBO(element) == false) throw new CoreException("Error %4x- Not enough required active VBO-Elements");
        this.addToCollection(element);
    }

    protected boolean checkVBO(E element) throws CoreException {
        if(element.getRawModel() == null){
            throw new CoreException("Error %5x- Not existing Vertex Array Object Attachment");
        }
        return this.vaoIdentifier.validate(element.getRawModel().getVaoIdentifier());
    }

    protected abstract void addToCollection(E element);

    public R getRenderer() {
        return renderer;
    }

    public S getShader() {
        return shader;
    }

    public boolean isEnabled() {
        return this.enabled;
    }

    public VAOIdentifier getVaoIdentifier() {
        return vaoIdentifier;
    }

    public D getData() {
        return data;
    }

    public void createSystem() {
        if(this.enabled == true){
            return;
        }
        this.enabled = true;
        this.shader.createShader();
        this.initData();
    }

    protected abstract void initData();

    public abstract void removeElement(E e);

    public void closeSystem() {
        if(this.enabled == false) return;
        this.enabled = false;
        this.shader.cleanUp();
        this.data.clear();
    }
}
