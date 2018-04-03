package projects.vier_gewinnt_v2.logic;

import org.lwjgl.util.vector.Vector3f;
import projects.vier_gewinnt_v2.visual.Core;
import projects.vier_gewinnt_v2.visual.GameCube;

import java.lang.instrument.Instrumentation;
import java.util.*;

/**
 * Created by finne on 01.04.2018.
 */
public class GameKI {

    private GameMap gameMap;

    public GameKI(GameMap gameMap) {
        this.gameMap = gameMap;
    }

    public GameMap getGameMap() {
        return gameMap;
    }

    public void setGameMap(GameMap gameMap) {
        this.gameMap = gameMap;
    }



    private int depth;
    private int players;
    private Vector3i move;

    public Vector3i bestMove(int playerID, int depth) {
        System.out.println("CURRENT EVALUATION: " + Arrays.toString(gameMap.getEvaluation()));
        this.depth = depth;
        this.players = gameMap.getPlayerAmount();
        move = null;
        max(depth, playerID);
        return move;
    }

    public int[] max (int depth, int player) {
        HashSet<Vector3i> vector3is = possibleMoves();
        if(depth == 0 || vector3is.size() == 0){
            int[] ret = gameMap.getEvaluation();
            return ret;
        }

        int[] val = new int[players];
        int diff = -10000000;
        for(Vector3i v:vector3is){
            gameMap.place((int)v.x,(int)v.y,(int)v.z, player);
            int nextID = (player + 1) % players;
            int[] ar = max(depth-1, nextID);

            int diffN = 0;
            for(int i = 0; i < ar.length; i++) {
                if(i == player){
                    diffN += ar[i];
                }else{
                    diffN -= (int)(1.1 * ar[i]);
                }
            }

            if(depth == this.depth) {
                System.out.println(v + "  " + Arrays.toString(ar) + "      DiffN: " + diffN);
            }

            if(diffN >= diff) {
                if(depth == this.depth)  {
                    move = v;
                }
                diff = diffN;
                val = ar;
            }
            gameMap.undoPlace();
        }
        return val;
    }

    public HashSet<Vector3i> possibleMoves() {
        HashSet<Vector3i> moves = new HashSet<>();

        for(Vector3i vec: gameMap.placements) {
                for(int i = Math.max(0,vec.x-1); i < Math.min(gameMap.size, vec.x + 2) ; i++){
                    for(int j = Math.max(0,vec.y-1); j < Math.min(gameMap.size, vec.y + 2) ; j++){
                        for(int k = Math.max(0,vec.z-1); k < Math.min(gameMap.size, vec.z + 2) ; k++){
                            if(gameMap.values[i][j][k] == -1){
                                moves.add(new Vector3i(i,j,k));
                            }
                        }
                    }
                }
        }
        return moves;
    }

    public static void main(String[] args) {





        GameMap map = new GameMap(8,3);

        map.place(0,1,0,0);
        map.place(1,1,0,0);
        map.place(2,1,0,0);
        map.place(3,1,0,0);
        map.place(4,1,0,0);

        System.out.println(map.getWinningPosition());


        GameKI ki = new GameKI(map);

        Vector3i bestMove;
        bestMove = ki.bestMove(1,3);
        System.out.println(bestMove);
        map.place((int)bestMove.x,(int)bestMove.y,(int)bestMove.z, 1);

        bestMove = ki.bestMove(2,3);
        System.out.println(bestMove);
        map.place((int)bestMove.x,(int)bestMove.y,(int)bestMove.z, 2);

    }




}
