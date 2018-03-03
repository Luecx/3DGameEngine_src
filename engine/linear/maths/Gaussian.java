package engine.linear.maths;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;
import java.util.TreeMap;

/**
 * Created by Luecx on 08.02.2017.
 */
public class Gaussian {

    public static float[] gaussian(float[][] matrix){
        if(matrix.length != matrix[0].length - 1) return null;

        int size = matrix.length;
        for(int i = 1; i < size; i++){
            for(int n = size-1; n >= i; n--){
                if(!createZero(matrix, n, i-1, i - 1)) return null;
            }
        }
        float[] out = new float[size];
        out[size - 1] = matrix[size-1][size] / matrix[size-1][size-1];
        for(int i = size-2; i >= 0; i--){
            float val = matrix[i][size];
            for(int n = size-2; n >= i; n--){
                val -= out[n+1] * matrix[i][n+1];
            }
            out[i] = val / matrix[i][i];
        }
        return out;
    }

    public static int amountOfZeros(float[] row){
        int c = 0;
        for(float f:row){
            if(f == 0) c ++;
        }
        return c;
    }

    public static void sortMatrix(float[][] matrix) {
        Map<Integer, ArrayList<float[]>> rows = new TreeMap<>();
        for(float[] row:matrix){
            Integer zeroes = amountOfZeros(row);
            if(rows.containsKey(zeroes)){
                rows.get(zeroes).add(row);
            }else{
                ArrayList<float[]> ar = new ArrayList<>();
                ar.add(row);
                rows.put(zeroes, ar);
            }
        }
        int index = 0;
        for (Map.Entry<Integer, ArrayList<float[]>> entry : rows.entrySet()) {
            for(float[] r:entry.getValue()){
                matrix[index] = r;
                index ++;
            }
        }
    }

    public static void main(String[] args){
        float[][] matrix = new float[][]{
                {1,1,4,6,4},
                {2,3,1,3,2},
                {3,2,1,5,1},
                {4,3,1,2,1}};
        System.out.println(Arrays.toString(gaussian(matrix)));
    }

    public static void printMatrix(float[][] matrix){
        for(float[] m: matrix){
            System.out.println(Arrays.toString(m));
        }
        System.out.println();
    }

    public static float[] extractRow(float[][] matrix, int row){
        float[] out = new float[matrix.length];
        for(int i = 0; i < matrix.length; i++){
            out[i] = matrix[row][i];
        }
        return out;
    }

    /**
     * substracts the row [source], multiplied by factor, from the matrix of the destination row of the matrix.
     * @param source
     * @param dest
     * @param factor
     */
    public static void subSourceFromDestination(float[][] matrix, int dest, int source, float factor) {
        for(int i = 0; i < matrix[dest].length;i ++){
            matrix[dest][i] -= matrix[source][i] * factor;
        }
    }

    /**
     * changed the destination row in the matrix as follows:
     * the first [depth + 1] digits will be zero. (depth starts with 0)
     * this is done by using the source row and subtracting that one from the destination row
     * This only works if the first [depth-1] numbers are already 0 of both rows.
     * @param matrix
     * @param source
     * @param destination
     * @param depth
     * @return
     */
    public static boolean createZero(float[][] matrix, int destination, int source, int depth) {
        float sc = matrix[source][depth];
        float de = matrix[destination][depth];
        if(sc == 0 && de != 0) return false;
        if(de == 0) return true;
        subSourceFromDestination(matrix, destination, source, de / sc);
        return true;
    }

}
