package engine.render.tesselationTerrainSystem;

import engine.core.components.Camera;
import engine.core.components.Light;
import engine.core.datastructs.DSArrayList;
import engine.core.datastructs.DSElement;
import engine.core.sourceelements.Signature;
import engine.core.sourceelements.VAOIdentifier;
import engine.core.system.RenderSystem;
import engine.linear.entities.Entity;
import engine.linear.terrain.Terrain;

import java.util.List;

/**
 * Created by Luecx on 12.01.2017.
 */
public class TerrainSystem extends RenderSystem<TerrainShader, TerrainRenderer, Entity, DSElement<Entity>>{

    private static TerrainShader absShader = new TerrainShader();

    public TerrainSystem() {
        super(new TerrainRenderer(absShader), absShader, new VAOIdentifier(Signature.EMPTY_SIGNATURE, 3, 0,1,2));
    }

    public void render(List<Light> lights, Camera camera) {
        if(!enabled){
            return;
        }
        shader.start();
        shader.loadProjectionMatrix(camera.getProjectionMatrix());
        shader.loadViewMatrix(camera.getViewMatrix());;
        renderer.render(data);
        shader.stop();
    }
    @Override
    protected void initData() {
        this.data = new DSElement<>();
    }
    @Override
    protected void addToCollection(Entity element) {
        this.data.setElement(element);
    }
    @Override
    public void removeElement(Entity e) {
        this.data.clear();
    }
}
