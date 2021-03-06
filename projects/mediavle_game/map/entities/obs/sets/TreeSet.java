package projects.mediavle_game.map.entities.obs.sets;

import engine.core.exceptions.CoreException;
import engine.core.sourceelements.RawModel;
import engine.core.system.Sys;
import engine.linear.entities.TexturedModel;
import engine.linear.loading.Loader;
import engine.linear.loading.OBJLoader;
import engine.linear.material.EntityMaterial;
import engine.render.instancedsystem.InstanceSet;
import projects.mediavle_game.map.entities.abs.InstancedGameEntitySet;
import projects.mediavle_game.map.entities.obs.Tree;

/**
 * Created by finne on 20.03.2018.
 */
public class TreeSet extends InstancedGameEntitySet<Tree> {

    public static TreeSet TREE_SET;
    public static TexturedModel texturedModel;

    public static void generateTexturedModel() {
        TREE_SET = new TreeSet();

        RawModel model = OBJLoader.loadOBJ("models/goodTree", true);
        EntityMaterial material = new EntityMaterial(Loader.loadTexture("textures/colormaps/treetexture"));
        texturedModel = new TexturedModel(model, material);

        TREE_SET.instanceSet = new InstanceSet(texturedModel);
        TREE_SET.instanceSet.setRandomRotation(false,true,false);
        TREE_SET.positions = TREE_SET.instanceSet.getPositions();
        try {
            Sys.INSTANCED_ENTITY_SYSTEM.addElement(TREE_SET.instanceSet);
        } catch (CoreException e) {
            e.printStackTrace();
        }
    }
}
