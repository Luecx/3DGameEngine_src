package engine.render.advancedTerrainSystem;

import engine.core.components.Camera;
import engine.core.components.Light;
import engine.core.datastructs.DSElement;
import engine.core.sourceelements.VAOIdentifier;
import engine.core.system.RenderSystem;
import engine.linear.advancedterrain.Terrain;

import java.util.List;

/**
 * Created by Luecx on 12.01.2017.
 */
public class AdvancedTerrainSystem extends RenderSystem<AdvancedTerrainShader, AdvancedTerrainRenderer, Terrain, DSElement<Terrain>>{

    private static AdvancedTerrainShader absShader = new AdvancedTerrainShader();

    public AdvancedTerrainSystem() {
        super(new AdvancedTerrainRenderer(absShader), absShader, VAOIdentifier.D3_ADVANCED_TERRAIN_MODEL);
    }

    public void render(List<Light> lights, Camera camera) {
        if(!enabled || this.data.getElement() == null)
            return;
        shader.start();
        shader.loadLights(lights, camera.getViewMatrix());
        shader.loadProjectionMatrix(camera.getProjectionMatrix());
        shader.loadViewMatrix(camera.getViewMatrix());
        renderer.render(data.getElement());
        shader.stop();
    }
    @Override
    protected void initData() {
        this.data = new DSElement();
    }
    @Override
    protected void addToCollection(Terrain element) {
        this.data.setElement(element);
    }
    @Override
    public void removeElement(Terrain e) {
        this.data.clear();
    }
}
