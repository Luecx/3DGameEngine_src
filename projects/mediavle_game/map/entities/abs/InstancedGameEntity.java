package projects.mediavle_game.map.entities.abs;

import engine.linear.entities.TexturedModel;

/**
 * Created by finne on 20.03.2018.
 */
public abstract class InstancedGameEntity extends GameEntity{


    protected InstancedGameEntitySet set;

    public InstancedGameEntity(int x, int y, InstancedGameEntitySet set) {
        this.x = x;
        this.y = y;
        this.set = set;
    }

    public void generateEntity() {
        this.set.addInstance(this);
    }

    public void destroyEntity() {
        this.set.removeInstance(this);
    }
}
