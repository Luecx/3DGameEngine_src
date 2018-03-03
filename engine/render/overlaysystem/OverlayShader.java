package engine.render.overlaysystem;


import engine.core.system.ShaderProgram;
import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector3f;


public class OverlayShader extends ShaderProgram{
	
	private static final String VERTEX_FILE = "src/engine/render/overlaysystem/vs.glsl";
	private static final String FRAGMENT_FILE = "src/engine/render/overlaysystem/fs.glsl";
	
	private int location_transformationMatrix;
	private int location_blackwhite;
	private int location_ignorealpha;
	private int location_blendFactor;
	private int location_blendColor;
	private int location_useTexCoords;

	public OverlayShader() {
		super(VERTEX_FILE, FRAGMENT_FILE);
	}
	
	public void loadTransformation(Matrix4f matrix){
		super.loadMatrix(location_transformationMatrix, matrix);
	}

	@Override
	protected void getAllUniformLocations() {
		location_blendFactor = super.getUniformLocation("blendFactor");
		location_blendColor = super.getUniformLocation("blendColor");
		location_blackwhite = super.getUniformLocation("blackwhite");
		location_ignorealpha = super.getUniformLocation("ignorealpha");
		location_useTexCoords = super.getUniformLocation("useTexCoords");
		location_transformationMatrix = super.getUniformLocation("transformationMatrix");
	}

	@Override
	public String toString() {
		return "OverlayShader{" +
				"location_transformationMatrix=" + location_transformationMatrix +
				", location_blackwhite=" + location_blackwhite +
				", location_ignorealpha=" + location_ignorealpha +
				"} ";
	}

	@Override
	protected void bindAttributes() {
        super.bindAttribute(0, "position");
	}

	@Override
	protected void connectTextureUnits() {
		
	}

	public void loadTextureSettings(boolean blackWhite, boolean ignoreAlpha, Vector3f blend,float fac, boolean texCoords) {
		super.loadBoolean(location_blackwhite, blackWhite);
		super.loadBoolean(location_ignorealpha, ignoreAlpha);
		super.loadBoolean(location_useTexCoords, texCoords);
		super.loadVector(location_blendColor, blend);
		super.loadFloat(location_blendFactor, fac);
	}

	
	
	

}
