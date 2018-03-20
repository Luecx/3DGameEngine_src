package projects.mediavle_game;

/**
 * Created by finne on 20.03.2018.
 */
public class Field {

    private GameEntity gameEntity;
    private int x;
    private int z;


    public Field(GameEntity gameEntity) {
        this.gameEntity = gameEntity;
    }

    public Field(int x, int z) {
        this.x = x;
        this.z = z;
    }

    public Field(GameEntity gameEntity, int x, int z) {
        this.gameEntity = gameEntity;
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

    public GameEntity getGameEntity() {
        return gameEntity;
    }

    public void setGameEntity(GameEntity gameEntity) {
        this.gameEntity = gameEntity;
    }
}
