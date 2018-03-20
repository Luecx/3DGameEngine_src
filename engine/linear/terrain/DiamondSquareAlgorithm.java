package engine.linear.terrain;

import java.util.Random;

/**
 * Created by Luecx on 07.02.2017.
 */
public class DiamondSquareAlgorithm {


    private int power;
    private long seed;
    private float height;
    private float roughness;

    private Random random;

    public DiamondSquareAlgorithm(int power, long seed, float height, float roughness) {
        this.power = power;
        this.seed = seed;
        this.height = height;
        this.roughness = roughness;
        this.random = new Random();
    }

    private float[][] heights;


    public static void main(String[] args){
        DiamondSquareAlgorithm a = new DiamondSquareAlgorithm(4, 1231231, 20, 0.5f);
        a.generate();
    }

    public static float[][] calc(int DATA_SIZE, float SEED, float h){

        float[][] data = new float[DATA_SIZE][DATA_SIZE];
        //seed the data
        data[0][0] = data[0][DATA_SIZE-1] = data[DATA_SIZE-1][0] =
                data[DATA_SIZE-1][DATA_SIZE-1] = SEED;

        Random r = new Random();//for the new value in range of h
        //side length is distance of a single square side
        //or distance of diagonal in diamond
        for(int sideLength = DATA_SIZE-1;
            //side length must be >= 2 so we always have
            //a new value (if its 1 we overwrite existing values
            //on the last iteration)
            sideLength >= 2;
            //each iteration we are looking at smaller squares
            //diamonds, and we decrease the variation of the offset
            sideLength /=2, h/= 2.0){
            //half the length of the side of a square
            //or distance from diamond center to one corner
            //(just to make calcs below a little clearer)
            int halfSide = sideLength/2;

            //generate the new square values
            for(int x=0;x<DATA_SIZE-1;x+=sideLength){
                for(int y=0;y<DATA_SIZE-1;y+=sideLength){
                    //x, z is upper left corner of square
                    //calculate average of existing corners
                    double avg = data[x][y] + //top left
                            data[x+sideLength][y] +//top right
                            data[x][y+sideLength] + //lower left
                            data[x+sideLength][y+sideLength];//lower right
                    avg /= 4.0;

                    //center is average plus random offset
                    data[x+halfSide][y+halfSide] =
                            (float) (//We calculate random value in range of 2h
                                    //and then subtract h so the end value is
                                    //in the range (-h, +h)
                                    avg + (r.nextDouble()*2*h) - h);
                }
            }

            //generate the diamond values
            //since the diamonds are staggered we only move x
            //by half side
            //NOTE: if the data shouldn't wrap then x < DATA_SIZE
            //to generate the far edge values
            for(int x=0;x<DATA_SIZE-1;x+=halfSide){
                //and z is x offset by half a side, but moved by
                //the full side length
                //NOTE: if the data shouldn't wrap then z < DATA_SIZE
                //to generate the far edge values
                for(int y=(x+halfSide)%sideLength;y<DATA_SIZE-1;y+=sideLength){
                    //x, z is center of diamond
                    //note we must use mod  and add DATA_SIZE for subtraction
                    //so that we can wrap around the array to find the corners
                    double avg =
                            data[(x-halfSide+DATA_SIZE)%DATA_SIZE][y] + //left of center
                                    data[(x+halfSide)%DATA_SIZE][y] + //right of center
                                    data[x][(y+halfSide)%DATA_SIZE] + //below center
                                    data[x][(y-halfSide+DATA_SIZE)%DATA_SIZE]; //above center
                    avg /= 4.0;

                    //new value = average plus random offset
                    //We calculate random value in range of 2h
                    //and then subtract h so the end value is
                    //in the range (-h, +h)
                    avg = avg + (r.nextDouble()*2*h) - h;
                    //update value for center of diamond
                    data[x][y] = (float) avg;

                    //wrap values on the edges, remove
                    //this and adjust loop condition above
                    //for non-wrapping values.
                    if(x == 0)  data[DATA_SIZE-1][y] = (float) avg;
                    if(y == 0)  data[x][DATA_SIZE-1] = (float) avg;
                }
            }
        }
        return data;
    }


    public float[][] generate() {
        int max = (int) Math.pow(2, power);
        heights = new float[max + 1][max + 1];

        heights[0]  [0]     = height;
        heights[max][0]     = height;
        heights[0]  [max]   = height;
        heights[max][max]   = height;

        for(int count = max; count >= 2; count /= 2){
            for(int i = count / 2; i < max; i += count){
                for(int n = count / 2; n < max; n += count){
                    calcDiamond(i, n, count);
                }
            }for(int i = count / 2; i < max; i += count){
                for(int n = count / 2; n < max; n += count){
                    calcSquare(i, n, count);
                }
            }
        }
        return heights;
    }

    private void calcSquare(int x, int y, int count){


        int half = count / 2;

        //Uhrzeigersinn Innen nach Außen
        float[] values = new float[]{
                getHeight(x ,y),                               //0
                getHeight(x + half,y + half),            //1
                getHeight(x - half,y + half),            //2
                getHeight(x - half,y - half),            //3
                getHeight(x + half,y - half),            //4
                getHeight(x ,y + count),                    //5
                getHeight(x + count ,y),                    //6
                getHeight(x ,y - count),                    //7
                getHeight(x - count ,y),                    //8
        };

        heights[x][y + half] = calc(values[0], values[4], values[1], values[5], roughness * getNoise(x, y + half) * count * 0.6f);
        heights[x + half][y] = calc(values[0], values[1], values[2], values[6], roughness * getNoise(x + half, y) * count * 0.6f);
        heights[x][y - half] = calc(values[0], values[2], values[3], values[7], roughness * getNoise(x, y - half) * count * 0.6f);
        heights[x - half][y] = calc(values[0], values[3], values[4], values[8], roughness * getNoise(x - half, y) * count * 0.6f);
    }

    private void calcDiamond(int x, int y, int count){
        heights[x][y] = calc(
                getHeight(x - count / 2,y - count / 2),
                getHeight(x + count / 2,y - count / 2),
                getHeight(x - count / 2,y + count / 2),
                getHeight(x + count / 2,y + count / 2),
                roughness * getNoise(x,y) * count);
    }

    private float getHeight(int x, int y){
        if(x < 0 || x > (int) Math.pow(2, power)|| y < 0 || y > (int) Math.pow(2, power)){
            return 0;
        }
        return heights[x][y];
    }

    private float calc(float a, float b, float c, float d, float offset){
        float am = 4;
        if(a == 0) am--;
        if(b == 0) am--;
        if(c == 0) am--;
        if(d == 0) am--;
        return (a+b+c+d) / am + offset;
    }

    public float getNoise(int x, int z) {
        random.setSeed(x * 49632 + z * 325176 + seed);
        //return (float)Math.random();
        return random.nextFloat() * 2 - 1;
    }



    public int getPower() {
        return power;
    }

    public void setPower(int power) {
        this.power = power;
    }

    public long getSeed() {
        return seed;
    }

    public void setSeed(long seed) {
        this.seed = seed;
    }

    public float getHeight() {
        return height;
    }

    public void setHeight(float height) {
        this.height = height;
    }

    public float getRoughness() {
        return roughness;
    }

    public void setRoughness(float roughness) {
        this.roughness = roughness;
    }
}
