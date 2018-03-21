package projects.mediavle_game.map.entities.abs;

import engine.core.exceptions.CoreException;
import engine.core.system.Sys;
import engine.linear.entities.Entity;
import engine.linear.entities.TexturedModel;

/**
 * Created by finne on 20.03.2018.
 */
public abstract class UniqueGameEntity<T extends UniqueGameEntity<T>> extends GameEntity{


    protected Entity entity;

    protected int width;
    protected int height;

    public UniqueGameEntity(int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public abstract void generateEntity();

    public void destroyEntity() {
        try {
            Sys.NORMAL_ENTITY_SYSTEM.removeElement(entity);
        } catch (Exception e1) {
            e1.printStackTrace();
        }
    }

    public abstract TexturedModel getTexturedModel();

    public Entity getEntity() {
        return entity;
    }

    public abstract T clone();
}
