package projects.newgame.logic.game;

import engine.core.components.Group;
import engine.linear.advancedterrain.Chunk;
import engine.linear.advancedterrain.HeightMap;
import engine.linear.advancedterrain.Terrain;
import projects.game.core.GameCore;
import projects.newgame.logic.game.playerlist.PlayerList;
import projects.newgame.logic.game.playerlist.PlayerListEntry;
import projects.newgame.logic.server.Lobby;
import projects.newgame.logic.server.ServerClient;
import projects.newgame.newcrafts.jets.JetSystem;
import projects.newgame.newcrafts.jets.ServerJet;
import udpserver.Server;

import java.io.IOException;

/**
 * Created by finne on 26.09.2017.
 */
public class ServerGame extends GameCore {

    public ServerGame(String map, Lobby lobby, Server server) {
        super(GameCore.MODE_128_TICK);

        this.server = server;

        Group center = new Group();
        Group pos = new Group((float) Math.pow(2,12), 1024,0);
        center.addChild(pos);

        for(int i = 0;i < lobby.getTeams(); i++) {
            for(int n = 0; n < lobby.getPlayer(); n++) {

                ServerClient c = lobby.getClients()[i][n];
                System.out.println(c);
                ServerJet jet = JetSystem.SERVER_generate_plane(c.getLoadout().getJet(), c.getLoadout().getMissile(), c.getLoadout().getGun());

                center.increaseRotation(0,1,0);

                playerList.getList().add(new PlayerListEntry<>(jet, c));
            }
            center.increaseRotation(0,360 / lobby.getTeams() - lobby.getPlayer(), 0);
        }

        terrain = new Terrain();
        Chunk c = new Chunk((float)-Math.pow(2,13),(float)-Math.pow(2,13),11,8);
        HeightMap heightMap = new HeightMap(11,1);
        heightMap.setScaleFactor(1000);
        heightMap.convertImageToData("textures/heightmaps/canH");
        c.setHeights(heightMap.getHeights());
        terrain.getChunks().add(c);

        this.start();
        this.setRunning(true);
    }

    private PlayerList<ServerJet, ServerClient> playerList = new PlayerList<>();
    private Terrain terrain;
    private Server server;

    boolean freeze = true;

    public boolean isFreeze() {
        return freeze;
    }

    public void setFreeze(boolean freeze) {
        this.freeze = freeze;
    }

    @Override
    protected void update() {
        double time = this.getProcessedTime();
        for(PlayerListEntry<ServerJet, ServerClient> e:playerList.getList()) {
            if(e.getClient().getUDP_CONNECTION() != null) {
                System.out.println("---------");
                try {
                    server.send(e.getClient().getUDP_CONNECTION(), this.getPlayerList());
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        }
        if(!freeze){
            for(PlayerListEntry<ServerJet, ServerClient> entry:playerList.getList()){
                entry.getJet().process(time, entry.getController());
            }
        }
    }

    public PlayerList<ServerJet, ServerClient> getPlayerList() {
        return playerList;
    }

    public Terrain getTerrain() {
        return terrain;
    }

}
