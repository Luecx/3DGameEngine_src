package projects.vier_gewinnt.logic.server;

import projects.vier_gewinnt.Main;
import projects.vier_gewinnt.logic.server.gui.ClientFrame;
import projects.vier_gewinnt.logic.server.protocol.Argument;
import projects.vier_gewinnt.logic.server.protocol.Command;
import projects.vier_gewinnt.logic.server.protocol.CommandDataBase;
import projects.vier_gewinnt.logic.server.protocol.Executable;
import server.client.Client;
import server.connection.Connection;
import server.entities.IncomingMessageHandler;

/**
 * Created by finne on 28.03.2018.
 */
public class GameClient extends Client{

    private GameCube gameCube;
    private CommandDataBase commandDataBase;

    private Main main;
    private ClientFrame clientFrame;

    public GameClient(ClientFrame clientFrame) {
        this.clientFrame = clientFrame;
        createCommands();
    }

    public void sendPlacement(int x, int y, int z){
        try {
            this.getConnection().sendMessage("place -x " + x + " -y " + y + " -z " + z);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void createCommands() {
        commandDataBase = new CommandDataBase();
        GameClient gameClient = this;
        this.addIncomingMessageHandler(new IncomingMessageHandler() {
            @Override
            public void incomingMessage(Connection<?> connection, Object o) {
                commandDataBase.executeCommand((String)o);
            }
        });
        commandDataBase.registerCommand(
                new Command("names", "send a message to the client")
                .registerArgument(
                        new Argument("a", true, "array"))
                .registerArgument(
                                new Argument("m", true, "max"))
                .setExecutable(new Executable() {
                    @Override
                    public void execute(Command c) {
                        clientFrame.setNames(c.getArgument("a").getValues(),
                        Integer.parseInt(c.getArgument("m").getValue()));
                    }
                })
        );
        commandDataBase.registerCommand(
                new Command("shutdown").setExecutable(new Executable() {
                    @Override
                    public void execute(Command c) {
                        main.stopRenderThread();
                        clientFrame.dispose();
                    }
                })
        );
        commandDataBase.registerCommand(
                new Command("place", "places a stone on the map")
                        .registerArgument(
                                new Argument("id", true, "i"))
                        .registerArgument(
                                new Argument("x", true))
                        .registerArgument(
                                new Argument("y", true))
                        .registerArgument(
                                new Argument("z", true))
                        .registerArgument(
                                new Argument("e", true))
                        .setExecutable(new Executable() {
                            @Override
                            public void execute(Command c) {
                                gameCube.place(
                                        Integer.parseInt(c.getArgument("x").getValue()),
                                        Integer.parseInt(c.getArgument("y").getValue()),
                                        Integer.parseInt(c.getArgument("z").getValue()),
                                        Integer.parseInt(c.getArgument("id").getValue())
                                );
                                if(Boolean.parseBoolean(c.getArgument("e").getValue()) == true){
                                    gameCube.showWinner();
                                }
                            }
                        })
        ); commandDataBase.registerCommand(
                new Command("startgame", "starts the game")
                        .registerArgument(
                                new Argument("g", true, "g"))
                        .setExecutable(new Executable() {
                            @Override
                            public void execute(Command c) {
                                main = new Main(
                                        Integer.parseInt(c.getArgument("g").getValue()),gameClient);
                                try {
                                    Thread.sleep(1000);
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                                gameCube = main.getGameCube();
                            }
                        })
        ); commandDataBase.registerCommand(
                new Command("request")
                        .registerArgument(
                                new Argument("i", true, "index"))
                .setExecutable(
                        new Executable() {
                            @Override
                            public void execute(Command c) {
                                gameCube = main.getGameCube();
                                gameCube.setActiveTurn(true,
                                        Integer.parseInt(c.getArgument("i").getValue()));
                            }
                        }
                )
        );
    }

}
