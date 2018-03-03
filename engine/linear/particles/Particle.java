package engine.linear.particles;

import engine.linear.material.ParticleMaterial;
import org.lwjgl.util.vector.Vector3f;

public class Particle {
	
	private ParticleMaterial texture;
	
	private Vector3f position;
	private Vector3f velocity;
	private float speed;
	private float rotation = 0;
	private float scale = 1;
	
	private double processedTime;
	private double maxTime;
	private boolean isAlive;
	
	public Particle(ParticleMaterial tex, Vector3f position, Vector3f velocity, float speed, double maxTime) {
		this.texture = tex;
		this.position = new Vector3f(position);
		this.velocity = velocity;
		this.velocity.normalise();
		this.maxTime = maxTime;
		this.speed = speed;
		this.isAlive = true;
	}
	
	public boolean process(double frameTime) {
		processedTime += frameTime;
		position.x += velocity.x * frameTime * speed;
		position.y += velocity.y * frameTime * speed;
		position.z += velocity.z * frameTime * speed;
		return this.processedTime < this.maxTime;
	}
	
	public float getCurrentTextureIndex() {
		int max = texture.getNumberOfRows() * texture.getNumberOfRows();
		return (float)Math.min(((processedTime / maxTime) * max), max - 1);
		
	}
	
	public ParticleMaterial getTexture() {
		return texture;
	}

	public Vector3f getPosition() {
		return position;
	}

	public Vector3f getVelocity() {
		return velocity;
	}

	public float getSpeed() {
		return speed;
	}

	public double getProcessedTime() {
		return processedTime;
	}

	public float getRotation() {
		return rotation;
	}

	public void setRotation(float rotation) {
		this.rotation = (float) Math.toRadians(rotation);
	}

	public float getScale() {
		return scale;
	}

	public void setScale(float scale) {
		this.scale = scale;
	}

	public boolean isAlive() {
		return isAlive;
	}
	
	public void setStatus(boolean value){
		this.isAlive = value;
	}
	
	
	
	
}
