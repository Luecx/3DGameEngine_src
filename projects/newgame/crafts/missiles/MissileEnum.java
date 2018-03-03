package projects.newgame.crafts.missiles;

import engine.linear.entities.Entity;
import engine.linear.entities.TexturedModel;
import engine.linear.loading.Loader;
import engine.linear.loading.OBJLoader;
import engine.linear.material.EntityMaterial;
import org.lwjgl.util.vector.Vector3f;

/**
 * Created by Luecx on 23.02.2017.
 */
public enum MissileEnum{

    POWER_A(new Vector3f(0,0,0), "objectWithTextures/missile/texture", "objectWithTextures/missile/missile", 1000,1200,30, 30000, 16),
    POWER_B(new Vector3f(-90,0,0), "objectWithTextures/missile/texture2", "objectWithTextures/missile/missile2", 1000,1200,30, 30000, 9)
    ;
    private Vector3f entityRotation;
    private String source_material;
    private String source_model;
    private TexturedModel model;
    private float rotation_speed;
    private float forward_speed;
    private float turn_speed;
    private float max_distance;
    private float distanceToFire;

    MissileEnum(Vector3f entityRotation, String source_material, String source_model, float rotation_speed, float forward_speed,  float turn_speed,float max_distance, float distanceToFire) {
        this.entityRotation = entityRotation;
        this.source_material = source_material;
        this.source_model = source_model;
        this.rotation_speed = rotation_speed;
        this.forward_speed = forward_speed;
        this.turn_speed = turn_speed;
        this.distanceToFire = distanceToFire;
        this.max_distance = max_distance;
    }

    public void generateModel() {
        if(model != null) return;
        try{
            Loader.loadTexture(source_material);
            TexturedModel model = new TexturedModel(OBJLoader.loadOBJ(source_model, false), new EntityMaterial(Loader.loadTexture(source_material)));
            this.model = model;
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public TexturedModel getModel() {
        return model;
    }

    public boolean isModelGenerated() {
        return model != null;
    }

    public Vector3f getEntityRotation() {
        return entityRotation;
    }

    public String getSource_material() {
        return source_material;
    }

    public String getSource_model() {
        return source_model;
    }

    public float getRotation_speed() {
        return rotation_speed;
    }

    public float getForward_speed() {
        return forward_speed;
    }

    public float getTurn_speed() {
        return turn_speed;
    }

    public Missile createMissile(Vector3f position, Vector3f rotation, Vector3f target) {
        if(model == null) return null;
        Entity e = new Entity(model);
        e.setRotation(entityRotation);
        return new Missile(position, rotation, e, rotation_speed, forward_speed, max_distance, target, turn_speed, distanceToFire);
    }

    public static void generateAllMissiles() {
        POWER_A.generateModel();
        POWER_B.generateModel();
    }
}