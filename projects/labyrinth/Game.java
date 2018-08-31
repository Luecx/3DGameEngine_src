package projects.labyrinth;

import engine.core.components.Light;
import engine.core.components.PerspectiveCamera;
import engine.core.exceptions.CoreException;
import engine.core.master.RenderCore;
import engine.core.master.RenderSettings;
import engine.core.sourceelements.RawModel;
import engine.core.system.Sys;
import engine.linear.entities.Entity;
import engine.linear.entities.TexturedModel;
import engine.linear.loading.Loader;
import engine.linear.loading.OBJLoader;
import engine.linear.material.EntityMaterial;
import engine.linear.material.Material;
import engine.linear.material.SkydomeElement;
import projects.buggy_project.Parameter;

import java.util.ArrayList;

public class Game extends RenderCore {

    private Labyrinth labyrinth;

    private PerspectiveCamera perspectiveCamera;
    private Entity ground;
    private ArrayList<Light> lights = new ArrayList<Light>();

    public Game(Labyrinth labyrinth) {
        this.labyrinth = labyrinth;
    }

    @Override
    protected void onEnable() {
        Sys.enableAll();
        labyrinth.generate();
        for(Entity e:labyrinth.getModel()){
            try {
                Sys.ENTITY_SYSTEM.addElement(e);
            } catch (CoreException e1) {
                e1.printStackTrace();
            }
        }

        perspectiveCamera = new PerspectiveCamera();
        perspectiveCamera.setPosition(10,10,10);

        lights.add(new Light(1000,10000,3000));

        RawModel rawModel = OBJLoader.loadOBJ("models/env", false);
        EntityMaterial material = new EntityMaterial(Loader.loadTexture("textures/colormaps/grass"));
        TexturedModel model = new TexturedModel(rawModel, material);
        model.setTextureStretch(1000);
        Entity e = new Entity(model);
        e.setScale(1000,1,1000);
        try {
            Sys.ENTITY_SYSTEM.addElement(e);
        } catch (CoreException e1) {
            e1.printStackTrace();
        }


        SkydomeElement element = new SkydomeElement(Loader.loadTexture("textures/colormaps/sky"));
        RenderSettings.skydome_use_skysphere = false;
        RenderSettings.skydome_radius = 30000;
        RenderSettings.skydome_follow_x_axis = true;
        RenderSettings.skydome_follow_z_axis = true;
        RenderSettings.skydome_follow_y_axis = false;
        RenderSettings.skydome_bounding_y_axis = 000;
        RenderSettings.skydome_fog_midlevel = 3000;
        RenderSettings.skydome_fog_gradient = 0.3f;
        try {
            Sys.SKYDOME_SYSTEM.addElement(element);
        } catch (CoreException e1) {
            e1.printStackTrace();
        }

    }

    @Override
    protected void onDisable() {

    }

    @Override
    protected void render() {
        Sys.ENTITY_SYSTEM.render(lights, perspectiveCamera);
        Sys.SKYDOME_SYSTEM.render(perspectiveCamera);
        perspectiveCamera.move();
    }


    public static void main(String[] args){
        Game g = new Game(new labyrinth_2D(10));
    }
}
