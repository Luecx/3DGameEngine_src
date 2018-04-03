package projects.vier_gewinnt;

import engine.core.exceptions.CoreException;
import engine.core.master.RenderSettings;
import engine.core.sourceelements.RawModel;
import engine.core.system.Sys;
import engine.linear.entities.Entity;
import engine.linear.entities.TexturedModel;
import engine.linear.loading.Loader;
import engine.linear.loading.OBJLoader;
import engine.linear.material.EntityMaterial;
import engine.linear.material.SkydomeElement;
import projects.mediavle_game.map.GroundMap;

/**
 * Created by finne on 27.03.2018.
 */
public class World {



    public World() {

        TexturedModel texturedModel;

        RawModel model = OBJLoader.loadOBJ("ground", true);
        EntityMaterial material = new EntityMaterial(Loader.loadTexture("raw"));
        material.setNormalMap(Loader.loadTexture("textures/normalmaps/groundnormal"));
        material.setUseNormalMap(true);
        texturedModel = new TexturedModel(model, material);
        texturedModel.setTextureStretch(2000);

        Entity e = new Entity(texturedModel);
        e.setScale(2000,1,2000);
        e.setPosition(-1000,0,1000);


        RenderSettings.skydome_fog = true;
        RenderSettings.skydome_radius = 5000;
        RenderSettings.skydome_fog_midlevel = 200;
        RenderSettings.skydome_fog_gradient = 4;
        RenderSettings.skydome_fog_density = 3;

        try {
            Sys.NORMAL_ENTITY_SYSTEM.addElement(e);
            Sys.SKYDOME_SYSTEM.addElement(new SkydomeElement("textures/colormaps/sky"));
        } catch (CoreException e1) {
            e1.printStackTrace();
        }
    }
}
