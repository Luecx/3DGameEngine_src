package projects.game.core;

/**
 * Created by Luecx on 28.02.2017.
 */
public class Player {

    private final int playerIndex;        //Index des Spielers im Team [0,1,...,playerEachTeam - 1]
    private final int globalIndex;        //Index des Spielers unter allen Spielern [0,1,...,playerEachTeam * amountOfTeams -1]
    private final Team team;

    public Player(Team t, int playerIndex, int globalIndex) {
        this.team = t;
        this.playerIndex = playerIndex;
        this.globalIndex = globalIndex;
    }

    public int getPlayerIndex() {
        return playerIndex;
    }

    public int getGlobalIndex() {
        return globalIndex;
    }

    String info() {
        return new String("playerIndex: " + playerIndex + "    globalIndex: " + globalIndex);
    }
}
