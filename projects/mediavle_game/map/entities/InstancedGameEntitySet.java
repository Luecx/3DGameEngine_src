package projects.mediavle_game.map.entities;

import engine.render.instancedsystem.InstanceSet;

import java.util.ArrayList;

/**
 * Created by finne on 20.03.2018.
 */
public abstract class InstancedGameEntitySet<T extends InstancedGameEntity> extends GameEntity{


    private ArrayList<T> instances = new ArrayList<T>();
    private ArrayList<float[]> positions;

    private InstanceSet instanceSet;




    public void generateInstanceSet() {
        this.instanceSet = new InstanceSet()
    }
}
