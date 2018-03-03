package projects.newgame.logic.client;

import java.io.Serializable;

/**
 * Created by finne on 25.09.2017.
 */
public class Client implements Serializable {

    protected String name;
    protected int teamIndex = -1;
    protected int playerIndex = -1;
    protected Loadout loadout;

    protected boolean admin;
    protected boolean ready;


    public Client(Client c) {
        if(c.name != null) {
            this.name = new String(c.name);
        } if(c.loadout != null) {
            this.loadout = new Loadout(c.loadout);
        }
        this.teamIndex = c.teamIndex;
        this.playerIndex = c.playerIndex;
        this.admin = c.admin;
        this.ready = c.ready;
    }
    public Client() {

    }

    public void load(Client c) {
        if(c.name != null) {
            this.name = new String(c.name);
        } if(c.loadout != null) {
            this.loadout = new Loadout(c.loadout);
        }
        this.teamIndex = c.teamIndex;
        this.playerIndex = c.playerIndex;
        this.admin = c.admin;
        this.ready = c.ready;
    }

    public Client(String name, Loadout loadout, boolean admin, boolean ready) {
        this.name = name;
        this.loadout = loadout;
        this.admin = admin;
        this.ready = ready;
    }

    @Override
    public String toString() {
        return "Client{" +
                "name='" + name + '\'' +
                ", teamIndex=" + teamIndex +
                ", playerIndex=" + playerIndex +
                ", admin=" + admin +
                ", ready=" + ready +
                '}';
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getTeamIndex() {
        return teamIndex;
    }

    public void setTeamIndex(int teamIndex) {
        this.teamIndex = teamIndex;
    }

    public int getPlayerIndex() {
        return playerIndex;
    }

    public void setPlayerIndex(int playerIndex) {
        this.playerIndex = playerIndex;
    }

    public boolean isAdmin() {
        return admin;
    }

    public void setAdmin(boolean admin) {
        this.admin = admin;
    }

    public boolean isReady() {
        return ready;
    }

    public void setReady(boolean ready) {
        this.ready = ready;
    }

    public Loadout getLoadout() {
        return loadout;
    }

    public void setLoadout(Loadout loadout) {
        this.loadout = loadout;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Client client = (Client) o;

        if (teamIndex != client.teamIndex) return false;
        if (playerIndex != client.playerIndex) return false;
        if (admin != client.admin) return false;
        if (ready != client.ready) return false;
        if (name != null ? !name.equals(client.name) : client.name != null) return false;
        return loadout != null ? loadout.equals(client.loadout) : client.loadout == null;
    }

    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + teamIndex;
        result = 31 * result + playerIndex;
        result = 31 * result + (loadout != null ? loadout.hashCode() : 0);
        result = 31 * result + (admin ? 1 : 0);
        result = 31 * result + (ready ? 1 : 0);
        return result;
    }
}
