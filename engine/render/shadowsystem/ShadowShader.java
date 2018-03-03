package engine.render.shadowsystem;

import engine.core.system.ShaderProgram;
import org.lwjgl.util.vector.Matrix4f;


public class ShadowShader extends ShaderProgram{


	private static final String VERTEX_FILE = "src/engine/render/shadowsystem/vs.glsl";
	private static final String FRAGMENT_FILE = "src/engine/render/shadowsystem/fs.glsl";


	private int location_transformationMatrix;
	private int location_proView;
	
	
	private int location_displacefactor;
	
	
	
	public ShadowShader() {
		super(VERTEX_FILE, FRAGMENT_FILE);
	}

	@Override
	protected void getAllUniformLocations() {
		location_transformationMatrix = super.getUniformLocation("transformationMatrix");
		location_proView = super.getUniformLocation("proView");
	
		location_displacefactor = super.getUniformLocation("displacefactor");
	
	}

	@Override
	public String toString() {
		return "ShadowShader{" +
				"location_transformationMatrix=" + location_transformationMatrix +
				", location_proView=" + location_proView +
				", location_displacefactor=" + location_displacefactor +
				"} ";
	}

	public void connectTextureUnits() {
	}

	
	public void loadDisplaceFactor(float fac){
		super.loadFloat(location_displacefactor, fac);
	}


	
	public void loadTransformationMatrix(Matrix4f matrix){
		super.loadMatrix(location_transformationMatrix, matrix);
	}

	
	public void loadProView(Matrix4f projection){
		super.loadMatrix(location_proView, projection);
	}
	@Override
	protected void bindAttributes() {
		super.bindAttribute(0, "position");
		super.bindAttribute(1, "textureCoords");
		super.bindAttribute(2, "normal");
	}


}
