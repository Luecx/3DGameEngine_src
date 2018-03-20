package projects.mediavle_game.map.entities.abs;

import engine.linear.entities.TexturedModel;

/**
 * Created by finne on 20.03.2018.
 */
public abstract class GameEntity {


    protected TexturedModel texturedModel;
    protected boolean rigidBody = true;

    /**
     * Override to set the TexturedModel for that specific object.
     */
    public abstract void generateTexturedModel();

    public boolean isRigidBody() {
        return rigidBody;
    }

    public void setRigidBody(boolean rigidBody) {
        this.rigidBody = rigidBody;
    }

    public abstract void destroy();

    public TexturedModel getTexturedModel() {
        return texturedModel;
    }



}
