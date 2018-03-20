package projects.mediavle_game;

import engine.linear.entities.Entity;
import engine.linear.entities.TexturedModel;

/**
 * Created by finne on 20.03.2018.
 */
public abstract class GameEntity {

    protected static TexturedModel texturedModel;

    //OVERRIDE FOR EVERY SUBCLASS!!
    static {
        generateTexturedModel();
    }
    public static void generateTexturedModel(){
        texturedModel =  null;
    }
    //OVERRIDE FOR EVERY SUBCLASS!! - END

    public static Entity generateEntity(int x, int z) {
        return new Entity(x,0,z);
    }



}
