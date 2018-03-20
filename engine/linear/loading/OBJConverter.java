package engine.linear.loading;

import engine.core.exceptions.CoreException;
import engine.core.sourceelements.VAOIdentifier;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Luecx on 04.03.2017.
 */
public class OBJConverter {


    private static class Vertex{
        private int texture_index = -1;
        private int normal_index = -1;

        private Vector3f tangent;
        private Vector3f position;

        public Vertex(Vector3f position) {
            this.position = position;
        }

        public int getTexture_index() {
            return texture_index;
        }

        public void setTexture_index(int texture_index) {
            this.texture_index = texture_index;
        }

        public int getNormal_index() {
            return normal_index;
        }

        public void setNormal_index(int normal_index) {
            this.normal_index = normal_index;
        }

        public Vector3f getPosition() {
            return position;
        }

        public void setPosition(Vector3f position) {
            this.position = position;
        }

        public Vector3f getTangent() {
            return tangent;
        }

        public void setTangent(Vector3f tangent) {
            this.tangent = tangent;
        }

        @Override
        public String toString() {
            return "Vertex{" +
                    "position=" + position +
                    ", texture_index=" + texture_index +
                    ", normal_index=" + normal_index +
                    '}';
        }

        public boolean hasData()  {
            return this.normal_index != -1 && this.texture_index != -1;
        }


        public void addTangent(Vector3f tangent) {
            this.tangent = tangent;
        }
    }

    private static BufferedReader reader;
    
    public static OBJModelData convertOBJ(String obj, boolean normalMapping) throws CoreException{
        try{
            reader = new BufferedReader(new FileReader(new File(OBJLoader.RES_LOC + obj + ".obj")));
        }catch(Exception e){
            throw new CoreException("File not found in ressource folder");
        }

        ArrayList<Vertex> vertices = new ArrayList<Vertex>();
        ArrayList<Vector2f> textures = new ArrayList<Vector2f>();
        ArrayList<Vector3f> normals = new ArrayList<Vector3f>();

        ArrayList<Integer> indices = new ArrayList<Integer>();

        String currentLine;

        try{
            while((currentLine = reader.readLine()) != null){
                String[] data = currentLine.trim().split(" ");
                if(data[0].equals("vt")){
                    textures.add(new Vector2f(
                            Float.parseFloat(data[1]),
                            Float.parseFloat(data[2])));
                }else if(data[0].equals("vn")){
                    normals.add(new Vector3f(
                            Float.parseFloat(data[1]),
                            Float.parseFloat(data[2]),
                            Float.parseFloat(data[3])));
                }else if(data[0].equals("v")) {
                    vertices.add(new Vertex(new Vector3f(
                        Float.parseFloat(data[1]),
                        Float.parseFloat(data[2]),
                        Float.parseFloat(data[3]))));
                }else if(data[0].equals("f")){
                    break;
                }
            }
            while(currentLine.startsWith("f") == true){

                String[] ve = currentLine.substring(2).trim().split(" |  |/");
                processVertex(Integer.parseInt(ve[0])- 1,Integer.parseInt(ve[1])- 1,Integer.parseInt(ve[2])- 1, vertices, indices);
                processVertex(Integer.parseInt(ve[3])- 1,Integer.parseInt(ve[4])- 1,Integer.parseInt(ve[5])- 1, vertices, indices);
                processVertex(Integer.parseInt(ve[6])- 1,Integer.parseInt(ve[7])- 1,Integer.parseInt(ve[8])- 1, vertices, indices);
                if(normalMapping){
                    calculateTangents(vertices.get(Integer.parseInt(ve[0])- 1),vertices.get(Integer.parseInt(ve[3])- 1),vertices.get(Integer.parseInt(ve[6])- 1), textures);
                }
                currentLine = reader.readLine();
                if(currentLine == null) break;
            }

            for(Vertex v: vertices) System.out.println(v);

            OBJModelData data = new OBJModelData(vertices.size(), indices.size() / 3, normalMapping ? VAOIdentifier.D3_NORMAL_MODEL:VAOIdentifier.D3_MODEL);

            for(int i = 0; i < vertices.size(); i++){
                data.getVertices()[i * 3 + 0] = vertices.get(i).getPosition().x;
                data.getVertices()[i * 3 + 1] = vertices.get(i).getPosition().y;
                data.getVertices()[i * 3 + 2] = vertices.get(i).getPosition().z;

                data.getTextureCoords()[i * 2 + 0] = textures.get(vertices.get(i).getTexture_index()).x;
                data.getTextureCoords()[i * 2 + 1] = textures.get(vertices.get(i).getTexture_index()).y;

                data.getNormals()[i * 3 + 0] = normals.get(vertices.get(i).getNormal_index()).x;
                data.getNormals()[i * 3 + 1] = normals.get(vertices.get(i).getNormal_index()).y;
                data.getNormals()[i * 3 + 2] = normals.get(vertices.get(i).getNormal_index()).z;

                if(normalMapping){
//                    data.getTangents()[i * 3 + 0] = vertices.get(i).getTangent().x;
//                    data.getTangents()[i * 3 + 1] = vertices.get(i).getTangent().z;
//                    data.getTangents()[i * 3 + 2] = vertices.get(i).getTangent().z;
                    data.getTangents()[i * 3 + 0] = 1.0f;
                    data.getTangents()[i * 3 + 1] = 1.0f;
                    data.getTangents()[i * 3 + 2] = 1.0f;
                }
            }for(int i = 0; i < indices.size(); i++){
                data.getIndices()[i] = indices.get(i);
            }
            return data;
        }catch (Exception e){
            e.printStackTrace();
            throw new CoreException("Unexpected Problem when reading file");
        }

    }

    private static void calculateTangents(Vertex v0,Vertex v1, Vertex v2,
                                          List<Vector2f> textures) {
        Vector3f deltaPos1 = Vector3f.sub(v1.getPosition(), v0.getPosition(), null);
        Vector3f deltaPos2 = Vector3f.sub(v2.getPosition(), v0.getPosition(), null);
        Vector2f uv0 = textures.get(v0.getTexture_index());
        Vector2f uv1 = textures.get(v1.getTexture_index());
        Vector2f uv2 = textures.get(v2.getTexture_index());
        Vector2f deltaUv1 = Vector2f.sub(uv1, uv0, null);
        Vector2f deltaUv2 = Vector2f.sub(uv2, uv0, null);

        float r = 1.0f / (deltaUv1.x * deltaUv2.y - deltaUv1.y * deltaUv2.x);
        deltaPos1.scale(deltaUv2.y);
        deltaPos2.scale(deltaUv1.y);
        Vector3f tangent = Vector3f.sub(deltaPos1, deltaPos2, null);
        tangent.scale(r);
        v0.addTangent(tangent);
        v1.addTangent(tangent);
        v2.addTangent(tangent);
    }

    private static void processVertex(int vertexIndex, int textureIndex, int normalIndex, ArrayList<Vertex> vertices, ArrayList<Integer> indices){
        Vertex v = vertices.get(vertexIndex);
        if(v.hasData()){
            if(v.getNormal_index() != normalIndex || v.getTexture_index() != textureIndex){
                Vertex v0 = new Vertex(v.getPosition());
                v0.setTexture_index(textureIndex);
                v0.setNormal_index(normalIndex);
                v0.setTangent(v.getTangent());
                vertices.add(v0);
                vertexIndex = vertices.size()-1;
            }
        }else{
            v.setTexture_index(textureIndex);
            v.setNormal_index(normalIndex);
        }
        indices.add(vertexIndex);
    }

    public static void main(String[] args) throws CoreException {
        OBJConverter.convertOBJ("models/cube", false);
        OBJLoader.loadOBJ("models/cube", false);
    }

}
