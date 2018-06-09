package engine.render.tesselationTerrainSystem;

import engine.core.system.ShaderProgram;
import org.lwjgl.util.vector.Matrix;
import org.lwjgl.util.vector.Matrix4f;

/**
 * Created by Luecx on 12.01.2017.
 */
public class TerrainShader extends ShaderProgram{

    private static final String VERTEX_FILE = "src/engine/render/tesselationTerrainSystem/vs.glsl";
    private static final String FRAGMENT_FILE = "src/engine/render/tesselationTerrainSystem/fs.glsl";
    private static final String TESSELATION_1 = "src/engine/render/tesselationTerrainSystem/tcs.glsl";
    private static final String TESSELATION_2 = "src/engine/render/tesselationTerrainSystem/tes.glsl";


    private int location_transformationMatrix;
    private int location_projectionMatrix;
    private int location_viewMatrix;



    public TerrainShader() {
        //super(VERTEX_FILE, FRAGMENT_FILE);
        super(VERTEX_FILE, FRAGMENT_FILE, TESSELATION_1, TESSELATION_2);
    }

    @Override
    protected void getAllUniformLocations() {
        location_projectionMatrix = super.getUniformLocation("projection");
        location_viewMatrix = super.getUniformLocation("viewmatrix");
        location_transformationMatrix = super.getUniformLocation("transformation");
    }


    @Override
    public String toString() {
        return "TerrainShader{" +
                "location_projectionMatrix=" + location_projectionMatrix +
                ", location_viewMatrix=" + location_viewMatrix +
                ", location_transformationMatrix=" + location_transformationMatrix +
                "} ";
    }


    public void loadViewMatrix(Matrix4f viewMatrix){
        super.loadMatrix(location_viewMatrix, viewMatrix);
    }

    public void loadProjectionMatrix(Matrix4f projection){
        super.loadMatrix(location_projectionMatrix, projection);
    }

    public void loadTransformationMatrix(Matrix4f transformation){
        super.loadMatrix(location_transformationMatrix, transformation);
    }


    @Override
    protected void bindAttributes() {
        super.bindAttribute(0, "position");
        super.bindAttribute(1, "textureCoords");
        super.bindAttribute(2, "normal");
    }

    @Override
    protected void connectTextureUnits() {
    }
}
