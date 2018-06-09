package projects.jet_game.client;

import engine.core.components.Light;
import engine.core.exceptions.CoreException;
import engine.core.master.RenderSettings;
import engine.core.sourceelements.RawModel;
import engine.core.system.Sys;
import engine.linear.advancedterrain.Chunk;
import engine.linear.advancedterrain.HeightMap;
import engine.linear.advancedterrain.Terrain;
import engine.linear.entities.TexturedModel;
import engine.linear.loading.Loader;
import engine.linear.loading.OBJLoader;
import engine.linear.material.EntityMaterial;
import engine.linear.material.SkydomeElement;
import engine.linear.material.TerrainMaterial;
import engine.linear.material.TerrainMultimapTexturePack;
import projects.jet_game.server.PlayerData;
import projects.jet_game.server.ServerOut;

import java.util.ArrayList;

public class World {


    static Terrain terrain;
    static Chunk chunk;
    static HeightMap map;
    static ArrayList<Light> lights;

    static TexturedModel texturedModel;

    static ArrayList<Plane> planes = new ArrayList<>();
    static Plane player;

    public static void generate_world(long player_id){


        Sys.enableAll();

        TerrainMultimapTexturePack advancedTerrainMaterial = new TerrainMultimapTexturePack(
                Loader.loadTexture("textures/heightmaps/canyonOverlay"),
                new TerrainMaterial(Loader.loadTexture("textures/colormaps/sand2"), Loader.loadTexture("textures/normalmaps/sand_NORM2")),
                new TerrainMaterial(Loader.loadTexture("textures/colormaps/sand2"), Loader.loadTexture("textures/normalmaps/sand_NORM2")),
                new TerrainMaterial(Loader.loadTexture("textures/colormaps/Craggy"), Loader.loadTexture("textures/normalmaps/stoneNormal")),
                new TerrainMaterial(Loader.loadTexture("textures/colormaps/cliffRock"), Loader.loadTexture("textures/normalmaps/cliffNormal")
                ));

        SkydomeElement skydomeElement = new SkydomeElement(Loader.loadTexture("textures/colormaps/sky"));

        RenderSettings.skydome_radius = 3000;
        RenderSettings.skydome_fog = true;
        RenderSettings.skydome_follow_x_axis = true;
        RenderSettings.skydome_follow_z_axis = true;
        RenderSettings.skydome_follow_y_axis = false;
        RenderSettings.skydome_use_skysphere = false;
        RenderSettings.skydome_bounding_y_axis = -000;
        RenderSettings.skydome_radius = 30000;

        RenderSettings.terrain_fog = true;
        RenderSettings.terrain_fog_density = 0.0002f;
        RenderSettings.terrain_fog_gradient = 3;


        terrain = new engine.linear.advancedterrain.Terrain();
        terrain.setTerrainMaterial(advancedTerrainMaterial);
        chunk = new Chunk(-5000,-5000,11,10);
        map = new engine.linear.advancedterrain.HeightMap(11, 1);
        map.setScaleFactor(1800);
        map.convertImageToData("textures/heightmaps/canyon");
        chunk.generateHeights(map.getHeights());
        chunk.smoothVertices(1);
        chunk.generateModelData();
        chunk.generateBlendDataFromNormals( 0,1000,0.15f,0.05f);
        chunk.createRawModelFromCollectedData();

        terrain.getChunks().add(chunk);

        lights = new ArrayList<>();
        lights.add(new Light(10000,100000,10000));

        EntityMaterial mat = new EntityMaterial(Loader.loadTexture("objectWithTextures/F-4E_Phantom_II/F-4E_Phantom_II_P01"));
        mat.setUseNormalMap(true);
        mat.setNormalMap(Loader.loadTexture("objectWithTextures/F-4E_Phantom_II/F-4E_Phantom_II_N"));
        RawModel rawModel = OBJLoader.loadOBJ("objectWithTextures/F-4E_Phantom_II/F-4E_Phantom_II", true);
        texturedModel = new TexturedModel(rawModel, mat);

        player = new Plane(texturedModel, player_id);
        player.setPosition(0,1000,0);
        player.setRotation(0,180,0);

        try {
            Sys.SKYDOME_SYSTEM.addElement(skydomeElement);
            Sys.ADVANCED_TERRAIN_SYSTEM.addElement(terrain);
            Sys.NORMAL_ENTITY_SYSTEM.addElement(player.getEntity());
        } catch (CoreException e) {
            e.printStackTrace();
        }
    }

    public static void processControlls(double passedTime){
        player.move(passedTime);
    }

    public static void updatePlanes(ServerOut serverOut){

        if(serverOut.getPlayerData().length > planes.size() + 1){
            for(int i = 0; i < planes.size(); i++){
                boolean has = false;
                for(PlayerData p:serverOut.getPlayerData()){
                    if(p.getId() == planes.get(i).getPlayer_id()){
                        has = true;
                    }
                }
                if(!has){
                    Sys.NORMAL_ENTITY_SYSTEM.removeElement(planes.get(i).getEntity());
                    planes.remove(i);
                }
            }
        }

        for(PlayerData p:serverOut.getPlayerData()){
            Plane pl = null;
            for(Plane plane:planes){
                if(p.getId() == plane.getPlayer_id()){
                    pl = plane;
                }
            }
            if(pl == null){
                pl = new Plane(texturedModel, p.getId());
                try {
                    Sys.NORMAL_ENTITY_SYSTEM.addElement(pl.getEntity());
                } catch (CoreException e) {
                    e.printStackTrace();
                }
            }

            pl.setPosition(p.getPosition());
            pl.setRotation(p.getRotation());
        }
    }

}
