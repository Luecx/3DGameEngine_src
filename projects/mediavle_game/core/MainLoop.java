package projects.mediavle_game.core;

import engine.core.components.Light;
import engine.core.master.DisplayManager;
import engine.core.master.RenderCore;
import engine.core.system.Sys;
import projects.mediavle_game.map.GroundMap;
import projects.mediavle_game.map.entities.obs.Tree;
import projects.mediavle_game.player.Player;
import projects.mediavle_game.player.PlayerCamera;

import java.util.ArrayList;

/**
 * Created by finne on 20.03.2018.
 */
public class MainLoop extends RenderCore {


    //private PlayerCamera perspectiveCamera = new PlayerCamera(500,10,500);
    private Player player = new Player();
    private ArrayList<Light> lights = new ArrayList<>();

    private GroundMap groundMap;


    @Override
    protected void onEnable() {
        Sys.enableAll();
        lights.add(new Light(10000,10000,10000));

        groundMap = new GroundMap(1000,1000);


        for(int i = 0; i < 5000; i++) {
            int x = (int)(Math.random() * 1000);
            int y = (int)(Math.random() * 1000);
            Tree tree = new Tree(x,y);
            groundMap.getFields()[x][y].setUniqueGameEntity(tree);
        }
    }



    @Override
    protected void onDisable() {

    }

    @Override
    protected void render() {

        Sys.NORMAL_ENTITY_SYSTEM.render(lights, player.getPerspectiveCamera());
        Sys.INSTANCED_ENTITY_SYSTEM.render(lights, player.getPerspectiveCamera());

        player.move(groundMap);
        //perspectiveCamera.move();
        //System.out.println(perspectiveCamera.lookingAtField());
        System.out.println(1d/ DisplayManager.processedFrameTime());

    }

    public static void main(String[] args) {
        MainLoop mainLoop = new MainLoop();
    }
}
