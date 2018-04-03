package projects.vier_gewinnt_v2.logic;

import org.lwjgl.util.vector.Vector3f;
import org.lwjgl.util.vector.Vector4f;

import java.util.ArrayList;

/**
 * Created by finne on 31.03.2018.
 */
public class GameMap {

    protected ArrayList<Vector3i> placements = new ArrayList<Vector3i>();
    protected int[][][] values;
    protected int size;
    protected int activePlayerID = 0;
    protected int playerAmount = 0;

    public GameMap(int size) {
        this.size = size;
        values = new int[size][size][size];

        for (int i = 0; i < size; i++) {
            for (int n = 0; n < size; n++) {
                for (int j = 0; j < size; j++) {
                    values[i][n][j] = -1;
                }
            }
        }
    }

    public GameMap(int size, int maxPlayer) {
        this.size = size;
        this.playerAmount = maxPlayer;
        values = new int[size][size][size];

        for (int i = 0; i < size; i++) {
            for (int n = 0; n < size; n++) {
                for (int j = 0; j < size; j++) {
                    values[i][n][j] = -1;
                }
            }
        }
    }

    public void nextPlayer() {
        activePlayerID++;
        activePlayerID = activePlayerID % playerAmount;
    }

    public void place(int x, int y, int z, int id) {
        values[x][y][z] = id;
        placements.add(new Vector3i(x, y, z));
    }

    public void undoPlace() {
        values[(int) placements.get(placements.size() - 1).x]
                [(int) placements.get(placements.size() - 1).y]
                [(int) placements.get(placements.size() - 1).z] = -1;
        placements.remove(placements.size() - 1);
    }

    public int[] getEvaluation() {
        int[] ret = new int[playerAmount];
        for (int i = 0; i < this.playerAmount; i++) {
            ret[i] = getEvaluationIndex(i).x;
        }
        return ret;
    }

    public int getWinnerID() {
        for (int i = 0; i < this.playerAmount; i++) {
            if (getEvaluationIndex(i).y >= 5) {
                return i;
            }
        }
        return -1;
    }

    public ArrayList<Vector3i> getWinningPosition() {
        ArrayList<Vector3i> vector3is = new ArrayList<>();
        for (int index = 0; index < this.playerAmount; index++) {

            for (int x = 0; x < size; x++) {
                for (int y = 0; y < size; y++) {
                    for (int z = 0; z < size; z++) {
                        if (values[x][y][z] == index) {
                            for (int i = -1; i < 1; i++) {
                                for (int j = -1; j < 2; j++) {
                                    for (int k = -1; k < 2; k++) {
                                        if (i != 0 || (i == 0 && j == 1) || (i == 0 && j == 0 && k == -1)) {
                                            if (getValue(x + i, y + j, z + k) != index && getValue(x - i, y - j, z - k) == index) {
                                                int c1 = x;
                                                int c2 = y;
                                                int c3 = z;
                                                vector3is.add(new Vector3i(c1, c2, c3));
                                                int amount = 0;
                                                while (getValue(c1 - i, c2 - j, c3 - k) == index) {
                                                    c1 -= i;
                                                    c2 -= j;
                                                    c3 -= k;
                                                    vector3is.add(new Vector3i(c1, c2, c3));
                                                    amount++;
                                                }
                                                if (amount >= 4) {
                                                    return vector3is;
                                                } else {
                                                    vector3is.clear();
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        return vector3is;
    }

    public int getValue(int x, int y, int z) {
        if (x < 0 || x >= size || y < 0 || y >= size || z < 0 || z >= size) {
            return -2;
        }
        return values[x][y][z];
    }

    private Vector3i getEvaluationIndex(int index) {
        int number = 0;
        int max = 0;
        for (int x = 0; x < size; x++) {
            for (int y = 0; y < size; y++) {
                for (int z = 0; z < size; z++) {
                    if (values[x][y][z] == index) {
                        for (int i = -1; i < 1; i++) {
                            for (int j = -1; j < 2; j++) {
                                for (int k = -1; k < 2; k++) {
                                    if (i != 0 || (i == 0 && j == 1) || (i == 0 && j == 0 && k == -1)) {
                                        if (getValue(x + i, y + j, z + k) != index && getValue(x - i, y - j, z - k) == index) {
                                            int c1 = x;
                                            int c2 = y;
                                            int c3 = z;
                                            int amount = 1;
                                            while (getValue(c1 - i, c2 - j, c3 - k) == index) {
                                                c1 -= i;
                                                c2 -= j;
                                                c3 -= k;
                                                amount++;
                                                if (amount > max) {
                                                    max = amount;
                                                }
                                            }

                                            int freeSpaces = 0;
                                            while (getValue(c1 - i, c2 - j, c3 - k) == -1 ||
                                                    getValue(c1 - i, c2 - j, c3 - k) == index) {
                                                c1 -= i;
                                                c2 -= j;
                                                c3 -= k;
                                                freeSpaces++;
                                            }
                                            c1 = x;
                                            c2 = y;
                                            c3 = z;
                                            while (getValue(c1 + i, c2 + j, c3 + k) == -1 ||
                                                    getValue(c1 + i, c2 + j, c3 + k) == index) {
                                                c1 += i;
                                                c2 += j;
                                                c3 += k;
                                                freeSpaces++;
                                            }

                                            if(amount + freeSpaces >= 5){
                                                number += Math.pow(10, amount);
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        return new Vector3i(number, max, 0);
    }


    public int[][][] getValues() {
        return values;
    }

    public int getSize() {
        return size;
    }

    public int getActivePlayerID() {
        return activePlayerID;
    }

    public int getPlayerAmount() {
        return playerAmount;
    }

    public ArrayList<Vector3i> getPlacements() {
        return placements;
    }
}
