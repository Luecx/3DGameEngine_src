package engine.render.instancedsystem;

import engine.core.components.Light;
import engine.core.master.RenderSettings;
import engine.core.system.ShaderProgram;
import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector3f;

import java.util.Arrays;
import java.util.List;

/**
 * Created by Luecx on 18.02.2017.
 */
public class InstancedEntityShader extends ShaderProgram {


    private static final String VERTEX_FILE = "src/engine/render/instancedsystem/vs.glsl";
    private static final String FRAGMENT_FILE = "src/engine/render/instancedsystem/fs.glsl";


    private int location_shineDamper;
    private int location_reflectivity;
    private int location_useLighting;


    private int location_textureStretch;
    private int location_randomRotation;

    private int location_fogDensity;
    private int location_fogGradient;
    private int location_fogColor;

    private int location_lightPosition[];
    private int location_lightColour[];
    private int location_attenuation[];
    private int location_lightAmount;

    private int location_projectionMatrix;
    private int location_viewMatrix;

    public InstancedEntityShader() {
        super(VERTEX_FILE, FRAGMENT_FILE);
    }

    @Override
    protected void getAllUniformLocations() {
        location_projectionMatrix = super.getUniformLocation("projectionMatrix");
        location_viewMatrix = super.getUniformLocation("viewMatrix");
        location_shineDamper = super.getUniformLocation("shineDamper");
        location_reflectivity = super.getUniformLocation("reflectivity");
        location_randomRotation = super.getUniformLocation("randomRotation");

        location_fogDensity = super.getUniformLocation("fogDensity");
        location_fogGradient = super.getUniformLocation("fogGradient");
        location_fogColor = super.getUniformLocation("fogColor");

        location_textureStretch = super.getUniformLocation("textureStretch");
        location_useLighting = super.getUniformLocation("useLighting");

        location_lightPosition = new int[RenderSettings.ENTITIES_MAX_LIGHTS];
        location_lightColour = new int[RenderSettings.ENTITIES_MAX_LIGHTS];
        location_attenuation = new int[RenderSettings.ENTITIES_MAX_LIGHTS];
        location_lightAmount = super.getUniformLocation("lightAmount");

        for(int i=0;i<RenderSettings.ENTITIES_MAX_LIGHTS;i++){
            location_lightPosition[i] = super.getUniformLocation("lightPosition[" + i + "]");
            location_lightColour[i] = super.getUniformLocation("lightColour[" + i + "]");
            location_attenuation[i] = super.getUniformLocation("attenuation[" + i + "]");
        }
    }


    public void loadUseLighting(boolean value){
        super.loadBoolean(location_useLighting, value);
    }

    public void loadShineVariables(float damper,float reflectivity){
        super.loadFloat(location_shineDamper, damper);
        super.loadFloat(location_reflectivity, reflectivity);
    }

    public void loadLights(List<Light> lights){
        super.loadFloat(this.location_lightAmount, Math.min(RenderSettings.ENTITIES_MAX_LIGHTS,lights.size()));
        for(int i=0;i<RenderSettings.ENTITIES_MAX_LIGHTS;i++){
            if(i<lights.size()){
                super.loadVector(location_lightPosition[i], lights.get(i).getAbsolutePosition());
                super.loadVector(location_lightColour[i], lights.get(i).getColor());
                super.loadVector(location_attenuation[i], lights.get(i).getAttenuation());
            }
        }
    }

    public void loadFog(boolean value, float density, float gradient, float red, float green, float blue){
        super.loadFloat(location_fogDensity, value == true ? density:0);
        super.loadFloat(location_fogGradient, gradient);
        super.loadVector(location_fogColor, red, green, blue);
    }

    public void loadTextureStretch(float texture_stretch){
        super.loadFloat(this.location_textureStretch, texture_stretch);
    }

    public void loadRandomRotation(Vector3f rand){
        super.loadVector(location_randomRotation, rand);
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
        super.bindAttribute(4, "worldSpace");
    }

    @Override
    public String toString() {
        return "InstancedEntityShader{" +
                "location_shineDamper=" + location_shineDamper +
                ", location_reflectivity=" + location_reflectivity +
                ", location_useLighting=" + location_useLighting +
                ", location_textureStretch=" + location_textureStretch +
                ", location_fogDensity=" + location_fogDensity +
                ", location_fogGradient=" + location_fogGradient +
                ", location_fogColor=" + location_fogColor +
                ", location_lightPosition=" + Arrays.toString(location_lightPosition) +
                ", location_lightColour=" + Arrays.toString(location_lightColour) +
                ", location_attenuation=" + Arrays.toString(location_attenuation) +
                ", location_lightAmount=" + location_lightAmount +
                ", location_projectionMatrix=" + location_projectionMatrix +
                ", location_viewMatrix=" + location_viewMatrix +
                "} " ;
    }

    @Override
    protected void connectTextureUnits() {

    }
}
