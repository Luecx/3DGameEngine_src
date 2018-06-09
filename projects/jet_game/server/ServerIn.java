package projects.jet_game.server;

import udp.udp_content.UDPContent;

public class ServerIn extends UDPContent {

    PlayerData playerData;

    public ServerIn(PlayerData playerData) {
        this.playerData = playerData;
    }

    public PlayerData getPlayerData() {
        return playerData;
    }

    public void setPlayerData(PlayerData playerData) {
        this.playerData = playerData;
    }
}
