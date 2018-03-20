package projects.buggy_project.buggy;

import engine.core.components.Group;
import engine.core.components.PerspectiveCamera;
import engine.core.master.Time;
import engine.core.sourceelements.RawModel;
import engine.core.system.Sys;
import engine.linear.entities.Entity;
import engine.linear.entities.TexturedModel;
import engine.linear.loading.Loader;
import engine.linear.loading.OBJLoader;
import engine.linear.material.EntityMaterial;
import engine.linear.material.Material;
import org.lwjgl.input.Keyboard;
import projects.buggy_project.Parameter;
import projects.buggy_project.world.WorldInterface;

/**
 * Created by finne on 04.03.2018.
 */
public class SandBuggy implements BuggyInterface {

    private Group group;
    private PerspectiveCamera camera;

    private PerspectiveCamera freeFlyCamera;
    private boolean freeFly = false;
    private Time freeFlyKeyTimer = new Time();

    private Entity car;
    private Entity[] tires = new Entity[4]; //vorne links, vorne rechts, hinten links, hinten rechts

    @Override
    public void process(double time, WorldInterface groundInterface) {
        if(Keyboard.isKeyDown(Keyboard.KEY_C) && freeFlyKeyTimer.timerIsUp()){
            freeFlyKeyTimer.setTimer(0.5f);
            freeFly = !freeFly;
        }
        if(freeFly) {
            freeFlyCamera.move();
        }else{
            //Control
        }
    }

    @Override
    public void spawnModel() {
        group = new Group();

        EntityMaterial carMat = new EntityMaterial(Loader.loadTexture(Parameter.CAR_TEXTURE));
        carMat.setNormalMap(Loader.loadTexture(Parameter.CAR_NORMAL));
        carMat.setUseNormalMap(true);
        carMat.setReflectivity(3);
        carMat.setShineDamper(0.2f);

        EntityMaterial tireMat = new EntityMaterial(Loader.loadTexture(Parameter.TIRE_TEXTURE));
        tireMat.setNormalMap(Loader.loadTexture(Parameter.TIRE_NORMAL));
        tireMat.setUseNormalMap(true);
        tireMat.setReflectivity(0.2f);
        tireMat.setShineDamper(10f);

        RawModel carModel = OBJLoader.loadOBJ(Parameter.CAR_MODEL,true);
        RawModel tireModel = OBJLoader.loadOBJ(Parameter.TIRE_MODEL,true);

        TexturedModel carTexModel = new TexturedModel(carModel, carMat);
        TexturedModel tireTexModel = new TexturedModel(tireModel, tireMat);

        car = new Entity(carTexModel);
        car.setScale(0.2f,0.2f,0.2f);

        tires[0] = new Entity(tireTexModel);
        tires[1] = new Entity(tireTexModel);
        tires[2] = new Entity(tireTexModel);
        tires[3] = new Entity(tireTexModel);
        tires[0].setPosition(-Parameter.TIRE_FRONT_DISTANCE / 2, Parameter.TIRE_RADIUS, -Parameter.TIRE_FRONT_TO_BACK_DISTANCE);
        tires[1].setPosition(Parameter.TIRE_FRONT_DISTANCE / 2, Parameter.TIRE_RADIUS, -Parameter.TIRE_FRONT_TO_BACK_DISTANCE);
        tires[2].setPosition(-Parameter.TIRE_BACK_DISTANCE / 2, Parameter.TIRE_RADIUS, 0);
        tires[3].setPosition(Parameter.TIRE_BACK_DISTANCE / 2, Parameter.TIRE_RADIUS, 0);


        group.addChild(car);
        group.addChild(tires[0]);
        group.addChild(tires[1]);
        group.addChild(tires[2]);
        group.addChild(tires[3]);

        camera = new PerspectiveCamera(Parameter.cameraPosition,Parameter.cameraRotation);
        freeFlyCamera = new PerspectiveCamera(0,0,0);

        try{
            Sys.NORMAL_ENTITY_SYSTEM.addElement(car);
            Sys.NORMAL_ENTITY_SYSTEM.addElement(tires[0]);
            Sys.NORMAL_ENTITY_SYSTEM.addElement(tires[1]);
            Sys.NORMAL_ENTITY_SYSTEM.addElement(tires[2]);
            Sys.NORMAL_ENTITY_SYSTEM.addElement(tires[3]);
        }catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void spawnParticles() {

    }

    @Override
    public PerspectiveCamera getActiveCamera() {
        if(this.freeFly) return freeFlyCamera;
        return camera;
    }
}
