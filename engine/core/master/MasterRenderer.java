package engine.core.master;

import org.lwjgl.opengl.GL11;

/**
 * Created by Luecx on 12.01.2017.
 */
public abstract class MasterRenderer {

    public static void enableCulling() {
        GL11.glEnable(GL11.GL_CULL_FACE);
        GL11.glCullFace(GL11.GL_BACK);
    }

    public static void disableCulling() {
        GL11.glDisable(GL11.GL_CULL_FACE);
    }

}
