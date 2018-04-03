package projects.vier_gewinnt_v2.communication.server;

import org.lwjgl.util.vector.Vector3f;
import projects.vier_gewinnt_v2.communication.protocol.Argument;
import projects.vier_gewinnt_v2.communication.protocol.Command;
import projects.vier_gewinnt_v2.communication.protocol.CommandDataBase;
import projects.vier_gewinnt_v2.communication.protocol.Executable;
import projects.vier_gewinnt_v2.gui.ServerGui;
import projects.vier_gewinnt_v2.logic.GameKI;
import projects.vier_gewinnt_v2.logic.GameMap;
import projects.vier_gewinnt_v2.logic.Vector3i;
import server.client.Client;
import server.connection.Connection;
import server.connection.ConnectionClosedHandler;
import server.entities.IncomingMessageHandler;
import server.server.Server;
import server.server.ServerLoginHandler;

import java.util.ArrayList;

/**
 * Created by finne on 31.03.2018.
 */
public class GameServer extends Server{

    private CommandDataBase commandDataBase;

    private int maxPlayers;
    private int gameSize;

    private GameKI gameKI;
    private Player[] players;
    private Player processedPlayer = null;

    private GameMap game;
    private boolean gameIsRunning = false;

    public GameServer(int gameSize, int players, int port) throws Exception {
        this.maxPlayers = players;
        this.gameSize = gameSize;
        this.commandDataBase = new CommandDataBase();
        this.players = new Player[maxPlayers];
        createListeners();
        createCommands();
        this.start(port);
    }
    private void createListeners(){
        this.addServerLoginHandler(new ServerLoginHandler() {
            @Override
            public void logInEvent(Connection<Server> connection) {
                if(gameIsRunning) {
                    connection.close();
                }
            }
        });
        this.addIncomingMessageHandler(new IncomingMessageHandler() {
            @Override
            public void incomingMessage(Connection<?> connection, Object o) {
                for(Player p:players){
                    if(p != null && p.getConnection() != null && p.getConnection().equals(connection)){
                        processedPlayer = p;
                    }
                }
                if(processedPlayer == null && !gameIsRunning){
                    processedPlayer = new Player(connection,"",  false);
                }
                System.out.println(processedPlayer.getName() + ": "+o.toString());
                commandDataBase.executeCommand((String) o);
                processedPlayer = null;
            }
        });
        this.addConnectionClosedHandler(new ConnectionClosedHandler() {
            @Override
            public void connectionClosed(Connection<?> connection) {
                for(int i = 0; i < players.length; i++) {
                    if(players[i] != null && players[i].getConnection() == connection) {
                        players[i] = null;
                    }
                }
                if(gameIsRunning) processFillBot();
                processSendNames();
            }
        });
    }
    private void createCommands() {
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
        commandDataBase.registerCommand(new Command("stopgame")
            .setExecutable(new Executable() {
                @Override
                public void execute(Command c) {
                    gameIsRunning = false;
                    game = null;
                    gameKI = null;
                    sendToAll("stopgame");
                }
            }));
        commandDataBase.registerCommand(new Command("login")
            .registerArgument(new Argument("name", true, "n"))
            .setExecutable(new Executable() {
                @Override
                public void execute(Command c) {
                    processLogIn(c);
                }
            })
        );
        commandDataBase.registerCommand(new Command("gameloaded")
            .setExecutable(new Executable() {
                @Override
                public void execute(Command c) {
                    processRecieveGameLoaded();
                }
            })
        );
        commandDataBase.registerCommand(new Command("shrinklist")
            .setExecutable(new Executable() {
                @Override
                public void execute(Command c) {
                    processShrinkList();
                }
            })
        );
        commandDataBase.registerCommand(new Command("addbot")
            .setExecutable(new Executable() {
                @Override
                public void execute(Command c) {
                    processAddBot();
                }
            })
        );
        commandDataBase.registerCommand(new Command("fillbot")
            .setExecutable(new Executable() {
                    @Override
                    public void execute(Command c) {
                        processFillBot();
                    }
                })
        );
        commandDataBase.registerCommand(new Command("startgame")
            .registerArgument(new Argument("fillbot", false, "fill"))
            .setExecutable(new Executable() {
                @Override
                public void execute(Command c) {
                    processStartGame(c);
                }
            })
        );
        commandDataBase.registerCommand(new Command("config")
            .registerArgument(new Argument("maxplayer", false, "max", "m"))
            .registerArgument(new Argument("gamesize", false, "size", "s"))
            .setExecutable(new Executable() {
                @Override
                public void execute(Command c) {
                    processChangeConfig(c);
                }
            })
        );
        commandDataBase.registerCommand(new Command("kick")
            .registerArgument(new Argument("index", true, "i"))
            .setExecutable(new Executable() {
                @Override
                public void execute(Command c) {
                    processKickPlayer(c);
                }
            })
        );
        commandDataBase.registerCommand(new Command("list")
            .setExecutable(new Executable() {
                @Override
                public void execute(Command c) {
                    System.out.format("%-20s %-20s %-20s \n", "Name", "Connection","Bot");
                    for(Player p:players){
                        if(p != null) {

                            System.out.format("%-20s %-20s %-20s \n", p.getName(), p.getConnection(),p.isBot());
                        }else{

                            System.out.format("%-20s %-20s %-20s \n", "- -", "- -","- -");
                        }
                    }
                }
            })
        );
    }

    public void processRecieveGameLoaded(){
        processedPlayer.setGameLoaded(true);

        boolean start = true;
        for(Player p: players) {
            System.out.println(p);
            if(p == null || p.isGameLoaded() == false) {
                start = false;
            }
        }
        if(start) {
            this.sendToBuffer(1000);
            processRequestMove();
        }
    }
    public void processRequestMove() {
        if(this.gameIsRunning) {

            System.out.println(this.gameKI.possibleMoves());

            if(this.game.getWinnerID() != -1) {
                sendToAll("gameover -full false");
                this.gameIsRunning = false;
                this.game = null;
                this.gameKI = null;
                this.sendToBuffer(10000);
                this.sendToAll("stopgame");
            }
            else if(this.gameKI.possibleMoves().size() == 0 && this.game.getValues()[0][0][0] != -1){
                sendToAll("gameover -full true");
                this.gameIsRunning = false;
                this.game = null;
                this.gameKI = null;
                this.sendToBuffer(10000);
                this.sendToAll("stopgame");
            }else{
                Player p = this.players[game.getActivePlayerID()];
                if(p.getConnection() != null){
                    sendToPlayer(game.getActivePlayerID(), "request");
                } else{
                    int index = this.game.getActivePlayerID();
                    Vector3i bestMove = gameKI.bestMove(index, 3);

                    System.out.println("KI places: " + bestMove);
                    sendToAll(
                            "place" +
                                    " -x " + (int)bestMove.x +
                                    " -y " + (int)bestMove.y +
                                    " -z " + (int)bestMove.z +
                                    " -id " + index);
                    game.place((int) bestMove.x, (int) bestMove.y, (int) bestMove.z, index);
                    game.nextPlayer();
                    processRequestMove();
                }
            }
        }
    }
    public void processRecieveMSG(Command c){
        String full = "";
        for(String s:c.getArgument("c").getValues()){
            full += s + " ";
        }
        sendToAll(processedPlayer.getName() + ": " + full);
    }
    public void processRecievePlacement(Command c){
        int x = Integer.parseInt(c.getArgument("x").getValue());
        int y = Integer.parseInt(c.getArgument("y").getValue());
        int z = Integer.parseInt(c.getArgument("z").getValue());
        int id = Integer.parseInt(c.getArgument("id").getValue());
        sendToAll("place -x " + x + " -y " + y + " -z " + z + " -id " + id);
        game.place(x,y,z,id);
        game.nextPlayer();
        processRequestMove();
    }
    public void processShrinkList() {
        ArrayList<Player> list = new ArrayList<>();
        for(Player p:players){
            if(p != null) {
                list.add(p);
            }
        }
        players = list.toArray(new Player[list.size()]);
        this.maxPlayers = players.length;
        this.processSendConfig();
        this.sendToBuffer(300);
        this.processSendNames();
    }
    public void processCommand(String cmd) {
        this.commandDataBase.executeCommand(cmd);
    }
    public void processKickPlayer(Command c) {
        int index = Integer.parseInt(c.getArgument("index").getValue());
        if(index >= 0 && index < players.length) {
            this.processedPlayer.getConnection().close();
            players[index] = null;
            if(gameIsRunning) {
                processFillBot();
            }
        }
        processSendNames();
    }
    public void processSendNames() {
        String names = "";
        for(Player p:players) {
            if(p != null) {
                names += p.getName() + ",";
            }else{
                names += "___,";
            }
        }
        sendToAll("names -array " +names);
        if(this.serverGui != null) {
            String[] n = new String[players.length + 1];
            for(int i = 0; i < players.length; i++) {
                n[i] = players[i] == null ? " ": players[i].getName();
            }
            n[n.length-1] = "-------------------";
            serverGui.setNames(n);
        }
    }
    public void processSendConfig() {
        for(int i = 0; i < players.length; i++) {
            sendToPlayer(i, "config" +
                    " -size " + gameSize +
                    " -id " + i);
        }
    }
    public void processChangeConfig(Command c) {
        if(c.getArgument("maxplayer").getValue() == null &&
                c.getArgument("gamesize").getValue() == null){
            System.out.println("########## Config ##########");
            System.out.format("%-20s %-20s \n", "max player: " , maxPlayers);
            System.out.format("%-20s %-20s \n", "gamesize: " , gameSize);
            System.out.format("%-20s %-20s \n", "running game: " , gameIsRunning);
        }else{
            if(!gameIsRunning){
                if(c.getArgument("maxplayer").getValue() != null){
                    this.maxPlayers = Integer.parseInt(c.getArgument("maxplayer").getValue());
                    Player[] newArray = new Player[this.maxPlayers];
                    if(maxPlayers >= players.length){
                        for(int i = 0; i < players.length; i++) {
                            newArray[i] = players[i];
                        }
                    }else{
                        int index = 0;
                        int max = 0;
                        for(int i = 0; i < players.length; i++) {
                            if(players[i] != null && players[i].isBot() == false){
                                newArray[index] = players[i];
                                index ++;
                                if(index > newArray.length -1){
                                    break;
                                }
                            }
                            max = i;
                        }
                        for(int i = max + 1; i < players.length; i++) {
                            this.commandDataBase.executeCommand("kick -index " + i);
                        }
                    }
                    this.players = newArray;
                }if(c.getArgument("gamesize").getValue() != null){
                    this.gameSize = Integer.parseInt(c.getArgument("gamesize").getValue());
                }
                processSendConfig();
            }
        }
    }
    public void processAddBot(){
        if(gameIsRunning) return;
        for(int i = 0; i < players.length; i++) {
            if(players[i] == null){
                players[i] = new Player("Bot#" + (int)(Math.random() * 100000), true);
                break;
            }
        }
        processSendNames();
    }
    public void processFillBot(){
        for(int i = 0; i < maxPlayers; i++) {
            if(players[i] == null){
                players[i] = new Player("Bot#" + (int)(Math.random() * 100000), true);
            }
        }
        processSendNames();
    }
    public void processLogIn(Command c) {
        if(gameIsRunning) return;
        if(this.processedPlayer != null){
            this.processedPlayer.setName(c.getArgument("name").getValue());
            boolean works = false;
            for(int i = 0; i < players.length; i++) {
                if(players[i] == null){
                    works = true;
                    players[i] = processedPlayer;
                    break;
                }
            }
            if(!works){
                sendToProcessedPlayer("disconnect -msg Not enough space");
            }
        }
        processSendConfig();
        sendToBuffer(200);
        processSendNames();
    }
    public void processStartGame(Command c) {
        if(gameIsRunning) return;

        this.gameIsRunning = true;

        if(c.getArgument("fillbot").getValue() != null &&
                c.getArgument("fillbot").getValue().equals("true")){
            processFillBot();
        }
        processShrinkList();

        this.game = new GameMap(this.gameSize, this.maxPlayers);
        this.gameKI = new GameKI(game);
        sendToBuffer(500);
        sendToAll("startgame");
    }

    private void sendToProcessedPlayer(String msg) {
        try {
            processedPlayer.getConnection().sendMessage(msg);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private void sendToAll(String msg) {
        for(Player p:players){
            if(p != null && p.getConnection() != null){
                try {
                    p.getConnection().sendMessage(msg);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
    private void sendToPlayer(int index, String msg) {
        if(players[index] != null && players[index].getConnection() != null){
            try {
                players[index].getConnection().sendMessage(msg);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    private void sendToBuffer(int millis){
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private ServerGui serverGui;
    public ServerGui getServerGui() {
        return serverGui;
    }
    public void setServerGui(ServerGui serverGui) {
        this.serverGui = serverGui;
    }
}
