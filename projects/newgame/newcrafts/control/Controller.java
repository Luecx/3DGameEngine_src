package projects.newgame.newcrafts.control;

/**
 * Created by finne on 30.09.2017.
 */
public class Controller {

    public static final int CONTROL_SPEED_UP = 1;
    public static final int CONTROL_SPEED_DOWN = 2;
    public static final int CONTROL_GEAR_RIGHT = 3;
    public static final int CONTROL_GEAR_LEFT = 4;
    public static final int CONTROL_ROTATE_RIGHT = 5;
    public static final int CONTROL_ROTATE_LEFT = 6;
    public static final int CONTROL_NICK_UP = 7;
    public static final int CONTROL_NICK_DOWN = 8;

    public static final int CONTROL_GUN_FIRE = 9;
    public static final int CONTROL_MISSILE_FIRE = 10;

    private boolean[] logs = new boolean[CONTROL_MISSILE_FIRE + 1];

    public Controller() {}

    public void process(ControlStatus[] keysDown) {
        for(ControlStatus c:keysDown) {
            logs[c.getController()] = c.getStatus();
        }
    }

    public boolean getStatus(int key) {
        return logs[key];
    }

    public boolean[] getLogs() {
        return logs;
    }
}
