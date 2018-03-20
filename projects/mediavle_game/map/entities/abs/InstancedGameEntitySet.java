package projects.mediavle_game.map.entities.abs;

import engine.core.exceptions.CoreException;
import engine.core.system.Sys;
import engine.render.instancedsystem.InstanceSet;

import java.util.ArrayList;

/**
 * Created by finne on 20.03.2018.
 */
public abstract class InstancedGameEntitySet<T extends InstancedGameEntity> extends GameEntity{


    private ArrayList<T> instances = new ArrayList<T>();
    private ArrayList<float[]> positions = new ArrayList<>();

    private InstanceSet instanceSet;

    public InstancedGameEntitySet() {
        this.generateTexturedModel();
    }

    public void addInstance(T instance) {
        instances.add(instance);
        positions.add(new float[]{instance.getX() + 0.5f,0,instance.getY() + 0.5f});
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

    public void generateInstanceSet() {
        this.instanceSet = new InstanceSet(texturedModel);

        this.positions = instanceSet.getPositions();
        try {
            Sys.INSTANCED_ENTITY_SYSTEM.addElement(instanceSet);
        } catch (CoreException e) {
            e.printStackTrace();
        }
    }
}
