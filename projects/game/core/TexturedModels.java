package projects.game.core;

import engine.core.sourceelements.RawModel;
import engine.linear.entities.TexturedModel;
import engine.linear.loading.Loader;
import engine.linear.loading.OBJLoader;
import engine.linear.material.EntityMaterial;

/**
 * Created by Luecx on 01.03.2017.
 */
public enum TexturedModels {

    //SPACE_STATION_A("objectWithTextures/spacestations/A/model"),

    ;


    private String dataFile;
    private String textureFile;
    private String normalFile;
    private String displaceFile;
    private String specularFile;

    private TexturedModel texturedModel;

    TexturedModels(String dataFile, String textureFile) {
        this.dataFile = dataFile;
        this.textureFile = textureFile;
    }

    TexturedModels(String dataFile, String textureFile, String normalFile) {
        this.dataFile = dataFile;
        this.textureFile = textureFile;
        this.normalFile = normalFile;
    }

    TexturedModels(String dataFile, String textureFile, String normalFile, String displaceFile) {
        this.dataFile = dataFile;
        this.textureFile = textureFile;
        this.normalFile = normalFile;
        this.displaceFile = displaceFile;
    }

    TexturedModels(String dataFile, String textureFile, String normalFile, String displaceFile, String specularFile) {
        this.dataFile = dataFile;
        this.textureFile = textureFile;
        this.normalFile = normalFile;
        this.displaceFile = displaceFile;
        this.specularFile = specularFile;
    }

    private void generateTexturedModel() {
        if(texturedModel != null) return;
        EntityMaterial material = new EntityMaterial(Loader.loadTexture(textureFile));
        if(normalFile != null){      material.setNormalMap(Loader.loadTexture(normalFile));          material.setUseNormalMap(true);}
        if(displaceFile != null){    material.setDisplacementMap(Loader.loadTexture(displaceFile));  material.setUseDisplacementMap(true);}
        if(specularFile != null){    material.setSpecularMap(Loader.loadTexture(specularFile));      material.setUseSpecularMap(true);}
        RawModel model = OBJLoader.loadOBJ(dataFile, normalFile == null);
        this.texturedModel = new TexturedModel(model, material);
    }

    public String getDataFile() {
        return dataFile;
    }

    public String getTextureFile() {
        return textureFile;
    }

    public String getNormalFile() {
        return normalFile;
    }

    public String getDisplaceFile() {
        return displaceFile;
    }

    public String getSpecularFile() {
        return specularFile;
    }

    public TexturedModel getTexturedModel() {
        return texturedModel;
    }

    public static void generateAllTexturedModels() {

    }
}
