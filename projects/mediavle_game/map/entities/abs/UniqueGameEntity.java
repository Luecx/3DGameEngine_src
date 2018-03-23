package projects.mediavle_game.map.entities.abs;

import engine.core.exceptions.CoreException;
import engine.core.system.Sys;
import engine.linear.entities.Entity;
import engine.linear.entities.TexturedModel;

/**
 * Created by finne on 20.03.2018.
 */
public abstract class UniqueGameEntity<T extends UniqueGameEntity<T>> extends GameEntity<T>{


    protected Entity entity;

    public UniqueGameEntity(int x, int y, int width, int height) {
        super(x, y, width, height);
    }

    public abstract void generateEntity();

    public void destroyEntity() {
        try {
            Sys.NORMAL_ENTITY_SYSTEM.removeElement(entity);
        } catch (Exception e1) {
            e1.printStackTrace();
        }
    }

    public abstract T clone();

    public Entity getEntity() {
        return entity;
    }

}
