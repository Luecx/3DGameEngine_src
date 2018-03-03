package projects.newgame.crafts.planes;

import engine.core.components.Group;
import engine.core.master.Time;
import engine.linear.advancedterrain.Terrain;
import projects.newgame.crafts.missiles.MissileEnum;
import projects.newgame.crafts.missiles.MissileSystem;
import org.lwjgl.input.Keyboard;
import org.lwjgl.util.vector.Vector3f;

/**
 * Created by finne on 27.09.2017.
 */
public class AbstractJet extends Group{
    private float min_speed;
    private float max_speed;
    private float speed_change;

    private float currentSpeed;
    private Time missileTimer = new Time();
    private Time fireTimer = new Time();

    private float roll_speed;
    private float gear_speed;
    private float nick_speed;

    private Group[] missiles;
    private Group[] terrainHitPositions;

    public AbstractJet(
            float min_speed,
            float max_speed,
            float speed_change,
            float roll_speed,
            float gear_speed,
            float nick_speed,
            Vector3f[] missilePositions,
            Vector3f[] gunPositions,
            Vector3f[] hitPositions) {


        this.min_speed = min_speed;
        this.max_speed = max_speed;
        this.speed_change = speed_change;
        this.roll_speed = roll_speed;
        this.gear_speed = gear_speed;
        this.nick_speed = nick_speed;

        this.missileTimer.setTimer(2);
        this.fireTimer.setTimer(0.2);
        this.currentSpeed = (max_speed + min_speed) / 2;

        this.missiles = new Group[missilePositions.length];
        this.terrainHitPositions = new Group[hitPositions.length];



        for(int i = 0; i < missiles.length; i++) {
            missiles[i] = new Group();
            missiles[i].setPosition(missilePositions[i]);
            this.addChild(missiles[i]);
        }

        for(int i = 0; i < terrainHitPositions.length; i++) {
            terrainHitPositions[i] = new Group();
            terrainHitPositions[i].setPosition(hitPositions[i]);
            this.addChild(terrainHitPositions[i]);
        }
    }

    public boolean move(Terrain t, double passedTime) {

        for(Group g:this.terrainHitPositions) {
            Vector3f p = g.getAbsolutePosition();
            if(p.y < t.getChunks().get(0).height(p.x,p.z)){
                return false;
            }
        }

        if(Keyboard.isKeyDown(Keyboard.KEY_S)){
            currentSpeed -= passedTime * speed_change;
        }
        if(Keyboard.isKeyDown(Keyboard.KEY_W)){
            currentSpeed += passedTime * speed_change;
        }

        currentSpeed = Math.min(max_speed, currentSpeed);
        currentSpeed = Math.max(min_speed, currentSpeed);

        Vector3f forward = this.getZAxis();
        forward.scale((float) -(passedTime * currentSpeed / (forward.length() * 3.6)));
        this.increasePosition(forward);

        if(Keyboard.isKeyDown(Keyboard.KEY_F)) {
            if(missileTimer.timerIsUp()){
                MissileSystem.addMissile(MissileEnum.POWER_B, this.getPosition(), this.getRotation(), this.getPosition());
                missileTimer.setTimer(2);
            }
        }





//        if (Keyboard.isKeyDown(Keyboard.KEY_SPACE) && fireTimer.timerIsUp()) {
//            Vector3f h = new Vector3f(forward);
//            h.scale(1f / (h.length() * 3));
//            for (int i = 0; i < 20; i++) {
//                h = new Vector3f(forward);
//                h.scale(i * 0.6f / (h.length()));
//                for(ParticleEmitter g:guns) {
//                    g.emittParticle(h, forward, 900, 1, 2);
//                }
//            }
//            fireTimer.setTimer(0.2);
//
//        }
//        if (Keyboard.isKeyDown(Keyboard.KEY_LSHIFT)) {
//            if(cameraTimer.timerIsUp()) {
//                this.currentCamera = (currentCamera + 1) % cameras.length;
//                this.activeCamera = cameras[currentCamera];
//                cameraTimer.setTimer(0.3f);
//            }
//        }
//
//        if (Keyboard.isKeyDown(Keyboard.KEY_RIGHT)) {
//            this.increaseRotation(this.getZAxis(), -roll_speed * (float)passedTime);
//        }
//        if (Keyboard.isKeyDown(Keyboard.KEY_LEFT)) {
//            this.increaseRotation(this.getZAxis(), roll_speed * (float)passedTime);
//        }
//        if (Keyboard.isKeyDown(Keyboard.KEY_UP)) {
//            this.increaseRotation(this.getXAxis(), -nick_speed * (float)passedTime);
//        }
//        if (Keyboard.isKeyDown(Keyboard.KEY_DOWN)) {
//            this.increaseRotation(this.getXAxis(), nick_speed * (float)passedTime);
//        }
//        if (Keyboard.isKeyDown(Keyboard.KEY_A)) {
//            this.increaseRotation(this.getYAxis(), gear_speed * (float)passedTime);
//        }
//        if (Keyboard.isKeyDown(Keyboard.KEY_D)) {
//            this.increaseRotation(this.getYAxis(), -gear_speed * (float)passedTime);
//        }
          return true;
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

    public float getCurrentSpeed() {
        return currentSpeed;
    }

    public float getRoll_speed() {
        return roll_speed;
    }

    public float getGear_speed() {
        return gear_speed;
    }

    public float getNick_speed() {
        return nick_speed;
    }

    public Group[] getMissiles() {
        return missiles;
    }

    public Time getMissileTimer() {
        return missileTimer;
    }

    public Time getFireTimer() {
        return fireTimer;
    }

    public Group[] getTerrainHitPositions() {
        return terrainHitPositions;
    }
}
