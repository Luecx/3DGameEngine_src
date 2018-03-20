package projects.mediavle_game.map.entities.obs;

import engine.core.sourceelements.RawModel;
import engine.linear.entities.TexturedModel;
import engine.linear.loading.Loader;
import engine.linear.loading.OBJLoader;
import engine.linear.material.EntityMaterial;
import projects.mediavle_game.map.entities.abs.InstancedGameEntitySet;

/**
 * Created by finne on 20.03.2018.
 */
public class TreeSet extends InstancedGameEntitySet<Tree> {

    public final static TreeSet TREE_SET = new TreeSet();

    @Override
    public void generateTexturedModel() {
        RawModel model = OBJLoader.loadOBJ("models/goodTree", true);
        EntityMaterial material = new EntityMaterial(Loader.loadTexture("raw"));
        this.texturedModel = new TexturedModel(model, material);
        this.generateInstanceSet();
    }

    @Override
    public void destroy() {

    }
}
