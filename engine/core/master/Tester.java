//package engine.core.master;
//
//import engine.core.components.Group;
//import engine.core.components.Light;
//import engine.core.components.PerspectiveCamera;
//import engine.core.exceptions.CoreException;
//import engine.core.system.Sys;
//import engine.debugging.guis.Frame;
//import engine.linear.entities.Entity;
//import engine.linear.entities.TexturedModel;
//import engine.linear.loading.Loader;
//import engine.linear.loading.OBJLoader;
//import engine.linear.material.EntityMaterial;
//import engine.linear.material.TerrainMaterial;
//import engine.linear.oldterrain.TerrainMultimapTexturePack;
//import engine.linear.terrain.Chunk;
//import engine.linear.terrain.HeightMap;
//import engine.linear.terrain.Terrain;
//import oldproject.game.objects.plane.planes.JetEnum;
//import oldproject.newgame.crafts.planes.JetData;
//import org.lwjgl.util.vector.Vector3f;
//import org.newdawn.slick.opengl.Texture;
//
//import java.util.ArrayList;
//
///**
// * Created by finne on 27.08.2017.
// */
//public class Tester extends RenderCore{
//
//
//    public static void main(String[] args) {
//        Tester t = new Tester();
//    }
//
//    PerspectiveCamera camera;
//    ArrayList<Light> lights;
//
//    JetData jet;
//
//    @Override
//    protected void onEnable() {
//        Sys.enableAll();
//
//        oldproject.newgame.crafts.planes.JetEnum.generateTexturedModel();
//        Entity e = new Entity(new TexturedModel(OBJLoader.loadOBJ("models/env", true), new EntityMaterial(Loader.loadTexture("textures/colormaps/sand"))));
//        e.setScale(100,100,100);
//        e.getModel().getMaterial().setTransparency(true);
//
//        jet = oldproject.newgame.crafts.planes.JetEnum.F_4E_Phantom_II.generatePlane(new Vector3f(), new Vector3f());
//        Entity plane = jet.getEntity();
//
//        Frame f = new Frame();
//        f.setFocusable(false);
//
//        f.addEntity("Ground", e);
//        f.addEntity("Plane", plane);
//
//        try {
//            Sys.ENTITY_SYSTEM.addElement(e);
//            Sys.ENTITY_SYSTEM.addElement(plane);
//        } catch (CoreException e1) {
//            e1.printStackTrace();
//        }
//
//        lights = new ArrayList<>();
//        lights.add(new Light(0,100000,0));
//
//        camera = new PerspectiveCamera(0,0,0,0,0,0);
//    }
//
//    @Override
//    protected void onDisable() {
//    }
//
//    @Override
//    protected void render() {
//        camera.move();
//        for(Group g:jet.getTerrainHitPositions()) {
//            if(g.getAbsolutePosition().y < 0) {
//                System.out.println("....");
//            }
//        }
//        Sys.ENTITY_SYSTEM.render(lights, camera);
//    }
//}
