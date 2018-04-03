package projects.vier_gewinnt_v2.gui;


import projects.vier_gewinnt_v2.communication.server.GameServer;

import java.io.PrintStream;

public class ServerGui extends MainGui{

    private GameServer gameServer;

    public ServerGui(int port, int size, int player) {
        super();
        try {
            gameServer = new GameServer(size, player, port);
        } catch (Exception e) {
            e.printStackTrace();
        }
        gameServer.setServerGui(this);

    }

    @Override
    public void dealWithCMD(String cmd) {
        gameServer.processCommand(cmd);
    }
}
