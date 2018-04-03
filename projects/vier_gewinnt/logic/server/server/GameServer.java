package projects.vier_gewinnt.logic.server.server;

import projects.vier_gewinnt.logic.server.gui.ServerFrame;
import projects.vier_gewinnt.logic.server.protocol.Argument;
import projects.vier_gewinnt.logic.server.protocol.Command;
import projects.vier_gewinnt.logic.server.protocol.CommandDataBase;
import projects.vier_gewinnt.logic.server.protocol.Executable;
import server.client.Client;
import server.connection.Connection;
import server.connection.ConnectionClosedHandler;
import server.entities.IncomingMessageHandler;
import server.server.Server;

import java.util.ArrayList;

/**
 * Created by finne on 28.03.2018.
 */
public class GameServer extends Server {

    private CommandDataBase dataBase = new CommandDataBase();

    private ArrayList<Player> players = new ArrayList<>();
    private Game game;
    private int maxPlayers = 4;
    private int gamesize = 9;

    private ServerFrame serverFrame;

    public static void main(String[] args) throws Exception {
        GameServer gameServer = new GameServer(null, 9,2,55555);
        Client clientA = new Client();
        Client clientB = new Client();

        clientA.connect("localhost", 55555);
        clientA.addIncomingMessageHandler(new IncomingMessageHandler() {
            @Override
            public void incomingMessage(Connection<?> connection, Object o) {
                System.err.println("[Finn] " +o);
            }
        });
        clientA.getConnection().sendMessage("login -name Finn -bot false");
        Thread.sleep(1000);

        clientB.connect("localhost", 55555);
        clientB.addIncomingMessageHandler(new IncomingMessageHandler() {
            @Override
            public void incomingMessage(Connection<?> connection, Object o) {
                System.err.println("[Eric] "+o);
            }
        });
        clientB.getConnection().sendMessage("login -name Eric -bot false");

        Thread.sleep(1000);
        clientA.getConnection().sendMessage("place -id 1 -x 2 -y 3 -z 6");

        Thread.sleep(1000);
        clientA.disconnect();
        Thread.sleep(1000);
        clientB.disconnect();
        Thread.sleep(1000);

        gameServer.close();
    }

    public GameServer(ServerFrame serverFrame, int gameSize, int players, int port) throws Exception {
        this.maxPlayers = players;
        this.gamesize = gameSize;
        this.serverFrame = serverFrame;

        setupCommands();

        this.start(port);
    }

    private void startGame() {
        if(players.size() == maxPlayers){
            game = new Game(gamesize, maxPlayers);
            sendToAll("startgame -p "+ maxPlayers + " -g " + gamesize);
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            requestMove();
        }
    }

    private void requestMove(){
        try {
            Thread.sleep(500);
            this.getConnection(this.game.currentPlayer).sendMessage("request -i " + this.game.currentPlayer);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void processMove(Command c) {
        int x = Integer.parseInt(c.getArgument("x").getValue());
        int y = Integer.parseInt(c.getArgument("y").getValue());
        int z = Integer.parseInt(c.getArgument("z").getValue());
        int a = this.game.currentPlayer;

        this.game.values[x][y][z] = a;
        this.game.nextPlayer();
        if(this.game.winnerWinnerChickenDinner() != -1){
            sendToAll("place" +
                    " -id "+ a +
                    " -x " + x +
                    " -y " + y +
                    " -z " + z +
                    " -e true");
            this.game = null;
            try {
                Thread.sleep(10000);
                sendToAll("shutdown");
                serverFrame.dispose();
                this.close();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }else{
            sendToAll("place" +
                    " -id "+ a +
                    " -x " + x +
                    " -y " + y +
                    " -z " + z +
                    " -e false");
            this.requestMove();
        }

    }

    private void sendToAll(String s) {
        System.out.println("[SHOUT] " + s);
        for(Connection<Server> connection:getConnections()){
            try {
                connection.sendMessage(new String(s));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void setupCommands(){

        final GameServer gameServer = this;

        this.addIncomingMessageHandler(new IncomingMessageHandler() {
            @Override
            public void incomingMessage(Connection<?> connection, Object o) {
                dataBase.executeCommand((String)o);
            }
        });
        this.addConnectionClosedHandler(new ConnectionClosedHandler() {
            @Override
            public void connectionClosed(Connection<?> connection) {
                int index = gameServer.connections.indexOf(connection);
                String name = gameServer.players.get(index).getName();
                gameServer.players.remove(index);
                serverFrame.updatePlayerList(players);
                sendToAll("msg -c " + name + " disconnected from the server");
            }
        });
        dataBase.registerCommand(
                new Command("place", "places a stone on the map")
                        .registerArgument(
                                new Argument("id", false, "i"))
                        .registerArgument(
                                new Argument("x", true))
                        .registerArgument(
                                new Argument("y", true))
                        .registerArgument(
                                new Argument("z", true))
                        .setExecutable(new Executable() {
                            @Override
                            public void execute(Command c) {
                                gameServer.processMove(c);
                            }
                        })
        );
        dataBase.registerCommand(
                new Command("login", "Login of a new player")
                        .registerArgument(
                                new Argument("name", true, "name","n","nm"))
                        .registerArgument(
                                new Argument("bot", true, "bot", "ai"))
                        .setExecutable(new Executable() {
                            @Override
                            public void execute(Command c) {

                                players.add(new Player(c.getArgument("name").getValue(),
                                        Boolean.parseBoolean(c.getArgument("bot").getValue())));
                                gameServer.startGame();
                                serverFrame.updatePlayerList(players);

                                sendToAll("msg -c " + c.getArgument("name").getValue()
                                        + " connected to the server");
                                String names = "";
                                for(Player p:players){
                                    names += p.getName() +" ";
                                }
                                sendToAll("names -a "+names + " -m " + maxPlayers);
                            }
                        }));

    }

    public CommandDataBase getDataBase() {
        return dataBase;
    }
}
