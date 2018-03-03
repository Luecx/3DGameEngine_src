package projects.ex1;

import engine.core.components.Light;
import engine.core.components.PerspectiveCamera;
import engine.core.exceptions.CoreException;
import engine.core.master.RenderCore;
import engine.core.sourceelements.RawModel;
import engine.core.system.Sys;
import engine.linear.entities.Entity;
import engine.linear.entities.TexturedModel;
import engine.linear.gui.GuiButton;
import engine.linear.loading.Loader;
import engine.linear.loading.OBJLoader;
import engine.linear.material.EntityMaterial;
import org.lwjgl.util.vector.Vector2f;

import java.util.ArrayList;

/**
 * Created by finne on 03.03.2018.
 */
public class Ex1Core extends RenderCore {

    public Ex1Core() {
    }

    ArrayList<Light> lights = new ArrayList<>();
    PerspectiveCamera perspectiveCamera = new PerspectiveCamera();
    Entity entity;

    @Override
    protected void onEnable() {
        Sys.enableAll();

        lights.add(new Light(1000,1000,1000));
        perspectiveCamera.setPosition(0,0,10);
        perspectiveCamera.setFOV(110);

        RawModel rawModel = OBJLoader.loadOBJ("models/cube", true);
        EntityMaterial material = new EntityMaterial(Loader.loadTexture("brick"));
        material.setNormalMap(Loader.loadTexture("brick"));
        material.setUseNormalMap(true);

        TexturedModel texturedModel = new TexturedModel(rawModel, material);

        entity = new Entity(texturedModel);
        entity.setPosition(0,0,0);
        entity.increaseRotation(10,10,10);

        try {
            Sys.NORMAL_ENTITY_SYSTEM.addElement(entity);
        } catch (CoreException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        Ex1Core c = new Ex1Core();
    }

    @Override
    protected void onDisable() {

    }

    @Override
    protected void render() {
        perspectiveCamera.move();
        Sys.NORMAL_ENTITY_SYSTEM.render(lights, perspectiveCamera);
        entity.increaseRotation(1,1,0);
    }
}
