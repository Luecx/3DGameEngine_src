package projects.wot_mini.client;

import engine.core.components.Group;
import engine.core.components.Light;
import engine.core.components.PerspectiveCamera;
import engine.core.exceptions.CoreException;
import engine.core.master.RenderCore;
import engine.core.sourceelements.RawModel;
import engine.core.system.Sys;
import engine.linear.entities.Entity;
import engine.linear.entities.TexturedModel;
import engine.linear.loading.Loader;
import engine.linear.loading.OBJLoader;
import engine.linear.material.EntityMaterial;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import projects.wot_mini.ServerInput;

import java.util.ArrayList;

public class ClientCore extends RenderCore {

    public Group entity_group = new Group();
    public Group camera_group = new Group();
    public PerspectiveCamera camera = new PerspectiveCamera();
    public ArrayList<Light> lights = new ArrayList<>();

    private TestClient testClient;

    public ClientCore(TestClient testClient) {
        this.testClient = testClient;
    }

    @Override
    protected void onEnable() {
        Sys.enableAll();

        Mouse.setGrabbed(true);
        RawModel model = OBJLoader.loadOBJ("ground", true);
        EntityMaterial material = new EntityMaterial(Loader.loadTexture("raw"));
        material.setNormalMap(Loader.loadTexture("textures/normalmaps/groundnormal"));
        material.setUseNormalMap(true);
        TexturedModel texturedModel = new TexturedModel(model, material);
        texturedModel.setTextureStretch(2000);

        Entity e = new Entity(texturedModel);
        e.setScale(1000,1000,1000);

        RawModel model2 = OBJLoader.loadOBJ("models/cube", true);
        EntityMaterial material2 = new EntityMaterial(Loader.loadTexture("textures/colormaps/fels"));
        material2.setNormalMap(Loader.loadTexture("textures/normalmaps/groundnormal"));
        material2.setUseNormalMap(true);
        TexturedModel texturedModel2 = new TexturedModel(model2, material2);

        Entity e2 = new Entity(texturedModel2);

        camera.setPosition(0,0,6);
        camera_group.addChild(camera);
        entity_group.addChild(e2);

        lights.add(new Light(100,1000,300));

        try {
            Sys.NORMAL_ENTITY_SYSTEM.addElement(e);
            Sys.NORMAL_ENTITY_SYSTEM.addElement(e2);
        } catch (CoreException e1) {
            e1.printStackTrace();
        }

    }

    @Override
    protected void onDisable() {
        this.testClient.close();
    }

    @Override
    protected void render() {
        this.sendControls();
        this.cameraControls();

        Sys.NORMAL_ENTITY_SYSTEM.render(lights, camera);
    }

    private void cameraControls(){

        this.camera_group.increaseRotation( (float)Mouse.getDY() * 0.03f,-(float)Mouse.getDX() * 0.03f, 0);
        if(this.camera_group.getRotation().x < -80){
            this.camera_group.getRotation().x = -80;
        }
        if(this.camera_group.getRotation().x > -10) {
            this.camera_group.getRotation().x = -10;
        }
    }

    private void sendControls(){
        ServerInput input = new ServerInput(
                new boolean[]{
                        Keyboard.isKeyDown(Keyboard.KEY_W),
                        Keyboard.isKeyDown(Keyboard.KEY_D),
                        Keyboard.isKeyDown(Keyboard.KEY_A)});
        try {
            testClient.send(input);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
