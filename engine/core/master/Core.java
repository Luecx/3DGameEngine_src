package engine.core.master;


import engine.core.components.Light;
import engine.core.components.PerspectiveCamera;
import engine.core.exceptions.CoreException;
import engine.core.sourceelements.RawModel;
import engine.core.system.Sys;
import engine.linear.entities.Entity;
import engine.linear.entities.TexturedModel;
import engine.linear.loading.Loader;
import engine.linear.loading.OBJLoader;
import engine.linear.material.EntityMaterial;
import engine.linear.material.SkydomeElement;
import engine.linear.material.TerrainMaterial;
import engine.linear.advancedterrain.Chunk;
import engine.linear.material.TerrainMultimapTexturePack;
import engine.render.instancedsystem.InstanceSet;
import org.lwjgl.util.vector.Vector3f;
import projects.game.objects.Plane;
import projects.game.objects.plane.missile.Missile;
import projects.game.objects.plane.missile.MissileSystem;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Luecx on 16.02.2017.
 */
public class Core extends RenderCore{
    public Core() {
        super();
    }

    Plane plane;
    ArrayList<Light> lights;


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


        engine.linear.advancedterrain.Terrain terrain = new engine.linear.advancedterrain.Terrain();
        terrain.setTerrainMaterial(advancedTerrainMaterial);
        Chunk chunk = new Chunk(-10000,-10000,11,10);
        engine.linear.advancedterrain.HeightMap map = new engine.linear.advancedterrain.HeightMap(11, 1);
        map.setScaleFactor(1300);
        map.convertImageToData("textures/heightmaps/canyon");
        chunk.generateHeights(map.getHeights());
        chunk.generateModelData();
        chunk.generateBlendDataFromNormals( 0,1000,0.15f,0.05f);
        chunk.createRawModelFromCollectedData();

        terrain.getChunks().add(chunk);

        lights = new ArrayList<>();
        lights.add(new Light(10000,100000,10000));

        MissileSystem.enableSystem();

        EntityMaterial mat = new EntityMaterial(Loader.loadTexture("objectWithTextures/F-4E_Phantom_II/F-4E_Phantom_II_P01"));
        mat.setUseNormalMap(true);
        mat.setNormalMap(Loader.loadTexture("objectWithTextures/F-4E_Phantom_II/F-4E_Phantom_II_N"));
        RawModel rawModel = OBJLoader.loadOBJ("objectWithTextures/F-4E_Phantom_II/F-4E_Phantom_II", true);
        plane = new Plane(new TexturedModel(rawModel, mat));
        plane.setPosition(0,1000,0);
        plane.setRotation(0,180,0);


        try {
            Sys.SKYDOME_SYSTEM.addElement(skydomeElement);
            Sys.ADVANCED_TERRAIN_SYSTEM.addElement(terrain);
            Sys.NORMAL_ENTITY_SYSTEM.addElement(plane.getEntity());
            Sys.PARTICLE_SYSTEM.addElement(plane.getEmitter());
            Sys.PARTICLE_SYSTEM.addElement(plane.getLeft());
            Sys.PARTICLE_SYSTEM.addElement(plane.getRight());
        } catch (CoreException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onDisable() {

    }

    @Override
    protected void render() {
        plane.move(DisplayManager.processedFrameTime());
        MissileSystem.update(DisplayManager.processedFrameTime());
        Sys.INSTANCED_ENTITY_SYSTEM.render(lights, plane.getActiveCamera());
        Sys.ADVANCED_TERRAIN_SYSTEM.render(lights, plane.getActiveCamera());
        Sys.SKYDOME_SYSTEM.render(plane.getActiveCamera());
        Sys.TERRAIN_SYSTEM.render(lights, plane.getActiveCamera());
        Sys.ENTITY_SYSTEM.render(lights, plane.getActiveCamera());
        Sys.NORMAL_ENTITY_SYSTEM.render(lights, plane.getActiveCamera());
        Sys.PARTICLE_SYSTEM.render(plane.getActiveCamera());
    }

    public static void main(String[] args){

        Core c = new Core();
//            RenderCore rend = new RenderCore() {
//
//                private PerspectiveCamera cam;
//                private ArrayList<Light> lights;
//
//                private InstanceSet set;
//
//                @Override
//                protected void onEnable() {
//                    Sys.enableAll();
//                    cam = new PerspectiveCamera();
//                    lights = new ArrayList<>();
//                    lights.add(new Light(10,10,10));
//
//                    TexturedModel model = new TexturedModel(OBJLoader.loadOBJ("models/cube",false),new EntityMaterial(Loader.loadTexture("textures/colormaps/CliffRock")));
//                    set = new InstanceSet(model);
//                    for(int i = 0; i < 100; i++){
//                        for(int n = 0; n < 100; n++){
//                            set.addInstance(i * 5, 0, n * 5);
//                        }
//                    }
//                    set.updateVbo();
//                    try {
//                        Sys.INSTANCED_ENTITY_SYSTEM.addElement(set);
//
//                    } catch (Exception ex) {
//                        ex.printStackTrace();
//                    }
//                }
//
//                @Override
//                protected void onDisable() {
//
//                }
//
//                @Override
//                protected void render() {
//                    cam.move();
//                    for(int i = 0; i < 100; i++){
//                        for(int n = 0; n < 100; n++){
//                            set.getInstance(i + n * 100)[1] = 3 * ((float) Math.sin(i / 3f + (System.currentTimeMillis() / 400d)) * (float) Math.sin(n / 3f + (System.currentTimeMillis() / 400d)));
//                        }
//                    }
//
//                    set.updateVbo();
//
//                    Sys.SKYDOME_SYSTEM.render(cam);
//                    Sys.OVERLAY_SYSTEM.render();
//                    Sys.TERRAIN_SYSTEM.render(lights, cam);
//                    Sys.ENTITY_SYSTEM.render(lights,cam);
//                    Sys.NORMAL_ENTITY_SYSTEM.render(lights,cam);
//                    Sys.INSTANCED_ENTITY_SYSTEM.render(lights, cam);
//                    Sys.PARTICLE_SYSTEM.render(cam);
//                }
//            };
//
//            RenderCore core = new RenderCore() {
//
//                private PerspectiveCamera cam;
//                private ArrayList<Light> lights;
//                private Missile m;
//
//                @Override
//                protected void onEnable() {
//
//                    Sys.enableAll();
//                    cam = new PerspectiveCamera();
//                    lights = new ArrayList<>();
//                    lights.add(new Light(0,10,0));
//
//                    TexturedModel model = new TexturedModel(OBJLoader.loadOBJ("models/cube", false), new EntityMaterial(Loader.loadTexture("textures/colormaps/redPng")));
//                    Entity e = new Entity(model);
//                    Entity e2 = new Entity(model);
//                    e.setScale(20,20,20);
//                    e2.setPosition(3000,0,0);
//                    e2.setScale(20,20,20);
//
//
//                    m = new Missile(new Vector3f(0,0,0), new Vector3f(3000,0,0), 500, 65);
//                    try {
//                        Sys.ENTITY_SYSTEM.addElement(e);
//                        Sys.ENTITY_SYSTEM.addElement(e2);
//                    } catch (Exception ex) {
//                        ex.printStackTrace();
//                    }
//
//                }
//
//                @Override
//                protected void onDisable() {
//
//                }
//
//                @Override
//                protected void render() {
//                    cam.move();
//                    m.process(DisplayManager.processedFrameTime());
//                    Sys.SKYDOME_SYSTEM.render(cam);
//                    Sys.OVERLAY_SYSTEM.render();
//                    Sys.TERRAIN_SYSTEM.render(lights, cam);
//                    Sys.ENTITY_SYSTEM.render(lights,cam);
//                    Sys.NORMAL_ENTITY_SYSTEM.render(lights,cam);
//                    Sys.INSTANCED_ENTITY_SYSTEM.render(lights, cam);
//                    Sys.PARTICLE_SYSTEM.render(cam);
//                }
//            };


//        Core c = new Core();
//        for(int i = 0; i < 90; i++) {
//            Vector3f vec = new Vector3f((float)Math.sin(Math.toRadians(i)),(float)Math.cos(Math.toRadians(i)), 0);
//            System.out.println(" deg = " + i + "   dot = " + Vector3f.dot(vec, new Vector3f(0,1,0)));
//        }

//            RenderCore c = new RenderCore() {
//
//                private PerspectiveCamera cam;
//                private ArrayList<Light> lights;
//
//                @Override
//                protected void onEnable() {
//                    Sys.enableAll();
//                    cam = new PerspectiveCamera();
//                    lights = new ArrayList<>();
//                    lights.add(new Light(-1000,1000,1000));
//                    TexturedModel model = null;
//                    try {
//                        model = new TexturedModel(Loader.loadToVao(OBJConverter.convertOBJ("models/cube", true)), new EntityMaterial(Loader.loadTexture("objectWithTextures/spacestation/A/tex")));
//                    } catch (CoreException e) {
//                        e.printStackTrace();
//                    }
//                    model.getMaterial().setNormalMap(Loader.loadTexture("objectWithTextures/spacestation/A/normal2"));
//                    model.getMaterial().setUseNormalMap(true);
//
//                    Entity entity = new Entity(model);
////
////                    TexturedModel model = new TexturedModel(OBJLoader.loadOBJ("objectWithTextures/spacestation/A/final", true), new EntityMaterial(Loader.loadTexture("objectWithTextures/spacestation/A/tex")));
////                    model.getMaterial().setNormalMap(Loader.loadTexture("objectWithTextures/spacestation/A/normal2"));
////                    model.getMaterial().setUseNormalMap(true);
////                    model.setTextureStretch(10);
////                    Entity e = new Entity(model);
////                    e.setScale(20,20,20);
////
////                    TexturedModel model2 = new TexturedModel(OBJLoader.loadOBJ("objectWithTextures/aircrafts/A", true), new EntityMaterial(Loader.loadTexture("objectWithTextures/aircrafts/A")));
////                    model2.getMaterial().setNormalMap(Loader.loadTexture("objectWithTextures/aircrafts/normal2"));
////                    model2.getMaterial().setUseNormalMap(true);
////                    Entity e2 = new Entity(model2);
//
//                    System.out.println(entity.getRawModel().getVaoIdentifier());
//
//                    try {
//                      //Sys.ENTITY_SYSTEM.addElement(entity);
//                        Sys.NORMAL_ENTITY_SYSTEM.addElement(entity);
//                        System.out.println(Sys.NORMAL_ENTITY_SYSTEM.getData().size());
//                    } catch (Exception ex) {
//                        ex.printStackTrace();
//                    }
//                }
//
//                @Override
//                protected void onDisable() {
//
//                }
//
//                @Override
//                protected void render() {
//                    cam.move();
//                    Sys.SKYDOME_SYSTEM.render(cam);
//                    Sys.OVERLAY_SYSTEM.render();
//                    Sys.TERRAIN_SYSTEM.render(lights, cam);
//                    Sys.ENTITY_SYSTEM.render(lights,cam);
//                    Sys.NORMAL_ENTITY_SYSTEM.render(lights,cam);
//                    Sys.INSTANCED_ENTITY_SYSTEM.render(lights, cam);
//                    Sys.PARTICLE_SYSTEM.render(cam);
//                }
//            };



//            RenderCore core = new RenderCore() {
//
//                private ShadowCamera shadowCamera;
//                private PerspectiveCamera camera;
//                private ArrayList<Light> lights = new ArrayList<>();
//
//                @Override
//                protected void onEnable() {
//                    Sys.NORMAL_ENTITY_SYSTEM.createSystem();
//                    Sys.ENTITY_SYSTEM.createSystem();
//                    Sys.SHADOW_SYSTEM.createSystem();
//
//                    shadowCamera = new ShadowCamera(new Vector3f(20,20,20), new Vector3f(3,3,3), ShadowCamera.STATIC_DIRECTION);
//                    shadowCamera.setDistanceToTarget(50);
//                    shadowCamera.setFAR(100);
//
//                    camera = new PerspectiveCamera();
//                    lights.add(new Light(100,100,100));
//
//                    RawModel model = OBJLoader.loadOBJ("models/Razor", true);
//                    EntityMaterial material = new EntityMaterial(Loader.loadTexture("textures/colormaps/brick"));
//                    material.setNormalMap(Loader.loadTexture("textures/normalmaps/normalMap"));
//                    material.setUseNormalMap(false);
//
//                    TexturedModel texModel = new TexturedModel(model,material);
//
//                    Entity e = new Entity(texModel);
//                    e.setPosition(3,3,3);
//
//                    RawModel model2 = OBJLoader.loadOBJ("models/env", true);
//                    EntityMaterial material2 = new EntityMaterial(Loader.loadTexture("textures/colormaps/grass"));
//                    TexturedModel texModel2 = new TexturedModel(model2,material2);
//                    texModel2.setTextureStretch(25);
//
//                    Entity e2 = new Entity(texModel2);
//                    e2.setScale(10,10,10);
//
//
//                    try {
//                        Sys.NORMAL_ENTITY_SYSTEM.addElement(e);
//                        //Sys.ENTITY_SYSTEM.addElement(e);
//                        Sys.ENTITY_SYSTEM.addElement(e2);
//                        Sys.SHADOW_SYSTEM.addElement(e);
//                    } catch (CoreException e1) {
//                        e1.printStackTrace();
//                    }
//
//                }
//
//                @Override
//                protected void onDisable() {
//
//                }
//
//                @Override
//                protected void render() {
//                    Sys.SHADOW_SYSTEM.render(shadowCamera);
//                    Sys.NORMAL_ENTITY_SYSTEM.render(lights, camera);
//                    Sys.ENTITY_SYSTEM.render(lights, camera);
//                    camera.move();
//                }
//            };

//        RenderCore renderCore = new RenderCore() {
//
//            PerspectiveCamera ca = new PerspectiveCamera();
//            ArrayList<Light> lights = new ArrayList<>();
//
//            @Override
//            protected void onEnable() {
//                Sys.enableAll();
//
//                RawModel rawModel = OBJLoader.loadOBJ("models/cube", false);
//                TexturedModel modelA = new TexturedModel(rawModel, new EntityMaterial(Loader.loadTexture("textures/colormaps/redPng")));
//                TexturedModel modelB = new TexturedModel(rawModel, new EntityMaterial(Loader.loadTexture("textures/colormaps/redPng")));
//
//
//                Entity a = new Entity(modelA);
//                a.setScale(1,10,10);
//
//                Entity a2 = new Entity(modelA);
//                a2.setScale(1,10,10);
//                a2.setPosition(1.5f,0,0);
//
//                Entity b1 = new Entity(modelA);
//                b1.setScale(1,7,7);
//                b1.setPosition(4,0,0);
//
//                Entity b2 = new Entity(modelA);
//                b2.setScale(1,7,7);
//                b2.setPosition(5.5f,0,0);
//
//
//                lights.add(new Light(100,100,100));
//
//                try {
//                    Sys.ENTITY_SYSTEM.addElement(a);
//                    Sys.ENTITY_SYSTEM.addElement(a2);
//                    Sys.ENTITY_SYSTEM.addElement(b2);
//                    Sys.ENTITY_SYSTEM.addElement(b1);
//                } catch (CoreException e) {
//                    e.printStackTrace();
//                }
//
//
//            }
//
//
//            @Override
//            protected void onDisable() {
//
//            }
//
//            @Override
//            protected void render() {
//                ca.move();
//                Sys.ENTITY_SYSTEM.render(lights, ca);
//            }
//        };

    }

}
