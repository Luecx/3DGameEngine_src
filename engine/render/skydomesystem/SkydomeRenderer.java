package engine.render.skydomesystem;

import engine.core.components.Camera;
import engine.core.master.MasterRenderer;
import engine.core.master.RenderSettings;
import engine.core.sourceelements.RawModel;
import engine.core.system.AbstractRenderer;
import engine.linear.material.SkydomeElement;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;

/**
 * Created by Luecx on 12.01.2017.
 */
public class SkydomeRenderer extends AbstractRenderer<SkydomeShader>{


    SkydomeRenderer(SkydomeShader shader){
        super(shader);
    }

    public void render(Camera c, RawModel rawModel, SkydomeElement texture) {
        this.prepare();
        GL30.glBindVertexArray(rawModel.getVaoID());
        GL20.glEnableVertexAttribArray(0);
        GL20.glEnableVertexAttribArray(1);

        shader.loadSkyboxPosition(c.getAbsolutePosition(),
                RenderSettings.skydome_follow_x_axis,
                RenderSettings.skydome_follow_y_axis,
                RenderSettings.skydome_follow_z_axis,
                RenderSettings.skydome_bounding_x_axis,
                RenderSettings.skydome_bounding_y_axis,
                RenderSettings.skydome_bounding_z_axis
        );

        shader.loadRadius(RenderSettings.skydome_radius);
        shader.loadFog(RenderSettings.skydome_fog,
                RenderSettings.skydome_fog_density,
                RenderSettings.skydome_fog_gradient,
                RenderSettings.skydome_fog_midlevel,
                RenderSettings.skydome_fog_color_red,
                RenderSettings.skydome_fog_color_green,
                RenderSettings.skydome_fog_color_blue);

        GL13.glActiveTexture(GL13.GL_TEXTURE0);
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, texture.getColorMap());
        GL11.glDrawElements(GL11.GL_TRIANGLES, rawModel.getVertexCount(), GL11.GL_UNSIGNED_INT, 0);
        GL20.glDisableVertexAttribArray(0);
        GL20.glDisableVertexAttribArray(1);
        GL30.glBindVertexArray(0);
        MasterRenderer.enableCulling();
    }

    public void prepare() {
        GL11.glEnable(GL11.GL_DEPTH_TEST);
        MasterRenderer.disableCulling();
    }

}
