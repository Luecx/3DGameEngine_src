package engine.render.tesselationTerrainSystem;

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

public class TesselationTester extends RenderCore {


    TerrainSystem terrainSystem;
    PerspectiveCamera perspectiveCamera = new PerspectiveCamera(0,0,0);

    @Override
    protected void onEnable() {

        Sys.enableAll();

        terrainSystem = new TerrainSystem();
        terrainSystem.createSystem();


        System.out.println(terrainSystem.isEnabled());

        RawModel model = OBJLoader.loadOBJ("models/cube", false);
        EntityMaterial entityMaterial = new EntityMaterial(Loader.loadTexture("textures/colormaps/redPng"));
        Entity entity = new Entity(new TexturedModel(model, entityMaterial));


        try {
            Sys.ENTITY_SYSTEM.addElement(entity);
            terrainSystem.addElement(entity);
        } catch (CoreException e) {
            e.printStackTrace();
        }


    }

    @Override
    protected void onDisable() {

        terrainSystem.closeSystem();
    }

    @Override
    protected void render() {
        perspectiveCamera.move();
        //Sys.ENTITY_SYSTEM.render(new ArrayList<>(), perspectiveCamera);
        terrainSystem.render(new ArrayList<>(), perspectiveCamera);
    }


    public static void main(String[] args){
        new TesselationTester();
    }
}
