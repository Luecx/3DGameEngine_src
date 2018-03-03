package projects.game.core;

/**
 * Created by Luecx on 28.02.2017.
 */
public class Team {

    private Player[] players;
    private int teamIndex;

    public Team(int teamIndex, int teamSize) {
        this.teamIndex = teamIndex;
        players = new Player[teamSize];
        for(int i = 0; i < teamSize; i++){
            players[i] = new Player(this, i, teamIndex * teamSize + i);
        }
    }

    String info(){
        String s = new String(">>>Team "+ teamIndex+ "<<<");
        for(Player p:players){
            s+= "\n"+p.info() ;
        }
        return s;
    }

    public Player[] getPlayers() {
        return players;
    }

    public int getTeamIndex() {
        return teamIndex;
    }

}
