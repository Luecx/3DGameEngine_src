package engine.linear.terrain;

import java.util.Random;

public class HeightsGenerator {

    private int seed;
    private final float AMPLITUDE;
    private final int OCTAVES;
    private final float ROUGHNESS;
    private final float STRETCH;

    private Random random = new Random();

    public HeightsGenerator(int seed, float aMPLITUDE, int oCTAVES, float rOUGHNESS, float sTRETCH) {
        super();
        this.seed = seed;
        AMPLITUDE = aMPLITUDE;
        OCTAVES = oCTAVES;
        ROUGHNESS = rOUGHNESS;
        STRETCH = sTRETCH;
    }

    public float generateHeight(float x, float z) {
        float total = 0;
        float d = (float) Math.pow(2, OCTAVES-1);
        for(int i=0;i<OCTAVES;i++){
            float freq = (float) (Math.pow(2, i) / d);
            float amp = (float) Math.pow(ROUGHNESS, i) * AMPLITUDE;
            total += getInterpolatedNoise((x)*freq, (z)*freq) * amp;
        }
        return total;
    }

    public float getInterpolatedNoise(float x, float z){
        x = x/STRETCH;
        z = z/STRETCH;
        int intX = (int) x;
        int intZ = (int) z;
        float fracX = x - intX;
        float fracZ = z - intZ;

        float v1 = getSmoothNoise(intX, intZ);
        float v2 = getSmoothNoise(intX + 1, intZ);
        float v3 = getSmoothNoise(intX, intZ + 1);
        float v4 = getSmoothNoise(intX + 1, intZ + 1);
        float i1 = interpolate(v1, v2, fracX);
        float i2 = interpolate(v3, v4, fracX);
        return interpolate(i1, i2, fracZ);
    }

    public static float interpolate(float a, float b, float blend){
        double theta = blend * Math.PI;
        float f = (float)(1f - Math.cos(theta)) * 0.5f;
        return a * (1f - f) + b * f;
    }

    public float getSmoothNoise(int x, int z) {
        float corners = (getNoise(x - 1, z - 1) + getNoise(x + 1, z - 1) + getNoise(x - 1, z + 1)
                + getNoise(x + 1, z + 1)) / 16f;
        float sides = (getNoise(x - 1, z) + getNoise(x + 1, z) + getNoise(x, z - 1)
                + getNoise(x, z + 1)) / 8f;
        float center = getNoise(x, z) / 4f;
        return corners + sides + center;
    }

    public float getNoise(int x, int z) {
        random.setSeed(x * 49632 + z * 325176 + seed);
        return random.nextFloat() * 2f - 1f;
    }


}