package projects.mediavle_game.map;

import projects.mediavle_game.map.entities.abs.GameEntity;

/**
 * Created by finne on 20.03.2018.
 */
public class Field {

    private GameEntity gameEntity;
    private int x;
    private int z;


    public Field(GameEntity uniqueGameEntity) {
        this.gameEntity = uniqueGameEntity;
    }

    public Field(int x, int z) {
        this.x = x;
        this.z = z;
    }

    public Field(GameEntity uniqueGameEntity, int x, int z) {
        this.gameEntity = uniqueGameEntity;
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

    public void setUniqueGameEntity(GameEntity uniqueGameEntity) {
        this.gameEntity = uniqueGameEntity;
    }
}
