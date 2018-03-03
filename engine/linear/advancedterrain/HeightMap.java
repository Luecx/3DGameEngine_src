package engine.linear.advancedterrain;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.awt.image.DataBuffer;
import java.awt.image.DataBufferUShort;
import java.io.File;
import java.io.IOException;

/**
 * Created by Luecx on 08.02.2017.
 */
public class HeightMap {

    private int vertexCount;
    private float[][] heights;

    private float scaleFactor = 500;

    public HeightMap(int vertexPower, int offset){
        this.vertexCount = (int)Math.pow(2,vertexPower)+ offset;
    }

    public HeightMap(int vertexPower){
        this(vertexPower,0);
    }

    /**
     * Interpolates the 4 values with a bicubic interpolation algorithm.
     * The calculated values position is between b and c where frac (in %)
     * gives the exact position. frac = 0 means interpolated at point b,
     * frac = 1 means inteprolated at c.
     *
     * @param a
     * @param b
     * @param c
     * @param d
     * @param fr
     * @return res
     */
    public float interpolate(float a, float b, float c, float d, float fr) {

        float res = (float)((-0.5 * a + 1.5 * b - 1.5 * c + 0.5 * d) * fr * fr * fr +
                (a - 2.5 * b + 2 * c - 0.5 * d)  * fr * fr + (-0.5 * a + 0.5 * c) * fr + b);
        return res;
    }

    private void convertHighPrecisionImage(BufferedImage img) throws Exception{
        DataBufferUShort buffer = (DataBufferUShort) img.getRaster().getDataBuffer();
        float[][] imageData = new float[img.getWidth()][img.getHeight()];
        for(int i = 0;i < img.getWidth(); i++){
            for(int n = 0; n < img.getHeight(); n++){
                imageData[i][n] = buffer.getElem(i + n * img.getHeight()) / (float)Math.pow(2,16);
            }
        }
        imgDataToHeights(img.getWidth(), imageData);
    }

    private void convertLowPrecisionImage(BufferedImage img) throws Exception{
        DataBuffer buffer = img.getRaster().getDataBuffer();
        float[][] imageData = new float[img.getWidth()][img.getHeight()];
        for(int i = 0;i < img.getWidth(); i++){
            for(int n = 0; n < img.getHeight(); n++){
                imageData[i][n] = buffer.getElem(i + n * img.getHeight()) / (float)Math.pow(2,16);
            }
        }
        imgDataToHeights(img.getWidth(), imageData);

    }

    private void imgDataToHeights(int imgWidth, float[][] imageData) {
        int bereiche = imgWidth-3;
        for(int i = 0;i < vertexCount; i++){
            for(int n = 0; n < vertexCount; n++){
                float pX = ((float)i / (float)vertexCount - 0.000001f);
                float pY = ((float)n / (float)vertexCount - 0.000001f);
                int sX = (int)(bereiche * pX);
                int sY = (int)(bereiche * pY);
                heights[i][n] = scaleFactor * interpolate(
                        interpolate(imageData[sX + 0][sY + 0],imageData[sX + 1][sY + 0],imageData[sX + 2][sY + 0],imageData[sX + 3][sY + 0], (pX % (1f / (float)bereiche)) * bereiche),
                        interpolate(imageData[sX + 0][sY + 1],imageData[sX + 1][sY + 1],imageData[sX + 2][sY + 1],imageData[sX + 3][sY + 1], (pX % (1f / (float)bereiche)) * bereiche),
                        interpolate(imageData[sX + 0][sY + 2],imageData[sX + 1][sY + 2],imageData[sX + 2][sY + 2],imageData[sX + 3][sY + 2], (pX % (1f / (float)bereiche)) * bereiche),
                        interpolate(imageData[sX + 0][sY + 3],imageData[sX + 1][sY + 3],imageData[sX + 2][sY + 3],imageData[sX + 3][sY + 3], (pX % (1f / (float)bereiche)) * bereiche),
                        (pY % (1f / (float)bereiche)) * bereiche
                );
            }
        }
    }

    public void convertImageToData(String file) {
        BufferedImage img;
        try {
            img = ImageIO.read(new File("res/"+file+".png"));
            this.heights = new float[vertexCount][vertexCount];
            if(img.getHeight() != img.getWidth()){
                throw new IOException("Only squared pictures allowed !");
            }
            try {
                convertHighPrecisionImage(img);
            } catch (Exception e) {
                try {
                    convertHighPrecisionImage(img);
                } catch (Exception ex) {
                    System.err.println("Cannot load image - Wrong format [precision]");
                    e.printStackTrace();
                }
            }

        } catch (IOException e) {
            System.err.println("Cannot load image - FileNotFoundException / IOException");
            e.printStackTrace();
        }
    }

    public int getVertexCount() {
        return vertexCount;
    }

    public float[][] getHeights() {
        return heights;
    }

    public float getScaleFactor() {
        return scaleFactor;
    }

    public void setScaleFactor(float scaleFactor) {
        this.scaleFactor = scaleFactor;
    }
}
