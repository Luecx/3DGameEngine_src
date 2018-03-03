package projects.game.solarsystem;

import engine.core.components.Group;
import engine.core.exceptions.CoreException;
import engine.core.sourceelements.RawModel;
import engine.core.system.Sys;
import engine.linear.entities.Entity;
import engine.linear.entities.TexturedModel;
import engine.linear.loading.Loader;
import engine.linear.loading.OBJLoader;
import engine.linear.material.EntityMaterial;

import java.util.ArrayList;

/**
 * Created by Luecx on 19.01.2017.
 */
public class Celestial {

    public static double SCALE_FACTOR = Math.pow(10,-2);
    public static double SPHERE_DEFAULT_RAD = 99.6;
    public static RawModel model = OBJLoader.loadOBJ("objectWithTextures/moon/moon", true);

    public ArrayList<Celestial> subObjects = new ArrayList<>();
    private Celestial parent;

    private Group connection = new Group();
    private Group group = new Group();
    private Entity entity;

    //Unit := KM
    private double dist;         //distance to center of rotation
    private double rad;          //radius of the object

    //Unit := KG
    private double mass;             //mass of the object

    //Unit := days
    public double circulationTime;  //time to rotate around the center
    private double rotationTime;     //time to rotate around its y axis


    public Celestial(double mass, double centerMass, double distance, double rotationTime){
        this.mass = mass;
        this.connection.addChild(group);
        this.dist =  distance;
        this.group.setPosition((float)(SCALE_FACTOR * dist),0,0);
        this.circulationTime = Calculus.calculateCirculationTime(mass, centerMass, distance);
        this.rotationTime = rotationTime;
    }

    public Celestial addSubObject(double mass, double dist, double rotationTime){
        Celestial subObj = new Celestial(mass, this.mass, dist, rotationTime);
        subObj.setParent(this);
        subObjects.add(subObj);
        return subObj;
    }

    public boolean hasActiveEntity() {
        return this.entity != null;
    }

    public void update(double days){
        if(circulationTime != 0)
            this.connection.increaseRotation(0,(float)(days/circulationTime)*360,0);
        if(rotationTime == 0) return;
        this.entity.increaseRotation(0,(float)(days/rotationTime)*360,0);
        updateAllChilds(days);
    }

    public void updateAllChilds(double days) {
        for (Celestial c : subObjects){
            c.update(days);
        }
    }

    public void generateEntity(String texture, String normal, boolean transparent, float specular, float reflec){
        if(hasActiveEntity()) {
            return;
        }
        EntityMaterial material = new EntityMaterial(Loader.loadTexture(texture));
        material.setNormalMap(Loader.loadTexture(normal));
        material.setTransparency(transparent);
        material.setShineDamper(specular);
        material.setReflectivity(reflec);
        TexturedModel model1 = new TexturedModel(Celestial.model , material);
        this.entity = new Entity(model1);
        this.entity.setParent(this.group);
        try {
            Sys.NORMAL_ENTITY_SYSTEM.addElement(this.entity);
        } catch (CoreException e) {
            e.printStackTrace();
        }
    }

    public void setParent(Celestial celestial) {
        this.parent = celestial;
        this.connection.setParent(celestial.group);
    }

    public void addChild(Celestial celestial) {
        celestial.setParent(this);
    }

    public double getMass() {
        return mass;
    }

    public void setMass(double mass) {
        this.mass = mass;
        this.circulationTime = Calculus.calculateCirculationTime(mass, this.parent.getMass(), dist);
    }

    public double getDist() {
        return dist;
    }

    @Deprecated
    public void setDist(double dist) {
        this.dist = dist;
        this.group.setPosition((float)(SCALE_FACTOR *dist),0,0);
        this.circulationTime = Calculus.calculateCirculationTime(mass, this.parent.getMass(), dist);
    }

    public double getRad() {
        return rad;
    }

    public void setRad(double rad) {
        this.rad = rad;
        this.entity.setScale((float)(rad * SCALE_FACTOR / SPHERE_DEFAULT_RAD),(float)(rad * SCALE_FACTOR / SPHERE_DEFAULT_RAD),(float)(rad * SCALE_FACTOR / SPHERE_DEFAULT_RAD));
    }

    public double getCirculationTime() {
        return circulationTime;
    }

    public void setCirculationTime(double circulationTime) {
        this.circulationTime = circulationTime;
    }

    public void printEntityInformation(){
        if(hasActiveEntity()){
            System.out.println(entity.toString());
        }
    }

    public Group getConnection() {
        return connection;
    }

    public Group getGroup() {
        return group;
    }

    public Entity getEntity() {
        return entity;
    }

    public Celestial getParent() {
        return parent;
    }

    public double getRotationTime() {
        return rotationTime;
    }

    @Override
    public String toString() {
        return "Celestial{" +
                ",\n dist=" + dist +
                ",\n rad=" + rad +
                ",\n mass=" + mass +
                ",\n circulationTime=" + circulationTime +
                ",\n rotationTime=" + rotationTime +
                '}';
    }
}
