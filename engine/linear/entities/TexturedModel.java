package engine.linear.entities;

import engine.core.sourceelements.RawModel;
import engine.core.sourceelements.SourceElement;
import engine.linear.material.EntityMaterial;

/**
 * Created by Luecx on 12.01.2017.
 */
public class TexturedModel implements SourceElement {

    private boolean wireframe = false;
    private boolean flatShading = false;
    private float textureStretch = 1;

    private RawModel rawModel;
    private EntityMaterial material;

    public TexturedModel(RawModel m, EntityMaterial material){
        this.rawModel = m;
        this.material = material;
    }

    public boolean isFlatShading() {
        return flatShading;
    }

    public boolean isWireframe() {
        return wireframe;
    }

    public void setWireframe(boolean wireframe) {
        this.wireframe = wireframe;
    }

    public RawModel getRawModel() {
        return rawModel;
    }

    public EntityMaterial getMaterial() {
        return material;
    }

    public void setFlatShading(boolean flatShading) {
        this.flatShading = flatShading;
    }

    public void setRawModel(RawModel rawModel) {
        this.rawModel = rawModel;
    }

    public void setMaterial(EntityMaterial material) {
        this.material = material;
    }

    public float getTextureStretch() {
        return textureStretch;
    }

    public void setTextureStretch(float textureStretch) {
        this.textureStretch = textureStretch;
    }


}
