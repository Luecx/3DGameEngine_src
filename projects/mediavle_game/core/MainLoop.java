package projects.mediavle_game.core;

import engine.core.components.Light;
import engine.core.components.PerspectiveCamera;
import engine.core.master.DisplayManager;
import engine.core.master.RenderCore;
import engine.core.system.Sys;
import org.lwjgl.opengl.Display;
import projects.mediavle_game.map.GroundMap;

import java.util.ArrayList;

/**
 * Created by finne on 20.03.2018.
 */
public class MainLoop extends RenderCore {


    private PerspectiveCamera perspectiveCamera = new PerspectiveCamera();
    private ArrayList<Light> lights = new ArrayList<>();

    private GroundMap groundMap;

    @Override
    protected void onEnable() {
        Sys.enableAll();
        lights.add(new Light(10000,10000,10000));

        groundMap = new GroundMap(1000,1000);
    }

    @Override
    protected void onDisable() {

    }

    @Override
    protected void render() {
        perspectiveCamera.move();
        Sys.NORMAL_ENTITY_SYSTEM.render(lights, perspectiveCamera);

    }

    public static void main(String[] args) {
        MainLoop mainLoop = new MainLoop();
    }
}
