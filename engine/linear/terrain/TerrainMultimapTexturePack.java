package engine.linear.terrain;

import engine.linear.material.Material;
import engine.linear.material.TerrainMaterial;

/**
 * Created by Luecx on 21.12.2016.
 */
public class TerrainMultimapTexturePack extends Material{

    private TerrainMaterial redMaterial;
    private TerrainMaterial greenMaterial;
    private TerrainMaterial blueMaterial;
    private TerrainMaterial blackMaterial;

    private float reflectivity = 0.01f;
    private float shineDamper = 20f;

    public TerrainMultimapTexturePack(int overlay, TerrainMaterial redMaterial, TerrainMaterial greenMaterial, TerrainMaterial blueMaterial, TerrainMaterial blackMaterial) {
        super(overlay);
        this.redMaterial = redMaterial;
        this.greenMaterial = greenMaterial;
        this.blueMaterial = blueMaterial;
        this.blackMaterial = blackMaterial;
    }

    public TerrainMultimapTexturePack() {
        super(-1);
    }

    public TerrainMaterial getRedMaterial() {
        return redMaterial;
    }

    public void setRedMaterial(TerrainMaterial redMaterial) {
        this.redMaterial = redMaterial;
    }

    public TerrainMaterial getGreenMaterial() {
        return greenMaterial;
    }

    public void setGreenMaterial(TerrainMaterial greenMaterial) {
        this.greenMaterial = greenMaterial;
    }

    public TerrainMaterial getBlueMaterial() {
        return blueMaterial;
    }

    public void setBlueMaterial(TerrainMaterial blueMaterial) {
        this.blueMaterial = blueMaterial;
    }

    public TerrainMaterial getBlackMaterial() {
        return blackMaterial;
    }

    public void setBlackMaterial(TerrainMaterial blackMaterial) {
        this.blackMaterial = blackMaterial;
    }

    public float getReflectivity() {
        return reflectivity;
    }

    public void setReflectivity(float reflectivity) {
        this.reflectivity = reflectivity;
    }

    public float getShineDamper() {
        return shineDamper;
    }

    public void setShineDamper(float shineDamper) {
        this.shineDamper = shineDamper;
    }
}
