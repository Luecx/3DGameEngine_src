package engine.render.particlesystem;

import engine.core.system.ShaderProgram;
import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector3f;

public class ParticleShader extends ShaderProgram {

	private static final String VERTEX_FILE = "src/engine/render/particlesystem/vs.glsl";
	private static final String FRAGMENT_FILE = "src/engine/render/particlesystem/fs.glsl";

	private int location_viewMatrix;
	private int location_projectionMatrix;
	
	private int location_scale;
	private int location_position;
	private int location_rotation;

	private int location_textureIndex;
	private int location_numberOfRows;
	
	public ParticleShader() {
		super(VERTEX_FILE, FRAGMENT_FILE);
	}

	@Override
	protected void getAllUniformLocations() {
		location_viewMatrix = super.getUniformLocation("viewMatrix");
		location_projectionMatrix = super.getUniformLocation("projectionMatrix");
		location_scale = super.getUniformLocation("scale");
		location_position = super.getUniformLocation("worldSpace");
		location_textureIndex = super.getUniformLocation("textureIndex");
		location_numberOfRows = super.getUniformLocation("numberOfRows");
		location_rotation = super.getUniformLocation("rotation");
	}

	@Override
	public String toString() {
		return "ParticleShader{" +
				"location_viewMatrix=" + location_viewMatrix +
				", location_projectionMatrix=" + location_projectionMatrix +
				", location_scale=" + location_scale +
				", location_position=" + location_position +
				", location_rotation=" + location_rotation +
				", location_textureIndex=" + location_textureIndex +
				", location_numberOfRows=" + location_numberOfRows +
				'}';
	}

	protected void loadViewMatrix(Matrix4f mat){
		super.loadMatrix(location_viewMatrix, mat);
	}
	
	protected void loadTextureIndex(float index){
		super.loadFloat(location_textureIndex, index);
	}
	
	protected void loadNumberOfRows(float number){
		super.loadFloat(location_numberOfRows, number);
	}
	
	protected void loadProjectionMatrix(Matrix4f mat){
		super.loadMatrix(location_projectionMatrix, mat);
	}
	
	protected void loadScaleAndRotation(float scale, float rotation){
		super.loadFloat(location_scale, scale);
		super.loadFloat(location_rotation, rotation);
	}
	
	protected void loadPosition(Vector3f position){
		super.loadVector(location_position, position);
		
	}


	@Override
	protected void bindAttributes() {
		// TODO Auto-generated method stub

	}

	@Override
	protected void connectTextureUnits() {

	}
}
