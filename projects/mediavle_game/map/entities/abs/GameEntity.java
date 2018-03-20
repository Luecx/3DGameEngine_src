package projects.mediavle_game.map.entities.abs;

import engine.linear.entities.TexturedModel;

/**
 * Created by finne on 20.03.2018.
 */
public abstract class GameEntity {


    protected TexturedModel texturedModel;

    /**
     * Override to set the TexturedModel for that specific object.
     */
    public abstract void generateTexturedModel();

    public TexturedModel getTexturedModel() {
        return texturedModel;
    }



}
