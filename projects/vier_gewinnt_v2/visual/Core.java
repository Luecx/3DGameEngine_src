package projects.vier_gewinnt_v2.visual;

import com.sun.org.apache.regexp.internal.RE;
import engine.core.components.Light;
import engine.core.master.RenderCore;
import engine.core.system.Sys;
import engine.linear.loading.Loader;
import engine.render.shadowsystem.ShadowCamera;
import org.lwjgl.util.vector.Vector3f;
import projects.vier_gewinnt.*;
import projects.vier_gewinnt_v2.communication.client.GameClient;
import projects.vier_gewinnt_v2.logic.GameKI;
import projects.vier_gewinnt_v2.logic.Vector3i;

import java.util.ArrayList;

/**
 * Created by finne on 31.03.2018.
 */
public class Core extends RenderCore {

    private GameCube gameCube;
    private GameClient gameClient;
    private int size;
    private int id;


    ArrayList<Light> lights = new ArrayList<>();
    ShadowCamera shadowCamera;

    public Core(GameClient gameClient, int size, int id) {
        super();
        this.gameClient = gameClient;
        this.size = size;
        this.id = id;
    }


    @Override
    protected void onEnable() {
        Sys.enableAll();
        new World();
        lights.add(new Light(1000,1600,1000));

        Stones.getStone(0);
        gameCube = new GameCube(gameClient, size, id);
        shadowCamera = new ShadowCamera(new Vector3f(1000,1600,1000),
                new Vector3f(size / 2 * gameCube.STRETCH,
                        size / 2 * gameCube.STRETCH,
                        size / 2 * gameCube.STRETCH));
        shadowCamera.setDistanceToTarget(100);
        shadowCamera.setFAR(200);
        shadowCamera.setNEAR(3);
        shadowCamera.setTOP(50);
        shadowCamera.setBOTTOM(-50);
        shadowCamera.setLEFT(-50);
        shadowCamera.setRIGHT(50);

        if(gameClient != null) {
            gameClient.sendGameLoaded();
        }
    }

    @Override
    protected void onDisable() {

        Loader.cleanUp();
        Sys.disableAll();
        if(gameClient != null) {
            gameClient.getConnection().close();
            gameClient.getClientGui().dispose();
            System.exit(-1);
        }
    }

    @Override
    protected void render() {
        this.gameCube.update();
        Sys.SHADOW_SYSTEM.render(shadowCamera);
        Sys.SKYDOME_SYSTEM.render(gameCube.getGameCamera());
        Sys.NORMAL_ENTITY_SYSTEM.render(lights,gameCube.getGameCamera());
        Sys.OVERLAY_SYSTEM.render();
    }

    public GameCube getGameCube() {
        return gameCube;
    }

    public GameClient getGameClient() {
        return gameClient;
    }



}
