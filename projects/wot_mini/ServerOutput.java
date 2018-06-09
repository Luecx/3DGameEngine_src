package projects.wot_mini;

import org.lwjgl.util.vector.Vector3f;
import udp.udp_content.UDPContent;

public class ServerOutput extends UDPContent {

    private Vector3f[] positions;
    private Vector3f[] rotation;
    private long[] ids;

    public ServerOutput(Vector3f[] positions, Vector3f[] forward, long[] ids) {
        this.positions = positions;
        this.rotation = forward;
        this.ids = ids;
    }

    public Vector3f[] getPositions() {
        return positions;
    }

    public void setPositions(Vector3f[] positions) {
        this.positions = positions;
    }

    public Vector3f[] getRotation() {
        return rotation;
    }

    public void setRotation(Vector3f[] rotation) {
        this.rotation = rotation;
    }

    public long[] getIds() {
        return ids;
    }

    public void setIds(long[] ids) {
        this.ids = ids;
    }
}
