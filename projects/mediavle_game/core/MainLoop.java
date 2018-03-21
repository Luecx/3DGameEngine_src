package projects.mediavle_game.core;

import engine.core.components.Light;
import engine.core.exceptions.CoreException;
import engine.core.master.DisplayManager;
import engine.core.master.RenderCore;
import engine.core.master.RenderSettings;
import engine.core.system.Sys;
import engine.linear.loading.Loader;
import engine.linear.material.SkydomeElement;
import projects.mediavle_game.gui.GuiInit;
import projects.mediavle_game.map.GroundMap;
import projects.mediavle_game.map.entities.obs.Ground;
import projects.mediavle_game.map.entities.obs.Townhall;
import projects.mediavle_game.map.entities.obs.Tree;
import projects.mediavle_game.map.entities.obs.TreeSet;
import projects.mediavle_game.player.Player;
import projects.mediavle_game.player.PlayerCamera;

import java.util.ArrayList;

/**
 * Created by finne on 20.03.2018.
 */
public class MainLoop extends RenderCore {

    private Player player = new Player();
    private ArrayList<Light> lights = new ArrayList<>();

    private GroundMap groundMap;

    @Override
    protected void onEnable() {
        Sys.enableAll();
        lights.add(new Light(10000,10000,10000));

        Ground.generateTexturedModel();
        Townhall.generateTexturedModel();
        TreeSet.generateTexturedModel();

        GuiInit.generateTextures();

        groundMap = new GroundMap();
        groundMap.generateEntity();
        GuiInit.initGui();

        Townhall townhall = new Townhall(510,510);
        townhall.generateEntity();

        try {
            RenderSettings.skydome_fog = true;
            RenderSettings.skydome_radius = 4000;
            RenderSettings.skydome_fog_midlevel = 0;
            RenderSettings.skydome_fog_gradient = 5;
            RenderSettings.skydome_fog_density = 2;
            Sys.SKYDOME_SYSTEM.addElement(new SkydomeElement(Loader.loadTexture("textures/colormaps/sky")));
        } catch (CoreException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onDisable() {

    }

    @Override
    protected void render() {

        Sys.SKYDOME_SYSTEM.render(player.getPerspectiveCamera());
        Sys.NORMAL_ENTITY_SYSTEM.render(lights, player.getPerspectiveCamera());
        Sys.INSTANCED_ENTITY_SYSTEM.render(lights, player.getPerspectiveCamera());
        Sys.OVERLAY_SYSTEM.render();

        player.move(groundMap);
    }

    public static void main(String[] args) {
        MainLoop mainLoop = new MainLoop();
    }
}
