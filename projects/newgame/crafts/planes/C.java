package projects.newgame.crafts.planes;

import engine.core.components.Light;
import engine.core.exceptions.CoreException;
import engine.core.master.RenderCore;
import engine.core.master.RenderSettings;
import engine.core.system.Sys;
import engine.linear.loading.Loader;
import engine.linear.material.SkydomeElement;
import engine.linear.material.TerrainMaterial;
import engine.linear.terrain.TerrainMultimapTexturePack;
import engine.linear.advancedterrain.Chunk;
import engine.linear.advancedterrain.Terrain;
import projects.newgame.crafts.missiles.MissileSystem;
import org.lwjgl.util.vector.Vector3f;

import java.util.ArrayList;

/**
 * Created by Luecx on 16.09.2017.
 */
public class C extends RenderCore{
    public C() {
        super();
    }

    Jet jet;
    Terrain terrain;
    ArrayList<Light> lights;


    public static void main(String[] args) {
        C c = new C();
    }

    @Override
    protected void onEnable()  {
        Sys.enableAll();
        TerrainMultimapTexturePack advancedTerrainMaterial = new TerrainMultimapTexturePack(
                Loader.loadTexture("textures/heightmaps/canyonOverlay"),
                new TerrainMaterial(Loader.loadTexture("textures/colormaps/sand2"), Loader.loadTexture("textures/normalmaps/sand_NORM2")),
                new TerrainMaterial(Loader.loadTexture("textures/colormaps/sand2"), Loader.loadTexture("textures/normalmaps/sand_NORM2")),
                new TerrainMaterial(Loader.loadTexture("textures/colormaps/Craggy"), Loader.loadTexture("textures/normalmaps/stoneNormal")),
                new TerrainMaterial(Loader.loadTexture("textures/colormaps/cliffRock"), Loader.loadTexture("textures/normalmaps/cliffNormal")
                ));
        advancedTerrainMaterial.getBlackMaterial().setTextureStretch(3000);
        advancedTerrainMaterial.getRedMaterial().setTextureStretch(3000);
        advancedTerrainMaterial.getGreenMaterial().setTextureStretch(3000);
        advancedTerrainMaterial.getBlueMaterial().setTextureStretch(3000);

        SkydomeElement skydomeElement = new SkydomeElement(Loader.loadTexture("textures/colormaps/sky"));

        RenderSettings.skydome_fog = true;
        RenderSettings.skydome_follow_x_axis = true;
        RenderSettings.skydome_follow_z_axis = true;
        RenderSettings.skydome_follow_y_axis = false;
        RenderSettings.skydome_use_skysphere = false;
        RenderSettings.skydome_bounding_y_axis = -1000;
        RenderSettings.skydome_radius = 30000;

        RenderSettings.terrain_fog = true;
        RenderSettings.terrain_fog_density = 0.0002f;
        RenderSettings.terrain_fog_gradient = 3;


        terrain = new engine.linear.advancedterrain.Terrain();
        terrain.setTerrainMaterial(advancedTerrainMaterial);
        Chunk chunk = new Chunk(-10000,-10000,10,22);
        engine.linear.advancedterrain.HeightMap map = new engine.linear.advancedterrain.HeightMap(10, 1);
        map.setScaleFactor(1000);
        map.convertImageToData("textures/heightmaps/canyon");
        chunk.generateHeights(map.getHeights());

        chunk.addNoise(0.7f);
        chunk.generateModelData();

        chunk.generateBlendDataFromNormals( 0,1000,0.15f,0.05f);
        chunk.createRawModelFromCollectedData();
        terrain.getChunks().add(chunk);

        lights = new ArrayList<>();
        lights.add(new Light(0,100000,0));

        MissileSystem.enableSystem();

        JetEnum.generateTexturedModel();
        jet = JetEnum.F_4E_Phantom_II.generatePlane(new Vector3f(0,1000,0),new Vector3f(0,0,0));

        try {
            Sys.SKYDOME_SYSTEM.addElement(skydomeElement);
            Sys.ADVANCED_TERRAIN_SYSTEM.addElement(terrain);
//            Sys.NORMAL_ENTITY_SYSTEM.addElement(jet.getEntity());
        } catch (CoreException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onDisable() {

    }

    @Override
    protected void render() {
//        if(jet.move(terrain,DisplayManager.processedFrameTime())==false){
//            this.stopRenderThread();
//        }
//        MissileSystem.update(terrain, DisplayManager.processedFrameTime());
//        Sys.INSTANCED_ENTITY_SYSTEM.render(lights, jet.getActiveCamera());
//        Sys.ADVANCED_TERRAIN_SYSTEM.render(lights, jet.getActiveCamera());
//        Sys.SKYDOME_SYSTEM.render(jet.getActiveCamera());
//        Sys.TERRAIN_SYSTEM.render(lights, jet.getActiveCamera());
//        Sys.ENTITY_SYSTEM.render(lights, jet.getActiveCamera());
//        Sys.NORMAL_ENTITY_SYSTEM.render(lights, jet.getActiveCamera());
//        Sys.PARTICLE_SYSTEM.render(jet.getActiveCamera());
    }
}
