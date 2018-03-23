package projects.mediavle_game.core;

import engine.core.components.Light;
import engine.core.exceptions.CoreException;
import engine.core.master.RenderCore;
import engine.core.master.RenderSettings;
import engine.core.system.Sys;
import engine.linear.loading.Loader;
import engine.linear.material.SkydomeElement;
import projects.mediavle_game.gui.GuiInit;
import projects.mediavle_game.map.GroundMap;
import projects.mediavle_game.map.entities.obs.Ground;
import projects.mediavle_game.map.entities.obs.Townhall;
import projects.mediavle_game.map.entities.obs.sets.StreetSet;
import projects.mediavle_game.map.entities.obs.sets.TreeSet;
import projects.mediavle_game.player.Player;

import java.util.ArrayList;

/**
 * Created by finne on 20.03.2018.
 */
public class MainLoop extends RenderCore {

    private ArrayList<Light> lights = new ArrayList<>();

    private GroundMap groundMap;

    @Override
    protected void onEnable() {
        Sys.enableAll();
        lights.add(new Light(10000,10000,10000));

        Ground.generateTexturedModel();
        Townhall.generateTexturedModel();
        TreeSet.generateTexturedModel();
        StreetSet.generateTexturedModel();

        GuiInit.generateTextures();

        groundMap = new GroundMap();
        groundMap.generateEntity();

        try {
            RenderSettings.skydome_fog = true;
            RenderSettings.skydome_radius = 5000;
            RenderSettings.skydome_fog_midlevel = 200;
            RenderSettings.skydome_fog_gradient = 4;
            RenderSettings.skydome_fog_density = 3;
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

        Sys.SKYDOME_SYSTEM.render(Player.getPerspectiveCamera());
        Sys.NORMAL_ENTITY_SYSTEM.render(lights, Player.getPerspectiveCamera());
        Sys.INSTANCED_ENTITY_SYSTEM.render(lights, Player.getPerspectiveCamera());
        Sys.OVERLAY_SYSTEM.render();

        Player.move(groundMap);
    }

    public static void main(String[] args) {
        new MainLoop();
    }
}
