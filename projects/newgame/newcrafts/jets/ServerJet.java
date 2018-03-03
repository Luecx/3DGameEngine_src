package projects.newgame.newcrafts.jets;


import projects.newgame.newcrafts.control.Controller;
import org.lwjgl.util.vector.Vector3f;

/**
 * Created by finne on 30.09.2017.
 */
public class ServerJet extends JetData{

    private float currentSpeed;

    public ServerJet(JetData data) {
        super(data);
        currentSpeed = (minSpeed + maxSpeed) / 2;
    }

    public void process(double time, Controller controller) {

        if(controller.getStatus(Controller.CONTROL_SPEED_UP)){
            currentSpeed = (float) Math.min(maxSpeed, currentSpeed + speedChange * time);
        }if(controller.getStatus(Controller.CONTROL_SPEED_DOWN)){
            currentSpeed = (float) Math.max(minSpeed, currentSpeed - speedChange * time);
        }

        Vector3f forward = this.getZAxis();
        forward.scale((float) -(time * currentSpeed / (forward.length() * 3.6)));
        this.increasePosition(forward);

        if (controller.getStatus(Controller.CONTROL_ROTATE_RIGHT)) {
            this.increaseRotation(getZAxis(), -0.7f);
        }
        if (controller.getStatus(Controller.CONTROL_ROTATE_LEFT)) {
            this.increaseRotation(getZAxis(), 0.7f);
        }
        if (controller.getStatus(Controller.CONTROL_NICK_DOWN)) {
            this.increaseRotation(getXAxis(), -0.4f);
        }
        if (controller.getStatus(Controller.CONTROL_NICK_UP)) {
            this.increaseRotation(getXAxis(), 0.4f);
        }
        if (controller.getStatus(Controller.CONTROL_GEAR_LEFT)) {
            this.increaseRotation(getYAxis(), 0.1f);
        }
        if (controller.getStatus(Controller.CONTROL_GEAR_RIGHT)) {
            this.increaseRotation(getYAxis(), -0.1f);
        }

    }

}
