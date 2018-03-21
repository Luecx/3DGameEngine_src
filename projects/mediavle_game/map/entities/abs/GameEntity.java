package projects.mediavle_game.map.entities.abs;

import engine.linear.entities.TexturedModel;

/**
 * Created by finne on 20.03.2018.
 */
public abstract class GameEntity {

    protected int x;
    protected int y;
    public int getX() {
        return x;
    }
    public int getY() {
        return y;
    }

    protected boolean rigidBody = true;
    public boolean isRigidBody() {
        return rigidBody;
    }
    public void setRigidBody(boolean rigidBody) {
        this.rigidBody = rigidBody;
    }

    public abstract void destroyEntity();
    public abstract void generateEntity();
}
