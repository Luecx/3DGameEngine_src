package projects.mediavle_game.map.entities.abs;

import engine.linear.entities.TexturedModel;

/**
 * Created by finne on 20.03.2018.
 */
public abstract class GameEntity<T extends GameEntity<T>> {

    protected int x;
    protected int y;
    protected int width;
    protected int height;

    public GameEntity(int x, int y, int width, int height, boolean rigidBody) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.rigidBody = rigidBody;
    }

    public GameEntity(int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public int getX() {
        return x;
    }
    public int getY() {
        return y;
    }

    public int getWidth() {
        return width;
    }
    public int getHeight() {
        return height;
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

    public abstract T clone();

    public abstract void update(double time);

    public abstract TexturedModel getTexturedModel();

    public void setX(int x) {
        this.x = x;
    }
    public void setY(int y) {
        this.y = y;
    }
}
