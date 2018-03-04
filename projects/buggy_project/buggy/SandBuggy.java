package projects.buggy_project.buggy;

import engine.core.components.Group;
import engine.core.components.PerspectiveCamera;
import engine.linear.entities.Entity;
import projects.buggy_project.world.WorldInterface;

/**
 * Created by finne on 04.03.2018.
 */
public class SandBuggy implements BuggyInterface {

    private Group group;
    private PerspectiveCamera camera;
    private Entity entity;
    private Entity[] räder;

    private Group radLinks;

    @Override
    public void process(double time, WorldInterface groundInterface) {
        camera.move();
    }

    @Override
    public void spawnModel() {
        group = new Group();
        entity = new Entity();
        camera = new PerspectiveCamera();
        group.addChild(entity);
        group.addChild(camera);

        radLinks = new Group(0,1,0);
        group.addChild(radLinks);


    }

    @Override
    public void spawnParticles() {

    }

    @Override
    public PerspectiveCamera getActiveCamera() {
        return camera;
    }
}
