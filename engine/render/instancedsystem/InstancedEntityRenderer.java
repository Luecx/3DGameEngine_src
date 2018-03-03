package engine.render.instancedsystem;

import engine.core.master.MasterRenderer;
import engine.core.master.RenderSettings;
import engine.core.sourceelements.RawModel;
import engine.core.system.AbstractRenderer;
import engine.linear.entities.TexturedModel;
import engine.linear.material.EntityMaterial;
import org.lwjgl.opengl.*;

import java.util.List;

/**
 * Created by Luecx on 18.02.2017.
 */
public class InstancedEntityRenderer extends AbstractRenderer<InstancedEntityShader>{

    public InstancedEntityRenderer(InstancedEntityShader shader) {
        super(shader);
    }

    public void prepare() {
        GL11.glDisable (GL11.GL_BLEND);
        GL11.glEnable(GL11.GL_DEPTH_TEST);

        MasterRenderer.enableCulling();
        shader.loadFog(RenderSettings.entity_fog,
                RenderSettings.entity_fog_density,
                RenderSettings.entity_fog_gradient,
                RenderSettings.entity_fog_color_red,
                RenderSettings.entity_fog_color_green,
                RenderSettings.entity_fog_color_blue);
    }

    public void render(List<InstanceSet> data){

        prepare();
        for(InstanceSet set:data){
            shader.loadRandomRotation(set.getRandomRotation());
            if(set != null){
                prepareTexturedModel(set.getModel());
                GL31.glDrawElementsInstanced(GL11.GL_TRIANGLES, set.getModel().getRawModel().getVertexCount(), GL11.GL_UNSIGNED_INT, 0,set.size());
                unbindTexturedModel();
            }
        }
    }

    private void prepareTexturedModel(TexturedModel model) {
        RawModel rawModel = model.getRawModel();
        GL30.glBindVertexArray(rawModel.getVaoID());
        GL20.glEnableVertexAttribArray(0);
        GL20.glEnableVertexAttribArray(1);
        GL20.glEnableVertexAttribArray(2);
        GL20.glEnableVertexAttribArray(4);
        EntityMaterial texture = model.getMaterial();
        GL11.glDisable (GL11.GL_BLEND);

        if (texture.hasTransparency()) {
            GL11.glEnable(GL11.GL_POLYGON_STIPPLE);
            GL11.glEnable (GL11.GL_BLEND);
            GL11.glBlendFunc (GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
            MasterRenderer.disableCulling();
        }
        shader.loadUseLighting(texture.useLighting());
        shader.loadShineVariables(texture.getShineDamper(), texture.getReflectivity());
        shader.loadTextureStretch(model.getTextureStretch());

        GL13.glActiveTexture(GL13.GL_TEXTURE0);
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, model.getMaterial().getColorMap());
    }

    private void unbindTexturedModel() {
        GL11.glDisable(GL11.GL_POLYGON_STIPPLE);
        GL20.glDisableVertexAttribArray(0);
        GL20.glDisableVertexAttribArray(1);
        GL20.glDisableVertexAttribArray(2);
        GL20.glDisableVertexAttribArray(4);
        MasterRenderer.enableCulling();
        GL30.glBindVertexArray(0);
    }
}
