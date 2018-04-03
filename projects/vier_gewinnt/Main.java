package projects.vier_gewinnt;

import engine.core.components.Light;
import engine.core.components.OrthographicCamera;
import engine.core.components.PerspectiveCamera;
import engine.core.exceptions.CoreException;
import engine.core.master.RenderCore;
import engine.core.system.Sys;
import engine.linear.gui.GuiPanel;
import engine.render.shadowsystem.ShadowCamera;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;
import projects.vier_gewinnt.logic.server.GameClient;
import projects.vier_gewinnt.logic.server.GameCube;

import java.util.ArrayList;

/**
 * Created by finne on 27.03.2018.
 */
public class Main extends RenderCore{

    ShadowCamera shadowCamera;
    ArrayList<Light> lights = new ArrayList<>();

    GameCube gameCube;
    World world;
    int size;
    GameClient gameClient;

    public Main(int size, GameClient gameClient) {
        this.size = size;
        this.gameClient = gameClient;
    }


    @Override
    protected void onEnable() {
        Sys.enableAll();
        world = new World();
        lights.add(new Light(1000,1600,1000));

        gameCube = new GameCube(size, gameClient);

        shadowCamera = new ShadowCamera(new Vector3f(1000,1600,1000),
                new Vector3f(gameCube.SIZE / 2 * gameCube.STRETCH,
                        gameCube.SIZE / 2 * gameCube.STRETCH,
                        gameCube.SIZE / 2 * gameCube.STRETCH));
        shadowCamera.setDistanceToTarget(30);
        shadowCamera.setFAR(200);
        shadowCamera.setNEAR(3);
        shadowCamera.setTOP(20);
        shadowCamera.setBOTTOM(-20);
        shadowCamera.setLEFT(-20);
        shadowCamera.setRIGHT(20);
    }

    @Override
    protected void onDisable() {

    }

    @Override
    protected void render() {
        this.gameCube.update();
        Sys.SHADOW_SYSTEM.render(shadowCamera);
        Sys.SKYDOME_SYSTEM.render(gameCube.getPerspectiveCamera());
        Sys.NORMAL_ENTITY_SYSTEM.render(lights,gameCube.getPerspectiveCamera());
        Sys.OVERLAY_SYSTEM.render();
    }

    public GameCube getGameCube() {
        return gameCube;
    }
}
