package projects.mediavle_game.player;

import engine.core.exceptions.CoreException;
import engine.core.master.DisplayManager;
import engine.core.system.Sys;
import engine.linear.entities.Entity;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;
import projects.mediavle_game.gui.GuiInit;
import projects.mediavle_game.gui.Item;
import projects.mediavle_game.map.GroundMap;
import projects.mediavle_game.map.entities.abs.UniqueGameEntity;

public class Player {
    private PlayerCamera perspectiveCamera = new PlayerCamera(500, 1.65f, 500);
    private float mouseSens = 0.1f;
    private float forwardSpeed = 4;
    private int score;
    private float totalWalked;

    UniqueGameEntity testEntity;

    public void move(GroundMap groundMap) {

//

        perspectiveCamera.increaseRotation(Mouse.getDY() * mouseSens, Mouse.getDX() * -1 * mouseSens, 0);
        if (perspectiveCamera.getRotation().x > 90)
            perspectiveCamera.getRotation().x = 90;
        else if (perspectiveCamera.getRotation().x < -90)
            perspectiveCamera.getRotation().x = -90;

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

        if (Mouse.isButtonDown(0)) {
            Vector2f look = this.perspectiveCamera.lookingAtField();
            if (look != null) {
                if (groundMap.getFields()[(int) look.x][(int) look.y].getGameEntity() != null) {
                    groundMap.getFields()[(int) look.x][(int) look.y].getGameEntity().destroyEntity();
                    groundMap.getFields()[(int) look.x][(int) look.y].setUniqueGameEntity(null);
                    score++;
                }
            }
        }
        GuiInit.setVisible(Keyboard.isKeyDown(Keyboard.KEY_TAB));
        if (Keyboard.isKeyDown(Keyboard.KEY_TAB) == false) {
            if (Mouse.isButtonDown(0)) {
                if(this.util_item != null){
                    this.util_item.onAction_Util();
                }
            } else if (Mouse.isButtonDown(1) && this.house_item != null) {
                if (testEntity == null && groundMap.couldPlace(
                        (int) perspectiveCamera.lookingAtField().x,
                        (int) perspectiveCamera.lookingAtField().y,
                        house_item.getUniqueGameEntity().getWidth(),
                        house_item.getUniqueGameEntity().getHeight())) {
                    testEntity = house_item.getUniqueGameEntity();
                    testEntity.getEntity().getPosition().x = this.perspectiveCamera.lookingAtField().x;
                    testEntity.getEntity().getPosition().z = this.perspectiveCamera.lookingAtField().y;
                    try {
                        Sys.NORMAL_ENTITY_SYSTEM.addElement(testEntity.getEntity());
                    } catch (CoreException e) {
                        e.printStackTrace();
                    }
                } else {
                    if (testEntity != null && groundMap.couldPlace(
                            (int) testEntity.getEntity().getPosition().x,
                            (int) testEntity.getEntity().getPosition().z,
                            testEntity.getWidth(),
                            testEntity.getHeight())) {
                        testEntity.getEntity().getPosition().x = this.perspectiveCamera.lookingAtField().x;
                        testEntity.getEntity().getPosition().z = this.perspectiveCamera.lookingAtField().y;
                    }
                }
            } else {
                if (testEntity != null) {
                    if (testEntity != null && groundMap.couldPlace(
                            (int) testEntity.getEntity().getPosition().x,
                            (int) testEntity.getEntity().getPosition().z,
                            testEntity.getWidth(),
                            testEntity.getHeight())) {
                        groundMap.place(testEntity.clone());
                    }
                    Sys.NORMAL_ENTITY_SYSTEM.removeElement(testEntity.getEntity());
                    testEntity = null;

                }
            }
        }
    }

    public PlayerCamera getPerspectiveCamera() {
        return perspectiveCamera;
    }

    public void setPerspectiveCamera(PlayerCamera perspectiveCamera) {
        this.perspectiveCamera = perspectiveCamera;
    }


    private Item util_item;
    private Item house_item;

    public void setItem(Item i) {
        if (i.getType() == Item.TYPE_HOUSE) {
            house_item = i;
        } else {
            util_item = i;
        }
        GuiInit.setItem(i);
    }
}
