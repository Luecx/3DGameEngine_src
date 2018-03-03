package engine.render.shadowsystem;


import engine.core.master.MasterRenderer;
import engine.core.sourceelements.RawModel;
import engine.core.system.AbstractRenderer;
import engine.linear.entities.Entity;
import engine.linear.entities.TexturedModel;
import engine.linear.material.EntityMaterial;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;

import java.util.HashMap;
import java.util.List;

class ShadowRenderer extends AbstractRenderer<ShadowShader> {

	public ShadowRenderer(ShadowShader shader) {
		super(shader);
	}

	void render(HashMap<TexturedModel, List<Entity>> data){
		
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
		
		EntityMaterial texture = model.getMaterial();
		if (texture.hasTransparency()) {
			GL11.glEnable(GL11.GL_POLYGON_STIPPLE);
			MasterRenderer.disableCulling();
		}

		if(texture.renderWithDisplacementMap()){
			GL13.glActiveTexture(GL13.GL_TEXTURE0);
			GL11.glBindTexture(GL11.GL_TEXTURE_2D, texture.getDisplacementMap());
			shader.loadDisplaceFactor(texture.getDisplaceFactor());
		}else{
			shader.loadDisplaceFactor(0);
		}

		
		
	}
    
	private void unbindTexturedModel() {
		MasterRenderer.enableCulling();
		GL11.glDisable(GL11.GL_POLYGON_STIPPLE);
		GL20.glDisableVertexAttribArray(0);
		GL30.glBindVertexArray(0);
	}
		
}
