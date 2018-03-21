package projects.mediavle_game.map.entities.obs;

import engine.core.exceptions.CoreException;
import engine.core.sourceelements.RawModel;
import engine.core.system.Sys;
import engine.linear.entities.Entity;
import engine.linear.entities.TexturedModel;
import engine.linear.loading.Loader;
import engine.linear.loading.OBJLoader;
import engine.linear.material.EntityMaterial;
import projects.mediavle_game.map.GroundMap;
import projects.mediavle_game.map.entities.abs.UniqueGameEntity;

/**
 * Created by finne on 21.03.2018.
 */
public class Ground extends UniqueGameEntity {
    private static TexturedModel texturedModel;

    public static void generateTexturedModel() {
        RawModel model = OBJLoader.loadOBJ("ground", true);
        EntityMaterial material = new EntityMaterial(Loader.loadTexture("raw"));
        texturedModel = new TexturedModel(model, material);
        texturedModel.setTextureStretch(GroundMap.WIDTH);
    }

    @Override
    public void generateEntity() {
        entity = new Entity(x, 0, y);
        entity.setModel(texturedModel);
        entity.setScale(GroundMap.WIDTH, 1, GroundMap.HEIGHT);
        entity.setPosition(0,0,GroundMap.HEIGHT);
        try {
            Sys.NORMAL_ENTITY_SYSTEM.addElement(entity);
        } catch (CoreException e1) {
            e1.printStackTrace();
        }
    }

    public Ground(int x, int y, int width, int height) {
        super(x, y, width, height);
    }
}
