package projects.game.objects;

import org.lwjgl.util.vector.Vector3f;

/**
 * Created by Luecx on 14.02.2017.
 */
public enum AircraftEnum {
    JET_F_4E_Phantom_II("objectWithTextures/F-4E_Phantom_II/F-4E_Phantom_II_P01",
            "objectWithTextures/F-4E_Phantom_II/F-4E_Phantom_II_N",
            "objectWithTextures/F-4E_Phantom_II/F-4E_Phantom_II",
            new Vector3f[]{new Vector3f(0,-0.3f,2.5f)},
            new Vector3f[]{}
            );


    private String textureFile;
    private String normalFile;
    private String objFile;

    private Vector3f[] cameraLocation;
    private Vector3f[] turbinePosition;
    private



    AircraftEnum(String textureFile, String normalFile, String objFile, Vector3f[] cameraLocation, Vector3f[] turbinePosition) {
        this.textureFile = textureFile;
        this.normalFile = normalFile;
        this.objFile = objFile;
        this.cameraLocation = cameraLocation;
        this.turbinePosition = turbinePosition;
    }

    public String getTextureFile() {
        return textureFile;
    }

    public String getNormalFile() {
        return normalFile;
    }

    public String getObjFile() {
        return objFile;
    }

    public Vector3f[] getCameraLocation() {
        return cameraLocation;
    }

    public Vector3f[] getTurbinePosition() {
        return turbinePosition;
    }
}
