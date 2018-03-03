package projects.game.objects.plane.planes;

import engine.linear.entities.TexturedModel;
import engine.linear.loading.Loader;
import engine.linear.loading.OBJLoader;
import engine.linear.material.EntityMaterial;
import org.lwjgl.util.vector.Vector3f;

/**
 * Created by Luecx on 24.02.2017.
 */
public class JetEnum {
    private String source_material;
    private String source_normal;
    private String source_model;

    private TexturedModel texturedModel;

    private float min_speed;
    private float max_speed;
    private float speed_change;
    private float forward_speed;

    private float rotation_speed;
    private float gear_speed;
    private float nick_speed;

    private Vector3f[] cameraLocations;
    private Vector3f[] turbinePositions;
    private Vector3f[] missilePositions;
    private Vector3f[] gunPositions;

    public JetEnum(String source_material, String source_normal, String source_model, float min_speed, float max_speed, float speed_change, float rotation_speed, float forward_speed, float gear_speed, float nick_speed, Vector3f[] cameraLocations, Vector3f[] turbinePositions, Vector3f[] missilePositions, Vector3f[] gunPositions) {
        this.source_material = source_material;
        this.source_normal = source_normal;
        this.source_model = source_model;
        this.min_speed = min_speed;
        this.max_speed = max_speed;
        this.speed_change = speed_change;
        this.rotation_speed = rotation_speed;
        this.forward_speed = forward_speed;
        this.gear_speed = gear_speed;
        this.nick_speed = nick_speed;
        this.cameraLocations = cameraLocations;
        this.turbinePositions = turbinePositions;
        this.missilePositions = missilePositions;
        this.gunPositions = gunPositions;
    }

    public void generateModel() {
        if(texturedModel != null) return;
        try{
            Loader.loadTexture(source_material);
            TexturedModel model = new TexturedModel(OBJLoader.loadOBJ(source_model, false), new EntityMaterial(Loader.loadTexture(source_material)));
            model.getMaterial().setNormalMap(Loader.loadTexture(source_normal));
            model.getMaterial().setUseNormalMap(true);
            this.texturedModel = model;
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public void setSource_material(String source_material) {
        this.source_material = source_material;
    }

    public void setSource_normal(String source_normal) {
        this.source_normal = source_normal;
    }

    public void setSource_model(String source_model) {
        this.source_model = source_model;
    }

    public TexturedModel getTexturedModel() {
        return texturedModel;
    }

    public void setTexturedModel(TexturedModel texturedModel) {
        this.texturedModel = texturedModel;
    }

    public void setMin_speed(float min_speed) {
        this.min_speed = min_speed;
    }

    public void setMax_speed(float max_speed) {
        this.max_speed = max_speed;
    }

    public void setSpeed_change(float speed_change) {
        this.speed_change = speed_change;
    }

    public void setRotation_speed(float rotation_speed) {
        this.rotation_speed = rotation_speed;
    }

    public void setForward_speed(float forward_speed) {
        this.forward_speed = forward_speed;
    }

    public void setGear_speed(float gear_speed) {
        this.gear_speed = gear_speed;
    }

    public void setNick_speed(float nick_speed) {
        this.nick_speed = nick_speed;
    }

    public void setCameraLocations(Vector3f[] cameraLocations) {
        this.cameraLocations = cameraLocations;
    }

    public void setTurbinePositions(Vector3f[] turbinePositions) {
        this.turbinePositions = turbinePositions;
    }

    public void setMissilePositions(Vector3f[] missilePositions) {
        this.missilePositions = missilePositions;
    }

    public void setGunPositions(Vector3f[] gunPositions) {
        this.gunPositions = gunPositions;
    }

    public String getSource_material() {
        return source_material;
    }

    public String getSource_normal() {
        return source_normal;
    }

    public String getSource_model() {
        return source_model;
    }

    public float getMin_speed() {
        return min_speed;
    }

    public float getMax_speed() {
        return max_speed;
    }

    public float getSpeed_change() {
        return speed_change;
    }

    public float getRotation_speed() {
        return rotation_speed;
    }

    public float getForward_speed() {
        return forward_speed;
    }

    public float getGear_speed() {
        return gear_speed;
    }

    public float getNick_speed() {
        return nick_speed;
    }

    public Vector3f[] getCameraLocations() {
        return cameraLocations;
    }

    public Vector3f[] getTurbinePositions() {
        return turbinePositions;
    }

    public Vector3f[] getMissilePositions() {
        return missilePositions;
    }

    public Vector3f[] getGunPositions() {
        return gunPositions;
    }

    public static void generateAllMissiles() {
    }
}
