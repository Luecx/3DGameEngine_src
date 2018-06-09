package projects.jet_game.server;

import templates.gameserver_v1.Game.UDPTransmittedGame;
import templates.gameserver_v1.UDPGameServer;

import java.net.SocketException;

public class GameServer extends UDPGameServer<ServerIn, ServerOut> {
    public GameServer() throws SocketException {
        super(new ServerController(), GameServer.REFRESH_RATE_100, 55555);
    }
}
