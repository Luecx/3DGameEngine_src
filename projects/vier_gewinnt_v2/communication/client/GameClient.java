package projects.vier_gewinnt_v2.communication.client;

import engine.core.master.DisplayManager;
import projects.vier_gewinnt_v2.communication.protocol.Argument;
import projects.vier_gewinnt_v2.communication.protocol.Command;
import projects.vier_gewinnt_v2.communication.protocol.CommandDataBase;
import projects.vier_gewinnt_v2.communication.protocol.Executable;
import projects.vier_gewinnt_v2.gui.ClientGui;
import projects.vier_gewinnt_v2.visual.Core;
import server.client.Client;
import server.connection.Connection;
import server.entities.IncomingMessageHandler;

import java.io.IOException;

/**
 * Created by finne on 31.03.2018.
 */
public class GameClient extends Client {

    private CommandDataBase commandDataBase;
    private CommandDataBase clientDataBase;

    private int playerID;
    private int gamesize;

    private Core core;

    public GameClient(String ip, int port, String name) {
        try {
            this.connect(ip, port);
            Thread.sleep(500);
            this.getConnection().sendMessage("login -name "+name);
        } catch (IOException e) {
            System.exit(-1);
        } catch (Exception e) {
            System.exit(-1);
        }
        this.commandDataBase = new CommandDataBase();
        this.clientDataBase = new CommandDataBase();
        this.createListeners();
        this.createCommands();
    }

    private void createListeners(){
        this.addIncomingMessageHandler(new IncomingMessageHandler() {
            @Override
            public void incomingMessage(Connection<?> connection, Object o) {
                System.out.println("[SERVER] "+o);
                commandDataBase.executeCommand((String) o);
            }
        });
    }
    private void createCommands() {
        commandDataBase.registerCommand(new Command("stopgame")
                .setExecutable(new Executable() {
                    @Override
                    public void execute(Command c) {
                        if(core != null){
                            core.stopRenderThread();
                            core = null;
                        }
                    }
                })
        );
        commandDataBase.registerCommand(new Command("gameover")
                .registerArgument(new Argument("full", true, "f"))
                .setExecutable(new Executable() {
                    @Override
                    public void execute(Command c) {
                        if(c.getArgument("full").getValues().equals("false")){
                            core.getGameCube().showWinner();
                        }
                        core.getGameCube().showGameOver();
                    }
                })
        );
        commandDataBase.registerCommand(new Command("startgame")
                .setExecutable(new Executable() {
                    @Override
                    public void execute(Command c) {
                        processStartGame();
                    }
                })
        );
        commandDataBase.registerCommand(new Command("request")
            .setExecutable(new Executable() {
                @Override
                public void execute(Command c) {
                    processRequestMove();
                }
            })
        );
        commandDataBase.registerCommand(new Command("place")
                .registerArgument(new Argument("x", true))
                .registerArgument(new Argument("y", true))
                .registerArgument(new Argument("z", true))
                .registerArgument(new Argument("id", true))
                .setExecutable(new Executable() {
                    @Override
                    public void execute(Command c) {
                        processRecievePlacement(c);
                    }
                })
        );
        commandDataBase.registerCommand(new Command("disconnect")
                .registerArgument(new Argument("msg", false))
                .setExecutable(new Executable() {
                    @Override
                    public void execute(Command c) {
                        disconnect();
                        if(clientGui != null) {
                            clientGui.dispose();
                        }
                    }
                })
        );
        commandDataBase.registerCommand(new Command("config")
                .registerArgument(new Argument("gamesize", true, "size", "gsize", "s"))
                .registerArgument(new Argument("playerid", true, "id", "pid", "i"))
                .setExecutable(new Executable() {
                    @Override
                    public void execute(Command c) {
                        processChangeConfig(c);
                    }
                })
        );
        commandDataBase.registerCommand(new Command("names")
             .registerArgument(new Argument("array", true, "a", "ar", "c"))
            .setExecutable(new Executable() {
                @Override
                public void execute(Command c) {
                    String[] n = c.getArgument("array").getValue().replace("___", " ").split(",");
                    String[] k = new String[n.length +1];
                    for(int i = 0; i < n.length; i++){
                        k[i] = n[i];
                    }
                    k[k.length-1] = "----------------";
                    if(clientGui != null) {
                        clientGui.setNames(k);
                    }
                }
            })
        );


        clientDataBase.registerCommand(new Command("display")
                .registerArgument(new Argument("width", false, "w"))
                .registerArgument(new Argument("height", false, "h"))
                .registerArgument(new Argument("fps", false))
                .registerArgument(new Argument("fullscreen", false))
                .registerArgument(new Argument("antialiasing", false, "aa"))
                .setExecutable(new Executable() {
                    @Override
                    public void execute(Command c) {
                        if(c.getArgument("width").getValue() != null && !c.getArgument("width").getValue().equals("")) {
                            DisplayManager.WIDTH = Integer.parseInt(c.getArgument("width").getValue());
                        }if(c.getArgument("height").getValue() != null && !c.getArgument("height").getValue().equals("")) {
                            DisplayManager.HEIGHT = Integer.parseInt(c.getArgument("height").getValue());
                        }if(c.getArgument("fps").getValue() != null && !c.getArgument("fps").getValue().equals("")) {
                            DisplayManager.FPS_CAP = Integer.parseInt(c.getArgument("fps").getValue());
                        }if(c.getArgument("fullscreen").getValue() != null && !c.getArgument("fullscreen").getValue().equals("")) {
                            DisplayManager.FULLSCREEN = Boolean.parseBoolean(c.getArgument("fullscreen").getValue());
                        }if(c.getArgument("antialiasing").getValue() != null && !c.getArgument("antialiasing").getValue().equals("")) {
                            DisplayManager.ANTIALIASING_LEVEL = Integer.parseInt(c.getArgument("antialiasing").getValue());
                        }
                        System.out.format("%-20s %-20s \n", "WIDTH", DisplayManager.WIDTH);
                        System.out.format("%-20s %-20s \n", "HEIGHT", DisplayManager.HEIGHT);
                        System.out.format("%-20s %-20s \n", "FPS_CAP", DisplayManager.FPS_CAP);
                        System.out.format("%-20s %-20s \n", "FULLSCREEN", DisplayManager.FULLSCREEN);
                        System.out.format("%-20s %-20s \n", "ANTIALIASING_LEVEL", DisplayManager.ANTIALIASING_LEVEL);
                    }
                })
        );
    }

    private void processRecievePlacement(Command c) {
        int x = Integer.parseInt(c.getArgument("x").getValue());
        int y = Integer.parseInt(c.getArgument("y").getValue());
        int z = Integer.parseInt(c.getArgument("z").getValue());
        int id = Integer.parseInt(c.getArgument("id").getValue());
        this.core.getGameCube().place(x,y,z,id);
    }

    private void processChangeConfig(Command c) {
        this.playerID = Integer.parseInt(c.getArgument("playerid").getValue());
        this.gamesize = Integer.parseInt(c.getArgument("gamesize").getValue());
        System.out.println("############# Config Changed ##############");
        System.out.format("%-20s %-20s \n", "playerID", playerID);
        System.out.format("%-20s %-20s \n", "game size", gamesize);
    }
    private void processStartGame() {
        core = new Core(this, gamesize, playerID);
    }
    private void processRequestMove() {
        this.core.getGameCube().setActiveTurn(true);
    }


    public void sendPlacement(int x, int y, int z){
        try {
            this.getConnection().sendMessage("place -x " + x + " -y " + y + " -z " + z + " -id " + playerID);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void sendMessage(String msg) {
        try {
            this.getConnection().sendMessage("msg -c " +msg);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void sendGameLoaded(){
        try {
            System.out.println("ijadwijawidjiawd");
            this.getConnection().sendMessage("gameloaded");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private ClientGui clientGui;
    public ClientGui getClientGui() {
        return clientGui;
    }
    public void setClientGui(ClientGui clientGui) {
        this.clientGui = clientGui;
    }

    public void processCMD(String cmd) {
        this.clientDataBase.executeCommand(cmd);
    }
}
