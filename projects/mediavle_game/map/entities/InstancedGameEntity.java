package projects.mediavle_game.map.entities;

import engine.core.exceptions.CoreException;
import engine.core.system.Sys;
import engine.linear.entities.TexturedModel;
import engine.render.instancedsystem.InstanceSet;
import org.lwjgl.util.vector.Vector3f;

import java.util.ArrayList;

/**
 * Created by finne on 20.03.2018.
 */
public abstract class InstancedGameEntity extends GameEntity{

    private InstanceSet instanceSet;
    ArrayList<float[]> positions;

    public InstancedGameEntity() {

    }

    public int indexOfPosition(int x, int z){
        int index = 0;
        for(float[] ar:positions){
            if(ar[0] == x && ar[2] == z){
                return index;
            }
            index ++;
        }
        return -1;
    }

    public boolean add(int x, int z){
        if(indexOfPosition(x,z) == -1) {
            positions.add(new float[]{x,0,z});
            return true;
        }
        return false;
    }

    public boolean remove(int x, int z) {
        int index = indexOfPosition(x,z);
        if(index != -1) {
            positions.remove(index);
            return true;
        }
        return false;
    }

    public void initInstanceSet() throws CoreException {
        this.instanceSet = new InstanceSet(texturedModel);
        Sys.INSTANCED_ENTITY_SYSTEM.addElement(instanceSet);
        this.positions = instanceSet.getPositions();
    }



}
