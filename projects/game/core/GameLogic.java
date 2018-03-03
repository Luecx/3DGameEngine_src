package projects.game.core;

import projects.game.solarsystem.SolarSystem;

/**
 * Created by Luecx on 28.02.2017.
 */
public class GameLogic {

    private final int teamsAmount;
    private final int playerEach;

    private Team[] teams;
    private SolarSystem solarSystem;
    private SpaceStation spaceStations[];

    public GameLogic(int teamsAmount, int playerEach, SolarSystem solarSystem) {
        this.teamsAmount = teamsAmount;
        this.playerEach = playerEach;
        teams = new Team[teamsAmount];
        spaceStations = new SpaceStation[teamsAmount];
        this.solarSystem = solarSystem;
        for(int i = 0; i < teams.length; i++){
            System.out.println(solarSystem.getPlanets().size());
            teams[i] = new Team(i, playerEach);
            spaceStations[i] = new SpaceStation(solarSystem.getPlanets().get(i), 100);
            solarSystem.getPlanets().get(i).addChild(spaceStations[i]);
        }
    }

    public int getTeamsAmount() {
        return teamsAmount;
    }

    public int getPlayerEach() {
        return playerEach;
    }

    public Team[] getTeams() {
        return teams;
    }

    String info() {
        String s = new String();
        for(Team t:teams){
            s+= t.info() +"\n";
        }
        return s;
    }

}
