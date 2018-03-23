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
import projects.mediavle_game.map.entities.obs.Street;

/**
 * Created by finne on 22.03.2018.
 */
public class StreetSet extends InstancedGameEntitySet<Street> {

    public static StreetSet STREET_SET;
    public static TexturedModel texturedModel;

    public static void generateTexturedModel() {
        STREET_SET = new StreetSet();

        RawModel model = OBJLoader.loadOBJ("street", true);
        EntityMaterial material = new EntityMaterial(Loader.loadTexture("raw"));
        texturedModel = new TexturedModel(model, material);

        STREET_SET.instanceSet = new InstanceSet(texturedModel);
        STREET_SET.instanceSet.setRandomRotation(false,false,false);
        STREET_SET.positions = STREET_SET.instanceSet.getPositions();
        try {
            Sys.INSTANCED_ENTITY_SYSTEM.addElement(STREET_SET.instanceSet);
        } catch (CoreException e) {
            e.printStackTrace();
        }
    }

}
