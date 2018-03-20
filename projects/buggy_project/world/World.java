package projects.buggy_project.world;

import engine.core.components.Light;
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
import engine.render.skydomesystem.SkydomeSystem;
import projects.buggy_project.Parameter;

import java.util.ArrayList;

/**
 * Created by finne on 04.03.2018.
 */
public class World implements WorldInterface {


    @Override
    public float getHeight(float x, float y) {
        return 0;
    }

    ArrayList<Light> lights = new ArrayList<>();

    @Override
    public ArrayList<Light> getLights() {
        return lights;
    }

    @Override
    public void generateWorld() {
        RawModel model = OBJLoader.loadOBJ("models/env", true);
        EntityMaterial material = new EntityMaterial(Loader.loadTexture("textures/colormaps/stripes"));
        TexturedModel texturedModel = new TexturedModel(model, material);
        texturedModel.setTextureStretch(1000);

        Entity e = new Entity(texturedModel);
        e.setScale(1000,1,1000);

        try {
            Sys.ENTITY_SYSTEM.addElement(e);
        } catch (CoreException e1) {
            e1.printStackTrace();
        }

        lights.add(new Light(1000,1000,1000));


        //Skydome:
        SkydomeElement element = new SkydomeElement(Loader.loadTexture(Parameter.SKYDOME_TEXTURE));
        RenderSettings.skydome_use_skysphere = false;
        RenderSettings.skydome_radius = 30000;
        RenderSettings.skydome_follow_x_axis = true;
        RenderSettings.skydome_follow_z_axis = true;
        RenderSettings.skydome_follow_y_axis = false;
        RenderSettings.skydome_bounding_y_axis = -3000;
        RenderSettings.skydome_fog_midlevel = 3000;
        RenderSettings.skydome_fog_gradient = 0.3f;
        try {
            Sys.SKYDOME_SYSTEM.addElement(element);
        } catch (CoreException e1) {
            e1.printStackTrace();
        }

    }

    @Override
    public void generateWorldRenderSettings() {

    }
}
