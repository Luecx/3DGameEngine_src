package projects.jet_game.server;

import engine.core.components.GroupableGameObject;
import server.server.Server;
import templates.gameserver_v1.Game.UDPTransmittedGame;
import udp.server_side.UDPClientInformation;

import java.util.ArrayList;

public class ServerController extends UDPTransmittedGame<ServerIn, ServerOut> {

    ArrayList<PlayerData> playerData = new ArrayList<>();

    @Override
    public void new_player(UDPClientInformation udpClientInformation) {
        playerData.add(new PlayerData(udpClientInformation.getId()));
    }

    @Override
    public void remove_player(UDPClientInformation udpClientInformation) {
        for(int i = 0; i < playerData.size(); i++){
            if(playerData.get(i).getId() == udpClientInformation.getId()){
                playerData.remove(i);
            }
        }
    }

    @Override
    public void process_controls(double v) {

    }

    @Override
    public ServerOut getUpdatedPositions() {
        return new ServerOut(playerData.toArray(new PlayerData[playerData.size()]));
    }
}
