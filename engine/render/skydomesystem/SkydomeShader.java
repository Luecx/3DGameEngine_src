package engine.render.skydomesystem;

import engine.core.system.ShaderProgram;
import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector3f;

/**
 * Created by Luecx on 12.01.2017.
 */
public class SkydomeShader extends ShaderProgram{

    private static final String VERTEX_FILE = "src/engine/render/skydomesystem/vs.glsl";
    private static final String FRAGMENT_FILE = "src/engine/render/skydomesystem/fs.glsl";

    private int location_projectionMatrix;
    private int location_viewMatrix;
    private int location_radius;

    private int location_center;

    private int location_fog;
    private int location_fogColor;

    private int location_colormap;

    public SkydomeShader() {
        super(VERTEX_FILE, FRAGMENT_FILE);
    }

    @Override
    protected void getAllUniformLocations() {
        location_projectionMatrix = super.getUniformLocation("projectionMatrix");
        location_viewMatrix = super.getUniformLocation("viewMatrix");
        location_radius = super.getUniformLocation("radius");

        location_center = super.getUniformLocation("center");

        location_fog = super.getUniformLocation("fogSettings");
        location_fogColor = super.getUniformLocation("fogColor");

        location_colormap = super.getUniformLocation("colormap");
    }

    @Override
    public String toString() {
        return "SkydomeShader{" +
                "location_projectionMatrix=" + location_projectionMatrix +
                ", location_viewMatrix=" + location_viewMatrix +
                ", location_radius=" + location_radius +
                ", location_center=" + location_center +
                ", location_fog=" + location_fog +
                ", location_fogColor=" + location_fogColor +
                ", location_colormap=" + location_colormap +
                "} ";
    }

    public void connectTextureUnits() {
        super.loadInt(location_colormap, 0);
    }

    public void loadRadius(float radius) {
        super.loadFloat(location_radius, radius);
    }

    public void loadSkyboxPosition(Vector3f pointOfView, boolean x, boolean y, boolean z, float bx, float by, float bz) {
        super.loadVector(location_center, x ? pointOfView.x : bx,y ? pointOfView.y : by,z ? pointOfView.z : bz);
    }

    public void loadFog(boolean value, float density, float gradient, float midLevel, float red, float green, float blue){
        super.loadVector(location_fog, value ? 1:0, density, gradient, midLevel);
        super.loadBoolean(location_fog, value);
        super.loadVector(location_fogColor, red, green, blue);
    }

    public void loadViewMatrix(Matrix4f viewMatrix){
        super.loadMatrix(location_viewMatrix, viewMatrix);
    }

    public void loadProjectionMatrix(Matrix4f projection){
        super.loadMatrix(location_projectionMatrix, projection);
    }
    @Override
    protected void bindAttributes() {
        super.bindAttribute(0, "position");
        super.bindAttribute(1, "textureCoords");
    }
}
