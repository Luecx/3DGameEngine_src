package projects.mediavle_game.map;

/**
 * Created by finne on 20.03.2018.
 */
public class Field {

    private UniqueGameEntity uniqueGameEntity;
    private int x;
    private int z;


    public Field(UniqueGameEntity uniqueGameEntity) {
        this.uniqueGameEntity = uniqueGameEntity;
    }

    public Field(int x, int z) {
        this.x = x;
        this.z = z;
    }

    public Field(UniqueGameEntity uniqueGameEntity, int x, int z) {
        this.uniqueGameEntity = uniqueGameEntity;
        this.x = x;
        this.z = z;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getZ() {
        return z;
    }

    public void setZ(int z) {
        this.z = z;
    }

    public Field() {
    }

    public UniqueGameEntity getUniqueGameEntity() {
        return uniqueGameEntity;
    }

    public void setUniqueGameEntity(UniqueGameEntity uniqueGameEntity) {
        this.uniqueGameEntity = uniqueGameEntity;
    }
}
