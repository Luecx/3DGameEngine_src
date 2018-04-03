package projects.vier_gewinnt_v2.gui;


import projects.vier_gewinnt_v2.communication.client.GameClient;

public class ClientGui extends MainGui{

    private GameClient gameClient;

    public ClientGui(String ip, int port, String name) {
        super();
        this.gameClient = new GameClient(ip, port, name);
        this.gameClient.setClientGui(this);
    }

    @Override
    public void dealWithCMD(String cmd) {
        gameClient.processCMD(cmd);
    }
}
