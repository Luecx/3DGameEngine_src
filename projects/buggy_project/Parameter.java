package projects.buggy_project;

import org.lwjgl.util.vector.Vector3f;

/**
 * Created by finne on 04.03.2018.
 */
public abstract class Parameter {

    public final static String SKYDOME_TEXTURE = "textures/colormaps/stripes";






    public final static String CAR_MODEL = "models/cube";
    public final static String TIRE_MODEL = "models/Tire2";

    public final static String CAR_TEXTURE = "textures/colormaps/Redpng";
    public final static String TIRE_TEXTURE = "textures/colormaps/bluePng";

    public final static String CAR_NORMAL = "textures/normalmaps/stoneNormal";
    public final static String TIRE_NORMAL = "textures/normalmaps/sofa";



    //Hinterreifen befinden sich bei z = 0
    //Vorderreifen befinden sich bei -TIRE_FRONT_TO_BACK_DISTANCE (In den bildschirm rein)
    //Zentrum der reifen beim TIRE_RADIUS.
    //Die Breite in x richtung wird durch TIRE_FRONT_DISTANCE und TIRE_BACK_DISTANCE gegeben.
    public final static float TIRE_RADIUS = 0.80f;
    public final static float TIRE_FRONT_DISTANCE = 1.2f;
    public final static float TIRE_BACK_DISTANCE = 1.2f;
    public final static float TIRE_FRONT_TO_BACK_DISTANCE = 2;

    public final static Vector3f cameraPosition = new Vector3f(0,4,4);
    public final static Vector3f cameraRotation = new Vector3f(-20,0,0);






}
