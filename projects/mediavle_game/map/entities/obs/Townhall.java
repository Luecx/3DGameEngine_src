package projects.mediavle_game.map.entities.obs;

import engine.core.sourceelements.RawModel;
import engine.linear.entities.TexturedModel;
import engine.linear.loading.Loader;
import engine.linear.loading.OBJLoader;
import engine.linear.material.EntityMaterial;
import projects.mediavle_game.map.entities.abs.UniqueGameEntity;

/**
 * Created by Anwender on 21.03.2018.
 */
public class Townhall extends UniqueGameEntity {
    @Override
    public void generateTexturedModel() {
        RawModel model = OBJLoader.loadOBJ("models/goodTree", true);
        EntityMaterial material = new EntityMaterial(Loader.loadTexture("raw"));
        this.texturedModel = new TexturedModel(model, material);
    }

    public Townhall(int x, int y) {
        super(x, y, 5, 5);
    }
}