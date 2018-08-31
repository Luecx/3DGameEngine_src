package projects.labyrinth;

import engine.core.components.Group;
import engine.core.components.PerspectiveCamera;
import engine.core.master.DisplayManager;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.util.vector.Vector;
import org.lwjgl.util.vector.Vector3f;

public class Player extends Group {


    private PerspectiveCamera camera;


    public Player(PerspectiveCamera camera) {
        this.camera = camera;
        this.addChild(camera);
    }

    public void move(Labyrinth labyrinth){
        double time = DisplayManager.processedFrameTime();

        float velocity = 3;


        Vector3f move = new Vector3f();


        if (Keyboard.isKeyDown(Keyboard.KEY_W)) {
            Vector3f dir = this.getZAxis();
            dir.negate();
            dir.normalise();
            dir.x = dir.x * velocity * (float) time;
            dir.y = dir.y * velocity * (float) time;
            dir.z = dir.z * velocity * (float) time;
            Vector3f.add(move, dir, move);
        }
        if (Keyboard.isKeyDown(Keyboard.KEY_S)) {
            Vector3f dir = this.getZAxis();
            dir.normalise();
            dir.x = dir.x * velocity * (float) time;
            dir.y = dir.y * velocity * (float) time;
            dir.z = dir.z * velocity * (float) time;
            Vector3f.add(move, dir, move);
        }
        if (Keyboard.isKeyDown(Keyboard.KEY_D)) {
            Vector3f dir = this.getXAxis();
            dir.normalise();
            dir.x = dir.x * velocity * (float) time;
            dir.y = dir.y * velocity * (float) time;
            dir.z = dir.z * velocity * (float) time;
            Vector3f.add(move, dir, move);
        }
        if (Keyboard.isKeyDown(Keyboard.KEY_A)) {
            Vector3f dir = this.getXAxis();
            dir.normalise();
            dir.negate();
            dir.x = dir.x * velocity * (float) time;
            dir.y = dir.y * velocity * (float) time;
            dir.z = dir.z * velocity * (float) time;
            Vector3f.add(move, dir, move);
        }

        this.increasePosition(move);

        if(labyrinth.collides(this.getPosition().x, this.getPosition().z)){
            this.increasePosition((Vector3f)move.negate());
        }



        this.increaseRotation(new Vector3f(0,-Mouse.getDX()/20f,0));
        this.camera.increaseRotation(new Vector3f(Mouse.getDY()/20f,0,0));

//
//        if (Keyboard.isKeyDown(Keyboard.KEY_UP)) {
//            this.increaseRotation(new Vector3f(1, 0, 0));
//        }
//        if (Keyboard.isKeyDown(Keyboard.KEY_DOWN)) {
//            this.increaseRotation(new Vector3f(-1, 0, 0));
//        }
//        if (Keyboard.isKeyDown(Keyboard.KEY_LEFT)) {
//            this.increaseRotation(new Vector3f(0, 1, 0));
//        }
//        if (Keyboard.isKeyDown(Keyboard.KEY_RIGHT)) {
//            this.increaseRotation(new Vector3f(0, -1, 0));
//        }

    }



}
