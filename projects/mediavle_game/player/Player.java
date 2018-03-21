package projects.mediavle_game.player;

import engine.core.exceptions.CoreException;
import engine.core.master.DisplayManager;
import engine.core.master.Time;
import engine.core.system.Sys;
import engine.linear.entities.Entity;
import engine.linear.entities.TexturedModel;
import engine.linear.material.EntityMaterial;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;
import projects.mediavle_game.gui.GuiInit;
import projects.mediavle_game.gui.HouseItem;
import projects.mediavle_game.map.GroundMap;
import projects.mediavle_game.map.entities.abs.UniqueGameEntity;

public abstract class Player {
    private static PlayerCamera perspectiveCamera = new PlayerCamera(500, 1.65f, 500);
    private static float mouseSens = 0.1f;
    private static float forwardSpeed = 4;
    private static int score;
    private static float totalWalked;

    public static void move(GroundMap groundMap) {

        perspectiveCamera.increaseRotation(Mouse.getDY() * mouseSens, Mouse.getDX() * -1 * mouseSens, 0);
        if (perspectiveCamera.getRotation().x >= 85)
            perspectiveCamera.getRotation().x = 85;
        else if (perspectiveCamera.getRotation().x <= -85)
            perspectiveCamera.getRotation().x = -85;

        if (Keyboard.isKeyDown(Keyboard.KEY_LSHIFT))
            forwardSpeed = 20;
        else
            forwardSpeed = 4;

        Vector3f forward = (Vector3f) (perspectiveCamera.getZAxis().negate());
        Vector3f sideward = (Vector3f) (perspectiveCamera.getXAxis().negate());
        Vector3f direction = (Vector3f) (new Vector3f(forward.x, 0, forward.z).normalise().scale(forwardSpeed * (float) DisplayManager.processedFrameTime()));
        Vector3f directionSide = (Vector3f) (new Vector3f(sideward.x, 0, sideward.z).normalise().scale(2 * (float) DisplayManager.processedFrameTime()));

        Vector3f pos = new Vector3f(perspectiveCamera.getPosition());


        if (Keyboard.isKeyDown(Keyboard.KEY_W))
            perspectiveCamera.increasePosition(direction);
        if (Keyboard.isKeyDown(Keyboard.KEY_S))
            perspectiveCamera.increasePosition((Vector3f) direction.scale(-.2f));
        if (Keyboard.isKeyDown(Keyboard.KEY_A))
            perspectiveCamera.increasePosition(directionSide);
        if (Keyboard.isKeyDown(Keyboard.KEY_D))
            perspectiveCamera.increasePosition((Vector3f) directionSide.negate());


        totalWalked += Vector3f.sub(perspectiveCamera.getPosition(), pos, null).length();

        perspectiveCamera.getPosition().y = 1.65f + (float) Math.sin(totalWalked * 2) * 0.1f;

        if (groundMap.isRigidBody(perspectiveCamera.getAbsolutePosition().x, perspectiveCamera.getAbsolutePosition().z)) {
            perspectiveCamera.setPosition(pos);
        }



        if(perspectiveCamera.lookingAtField() != null) {
            previewX = (int)perspectiveCamera.lookingAtField().getX();
            previewY = (int)perspectiveCamera.lookingAtField().getY();
        }

        GuiInit.setVisible(Keyboard.isKeyDown(Keyboard.KEY_TAB));
        if(Mouse.isButtonDown(0) &&
                previewEntity != null &&
                groundMap.couldPlace(previewX,previewY,previewObject.getWidth(), previewObject.getHeight())){
            UniqueGameEntity entityToPlace = previewObject.clone();
            entityToPlace.setX(previewX);
            entityToPlace.setY(previewY);
            groundMap.place(entityToPlace);
        }
        if(Mouse.isButtonDown(1) && previewClickTimer.timerIsUp()){
            previewClickTimer.setTimer(0.3);
            if(previewEntity != null) {
                Sys.NORMAL_ENTITY_SYSTEM.removeElement(previewEntity);
                previewObject = null;
                previewEntity = null;
            }else {
                if(houseItem != null) {
                    UniqueGameEntity e = houseItem.getUniqueGameEntity();
                    previewObject = e;
                    TexturedModel previewTexModel = new TexturedModel(
                            e.getTexturedModel().getRawModel(),
                            e.getTexturedModel().getMaterial());
                    previewMaterial = previewTexModel.getMaterial();
                    previewEntity = new Entity(previewTexModel);
                    try {
                        Sys.NORMAL_ENTITY_SYSTEM.addElement(previewEntity);
                    } catch (CoreException e1) {
                        e1.printStackTrace();
                    }
                }
            }
        }if(previewEntity != null){

            previewEntity.setPosition(previewX,0,previewY);
            if(!groundMap.couldPlace(previewX,previewY,previewObject.getWidth(), previewObject.getHeight())){
                previewEntity.getModel().setWireframe(true);
            }else{
                previewEntity.getModel().setWireframe(false);
            }
        }
    }

    private static UniqueGameEntity previewObject;
    private static Entity previewEntity;
    private static int previewX;
    private static int previewY;
    private static EntityMaterial previewMaterial;
    private static Time previewClickTimer = new Time();

    public static PlayerCamera getPerspectiveCamera() {
        return perspectiveCamera;
    }

    private static HouseItem houseItem;
    public static void setItem(HouseItem i) {
        houseItem = i;
        GuiInit.setItem(i);
    }
}
