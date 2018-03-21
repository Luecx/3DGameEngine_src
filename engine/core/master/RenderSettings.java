package engine.core.master;

/**
 * Created by Luecx on 22.12.2016.
 */
public class RenderSettings {

    public static final int ENTITIES_MAX_LIGHTS = 4;

    public static float skydome_fog_color_red = 0.8f;
    public static float skydome_fog_color_green = 0.8f;
    public static float skydome_fog_color_blue = 0.8f;
    public static float skydome_fog_density = 4f;
    public static float skydome_fog_gradient = 3;
    public static float skydome_fog_midlevel = 0;
    public static boolean skydome_fog = true;
    public static boolean skydome_use_skysphere = false;
    public static boolean skydome_follow_x_axis = true;
    public static boolean skydome_follow_y_axis = false;
    public static boolean skydome_follow_z_axis = true;
    public static float skydome_bounding_x_axis = 0;
    public static float skydome_bounding_y_axis = -500;
    public static float skydome_bounding_z_axis = 0;
    public static int skydome_radius = 45000;
    
    public static float terrain_fog_color_red = 0.8f;
    public static float terrain_fog_color_green = 0.8f;
    public static float terrain_fog_color_blue = 0.8f;
    public static float terrain_fog_density = 0.0003f;
    public static float terrain_fog_gradient = 4f;
    public static boolean terrain_fog = false;

    public static boolean entity_fog = true;
    public static float entity_fog_density = 0.01f;
    public static float entity_fog_gradient = 2.5f;
    public static float entity_fog_color_red = 0.8f;
    public static float entity_fog_color_green = 0.9f;
    public static float entity_fog_color_blue = 0.9f;

}
