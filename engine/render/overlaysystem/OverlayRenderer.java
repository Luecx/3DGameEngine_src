package engine.render.overlaysystem;

import engine.core.master.DisplayManager;
import engine.core.sourceelements.RawModel;
import engine.core.system.AbstractRenderer;
import engine.linear.gui.FontPanel;
import engine.linear.loading.Loader;
import engine.linear.material.GuiElement;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;

import java.util.List;


public class OverlayRenderer extends AbstractRenderer<OverlayShader>{

	public static final RawModel quad = Loader.loadToVao(new float[]{-1,1,-1,-1,1,1,1,1,1,-1,-1,-1},2);

	public OverlayRenderer(OverlayShader shader) {
		super(shader);
	}

	public void render(List<GuiElement> guis){
		GL11.glDisable(GL11.GL_DEPTH_TEST);
		
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);

		float time = (float)DisplayManager.processedFrameTime();

		for(GuiElement gui:guis){


			GL30.glBindVertexArray(gui.getRawModel().getVaoID());
			GL20.glEnableVertexAttribArray(0);
			if(gui instanceof FontPanel){
				GL20.glEnableVertexAttribArray(1);
			}

			gui.update();
			gui.updateAnimation(time);

			shader.loadTransformation(gui.getTransformationMatrix());
			shader.loadTextureSettings(gui.isBlackWhite(),gui.isIgnoreAlpha(), gui.getBlendColor(), gui.getBlendFactor(), gui instanceof  FontPanel);
			GL13.glActiveTexture(GL13.GL_TEXTURE0);
			GL11.glBindTexture(GL11.GL_TEXTURE_2D, gui.getColorMap());
			GL11.glDrawArrays(GL11.GL_TRIANGLES, 0, gui.getRawModel().getVertexCount());

			GL20.glDisableVertexAttribArray(1);
			GL20.glDisableVertexAttribArray(0);
			GL30.glBindVertexArray(0);

		}
		
		GL11.glDisable(GL11.GL_BLEND);

		GL11.glEnable(GL11.GL_DEPTH_TEST);
	}
	public void cleanUp() {
		shader.cleanUp();
	}
}
