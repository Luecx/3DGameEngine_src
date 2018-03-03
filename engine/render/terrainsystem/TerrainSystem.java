package engine.render.terrainsystem;

import engine.core.components.Camera;
import engine.core.components.Light;
import engine.core.datastructs.DSArrayList;
import engine.core.sourceelements.Signature;
import engine.core.sourceelements.VAOIdentifier;
import engine.core.system.RenderSystem;
import engine.linear.terrain.Terrain;

import java.util.List;

/**
 * Created by Luecx on 12.01.2017.
 */
public class TerrainSystem extends RenderSystem<TerrainShader, TerrainRenderer, Terrain, DSArrayList<Terrain>>{

    private static TerrainShader absShader = new TerrainShader();

    public TerrainSystem() {
        super(new TerrainRenderer(absShader), absShader, new VAOIdentifier(Signature.EMPTY_SIGNATURE, 3, 0,1,2,7));
    }



    public void render(List<Light> lights, Camera camera) {
        if(!enabled)
            return;
        shader.start();
        shader.loadLights(lights);
        shader.loadProjectionMatrix(camera.getProjectionMatrix());
        shader.loadViewMatrix(camera.getViewMatrix());
        renderer.render(data);
        shader.stop();
    }
    @Override
    protected void initData() {
        this.data = new DSArrayList<>();
    }
    @Override
    protected void addToCollection(Terrain element) {
        this.data.add(element);
    }
    @Override
    public void removeElement(Terrain e) {
        this.data.remove(e);
    }
}
