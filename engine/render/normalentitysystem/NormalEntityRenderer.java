package engine.render.normalentitysystem;

import engine.core.master.MasterRenderer;
import engine.core.master.RenderSettings;
import engine.core.sourceelements.RawModel;
import engine.core.system.AbstractRenderer;
import engine.linear.entities.Entity;
import engine.linear.entities.TexturedModel;
import engine.linear.material.EntityMaterial;
import engine.render.shadowsystem.ShadowSystem;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;
import org.lwjgl.util.vector.Vector3f;

import java.util.HashMap;
import java.util.List;

/**
 * Created by Luecx on 13.01.2017.
 */
public class NormalEntityRenderer extends AbstractRenderer<NormalEntityShader> {

    public NormalEntityRenderer(NormalEntityShader shader) {
        super(shader);
    }

    private void prepare() {
        GL11.glEnable(GL11.GL_DEPTH_TEST);
        GL11.glDepthMask(true);
        MasterRenderer.enableCulling();

        shader.loadFog(RenderSettings.entity_fog,
                RenderSettings.entity_fog_density,
                RenderSettings.entity_fog_gradient,
                RenderSettings.entity_fog_color_red,
                RenderSettings.entity_fog_color_green,
                RenderSettings.entity_fog_color_blue);

        if(ShadowSystem.getShadowData().isActive()){
            GL13.glActiveTexture(GL13.GL_TEXTURE3);
            GL11.glBindTexture(GL11.GL_TEXTURE_2D, ShadowSystem.getShadowData().getFrameBuffer().getShadowMap());
            shader.loadShadows(true, ShadowSystem.getShadowData().getShadowMatrix());
        }else{
            shader.loadShadows(false, null);
        }
    }

    void render(HashMap<TexturedModel, List<Entity>> data){

        prepare();
        for(TexturedModel model:data.keySet()){
            prepareTexturedModel(model);
            List<Entity> batch = data.get(model);
            for(Entity entity:batch){
                shader.loadTransformationMatrix(entity.getAbsoluteTransformationMatrix());
                GL11.glDrawElements(GL11.GL_TRIANGLES, model.getRawModel().getVertexCount(), GL11.GL_UNSIGNED_INT, 0);
            }
            unbindTexturedModel();
        }
    }

    private void prepareTexturedModel(TexturedModel model) {
        RawModel rawModel = model.getRawModel();
        GL30.glBindVertexArray(rawModel.getVaoID());
        GL20.glEnableVertexAttribArray(0);
        GL20.glEnableVertexAttribArray(1);
        GL20.glEnableVertexAttribArray(2);
        GL20.glEnableVertexAttribArray(3);

        if(model.isWireframe()){
            GL11.glPolygonMode( GL11.GL_FRONT_AND_BACK, GL11.GL_LINE );
            MasterRenderer.disableCulling();
        }else{
            GL11.glPolygonMode( GL11.GL_FRONT_AND_BACK, GL11.GL_FILL );
            MasterRenderer.enableCulling();
        }

        EntityMaterial texture = model.getMaterial();
        if (texture.hasTransparency()) {
            GL11.glEnable(GL11.GL_POLYGON_STIPPLE);
            GL11.glEnable (GL11.GL_BLEND);
            GL11.glBlendFunc (GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
            MasterRenderer.disableCulling();
        } else{

            GL11.glDisable (GL11.GL_BLEND);
        }

        Vector3f texData = texture.getRenderWithVector();
        shader.loadRenderWithVector(texData);
        GL13.glActiveTexture(GL13.GL_TEXTURE0);
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, texture.getColorMap());


        if(texData.x > 0.5f){
            GL13.glActiveTexture(GL13.GL_TEXTURE1);
            GL11.glBindTexture(GL11.GL_TEXTURE_2D, texture.getDisplacementMap());
        }
        if(texData.y > 0.5f) {
            GL13.glActiveTexture(GL13.GL_TEXTURE2);
            GL11.glBindTexture(GL11.GL_TEXTURE_2D, texture.getSpecularMap());
        }
        if(texData.z > 0.5f) {
            GL13.glActiveTexture(GL13.GL_TEXTURE4);
            GL11.glBindTexture(GL11.GL_TEXTURE_2D, texture.getNormalMap());
        }
        if(model.isFlatShading()){
            GL11.glShadeModel(GL11.GL_FLAT);
        }else{
            GL11.glShadeModel(GL11.GL_SMOOTH);
        }

        shader.loadMaterial(model);

    }

    private void unbindTexturedModel() {
        MasterRenderer.enableCulling();
        GL11.glDisable(GL11.GL_POLYGON_STIPPLE);
        GL11.glDisable (GL11.GL_BLEND);
        GL20.glDisableVertexAttribArray(0);
        GL20.glDisableVertexAttribArray(1);
        GL20.glDisableVertexAttribArray(2);
        GL20.glDisableVertexAttribArray(3);
        GL30.glBindVertexArray(0);
    }


}
