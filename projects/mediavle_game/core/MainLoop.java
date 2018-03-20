package projects.mediavle_game.core;

import engine.core.components.Light;
import engine.core.master.RenderCore;
import engine.core.system.Sys;
import projects.mediavle_game.map.GroundMap;
import projects.mediavle_game.map.entities.obs.Tree;
import projects.mediavle_game.player.PlayerCamera;

import java.util.ArrayList;

/**
 * Created by finne on 20.03.2018.
 */
public class MainLoop extends RenderCore {


    private PlayerCamera perspectiveCamera = new PlayerCamera(500,10,500);
    private ArrayList<Light> lights = new ArrayList<>();

    private GroundMap groundMap;


    @Override
    protected void onEnable() {
        Sys.enableAll();
        lights.add(new Light(10000,10000,10000));

        groundMap = new GroundMap(1000,1000);

        Tree tree = new Tree(10,10);
        Tree tree1 = new Tree(15,10);
        Tree tree2 = new Tree(20,10);
        tree.generateEntity();
        tree1.generateEntity();
        tree2.generateEntity();
    }

    @Override
    protected void onDisable() {

    }

    @Override
    protected void render() {

        Sys.NORMAL_ENTITY_SYSTEM.render(lights, perspectiveCamera);
        Sys.INSTANCED_ENTITY_SYSTEM.render(lights, perspectiveCamera);

        perspectiveCamera.move();
        //System.out.println(perspectiveCamera.lookingAtField());

    }

    public static void main(String[] args) {
        MainLoop mainLoop = new MainLoop();
    }
}
