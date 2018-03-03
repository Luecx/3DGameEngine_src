package engine.core.toolbox;


import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by Luecx on 22.12.2016.
 */
public abstract class ToolboxA {

    public static final double randomValue(double lower, double upper){
        return Math.random() * (upper-lower) - lower;
    }

    public static final double[] randomDoubleArray(double lower, double upper, int size){
        double[] ar = new double[size];
        for(int i = 0; i < ar.length; i++){
            ar[i] = randomValue(lower, upper);
        }
        return ar;
    }

    public static final double[][] randomDoubleArray(double lower, double upper, int x, int y){
        double[][] ar = new double[x][];
        for(int i = 0; i < ar.length; i++){
            ar[i] = randomDoubleArray(lower, upper, y);
        }
        return ar;
    }

    public static final double[][][] randomDoubleArray(double lower, double upper, int x, int y,int z){
        double[][][] ar = new double[x][][];
        for(int i = 0; i < ar.length; i++){
            ar[i] = randomDoubleArray(lower, upper, y,z);
        }
        return ar;
    }

    public static final double[][][][] randomDoubleArray(double lower, double upper, int x, int y,int z, int w){
        double[][][][] ar = new double[x][][][];
        for(int i = 0; i < ar.length; i++){
            ar[i] = randomDoubleArray(lower, upper, y,z, w);
        }
        return ar;
    }

    public static <T extends Comparable<T>> boolean contains(T[] ar, T value){
        for(T a:ar){
            if(a.equals(value)) return true;
        }
        return false;
    }
    public static boolean contains(int[] ar, int i){
        for(int a:ar){
            if(a == i) return true;
        }
        return false;
    }
    public static boolean contains(double[] ar, double i){
        for(double a:ar){
            if(a == i) return true;
        }
        return false;
    }
    public static boolean contains(float[] ar, float i){
        for(float a:ar){
            if(a == i) return true;
        }
        return false;
    }
    public static boolean contains(boolean[] ar, boolean i){
        for(boolean a:ar){
            if(a == i) return true;
        }
        return false;
    }
    public static boolean contains(char[] ar, char i){
        for(char a:ar){
            if(a == i) return true;
        }
        return false;
    }
    public static boolean contains(byte[] ar, byte i){
        for(byte a:ar){
            if(a == i) return true;
        }
        return false;
    }
    public static boolean contains(long[] ar, long i){
        for(long a:ar){
            if(a == i) return true;
        }
        return false;
    }
    public static boolean contains(short[] ar, short i){
        for(short a:ar){
            if(a == i) return true;
        }
        return false;
    }

    public static <T extends Number> T[] cloneNumberArray(T[] ar){
        ArrayList<T> s = new ArrayList<T>();
        for(T g:ar){
            s.add(g);
        }
        return (T[])s.toArray();
    }


    public static <T extends Comparable<T>> int indexOf(T[] ar, T value ){
        int index = 0;
        for(T a:ar){
            if(a.equals(value)) return index;
            index++;
        }
        return -1;
    }

    public static final void printDoubleArray(double[] ar){

    }

    public static void main(String[] args){
        System.out.println(Arrays.toString(randomDoubleArray(0,1,2,2,2,2)[1][1][1]));
    }
}
