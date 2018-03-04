package projects.buggy_project;

import engine.core.master.DisplayManager;
import engine.core.master.RenderCore;
import engine.core.system.Sys;
import projects.buggy_project.buggy.BuggyInterface;
import projects.buggy_project.world.WorldInterface;

/**
 * Created by finne on 04.03.2018.
 */
public abstract class BuggyGame<W extends WorldInterface, B extends BuggyInterface> extends RenderCore{

    W world;
    B buggy;

    abstract void initWorldAndBuggy();

    @Override
    protected void onEnable() {

        Sys.enableAll();
        initWorldAndBuggy();

        world.generateWorld();
        buggy.spawnModel();
    }

    @Override
    protected void onDisable() {

    }

    @Override
    protected void render() {

        buggy.process(DisplayManager.processedFrameTime(), world);
        buggy.spawnParticles();

        Sys.ADVANCED_TERRAIN_SYSTEM.render(world.getLights(), buggy.getActiveCamera());
        Sys.SKYDOME_SYSTEM.render(buggy.getActiveCamera());
        Sys.PARTICLE_SYSTEM.render(buggy.getActiveCamera());
        Sys.NORMAL_ENTITY_SYSTEM.render(world.getLights(), buggy.getActiveCamera());
    }
}
