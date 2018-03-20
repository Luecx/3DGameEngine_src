package projects.buggy_project.world;

import engine.core.components.Light;
import engine.core.exceptions.CoreException;
import engine.core.master.RenderSettings;
import engine.core.sourceelements.RawModel;
import engine.core.system.Sys;
import engine.linear.advancedterrain.Chunk;
import engine.linear.advancedterrain.HeightMap;
import engine.linear.advancedterrain.Terrain;
import engine.linear.entities.Entity;
import engine.linear.entities.TexturedModel;
import engine.linear.loading.Loader;
import engine.linear.loading.OBJLoader;
import engine.linear.material.EntityMaterial;
import engine.linear.material.SkydomeElement;
import engine.linear.material.TerrainMaterial;
import engine.linear.material.TerrainMultimapTexturePack;
import engine.render.skydomesystem.SkydomeSystem;
import projects.buggy_project.Parameter;

import java.awt.*;
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


        Terrain terrain = new Terrain();

        TerrainMaterial materialA = new TerrainMaterial(
                Loader.loadTexture(Parameter.TERRAIN_MAT_A_COLOR),
                Loader.loadTexture(Parameter.TERRAIN_MAT_A_NORMAL));
        TerrainMaterial materialB = new TerrainMaterial(
                Loader.loadTexture(Parameter.TERRAIN_MAT_B_COLOR),
                Loader.loadTexture(Parameter.TERRAIN_MAT_B_NORMAL));
        TerrainMaterial materialC = new TerrainMaterial(
                Loader.loadTexture(Parameter.TERRAIN_MAT_C_COLOR),
                Loader.loadTexture(Parameter.TERRAIN_MAT_C_NORMAL));
        TerrainMaterial materialD = new TerrainMaterial(
                Loader.loadTexture(Parameter.TERRAIN_MAT_D_COLOR),
                Loader.loadTexture(Parameter.TERRAIN_MAT_D_NORMAL));
        TerrainMultimapTexturePack material = new TerrainMultimapTexturePack(
                Loader.loadTexture(Parameter.TERRAIN_OVERLAY_MAP),
                materialA,materialB,materialC,materialD
        );
        HeightMap heightMap = new HeightMap(
                Parameter.TERRAIN_VERTEX_POWER,
                Parameter.TERRAIN_VERTEX_OFFSET);
        heightMap.setScaleFactor(Parameter.TERRAIN_HEIGHT_SCALE);
        heightMap.convertImageToData(Parameter.TERRAIN_HEIGHT_MAP);

        Chunk c = new Chunk(
                -Parameter.TERRAIN_SIZE / 2,
                -Parameter.TERRAIN_SIZE / 2,
                Parameter.TERRAIN_VERTEX_POWER,
                (float)((double)Parameter.TERRAIN_SIZE / (double)(
                        Math.pow(2,Parameter.TERRAIN_VERTEX_POWER)-
                                Parameter.TERRAIN_VERTEX_OFFSET)));

        c.setHeights(heightMap.getHeights());
        c.generateModelData();
        c.generateBlendDataFromNormals(
                Parameter.TERRAIN_LOWER_MAT_HEIGHT,
                Parameter.TERRAIN_UPPER_MAT_HEIGHT,
                Parameter.TERRAIN_SLOAP_LIGHT,
                Parameter.TERRAIN_SLOAP_HARD);
        c.createRawModelFromCollectedData();
        terrain.getChunks().add(c);
        terrain.setTerrainMaterial(material);

        try {
            Sys.ADVANCED_TERRAIN_SYSTEM.addElement(terrain);
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
    public void generateWorldRenderSettings() {

    }
}
