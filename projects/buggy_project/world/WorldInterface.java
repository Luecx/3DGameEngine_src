package projects.buggy_project.world;

import engine.core.components.Light;

import java.util.ArrayList;

/**
 * Created by finne on 04.03.2018.
 */
public interface WorldInterface {

    /**
     * returns the height at any given float value.
     * The height needs to be interpolated in between the vertices.
     * @param x
     * @param y
     * @return
     */
    float getHeight(float x, float y);

    /**
     * Returns the lights in the world
     * @return
     */
    ArrayList<Light> getLights();

    /**
     * Generates the world and adds it to the terrain system.
     * textures etc preset.
     * Generates the lights.
     * Generates the skydome.
     * Add parameters to the parameter class.
     *
     * @return
     */
    void generateWorld();

    /**
     * Changes the render settings (fog)
     */
    void generateWorldRenderSettings();
}
