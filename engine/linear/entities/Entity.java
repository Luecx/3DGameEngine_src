package engine.linear.entities;

import engine.core.components.Group;
import engine.core.components.GroupableGameObject;
import engine.core.sourceelements.RawModel;
import engine.core.sourceelements.SourceElement;
import org.lwjgl.util.vector.Vector3f;

/**
 * Created by Luecx on 12.01.2017.
 */
public class Entity extends Group implements SourceElement {
    private TexturedModel model;

    public Entity() {
        super();
    }

    public Entity(float x, float y, float z, float rx, float ry, float rz, float sx, float sy, float sz) {
        super(x, y, z, rx, ry, rz, sx, sy, sz);
    }

    public Entity(float x, float y, float z) {
        super(x, y, z);
    }

    public Entity(Vector3f position, Vector3f rotation, Vector3f scalation) {
        super(position, rotation, scalation);
    }

    public Entity(TexturedModel model, float x, float y, float z, float rx, float ry, float rz, float sx, float sy, float sz) {
        super(x, y, z, rx, ry, rz, sx, sy, sz);
        this.model = model;
    }

    public Entity(TexturedModel model, float x, float y, float z) {
        super(x, y, z);
        this.model = model;
    }

    public Entity(TexturedModel model, Vector3f position, Vector3f rotation, Vector3f scalation) {
        super(position, rotation, scalation);
        this.model = model;
    }

    public Entity(TexturedModel model) {
        super();
        this.model = model;
    }

    public TexturedModel getModel() {
        return model;
    }

    public void setModel(TexturedModel model) {
        this.model = model;
    }

    @Override
    public RawModel getRawModel() {
        return model.getRawModel();
    }

    @Override
    protected void absoluteDataChangedNotification() {

    }
}
