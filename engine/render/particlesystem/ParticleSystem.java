package engine.render.particlesystem;

import engine.core.components.Camera;
import engine.core.datastructs.DSArrayList;
import engine.core.sourceelements.Signature;
import engine.core.sourceelements.VAOIdentifier;
import engine.core.system.RenderSystem;
import engine.linear.particles.ParticleEmitter;

public class ParticleSystem extends RenderSystem<ParticleShader, ParticleRenderer, ParticleEmitter, DSArrayList<ParticleEmitter>>{

	private static ParticleShader shader = new ParticleShader();

	public ParticleSystem() {
		super(new ParticleRenderer(shader), shader, new VAOIdentifier(Signature.EMPTY_SIGNATURE, 2, 0));
	}

	@Override
	protected void addToCollection(ParticleEmitter element) {
		data.add(element);

	}

	@Override
	protected void initData() {
		data = new DSArrayList<>();
	}

	@Override
	public void removeElement(ParticleEmitter particleEmitter) {
		data.remove(particleEmitter);
	}


	public void render(Camera c) {
		if(!this.enabled)return;
		shader.start();
		renderer.render(c, data);
		shader.stop();
	}

	
}
