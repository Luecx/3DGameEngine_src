package projects.wot_mini;

import templates.gameserver_v1.Game.UDPTransmittedGame;
import templates.gameserver_v1.UDPGameServer;

import java.net.SocketException;
import java.util.Scanner;

public class TestServer extends UDPGameServer<ServerInput, ServerOutput> {

    public TestServer() throws SocketException {
        super(new TestGame(), 10, 12345);
    }

    public static void main(String[] args) throws Exception {
        TestServer server= new TestServer();
        Scanner sc = new Scanner(System.in);

        sc.next();

        server.close();

        sc.close();


    }
}
