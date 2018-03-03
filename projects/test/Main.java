package projects.test;

import engine.core.components.Light;
import engine.core.components.PerspectiveCamera;
import engine.core.exceptions.CoreException;
import engine.core.master.RenderCore;
import engine.core.sourceelements.RawModel;
import engine.core.system.Sys;
import engine.linear.entities.Entity;
import engine.linear.entities.TexturedModel;
import engine.linear.loading.Loader;
import engine.linear.loading.OBJLoader;
import engine.linear.material.EntityMaterial;

import java.util.ArrayList;

/**
 * Created by finne on 25.01.2018.
 */
public class Main extends RenderCore {


    ArrayList<Light> lights = new ArrayList<>();
    PerspectiveCamera camera = new PerspectiveCamera();

    @Override
    protected void onEnable() {

        Sys.enableAll();

        lights.add(new Light(1000,1000,1000));

        camera.setPosition(0,0,10);

        EntityMaterial mat = new EntityMaterial(Loader.loadTexture("textures/colormaps/sand"));
        mat.setUseNormalMap(true);
        mat.setNormalMap(Loader.loadTexture("textures/normalmaps/sand_NORM"));
        mat.setTransparency(false);
        mat.setDisplacementMap(Loader.loadTexture("textures/heightmaps/inv"));
        mat.setUseDisplacementMap(true);
        mat.setDisplaceFactor(600);

        RawModel model = OBJLoader.loadOBJ("models/env", true);

        TexturedModel texturedModel = new TexturedModel(model, mat);
        texturedModel.setTextureStretch(200);

        Entity a = new Entity(texturedModel);
        a.setScale(2000,1,2000);

        try {
            Sys.NORMAL_ENTITY_SYSTEM.addElement(a);
        } catch (CoreException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onDisable() {
        Sys.disableAll();
    }

    @Override
    protected void render() {

        camera.move();

        Sys.NORMAL_ENTITY_SYSTEM.render(lights, camera);
    }

    public static void main(String[] args) {
        Main m = new Main();
    }
}
