package projects.newgame.newcrafts.control;

import java.io.Serializable;

/**
 * Created by finne on 30.09.2017.
 */
public class ControlStatus implements Serializable {

    int controller;
    boolean status;

    public ControlStatus(int controller, boolean status) {
        this.controller = controller;
        this.status = status;
    }

    public int getController() {
        return controller;
    }

    public void setController(int controller) {
        this.controller = controller;
    }

    public boolean getStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }
}
