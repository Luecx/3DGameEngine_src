package engine.render.entitysystem;

import engine.core.components.Camera;
import engine.core.components.Light;
import engine.core.datastructs.DSHashMap;
import engine.core.sourceelements.Signature;
import engine.core.sourceelements.VAOIdentifier;
import engine.core.system.RenderSystem;
import engine.linear.entities.Entity;
import engine.linear.entities.TexturedModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Luecx on 12.01.2017.
 */
public class EntitySystem extends RenderSystem<EntityShader, EntityRenderer, Entity, DSHashMap<TexturedModel, List<Entity>>>{

    private static EntityShader absShader = new EntityShader();

    public EntitySystem() {
        super(new EntityRenderer(absShader), absShader, new VAOIdentifier(Signature.EMPTY_SIGNATURE, 3, 0,1,2));
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
        this.data = new DSHashMap<>();
    }
    @Override
    protected void addToCollection(Entity element) {
        TexturedModel entityModel = element.getModel();
        List<Entity> batch = data.get(entityModel);
        if(batch!=null){
            batch.add(element);
        }else{
            List<Entity> newBatch = new ArrayList<Entity>();
            newBatch.add(element);
            data.put(entityModel, newBatch);
        }
    }
    @Override
    public void removeElement(Entity e) {
        if(!enabled)
            return;
        List<Entity> list = data.get(e.getModel());
        list.remove(e);
    }
}
