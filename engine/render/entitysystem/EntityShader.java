package engine.render.entitysystem;

import engine.core.components.Light;
import engine.core.master.RenderSettings;
import engine.core.system.ShaderProgram;
import engine.linear.entities.TexturedModel;
import engine.linear.material.EntityMaterial;
import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector3f;

import java.util.Arrays;
import java.util.List;

/**
 * Created by Luecx on 12.01.2017.
 */
public class EntityShader extends ShaderProgram{

    private static final String VERTEX_FILE = "src/engine/render/entitysystem/vs.glsl";
    private static final String FRAGMENT_FILE = "src/engine/render/entitysystem/fs.glsl";

    private int location_transformationMatrix;
    private int location_projectionMatrix;
    private int location_viewMatrix;

    private int location_lightPosition[];
    private int location_lightColour[];
    private int location_attenuation[];
    private int location_lightAmount;
    private int location_discardLimit;

    private int location_shineDamper;
    private int location_reflectivity;
    private int location_useLighting;

    private int location_textureStretch;

    private int location_colormap;
    private int location_displacementmap;
    private int location_specularmap;

    private int location_displacefactor;
    private int location_renderWithVector;

    private int location_shadowmap;
    private int location_useshadowmap;
    private int location_shadowmatrix;

    private int location_fogDensity;
    private int location_fogGradient;
    private int location_fogColor;

    public EntityShader() {
        super(VERTEX_FILE, FRAGMENT_FILE);
    }

    @Override
    protected void getAllUniformLocations() {
        location_transformationMatrix = super.getUniformLocation("transformationMatrix");
        location_projectionMatrix = super.getUniformLocation("projectionMatrix");
        location_viewMatrix = super.getUniformLocation("viewMatrix");
        location_shineDamper = super.getUniformLocation("shineDamper");
        location_reflectivity = super.getUniformLocation("reflectivity");
        location_discardLimit = super.getUniformLocation("discardLimit");

        location_textureStretch = super.getUniformLocation("textureStretch");
        location_useLighting = super.getUniformLocation("useLighting");

        location_fogDensity = super.getUniformLocation("fogDensity");
        location_fogGradient = super.getUniformLocation("fogGradient");
        location_fogColor = super.getUniformLocation("fogColor");

        location_lightPosition = new int[RenderSettings.ENTITIES_MAX_LIGHTS];
        location_lightColour = new int[RenderSettings.ENTITIES_MAX_LIGHTS];
        location_attenuation = new int[RenderSettings.ENTITIES_MAX_LIGHTS];
        location_lightAmount = super.getUniformLocation("lightAmount");

        location_shadowmap = super.getUniformLocation("shadowmap");
        location_colormap = super.getUniformLocation("colormap");
        location_displacementmap = super.getUniformLocation("displacementmap");
        location_specularmap = super.getUniformLocation("specularmap");
        location_displacefactor = super.getUniformLocation("displacefactor");

        location_renderWithVector = super.getUniformLocation("renderWithVector");
        location_useshadowmap = super.getUniformLocation("useshadowmap");
        location_shadowmatrix = super.getUniformLocation("shadowmatrix");


        for(int i=0;i<RenderSettings.ENTITIES_MAX_LIGHTS;i++){
            location_lightPosition[i] = super.getUniformLocation("lightPosition["+i+"]");
            location_lightColour[i] = super.getUniformLocation("lightColor["+i+"]");
            location_attenuation[i] = super.getUniformLocation("lightAttenuation["+i+"]");

        }
    }

    @Override
    public String toString() {
        return "EntityShader{" +
                "location_transformationMatrix=" + location_transformationMatrix +
                ", location_projectionMatrix=" + location_projectionMatrix +
                ", location_viewMatrix=" + location_viewMatrix +
                ", location_lightPosition=" + Arrays.toString(location_lightPosition) +
                ", location_lightColour=" + Arrays.toString(location_lightColour) +
                ", location_attenuation=" + Arrays.toString(location_attenuation) +
                ", location_lightAmount=" + location_lightAmount +
                ", location_discardLimit=" + location_discardLimit +
                ", location_shineDamper=" + location_shineDamper +
                ", location_reflectivity=" + location_reflectivity +
                ", location_useLighting=" + location_useLighting +
                ", location_textureStretch=" + location_textureStretch +
                ", location_colormap=" + location_colormap +
                ", location_displacementmap=" + location_displacementmap +
                ", location_specularmap=" + location_specularmap +
                ", location_displacefactor=" + location_displacefactor +
                ", location_renderWithVector=" + location_renderWithVector +
                ", location_shadowmap=" + location_shadowmap +
                ", location_useshadowmap=" + location_useshadowmap +
                ", location_shadowmatrix=" + location_shadowmatrix +
                ", location_fogDensity=" + location_fogDensity +
                ", location_fogGradient=" + location_fogGradient +
                ", location_fogColor=" + location_fogColor +
                "} ";
    }

    public void loadShadows(boolean useShadows, Matrix4f shadowMap) {
        super.loadBoolean(location_useshadowmap, useShadows);
        if(useShadows){
            super.loadMatrix(location_shadowmatrix, shadowMap);
        }
    }

    public void connectTextureUnits() {
        super.loadInt(location_colormap, 0);
        super.loadInt(location_displacementmap, 1);
        super.loadInt(location_specularmap, 2);
        super.loadInt(location_shadowmap, 3);
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

    public void loadMaterial(TexturedModel model) {
        EntityMaterial m = model.getMaterial();
        super.loadFloat(location_displacefactor, m.getDisplaceFactor());
        super.loadFloat(this.location_reflectivity, m.getReflectivity());
        super.loadFloat(this.location_textureStretch, model.getTextureStretch());
        super.loadFloat(location_shineDamper, m.getShineDamper());
        super.loadFloat(this.location_discardLimit, m.getDiscardLimit());
        super.loadBoolean(location_useLighting, m.useLighting());
    }

    public void loadFog(boolean value, float density, float gradient, float red, float green, float blue){
        super.loadFloat(location_fogDensity, value == true ? density:0);
        super.loadFloat(location_fogGradient, gradient);
        super.loadVector(location_fogColor, red, green, blue);
    }

    public void loadRenderWithVector(Vector3f vec){
        this.loadVector(location_renderWithVector, vec);
    }

    public void loadTransformationMatrix(Matrix4f matrix){
        super.loadMatrix(location_transformationMatrix, matrix);
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
    }
}
