package engine.render.instancedsystem;

import engine.core.components.Camera;
import engine.core.components.Light;
import engine.core.datastructs.DSArrayList;
import engine.core.sourceelements.Signature;
import engine.core.sourceelements.VAOIdentifier;
import engine.core.system.RenderSystem;

import java.util.List;

/**
 * Created by Luecx on 18.02.2017.
 */
public class InstancedEntitySystem extends RenderSystem<InstancedEntityShader, InstancedEntityRenderer, InstanceSet, DSArrayList<InstanceSet>>{

    private static InstancedEntityShader absShader = new InstancedEntityShader();

    public InstancedEntitySystem() {
        //VAOIdentifier does not take attribute 4 as argument. This is implemented in the InstanceSet.
        super(new InstancedEntityRenderer(absShader), absShader, new VAOIdentifier(Signature.EMPTY_SIGNATURE, 3, 0,1,2));
    }

    public void render(List<Light> lights, Camera c){
        if(this.enabled == false) return;
        shader.start();
        shader.loadProjectionMatrix(c.getProjectionMatrix());
        shader.loadViewMatrix(c.getViewMatrix());
        shader.loadLights(lights);
        renderer.render(data);
        shader.stop();
    }

    @Override
    protected void addToCollection(InstanceSet element) {
        this.data.add(element);
    }

    @Override
    protected void initData() {
        this.data = new DSArrayList<>();
    }

    @Override
    public void removeElement(InstanceSet instanceSet) {
        this.data.remove(instanceSet);
    }
}
