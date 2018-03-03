package projects.newgame.logic.server;

import server.connection.Connection;
import server.server.Server;

import java.util.Arrays;

/**
 * Created by finne on 25.09.2017.
 */
public class Lobby {
    private int teams;
    private int player;

    private ServerClient[][] clients;

    @Override
    public String toString() {
        return "Lobby{" +
                "teams=" + teams +
                ", player=" + player +
                ", clients=" + Arrays.deepToString(clients) +
                '}';
    }

    public void print() {
        for(int i = 0; i < teams; i++) {
            System.out.println("\nTeam "+ (i + 1));
            for(int n = 0; n < player; n++) {
                if(clients[i][n] != null) {
                    System.out.println("    name= "+ clients[i][n].getName() + "    ready= "+clients[i][n].isReady()+ "    admin= "+clients[i][n].isAdmin());
                }
            }
        }
    }

    public Lobby(int teams, int player) {
        this.teams = teams;
        this.player = player;
        clients = new ServerClient[teams][player];
    }

    public boolean addClient(ServerClient c) {
        int min = player;
        int index = -1;
        for(int i = 0; i < teams; i++) {
            inner:
            for(int n = 0; n < player; n++) {
                if(n < min && clients[i][n] == null){
                    min = n;
                    index = i;
                }
            }
        }
        if(index != -1){
            clients[index][min] = c;
            c.setPlayerIndex(min);
            c.setTeamIndex(index);
            return true;
        }
        return false;
    }

    public int getTeams() {
        return teams;
    }

    public int getPlayer() {
        return player;
    }

    public ServerClient[][] getClients() {
        return clients;
    }

    public ServerClient getClient(Connection<Server> connection) {
        for(int i = 0; i < teams; i++) {
            for(int n = 0; n < player; n++) {
                if(clients[i][n] != null && clients[i][n].getTCP_CONNECTION() == connection) return clients[i][n];
            }
        }
        return null;
    }

    public boolean isFull() {
        for(int i = 0; i < teams; i++) {
            for(int n = 0; n < player; n++) {
                if(clients[i][n] == null) return false;
            }
        }
        return true;
    }

    public boolean isReady() {
        for(int i = 0; i < teams; i++) {
            for(int n = 0; n < player; n++) {
                if(clients[i][n] == null) return false;
                if(clients[i][n].isReady() == false) return false;
            }
        }
        return true;
    }
}
