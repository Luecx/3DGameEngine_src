package projects.vier_gewinnt.logic.server.server;

import org.lwjgl.util.vector.Vector3f;
import org.lwjgl.util.vector.Vector4f;
import projects.vier_gewinnt.logic.server.GameCube;

import java.util.ArrayList;

/**
 * Created by finne on 28.03.2018.
 */
public class Game {


    public final int players;
    public int currentPlayer;
    public final int size;
    public final int[][][] values;

    private GameCube gameCube;

    public Game(int size, int players) {
        this.players = players;
        this.size = size;
        this.values = new int[size][size][size];
        this.currentPlayer = 0;
        for(int i = 0; i < size; i++){
            for(int n = 0; n < size; n++){
                for(int j = 0; j < size; j++){
                    values[i][n][j] = -1;
                }
            }
        }
    }

    public void nextPlayer() {
        this.currentPlayer ++;
        this.currentPlayer = this.currentPlayer % players;
    }

    private int winnerInLine(ArrayList<Vector4f> row){
        int cur = -1;
        int counter = 1;
        for(Vector4f i:row){
            if((int)i.getW() == cur && cur != -1){
                counter ++;
            }else{
                cur = (int)i.getW();
                counter = 1;
            }
            if(counter == 5){
                return cur;
            }
        }
        return -1;
    }
    private ArrayList<Vector3f> winningPositions(ArrayList<Vector4f> row){
        int cur = -1;
        int counter = 1;
        ArrayList<Vector4f> vector4fs = new ArrayList<>();
        for(Vector4f i:row){
            if((int)i.getW() == cur && cur != -1){
                counter ++;
                vector4fs.add(i);
            }else{
                vector4fs.clear();
                cur = (int)i.getW();
                vector4fs.add(i);
                counter = 1;
            }
            if(counter == 5){
                ArrayList<Vector3f> vector3fs = new ArrayList<>();
                for(Vector4f v:vector4fs){
                    vector3fs.add(new Vector3f(v.x,v.y,v.z));
                }
                return vector3fs;
            }
        }
        return null;
    }
    private void addRow(ArrayList<Vector4f> arrayList, int index){
        for(int i = 0; i < this.size; i++) {
            for(int n = 0; n < this.size; n++) {
                for(int k = 0; k < this.size; k++){
                    if(index == 1){
                        arrayList.add(new Vector4f(i,k,n,values[i][k][n]));
                    }else if(index == 2){
                        arrayList.add(new Vector4f(i,n,k,values[i][n][k]));
                    }else{
                        arrayList.add(new Vector4f(k,i,n,values[k][i][n]));
                    }
                }
                arrayList.add(new Vector4f(-1,-1,-1,-1));
            }
        }
    }
    private void addDiagonal2DY(ArrayList<Vector4f> arrayList){
        for(int i = 0; i < this.size; i++) {
            for(int j = - this.size + 1; j < this.size; j++){
                int x = j;
                int h = 0;
                while(h < this.size){
                    if(x >= 0 && x < this.size){
                        arrayList.add(new Vector4f(x,i,h,values[x][i][h]));
                    }
                    x ++;
                    h ++;
                }
                arrayList.add(new Vector4f(-1,-1,-1,-1));
            }
            for(int j = 0; j < this.size + this.size - 1; j++){
                int x = 0;
                int h = j;
                while(x < this.size){
                    if(h >= 0 && h < this.size){
                        arrayList.add(new Vector4f(x,i,h,values[x][i][h]));
                    }
                    x ++;
                    h --;
                }
                arrayList.add(new Vector4f(-1,-1,-1,-1));
            }
        }
    }
    private void addDiagonal2DX(ArrayList<Vector4f> arrayList){
        for(int i = 0; i < this.size; i++) {
            for(int j = - this.size + 1; j < this.size; j++){
                int x = j;
                int h = 0;
                while(h < this.size){
                    if(x >= 0 && x < this.size){
                        arrayList.add(new Vector4f(i,x,h,values[i][x][h]));
                    }
                    x ++;
                    h ++;
                }
                arrayList.add(new Vector4f(-1,-1,-1,-1));
            }
            for(int j = 0; j < this.size + this.size - 1; j++){
                int x = 0;
                int h = j;
                while(x < this.size){
                    if(h >= 0 && h < this.size){
                        arrayList.add(new Vector4f(i,x,h,values[i][x][h]));
                    }
                    x ++;
                    h --;
                }
                arrayList.add(new Vector4f(-1,-1,-1,-1));
            }
        }
    }
    private void addDiagonal2DZ(ArrayList<Vector4f> arrayList){
        for(int i = 0; i < this.size; i++) {
            for(int j = - this.size + 1; j < this.size; j++){
                int x = j;
                int h = 0;
                while(h < this.size){
                    if(x >= 0 && x < this.size){
                        arrayList.add(new Vector4f(x,h,i,values[x][h][i]));
                    }
                    x ++;
                    h ++;
                }
                arrayList.add(new Vector4f(-1,-1,-1,-1));
            }
            for(int j = 0; j < this.size + this.size - 1; j++){
                int x = 0;
                int h = j;
                while(x < this.size){
                    if(h >= 0 && h < this.size){
                        arrayList.add(new Vector4f(x,h,i,values[x][h][i]));
                    }
                    x ++;
                    h --;
                }
                arrayList.add(new Vector4f(-1,-1,-1,-1));
            }
        }
    }
    private void addDiagonal3D(ArrayList<Vector4f> arrayList) {
        for(int x = - (size -1); x < (size - 1); x++){
            for(int z = - (size -1); z < (size - 1); z++){
                Vector3f pos = new Vector3f(x,0,z);
                while(pos.y < size){
                    if(pos.x >= 0 && pos.x < size && pos.z >= 0 && pos.z < size){
                        arrayList.add(new Vector4f(
                                pos.x, pos.y, pos.z,values[(int)pos.x][(int)pos.y][(int)pos.z]));
                    }
                    pos.x ++;
                    pos.y ++;
                    pos.z ++;
                }
                arrayList.add(new Vector4f(-1,-1,-1,-1));
            }
        }
        for(int x = 0; x < (size * 2 - 1); x++){
            for(int z = - (size -1); z < (size - 1); z++){
                Vector3f pos = new Vector3f(x,0,z);
                while(pos.y < size){
                    if(pos.x >= 0 && pos.x < size && pos.z >= 0 && pos.z < size){
                        arrayList.add(new Vector4f(
                                pos.x, pos.y, pos.z,values[(int)pos.x][(int)pos.y][(int)pos.z]));
                    }
                    pos.x --;
                    pos.y ++;
                    pos.z ++;
                }
                arrayList.add(new Vector4f(-1,-1,-1,-1));
            }
        }for(int x = - (size -1); x < (size - 1); x++){
            for(int z = 0; z < (size * 2 - 1); z++){
                Vector3f pos = new Vector3f(x,0,z);
                while(pos.y < size){
                    if(pos.x >= 0 && pos.x < size && pos.z >= 0 && pos.z < size){
                        arrayList.add(new Vector4f(
                                pos.x, pos.y, pos.z,values[(int)pos.x][(int)pos.y][(int)pos.z]));
                    }
                    pos.x ++;
                    pos.y ++;
                    pos.z --;
                }
                arrayList.add(new Vector4f(-1,-1,-1,-1));
            }
        }for(int x = 0; x < (size * 2 - 1); x++){
            for(int z = 0; z < (size * 2 - 1); z++){
                Vector3f pos = new Vector3f(x,0,z);
                while(pos.y < size){
                    if(pos.x >= 0 && pos.x < size && pos.z >= 0 && pos.z < size){
                        arrayList.add(new Vector4f(
                                pos.x, pos.y, pos.z,values[(int)pos.x][(int)pos.y][(int)pos.z]));
                    }
                    pos.x --;
                    pos.y ++;
                    pos.z --;
                }
                arrayList.add(new Vector4f(-1,-1,-1,-1));
            }
        }
    }
    public int winnerWinnerChickenDinner(){
        ArrayList<Vector4f> arrayList = new ArrayList<>();
        addRow(arrayList, 0);
        addRow(arrayList, 1);
        addRow(arrayList, 2);
        addDiagonal2DX(arrayList);
        addDiagonal2DY(arrayList);
        addDiagonal2DZ(arrayList);
        addDiagonal3D(arrayList);
        return winnerInLine(arrayList);
    }
    
    public int getPlayers() {
        return players;
    }

    public int getCurrentPlayer() {
        return currentPlayer;
    }

    public int getSize() {
        return size;
    }

    public int[][][] getValues() {
        return values;
    }

    public GameCube getGameCube() {
        return gameCube;
    }

    public void setGameCube(GameCube gameCube) {
        this.gameCube = gameCube;
    }
}
