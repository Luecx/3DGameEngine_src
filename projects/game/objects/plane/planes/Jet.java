package projects.game.objects.plane.planes;

import engine.core.components.Camera;
import engine.core.components.Group;
import engine.linear.entities.Entity;
import org.lwjgl.util.vector.Vector3f;

/**
 * Created by Luecx on 24.02.2017.
 */
public class Jet {

    private float min_speed;
    private float max_speed;
    private float speed_change;
    private float forward_speed;

    private float rotation_speed;
    private float gear_speed;
    private float nick_speed;

    private Entity entity;

    private Camera[] cameras;
    private Group[] turbines;
    private Group[] missiles;
    private Group[] guns;

    private Vector3f[] cameraLocations;
    private Vector3f[] turbinePositions;
    private Vector3f[] missilePositions;
    private Vector3f[] gunPositions;

    public Jet(Entity entity, float min_speed, float max_speed, float speed_change, float forward_speed, float rotation_speed, float gear_speed, float nick_speed, Vector3f[] cameraLocations, Vector3f[] turbinePositions, Vector3f[] missilePositions, Vector3f[] gunPositions) {
        this.entity = entity;
        this.min_speed = min_speed;
        this.max_speed = max_speed;
        this.speed_change = speed_change;
        this.forward_speed = forward_speed;
        this.rotation_speed = rotation_speed;
        this.gear_speed = gear_speed;
        this.nick_speed = nick_speed;
        this.cameraLocations = cameraLocations;
        this.turbinePositions = turbinePositions;
        this.missilePositions = missilePositions;
        this.gunPositions = gunPositions;
    }
}
