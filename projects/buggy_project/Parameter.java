package projects.buggy_project;

import org.lwjgl.util.vector.Vector3f;

/**
 * Created by finne on 04.03.2018.
 */
public abstract class Parameter {

    public final static String SKYDOME_TEXTURE = "textures/colormaps/sky";

    public final static String TERRAIN_HEIGHT_MAP = "terrain/canyon";
    public final static String TERRAIN_OVERLAY_MAP = "terrain/canyonOverlay";
    public final static String TERRAIN_MAT_A_COLOR = "terrain/sand";         //unten
    public final static String TERRAIN_MAT_A_NORMAL = "terrain/sand_NORM";
    public final static String TERRAIN_MAT_B_COLOR = "terrain/159";         //oben
    public final static String TERRAIN_MAT_B_NORMAL = "terrain/159_norm";
    public final static String TERRAIN_MAT_C_COLOR = "terrain/151";         //leichter Hang
    public final static String TERRAIN_MAT_C_NORMAL = "terrain/151_norm";
    public final static String TERRAIN_MAT_D_COLOR = "terrain/165";         //großer Hang
    public final static String TERRAIN_MAT_D_NORMAL = "terrain/165_norm";
    public final static int TERRAIN_VERTEX_POWER = 10;
    public final static int TERRAIN_VERTEX_OFFSET = 1; //DO not change this!!
    public final static int TERRAIN_SIZE = 1000;
    public final static float TERRAIN_LOWER_MAT_HEIGHT = 300f;
    public final static float TERRAIN_UPPER_MAT_HEIGHT = 1000f;
    public final static float TERRAIN_SLOAP_LIGHT = 0.9f;
    public final static float TERRAIN_SLOAP_HARD = 0.8f;
    public final static float TERRAIN_HEIGHT_SCALE = 40;









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
