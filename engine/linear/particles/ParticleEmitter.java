package engine.linear.particles;

import engine.core.components.Group;
import engine.core.sourceelements.RawModel;
import engine.core.sourceelements.SourceElement;
import engine.linear.material.ParticleMaterial;
import engine.render.particlesystem.ParticleRenderer;
import org.lwjgl.util.vector.Vector3f;

import java.util.ArrayList;

public class ParticleEmitter extends Group implements SourceElement{

	private ArrayList<Particle> particles = new ArrayList<>();
	private ParticleMaterial texture;
	
	public ParticleEmitter(Vector3f center, ParticleMaterial tex) {
		this.setPosition(center);
		this.texture = tex;
	}
	
	public synchronized ParticleMaterial getTexture() {
		return texture;
	}
	public synchronized ArrayList<Particle> getParticles() {
		return particles;
	}

	public synchronized Particle emittParticle(Vector3f velocity, float speed, float scale,double lifeTime) {
		Particle p = new Particle(texture, this.getAbsolutePosition(), velocity, speed ,lifeTime);
		p.setScale(scale);
		particles.add(p);
		return p;
	}
	public synchronized Particle emittParticle(Vector3f relativeStart, Vector3f velocity, float speed, float scale,double lifeTime){
		Vector3f start = new Vector3f();
		Vector3f.add(this.getAbsolutePosition(), relativeStart,  start);
		Particle p = new Particle(texture, start , velocity, speed ,lifeTime);
		p.setScale(scale);
		particles.add(p);
		return p;
	}
	
	public synchronized Particle addParticle(Vector3f start, Vector3f velocity, float speed, float scale, double lifeTime){
		Particle p = new Particle(texture, start , velocity, speed ,lifeTime);
		p.setScale(scale);
		particles.add(p);
		return p;
	}
	
	public synchronized void addParticle(Particle p){
		this.particles.add(p);
	}
	
	public synchronized void processParticles(double frameTime) {
		for(int i = 0; i< particles.size(); i++) {
			if(particles.get(i).process(frameTime) == false){
				particles.get(i).setStatus(false);
				particles.remove(i);
			}
		}
	}

	public synchronized void clear() {
		particles.clear();
	}

	
	
	@Deprecated
	public void increaseRotation(Vector3f vec){}
	@Deprecated
	public void increaseScale(Vector3f vec){}
	@Deprecated
	public void setRotation(Vector3f vec){}
	@Deprecated
	public void setScale(Vector3f vec){}

	@Override
	public RawModel getRawModel() {
		return ParticleRenderer.quad;
	}
}
