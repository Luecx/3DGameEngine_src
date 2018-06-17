package projects.jet_game.server;

import projects.wot_mini.TestServer;
import templates.gameserver_v1.Game.UDPTransmittedGame;
import templates.gameserver_v1.UDPGameServer;

import java.net.SocketException;
import java.util.Scanner;

public class GameServer extends UDPGameServer<ServerIn, ServerOut> {
    public GameServer() throws SocketException {
        super(new ServerController(), GameServer.REFRESH_RATE_100, 55555);
    }

    public static void main(String[] args) throws Exception {
        GameServer server= new GameServer();
        Scanner sc = new Scanner(System.in);

        sc.next();
        server.close();
        sc.close();
    }
}
