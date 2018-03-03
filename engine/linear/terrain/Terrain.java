package engine.linear.terrain;

import engine.core.sourceelements.RawModel;
import engine.core.sourceelements.SourceElement;
import org.lwjgl.util.vector.Vector3f;
import java.awt.image.BufferedImage;

/**
 * Created by Luecx on 05.02.2017.
 */
public class Terrain implements SourceElement{

    private float startX, centerX, size;
    private float startY, centerY;
    private float stretchFactor;
    private int vertexCount;

    private HeightsGenerator[] generators;

    private TerrainModelData modelData = new TerrainModelData();;

    private RawModel rawModel;
    private TerrainMultimapTexturePack texturesPack = new TerrainMultimapTexturePack();

    private float[][] heights;


    public Terrain(float start_x, float start_y, float vertex_power, float stretchFactor) {
        this.startX = start_x;
        this.startY = start_y;
        this.vertexCount = (int) Math.pow(2, vertex_power) + 1;
        this.size = (this.vertexCount - 1) * stretchFactor;
        this.centerX = start_x + size / 2;
        this.centerY = start_y + size / 2;
        this.stretchFactor = stretchFactor;
    }

    public void generateHeights(HeightsGenerator... generators) {
        this.generators = generators;

        this.heights = new float[vertexCount][vertexCount];

        for (int i = 0; i < this.vertexCount; i++) {
            for (int n = 0; n < this.vertexCount; n++) {
                heights[i][n] = this.generateHeight(i * this.stretchFactor + this.startX,
                        n * this.stretchFactor + this.startY);
            }
        }
    }

    public void generateHeights(float[][] ar){
        if(ar.length == this.vertexCount && ar[0].length == this.vertexCount){
            this.heights = ar;
        }
    }

    public void smoothVertices(int radius){
        float[][] newHeights = new float[vertexCount][vertexCount];
        for(int i = radius; i < this.vertexCount - radius; i++){
            for(int n = radius; n < this.vertexCount- radius; n++) {
                for(int q = -radius; q <= radius; q++){
                    for(int w = -radius; w <= radius; w++){
                        newHeights[i][n] += heights[i + q][n + w];
                    }
                }
                newHeights[i][n] /= Math.pow(radius * 2 + 1, 2);

            }
        }
        this.heights = newHeights;
    }

    public void addNoise(float maxNoise){
        for(int i = 0; i < vertexCount; i++){
            for(int n = 0; n < vertexCount; n++){
                heights[i][n] += Math.random() * maxNoise;
            }
        }
    }

    public boolean createRawModelFromCollectedData() {
        RawModel r = this.modelData.createRawModel();
        if (r == null) {
            return false;
        } else {
            this.rawModel = r;
            return true;
        }
    }

    public void generateModelData() {
        if (!this.heightsGenerated()) {
            return;
        }

        float[] vertices = new float[vertexCount * vertexCount * 3];
        float[] normals = new float[vertexCount * vertexCount * 3];
        float[] textureCoords = new float[vertexCount * vertexCount * 2];
        int[] indices = new int[6 * (vertexCount - 1) * (vertexCount - 1)];

        int vertexPointer = 0;

        for (int i = 0; i < vertexCount; i++) {
            for (int n = 0; n < vertexCount; n++) {
                vertices[vertexPointer * 3] = startX + (i * this.stretchFactor);
                vertices[vertexPointer * 3 + 2] = startY + (n * this.stretchFactor);
                vertices[vertexPointer * 3 + 1] = heights[i][n];

                textureCoords[vertexPointer * 2] = (float) i / (float) vertexCount;
                textureCoords[vertexPointer * 2 + 1] = (float) n / (float) vertexCount;

                vertexPointer++;
            }
        }
        vertexPointer = 0;
        for (int i = 0; i < vertexCount; i++) {
            for (int n = 0; n < vertexCount; n++) {
                Vector3f normal = calculateNormal(i, n);
                normals[vertexPointer * 3] = normal.x;
                normals[vertexPointer * 3 + 1] = normal.y;
                normals[vertexPointer * 3 + 2] = normal.z;
                vertexPointer++;
            }
        }
        int pointer = 0;
        for (int gz = 0; gz < vertexCount - 1; gz++) {
            for (int gx = 0; gx < vertexCount - 1; gx++) {
                int topLeft = (gz * vertexCount) + gx;
                int topRight = topLeft + 1;
                int bottomLeft = ((gz + 1) * vertexCount) + gx;
                int bottomRight = bottomLeft + 1;
                indices[pointer++] = topLeft;
                indices[pointer++] = bottomLeft;
                indices[pointer++] = topRight;
                indices[pointer++] = topRight;
                indices[pointer++] = bottomLeft;
                indices[pointer++] = bottomRight;
            }
        }
        this.modelData.setIndices(indices);
        this.modelData.setVertices(vertices);
        this.modelData.setNormals(normals);
        this.modelData.setTextureCoords(textureCoords);
    }

    private Vector3f calculateNormal(int x, int z) {

        float left = (x < 1) ? this.generateHeight(this.startX - this.stretchFactor, z * stretchFactor + this.startY)
                : heights[x - 1][z];
        float right = (x >= this.vertexCount - 1)
                ? this.generateHeight(this.startX + size + this.stretchFactor, z * stretchFactor + this.startY)
                : heights[x + 1][z];

        float top = (z < 1) ? this.generateHeight(this.startX + x * stretchFactor, this.startY - this.stretchFactor)
                : heights[x][z - 1];
        float bottom = (z >= this.vertexCount - 1)
                ? this.generateHeight(this.startX + x * stretchFactor, this.startY - this.stretchFactor + this.size)
                : heights[x][z + 1];

        Vector3f normal = new Vector3f(left - right, 2f, top - bottom);
        normal.normalise();
        return normal;
    }

    public Vector3f indexToPosition(int x, int z) {
        return new Vector3f(x * stretchFactor + this.startX, heights[x][z], z * stretchFactor + this.startY);
    }

    public boolean heightsGenerated() {
        return this.heights != null;
    }

//

    public void generateBlendData(float a, float b, float c, float d) {
        if (!this.heightsGenerated()) {
            return;
        }

        float[] blending = new float[this.vertexCount * this.vertexCount * 4];

        for (int k = 0; k < vertexCount; k++) {
            for (int j = 0; j < vertexCount; j++) {
                float h = heights[k][j];
                int index = (k * vertexCount + j) * 4;
                if (h < a) {
                    blending[index + 0] = 1;
                } else if (h < b) {
                    float dif = b - a;
                    float sig = (h - a) / dif;
                    blending[index + 0] = 1 - sig;
                    blending[index + 1] = sig;
                } else if (h < c) {
                    float dif = c - b;
                    float sig = (h - b) / dif;
                    blending[index + 1] = 1 - sig;
                    blending[index + 2] = sig;
                } else if (h < d) {
                    float dif = d - c;
                    float sig = (h - c) / dif;
                    blending[index + 2] = 1 - sig;
                    blending[index + 3] = sig;
                } else {
                    blending[index + 3] = 1;
                }
            }
        }
        this.modelData.setBlending(blending);

    }

    public void generateBlendData(BufferedImage blendMap) {
        throw new UnsupportedOperationException("");
    }

    public boolean renderable() {
        return (this.rawModel != null);
    }

    public float generateHeight(float x, float y) {
        float total = 0;
        if (this.generators == null)
            return 0;
        for (HeightsGenerator g : generators) {
            total += g.generateHeight(x, y);
        }
        return total;
    }


    public float getStartX() {
        return startX;
    }

    public float getCenterX() {
        return centerX;
    }

    public float getSize() {
        return size;
    }

    public float getStartY() {
        return startY;
    }

    public float getCenterY() {
        return centerY;
    }

    public float getStretchFactor() {
        return stretchFactor;
    }

    public int getVertexCount() {
        return vertexCount;
    }

    public float getVertexDistance() {
        return stretchFactor;
    }

    public float[][] getHeights() {
        return heights;
    }

    public void setHeights(float[][] heights) {
        if(heights.length == this.vertexCount && heights[0].length == this.vertexCount){
            this.heights = heights;
        }
    }

    public HeightsGenerator[] getGenerators() {
        return generators;
    }

    public TerrainModelData getModelData() {
        return modelData;
    }

    public RawModel getRawModel() {
        return rawModel;
    }

    public TerrainMultimapTexturePack getTexturesPack() {
        return texturesPack;
    }

    public void setTexturesPack(TerrainMultimapTexturePack texturesPack) {
        this.texturesPack = texturesPack;
    }

    public void generateBlendDataFromNormals(float top, float bot, float light, float hard) {

        float steileSmooth = 0.1f;
        float[] blending = new float[this.vertexCount * this.vertexCount * 4];

        for (int k = 0; k < vertexCount; k++) {
            for (int j = 0; j < vertexCount; j++) {

                int index = (k * vertexCount + j) * 4;

                float h = heights[k][j];
                float vh = 0;
                if(h < top && h > bot){
                    vh = (h - bot) / (top - bot);
                } else if(h > top) vh = 1;


                Vector3f normal = new Vector3f(this.modelData.getNormals()[(k * vertexCount + j) * 3 + 0],
                        this.modelData.getNormals()[(k * vertexCount + j) * 3 + 1],
                        this.modelData.getNormals()[(k * vertexCount + j) * 3 + 2]
                );


                Vector3f up = new Vector3f(0,1,0);
                float steile = Vector3f.dot(up, normal);
                if(steile < light){
                    if(steile < hard) {
                        blending[index] = 0;
                        blending[index+1] = 0;
                        blending[index+2] = 1-Math.min(1, (hard-steile) / steileSmooth);
                        blending[index+3] = Math.min(1, (hard-steile) / steileSmooth);
                    }else{
                        blending[index+2] = Math.min(1, (light-steile) / steileSmooth);
                        blending[index] = (1 - vh) * (1-blending[index+2]);
                        blending[index+1] = (vh) * (1-blending[index+2]);;
                        blending[index+3] = 0;
                    }
                }else{
                    blending[index] = 1-vh;
                    blending[index+1] = vh;
                    blending[index+2] = 0;
                    blending[index+3] = 0;
                }
            }
        }



        this.modelData.setBlending(blending);





    }

        //    public void generateHeights(BufferedImage heightmap, float factor) throws DivergenceFormatException{
//
//
//        if(heightmap.getWidth() != heightmap.getHeight()) throw new DivergenceFormatException("Inputmap not squared!");
//
//        this.heights = new float[vertexCount][vertexCount];
//
//        for(int i = 0; i < vertexCount; i++){
//            for(int n = 0; n < vertexCount; n++){
//                heights[i][n] = factor * avgHeight(heightmap, i / (float)(vertexCount), n / (float)(vertexCount));
//            }
//        }
//    }


    public void setRawModel(RawModel rawModel) {
        this.rawModel = rawModel;
    }
}
