package engine.render.particlesystem;

import engine.core.components.Camera;
import engine.core.master.DisplayManager;
import engine.core.master.MasterRenderer;
import engine.core.sourceelements.RawModel;
import engine.core.system.AbstractRenderer;
import engine.linear.loading.Loader;
import engine.linear.particles.Particle;
import engine.linear.particles.ParticleEmitter;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;

import java.util.ArrayList;


public class ParticleRenderer extends AbstractRenderer<ParticleShader>{
	
	public static final RawModel quad = Loader.loadToVao(new float[]{-0.5f,0.5f,-0.5f,-0.5f,0.5f,0.5f,0.5f,-0.5f}, 2);

	public ParticleRenderer(ParticleShader shader) {
		super(shader);
	}
	
	public void prepare() {
		
		GL11.glEnable(GL11.GL_POLYGON_STIPPLE);
		GL11.glEnable (GL11.GL_BLEND);
		GL11.glBlendFunc (GL11.GL_SRC_ALPHA, GL11.GL_ONE);

		GL11.glDepthMask(false);
		GL11.glEnable(GL11.GL_DEPTH_TEST);        
		MasterRenderer.disableCulling();
        
		
	}
	
	public void render(Camera c, ArrayList<ParticleEmitter> emitter){
		
		prepare();
        GL30.glBindVertexArray(quad.getVaoID());
		GL20.glEnableVertexAttribArray(0);
		
		
		shader.loadProjectionMatrix(c.getProjectionMatrix());
		shader.loadViewMatrix(c.getViewMatrix());
	
		double frameTime = DisplayManager.processedFrameTime();

		for(ParticleEmitter e:emitter){

			GL13.glActiveTexture(GL13.GL_TEXTURE0);
			GL11.glBindTexture(GL11.GL_TEXTURE_2D, e.getTexture().getColorMap());
			e.processParticles(frameTime);
			shader.loadNumberOfRows(e.getTexture().getNumberOfRows());


			ArrayList<Particle> particles = e.getParticles();
			for(int i = 0; i <  particles.size(); i++){
				shader.loadScaleAndRotation(particles.get(i).getScale(), particles.get(i).getRotation());
				shader.loadPosition(particles.get(i).getPosition());
				shader.loadTextureIndex(particles.get(i).getCurrentTextureIndex());
				GL11.glDrawArrays(GL11.GL_TRIANGLE_STRIP, 0, quad.getVertexCount());
			}
		}

		GL11.glDisable(GL11.GL_POLYGON_STIPPLE);

        GL30.glBindVertexArray(quad.getVaoID());
		GL20.glEnableVertexAttribArray(0);
		GL11.glDisable (GL11.GL_BLEND);		
		GL11.glDepthMask(true);

		MasterRenderer.enableCulling();
	}
}
