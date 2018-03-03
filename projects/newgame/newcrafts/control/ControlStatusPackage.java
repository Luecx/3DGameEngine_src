package projects.newgame.newcrafts.control;

/**
 * Created by finne on 01.10.2017.
 */
public class ControlStatusPackage extends udpserver.Package {

    private ControlStatus[] controlStatuses;

    public ControlStatusPackage(ControlStatus[] controlStatuses) {
        super(2001200015041999l);
        this.controlStatuses = controlStatuses;
    }

    public ControlStatus[] getControlStatuses() {
        return controlStatuses;
    }

    public void setControlStatuses(ControlStatus[] controlStatuses) {
        this.controlStatuses = controlStatuses;
    }
}
