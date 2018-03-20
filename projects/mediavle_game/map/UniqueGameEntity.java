package projects.mediavle_game.map;

import engine.core.exceptions.CoreException;
import engine.core.system.Sys;
import engine.linear.entities.Entity;
import engine.linear.entities.TexturedModel;

/**
 * Created by finne on 20.03.2018.
 */
public abstract class UniqueGameEntity {

    protected static TexturedModel texturedModel;

    protected Entity entity;

    protected int x;
    protected int y;
    protected int width;
    protected int height;

    public UniqueGameEntity(int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    /**
     * Override to set the TexturedModel for that specific object.
     */
    public abstract void generateTexturedModel();

    public static TexturedModel getTexturedModel() {
        return texturedModel;
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

    public void generateEntity(int x, int z) {
        entity = new Entity(x,0,z);
        entity.setModel(texturedModel);
        try {
            Sys.NORMAL_ENTITY_SYSTEM.addElement(entity);
        } catch (CoreException e1) {
            e1.printStackTrace();
        }
    }

    public void destroyEntity() {
        try {
            Sys.NORMAL_ENTITY_SYSTEM.removeElement(entity);
        } catch (Exception e1) {
            e1.printStackTrace();
        }
    }



}
