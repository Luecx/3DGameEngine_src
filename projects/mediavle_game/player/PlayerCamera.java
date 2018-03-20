package projects.mediavle_game.player;

import engine.core.components.PerspectiveCamera;
import org.lwjgl.opengl.Display;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;

/**
 * Created by finne on 20.03.2018.
 */
public class PlayerCamera extends PerspectiveCamera{

    public Vector3f lookingAt(){
        double length = this.calculateDistance(Display.getDisplayMode().getWidth() / 2, Display.getDisplayMode().getHeight()/ 2);
        Vector3f forward = (Vector3f)this.getZAxis().negate();
        Vector3f result = Vector3f.add(
                (Vector3f)forward.scale((float)length),
                this.getAbsolutePosition(),null);
        if(result.y > 5 || length > 8){
            return null;
        }
        return result;
    }

    public Vector2f lookingAtField() {
        Vector3f res = lookingAt();
        if(res == null) {
            return null;
        }
        return new Vector2f((int)res.x, (int)res.z);
    }

}
