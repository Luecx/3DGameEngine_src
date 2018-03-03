package projects.newgame.logic.client;


import projects.newgame.logic.server.Lobby;
import java.io.Serializable;

/**
 * Created by finne on 25.09.2017.
 */
public class ClientLobby implements Serializable{

    private int teams;
    private int player;

    private Client[][] clients;

    public void load(ClientLobby lobby) {
        this.teams = lobby.getTeams();
        this.player = lobby.getPlayer();
        this.clients = lobby.clients;
    }

    public ClientLobby() {
    }

    public ClientLobby(Lobby lobby) {
        this.teams = lobby.getTeams();
        this.player = lobby.getPlayer();
        clients = new Client[teams][player];
        for(int i = 0; i < teams; i++) {
            for(int n = 0; n<  player; n++) {
                if(lobby.getClients()[i][n] != null) {
                    clients[i][n] = new Client(lobby.getClients()[i][n]);
                }
            }
        }
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

    public int getTeams() {
        return teams;
    }

    public void setTeams(int teams) {
        this.teams = teams;
    }

    public int getPlayer() {
        return player;
    }

    public void setPlayer(int player) {
        this.player = player;
    }

    public Client[][] getClients() {
        return clients;
    }

    public void setClients(Client[][] clients) {
        this.clients = clients;
    }
}
