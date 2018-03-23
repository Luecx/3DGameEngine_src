package projects.mediavle_game.map.entities.abs;

import engine.core.exceptions.CoreException;
import engine.core.system.Sys;
import engine.linear.entities.TexturedModel;
import engine.render.instancedsystem.InstanceSet;

import java.util.ArrayList;

/**
 * Created by finne on 20.03.2018.
 */
public abstract class InstancedGameEntitySet<T extends InstancedGameEntity>{

    protected ArrayList<T> instances = new ArrayList<T>();
    protected ArrayList<float[]> positions = new ArrayList<>();

    protected InstanceSet instanceSet;

    public void addInstance(T instance) {
        instances.add(instance);
        positions.add(new float[]{
                instance.getX() + (float)instance.getWidth() / 2f,
                0,
                instance.getY() + (float)instance.getHeight() / 2f});
        instanceSet.setOutdated(true);
        instanceSet.updateVbo();
    }

    public void removeInstance(T instance) {
        int index = instances.indexOf(instance);
        if(index >= 0 && index < instances.size()){
            instances.remove(index);
            positions.remove(index);
        }
        instanceSet.setOutdated(true);
        instanceSet.updateVbo();
    }


}
