package projects.buggy_project.buggy;

import engine.core.components.PerspectiveCamera;
import projects.buggy_project.world.WorldInterface;

/**
 * Created by finne on 04.03.2018.
 */
public interface BuggyInterface {

    /**
     * Updates the position of the buggy. Including rotation etc.
     * Physics happens inside. (called every frame)
     * Every control is inside.
     * Camera change is inside.
     * tilting/shifting/speeding is inside.
     *
     * @param time
     * @param groundInterface
     */
    void process(double time, WorldInterface groundInterface);

    /**
     * Generates the entity object and spawns it (adds it to the entitySystem)
     * Textures etc will be preset.
     * * Add parameters to the parameter class.
     *
     * @param x
     * @param y
     */
    void spawnModel();

    /**
     * Spawns the particles.
     * e.g. hitting the ground (dust).
     * changing gears (fire)...
     * up to the programmer.
     */
    void spawnParticles();

    /**
     * Returns the camera that is currently active. This class could hold more than one cameras.
     * The active camera should only be changed in the process method.
     * @param
     */
    PerspectiveCamera getActiveCamera();


}
