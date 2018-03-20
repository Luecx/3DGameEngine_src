package projects.mediavle_game.map.entities;

import engine.linear.entities.TexturedModel;

/**
 * Created by finne on 20.03.2018.
 */
public abstract class GameEntity {


    protected static TexturedModel texturedModel;

    /**
     * Override to set the TexturedModel for that specific object.
     */
    public abstract void generateTexturedModel();

    public static TexturedModel getTexturedModel() {
        return texturedModel;
    }


}
