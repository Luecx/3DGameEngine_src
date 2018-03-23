package projects.mediavle_game.map.entities.abs;

import engine.linear.entities.TexturedModel;

/**
 * Created by finne on 20.03.2018.
 */
public abstract class InstancedGameEntity<T extends InstancedGameEntity<T>> extends GameEntity<T>{


    protected InstancedGameEntitySet set;

    public InstancedGameEntity(int x, int y, int width, int height, boolean rigidBody, InstancedGameEntitySet set) {
        super(x, y, width, height, rigidBody);
        this.set = set;
    }

    public void generateEntity() {
        this.set.addInstance(this);
    }

    @Override
    public TexturedModel getTexturedModel() {
        return set.instanceSet.getModel();
    }

    public abstract T clone();

    public void destroyEntity() {
        this.set.removeInstance(this);
    }
}
