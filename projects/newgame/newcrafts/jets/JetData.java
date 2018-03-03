package projects.newgame.newcrafts.jets;

import engine.core.components.Group;
import engine.core.sourceelements.RawModel;
import engine.linear.entities.TexturedModel;
import engine.linear.loading.Loader;
import engine.linear.loading.OBJLoader;
import engine.linear.material.EntityMaterial;
import org.lwjgl.util.vector.Vector3f;
import parser.Attribute;
import parser.Node;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by finne on 29.09.2017.
 */
public class JetData extends Group implements Serializable {

    protected String identifier;
    protected String hitboxFile;
    protected String objectFile;
    protected String textureFile;
    protected String normalFile;

    protected float minSpeed;
    protected float maxSpeed;
    protected float speedChange;
    protected float rotationSpeed;
    protected float gearSpeed;
    protected float nickSpeed;

    protected ArrayList<String>  availableMissiles = new ArrayList<>();
    protected ArrayList<Vector3f> missileLocations = new ArrayList<>();

    protected ArrayList<String> availableGuns = new ArrayList<>();
    protected ArrayList<Vector3f> gunLocations = new ArrayList<>();

    protected ArrayList<Vector3f> turbineLocations = new ArrayList<>();
    protected ArrayList<Vector3f> terrainHitLocations = new ArrayList<>();

    public JetData(Node node) {
        this.identifier = node.getName();
        this.hitboxFile = node.getChild("files").getAttribute("hitbox").getValue();
        this.objectFile = node.getChild("files").getAttribute("objectFile").getValue();
        this.textureFile = node.getChild("files").getAttribute("textureFile").getValue();
        this.normalFile = node.getChild("files").getAttribute("normalFile").getValue();

        this.minSpeed = Float.parseFloat(node.getChild("speeds").getAttribute("minSpeed").getValue());
        this.maxSpeed = Float.parseFloat(node.getChild("speeds").getAttribute("maxSpeed").getValue());
        this.speedChange = Float.parseFloat(node.getChild("speeds").getAttribute("speedChange").getValue());
        this.rotationSpeed = Float.parseFloat(node.getChild("speeds").getAttribute("rotationSpeed").getValue());
        this.gearSpeed = Float.parseFloat(node.getChild("speeds").getAttribute("gearSpeed").getValue());
        this.nickSpeed = Float.parseFloat(node.getChild("speeds").getAttribute("nickSpeed").getValue());

        for(int i = 0; i < 1024; i++) {
            Attribute t = node.getChild("missiles").getChild("locations").getAttribute(""+i);
            if(t == null) {
                break;
            }
            String[] vals = t.getValue().split(" ");
            missileLocations.add(new Vector3f(Float.parseFloat(vals[0]),Float.parseFloat(vals[1]),Float.parseFloat(vals[2])));
        }
        for(int i = 0; i < 1024; i++) {
            Attribute t = node.getChild("missiles").getChild("types").getAttribute(""+i);
            if(t == null) {
                break;
            }
            availableMissiles.add(t.getValue());
        }

        for(int i = 0; i < 1024; i++) {
            Attribute t = node.getChild("guns").getChild("locations").getAttribute(""+i);
            if(t == null) {
                break;
            }
            String[] vals = t.getValue().split(" ");
            gunLocations.add(new Vector3f(Float.parseFloat(vals[0]),Float.parseFloat(vals[1]),Float.parseFloat(vals[2])));
        }
        for(int i = 0; i < 1024; i++) {
            Attribute t = node.getChild("guns").getChild("types").getAttribute(""+i);
            if(t == null) {
                break;
            }
            availableGuns.add(t.getValue());
        }

        for(int i = 0; i < 1024; i++) {
            Attribute t = node.getChild("other").getChild("terrainHitLocations").getAttribute(""+i);
            if(t == null) {
                break;
            }
            String[] vals = t.getValue().split(" ");
            terrainHitLocations.add(new Vector3f(Float.parseFloat(vals[0]),Float.parseFloat(vals[1]),Float.parseFloat(vals[2])));
        }

        for(int i = 0; i < 1024; i++) {
            Attribute t = node.getChild("other").getChild("turbineLocations").getAttribute(""+i);
            if(t == null) {
                break;
            }
            String[] vals = t.getValue().split(" ");
            turbineLocations.add(new Vector3f(Float.parseFloat(vals[0]),Float.parseFloat(vals[1]),Float.parseFloat(vals[2])));
        }


    }

    public JetData(JetData data) {
        this.identifier = data.identifier;
        this.minSpeed = data.minSpeed;
        this.maxSpeed = data.maxSpeed;
        this.speedChange = data.speedChange;
        this.rotationSpeed = data.rotationSpeed;
        this.gearSpeed = data.gearSpeed;
        this.nickSpeed = data.nickSpeed;
        this.missileLocations = data.missileLocations;
        this.gunLocations = data.gunLocations;
        this.turbineLocations = data.turbineLocations;
        this.terrainHitLocations = data.terrainHitLocations;
    }

    public String getIdentifier() {
        return identifier;
    }
    public String getHitboxFile() {
        return hitboxFile;
    }
    public String getObjectFile() {
        return objectFile;
    }
    public String getTextureFile() {
        return textureFile;
    }
    public String getNormalFile() {
        return normalFile;
    }
    public float getMinSpeed() {
        return minSpeed;
    }
    public float getMaxSpeed() {
        return maxSpeed;
    }
    public float getSpeedChange() {
        return speedChange;
    }
    public float getRotationSpeed() {
        return rotationSpeed;
    }
    public float getGearSpeed() {
        return gearSpeed;
    }
    public float getNickSpeed() {
        return nickSpeed;
    }
    public ArrayList<String> getAvailableMissiles() {
        return availableMissiles;
    }
    public ArrayList<Vector3f> getMissileLocations() {
        return missileLocations;
    }
    public ArrayList<String> getAvailableGuns() {
        return availableGuns;
    }
    public ArrayList<Vector3f> getGunLocations() {
        return gunLocations;
    }
    public ArrayList<Vector3f> getTurbineLocations() {
        return turbineLocations;
    }
    public ArrayList<Vector3f> getTerrainHitLocations() {
        return terrainHitLocations;
    }

    @Override
    public String toString() {
        return String.format(
                "JetData (\n        identifier=%s        ,\n        hitboxFile=%s        ,\n        objectFile=%s        ,\n        textureFile=%s        ,\n        normalFile=%s        ,\n        minSpeed=%s        ,\n        maxSpeed=%s        ,\n        speedChange=%s        ,\n        rotationSpeed=%s        ,\n        gearSpeed=%s        ,\n        nickSpeed=%s        ,\n        availableMissiles=%s        ,\n        missileLocations=%s        ,\n        availableGuns=%s        ,\n        gunLocations=%s        ,\n        turbineLocations=%s        ,\n        terrainHitLocations=%s)", this.identifier, this.hitboxFile, this.objectFile, this.textureFile, this.normalFile, this.minSpeed, this.maxSpeed, this.speedChange, this.rotationSpeed, this.gearSpeed, this.nickSpeed, this.availableMissiles, this.missileLocations, this.availableGuns, this.gunLocations, this.turbineLocations, this.terrainHitLocations);
    }



    private TexturedModel texturedModel;
    public void generateTexturedModel() {
        EntityMaterial material = new EntityMaterial(Loader.loadTexture(textureFile));
        material.setNormalMap(Loader.loadTexture(normalFile));
        material.setUseNormalMap(true);

        RawModel rawModel = OBJLoader.loadOBJ(objectFile, true);
        texturedModel = new TexturedModel(rawModel, material);
    }
    public TexturedModel getTexturedModel() {
        return texturedModel;
    }
}
