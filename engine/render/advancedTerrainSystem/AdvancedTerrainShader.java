package engine.render.advancedTerrainSystem;

import engine.core.components.Light;
import engine.core.master.RenderSettings;
import engine.core.system.ShaderProgram;
import engine.linear.terrain.TerrainMultimapTexturePack;
import engine.render.normalentitysystem.NormalEntityShader;
import org.lwjgl.util.vector.Matrix4f;

import java.util.Arrays;
import java.util.List;

/**
 * Created by Luecx on 12.01.2017.
 */
public class AdvancedTerrainShader extends ShaderProgram{

    private static final String VERTEX_FILE = "src/engine/render/advancedTerrainSystem/vs.glsl";
    private static final String FRAGMENT_FILE = "src/engine/render/advancedTerrainSystem/fs.glsl";

    private int location_projectionMatrix;
    private int location_viewMatrix;

    private int location_lightPosition[] ;
    private int location_lightColour[];

    private int location_lightAmount;
    private int location_shineDamper;
    private int location_reflectivity;

    private int location_c1;
    private int location_c2;
    private int location_c3;
    private int location_c4;

    private int location_n1;
    private int location_n2;
    private int location_n3;
    private int location_n4;
    private int location_overlay;
    
    private int location_textureStretch;

    private int location_fogGradient;
    private int location_fogDensity;
    private int location_fogColor;

    public AdvancedTerrainShader() {
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

        location_fogGradient = super.getUniformLocation("fogGradient");
        location_fogDensity = super.getUniformLocation("fogDensity");
        location_fogColor = super.getUniformLocation("fogColor");

        for(int i=0;i< RenderSettings.ENTITIES_MAX_LIGHTS;i++){
            location_lightPosition[i] = super.getUniformLocation("lightPosition[" + i + "]");
            location_lightColour[i] = super.getUniformLocation("lightColor[" + i + "]");
        }

        location_c1= super.getUniformLocation("c1");
        location_c2= super.getUniformLocation("c2");
        location_c3= super.getUniformLocation("c3");
        location_c4= super.getUniformLocation("c4");

        location_n1= super.getUniformLocation("n1");
        location_n2= super.getUniformLocation("n2");
        location_n3= super.getUniformLocation("n3");
        location_n4= super.getUniformLocation("n4");

        location_overlay = super.getUniformLocation("overlay");

        location_textureStretch = super.getUniformLocation("textureStretch");
    }


    @Override
    public String toString() {
        return "AdvancedTerrainShader{" +
                "location_projectionMatrix=" + location_projectionMatrix +
                ", location_viewMatrix=" + location_viewMatrix +
                ", location_lightPosition=" + Arrays.toString(location_lightPosition) +
                ", location_lightColour=" + Arrays.toString(location_lightColour) +
                ", location_lightAmount=" + location_lightAmount +
                ", location_shineDamper=" + location_shineDamper +
                ", location_reflectivity=" + location_reflectivity +
                ", location_c1=" + location_c1 +
                ", location_c2=" + location_c2 +
                ", location_c3=" + location_c3 +
                ", location_c4=" + location_c4 +
                ", location_n1=" + location_n1 +
                ", location_n2=" + location_n2 +
                ", location_n3=" + location_n3 +
                ", location_n4=" + location_n4 +
                ", location_overlay=" + location_overlay +
                ", location_textureStretch=" + location_textureStretch +
                ", location_fogGradient=" + location_fogGradient +
                ", location_fogDensity=" + location_fogDensity +
                ", location_fogColor=" + location_fogColor +
                "} ";
    }

    public void loadFog(float gradient, float density, float colorR, float colorG, float colorB){
        super.loadFloat(location_fogGradient, gradient);
        super.loadFloat(location_fogDensity, density);
        super.loadVector(location_fogColor, colorR, colorG, colorB);
    }

    public void loadLights(List<Light> lights, Matrix4f viewMatrix){
        super.loadFloat(location_lightAmount, lights.size());
        for(int i=0;i<RenderSettings.ENTITIES_MAX_LIGHTS;i++){
            if(i<lights.size()){
                super.loadVector(location_lightPosition[i], NormalEntityShader.getEyeSpacePosition(lights.get(i), viewMatrix));
                super.loadVector(location_lightColour[i], lights.get(i).getColor());
            }
        }
    }

    public void loadMaterials(TerrainMultimapTexturePack material) {
        super.loadVector(location_shineDamper,
                material.getRedMaterial().getShineDamper(),
                material.getGreenMaterial().getShineDamper(),
                material.getBlueMaterial().getShineDamper(),
                material.getBlackMaterial().getShineDamper());
        super.loadVector(location_reflectivity,
                material.getRedMaterial().getReflectivity(),
                material.getGreenMaterial().getReflectivity(),
                material.getBlueMaterial().getReflectivity(),
                material.getBlackMaterial().getReflectivity());
        super.loadVector(location_textureStretch,
                material.getRedMaterial().getTextureStretch(),
                material.getGreenMaterial().getTextureStretch(),
                material.getBlueMaterial().getTextureStretch(),
                material.getBlackMaterial().getTextureStretch());
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
        super.bindAttribute(3, "tangent");
        super.bindAttribute(7, "blend");
    }

    @Override
    protected void connectTextureUnits() {
        super.loadInt(location_c1, 10);
        super.loadInt(location_c2, 12);
        super.loadInt(location_c3, 14);
        super.loadInt(location_c4, 16);
        super.loadInt(location_n1, 11);
        super.loadInt(location_n2, 13);
        super.loadInt(location_n3, 15);
        super.loadInt(location_n4, 17);
        super.loadInt(location_overlay, 18);
    }
}
