package engine.render.terrainsystem;

import engine.core.components.Light;
import engine.core.master.RenderSettings;
import engine.core.system.ShaderProgram;
import org.lwjgl.util.vector.Matrix4f;

import java.util.Arrays;
import java.util.List;

/**
 * Created by Luecx on 12.01.2017.
 */
public class TerrainShader extends ShaderProgram{

    private static final String VERTEX_FILE = "src/engine/render/terrainsystem/vs.glsl";
    private static final String FRAGMENT_FILE = "src/engine/render/terrainsystem/fs.glsl";

    private int location_projectionMatrix;
    private int location_viewMatrix;

    private int location_lightPosition[] ;
    private int location_lightColour[];
    private int location_attenuation[];

    private int location_lightAmount;
    private int location_shineDamper;
    private int location_reflectivity;

    private int location_redMap;
    private int location_greenMap;
    private int location_blueMap;
    private int location_blackMap;
    private int location_textureStretch;

    private int location_fogGradient;
    private int location_fogDensity;
    private int location_fogColor;

    public TerrainShader() {
        super(VERTEX_FILE, FRAGMENT_FILE);
    }

    @Override
    protected void getAllUniformLocations() {
        location_projectionMatrix = super.getUniformLocation("projectionMatrix");
        location_viewMatrix = super.getUniformLocation("viewMatrix");
        location_shineDamper = super.getUniformLocation("shineDamper");
        location_reflectivity = super.getUniformLocation("reflectivity");
        location_lightAmount = super.getUniformLocation("lightAmount");

        location_lightPosition = new int[RenderSettings.ENTITIES_MAX_LIGHTS];
        location_lightColour = new int[RenderSettings.ENTITIES_MAX_LIGHTS];
        location_attenuation = new int[RenderSettings.ENTITIES_MAX_LIGHTS];

        location_fogGradient = super.getUniformLocation("fogGradient");
        location_fogDensity = super.getUniformLocation("fogDensity");
        location_fogColor = super.getUniformLocation("fogColor");

        for(int i=0;i< RenderSettings.ENTITIES_MAX_LIGHTS;i++){
            location_lightPosition[i] = super.getUniformLocation("lightPosition[" + i + "]");
            location_lightColour[i] = super.getUniformLocation("lightColor[" + i + "]");
            location_attenuation[i] = super.getUniformLocation("attenuation[" + i + "]");
        }

        location_redMap = super.getUniformLocation("redMap");
        location_greenMap = super.getUniformLocation("greenMap");
        location_blueMap = super.getUniformLocation("blueMap");
        location_blackMap = super.getUniformLocation("blackMap");
        location_textureStretch = super.getUniformLocation("textureStretch");
    }


    @Override
    public String toString() {
        return "TerrainShader{" +
                "location_projectionMatrix=" + location_projectionMatrix +
                ", location_viewMatrix=" + location_viewMatrix +
                ", location_lightPosition=" + Arrays.toString(location_lightPosition) +
                ", location_lightColour=" + Arrays.toString(location_lightColour) +
                ", location_attenuation=" + Arrays.toString(location_attenuation) +
                ", location_lightAmount=" + location_lightAmount +
                ", location_shineDamper=" + location_shineDamper +
                ", location_reflectivity=" + location_reflectivity +
                ", location_redMap=" + location_redMap +
                ", location_greenMap=" + location_greenMap +
                ", location_blueMap=" + location_blueMap +
                ", location_blackMap=" + location_blackMap +
                ", location_textureStretch=" + location_textureStretch +
                "} ";
    }

    public void loadFog(float gradient, float density, float colorR, float colorG, float colorB){
        super.loadFloat(location_fogGradient, gradient);
        super.loadFloat(location_fogDensity, density);
        super.loadVector(location_fogColor, colorR, colorG, colorB);
    }

    public void loadLights(List<Light> lights){
        super.loadFloat(location_lightAmount, lights.size());
        for(int i=0;i<RenderSettings.ENTITIES_MAX_LIGHTS;i++){
            if(i<lights.size()){
                super.loadVector(location_lightPosition[i], lights.get(i).getAbsolutePosition());
                super.loadVector(location_lightColour[i], lights.get(i).getColor());
                super.loadVector(location_attenuation[i], lights.get(i).getAttenuation());
            }
        }
    }

    public void loadShineVariables(float damper,float reflectivity){
        super.loadFloat(location_shineDamper, damper);
        super.loadFloat(location_reflectivity, reflectivity);
    }

    public void loadTextureStretch(float a, float b, float c, float d){
        super.loadVector(location_textureStretch,a,b,c,d);
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
        super.bindAttribute(2, "normal");
        super.bindAttribute(7, "blend");
    }

    @Override
    protected void connectTextureUnits() {
        super.loadInt(location_redMap, 0);
        super.loadInt(location_greenMap, 1);
        super.loadInt(location_blueMap, 2);
        super.loadInt(location_blackMap, 3);
    }
}
