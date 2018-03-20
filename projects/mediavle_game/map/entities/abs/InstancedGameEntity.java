package projects.mediavle_game.map.entities.abs;

/**
 * Created by finne on 20.03.2018.
 */
public abstract class InstancedGameEntity extends GameEntity{


    protected int x;
    protected int y;

    protected InstancedGameEntitySet set;

    public InstancedGameEntity(int x, int y, InstancedGameEntitySet set) {
        this.x = x;
        this.y = y;
        this.set = set;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void generateEntity() {
        this.set.addInstance(this);
    }

    public void destroyEntity() {
        this.set.removeInstance(this);
    }

    @Override
    public void generateTexturedModel() {

    }
}
