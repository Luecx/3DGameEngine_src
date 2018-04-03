package projects.mediavle_game.map.entities.obs;

import engine.core.exceptions.CoreException;
import engine.core.sourceelements.RawModel;
import engine.core.system.Sys;
import engine.linear.entities.Entity;
import engine.linear.entities.TexturedModel;
import engine.linear.loading.Loader;
import engine.linear.loading.OBJLoader;
import engine.linear.material.EntityMaterial;
import projects.mediavle_game.map.entities.abs.UniqueGameEntity;

/**
 * Created by Anwender on 21.03.2018.
 */
public class Townhall extends UniqueGameEntity<Townhall> {

    private static TexturedModel texturedModel;

    public static void generateTexturedModel() {
        RawModel model = OBJLoader.loadOBJ("5x5House", true);
        EntityMaterial material = new EntityMaterial(Loader.loadTexture("raw"));
        Townhall.texturedModel = new TexturedModel(model, material);
    }

    @Override
    public void generateEntity() {
        entity = new Entity(x, 0, y);
        entity.setModel(texturedModel);
        try {
            Sys.NORMAL_ENTITY_SYSTEM.addElement(entity);
        } catch (CoreException e1) {
            e1.printStackTrace();
        }
    }

    @Override
    public Townhall clone() {
        return new Townhall(this.getX(), this.getY());
    }

    @Override
    public TexturedModel getTexturedModel() {
        return texturedModel;
    }

    public Townhall(int x, int y) {
        super(x, y, 5, 5);
    }

    @Override
    public void update(double time) {

    }
}