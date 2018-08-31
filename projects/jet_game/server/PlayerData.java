package projects.jet_game.server;

import engine.core.components.GroupableGameObject;
import engine.linear.maths.Ray;
import org.lwjgl.util.vector.Vector3f;

import java.io.Serializable;

public class PlayerData implements Serializable {

    private Vector3f position, rotation;
    private long id;


    public PlayerData(long id) {
        this.id = id;
    }

    public Vector3f getPosition() {
        return position;
    }

    public void setPosition(Vector3f position) {
        this.position = position;
    }

    public Vector3f getRotation() {
        return rotation;
    }

    public void setRotation(Vector3f rotation) {
        this.rotation = rotation;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
