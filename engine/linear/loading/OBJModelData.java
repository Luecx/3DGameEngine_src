package engine.linear.loading;

import engine.core.exceptions.CoreException;
import engine.core.sourceelements.VAOIdentifier;

import java.io.*;
import java.util.Arrays;

/**
 * Created by Luecx on 07.03.2017.
 */
public class OBJModelData implements Serializable{

    private float[] vertices;
    private float[] textureCoords;
    private float[] normals;
    private float[] tangents;

    private int[] indices;

    private VAOIdentifier identifier;


    public OBJModelData(int totalVertices, int trianglesCount, VAOIdentifier identifier) {
        vertices = new float[totalVertices * 3];
        textureCoords = new float[totalVertices * 2];
        normals = new float[totalVertices * 3];
        tangents = new float[totalVertices * 3];
        indices = new int[trianglesCount * 3];
        this.identifier = identifier;
    }

    public float[] getVertices() {
        return vertices;
    }

    public float[] getTextureCoords() {
        return textureCoords;
    }

    public float[] getNormals() {
        return normals;
    }

    public float[] getTangents() {
        return tangents;
    }

    public int[] getIndices() {
        return indices;
    }

    @Override
    public String toString() {
        return "OBJModelData{" +
                "vertices=" + Arrays.toString(vertices) +
                ", textureCoords=" + Arrays.toString(textureCoords) +
                ", normals=" + Arrays.toString(normals) +
                ", tangents=" + Arrays.toString(tangents) +
                ", indices=" + Arrays.toString(indices) +
                '}';
    }


    public VAOIdentifier getIdentifier() {
        return identifier;
    }


    public static OBJModelData loadFromFile(String file) throws CoreException {
        File f = new File(OBJLoader.RES_LOC+ file +".dat");
        try {
            ObjectInputStream in = new ObjectInputStream(new FileInputStream(f));
            return (OBJModelData)in.readObject();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        throw new CoreException("Cannot read from file");
    }

    public void writeToFile(String file){
        File f = new File(OBJLoader.RES_LOC+ file +".dat");
        try {
            ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(f));
            out.writeObject(this);
            out.flush();
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
