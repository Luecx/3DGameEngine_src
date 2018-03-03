package projects.newgame.logic.server;

import projects.newgame.logic.client.Client;
import projects.newgame.logic.client.ClientLobby;
import projects.newgame.logic.game.ServerGame;
import projects.newgame.logic.game.playerlist.PlayerListEntry;
import projects.newgame.newcrafts.control.ControlStatusPackage;
import projects.newgame.newcrafts.jets.ServerJet;
import server.connection.Connection;
import server.connection.ConnectionClosedHandler;
import server.entities.IncomingMessageHandler;
import server.server.Server;
import server.server.ServerLoginHandler;
import udpserver.Package;

import java.net.InetAddress;

/**
 * Created by finne on 25.09.2017.
 */
public class ServerMain {

    public static final int TCP_PORT = 55555;
    public static final int UDP_PORT = TCP_PORT+1;

    private Server TCP_SERVER;
    private udpserver.Server UDP_SERVER;

    private Lobby lobby;
    private ServerGame game;

    public ServerMain(int teams ,int player) throws Exception {
        this.lobby = new Lobby(teams, player);
        this.TCP_SERVER = new Server();
        this.TCP_INIT_SERVER_LOBBY_LISTENER();
        this.TCP_SERVER.start(TCP_PORT);
    }

    private void TCP_INIT_SERVER_LOBBY_LISTENER(){
        this.TCP_SERVER.addServerLoginHandler(new ServerLoginHandler() {
            @Override
            public void logInEvent(Connection<Server> connection) {
                ServerClient serverClient = new ServerClient(connection);
                if(lobby.addClient(serverClient) == false) {
                    connection.close();
                }else{
                    try {
                        connection.sendMessage(new ClientLobby(lobby));
                        connection.sendMessage(new Client(serverClient));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    TCP_spreadLobby();
                }
            }
        });
        this.TCP_SERVER.addConnectionClosedHandler(new ConnectionClosedHandler() {
            @Override
            public void connectionClosed(Connection<?> connection) {
                ServerClient c = lobby.getClient((Connection<Server>)connection);
                lobby.getClients()[c.getTeamIndex()][c.getPlayerIndex()] = null;
                TCP_spreadLobby();
            }
        });
        this.TCP_SERVER.addIncomingMessageHandler(new IncomingMessageHandler() {
            @Override
            public void incomingMessage(Connection<?> connection, Object o) {
                if(o instanceof Client) {
                    ServerClient client = lobby.getClient((Connection<Server>)connection);
                    client.setName(((Client) o).getName());
                    client.setAdmin(((Client) o).isAdmin());
                    client.setReady(((Client) o).isReady());
                    client.setLoadout(((Client) o).getLoadout());
                    TCP_spreadLobby();

                    //-------------------------StartChecking
                    if(lobby.isReady()){
                        startGame();
                    }
                    //-------------------------StartChecking
                }
                else if(o instanceof String) {
                    try{
                        if(((String) o).startsWith("-")){
                            String[] args = ((String) o).substring(1).toLowerCase().split(" ");
                            switch (args[0]){
                                case "requestlobby": connection.sendMessage(new ClientLobby(lobby)); break;

                                case "sendmessage": TCP_sendMessageToAll(new String[]{lobby.getClient((Connection<Server>)connection).getName(), ((String) o).substring(13)});break;

                            }
                        }else{

                        }
                    }catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    private void TCP_sendMessageToAll(Object o) {
        for(Connection<Server> c:TCP_SERVER.getConnections()){
            try {
                c.sendMessage(o);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void TCP_spreadLobby() {
        TCP_sendMessageToAll(new ClientLobby(lobby));
    }


    private void startGame() {

        TCP_INIT_SERVER_GAME_LISTENER();
        UDP_INIT_SERVER_GAME_LISTENER();
        /* ToDo Start oldproject.game in here
        * Thread starting
        * Sending basic information via TCP
        * */
        game = new ServerGame("map", lobby, this.UDP_SERVER);

        TCP_sendMessageToAll("-start");
        for(ServerClient[] ar:lobby.getClients()) {
            for(ServerClient c:ar) {
                c.setReady(false);
            }
        }
    }

    private void TCP_INIT_SERVER_GAME_LISTENER() {
        TCP_SERVER.addIncomingMessageHandler(new IncomingMessageHandler() {
            @Override
            public void incomingMessage(Connection<?> connection, Object o) {
                if(o instanceof InetAddress){

                    lobby.getClient((Connection<Server>) connection).setInetAddress((InetAddress) o);
                    lobby.getClient((Connection<Server>) connection).setReady(true);

                    if(lobby.isReady()) {
                        game.setFreeze(false);
                    }
                }
            }
        });
    }

    private void UDP_INIT_SERVER_GAME_LISTENER() {

        UDP_SERVER = new udpserver.Server(UDP_PORT, 2001200015041999l) {
            @Override
            public void processMessage(udpserver.Connection connection, byte[] bytes) {

            }

            @Override
            public void processMessage(udpserver.Connection connection, Package aPackage) {
                if(aPackage instanceof ControlStatusPackage){
                    System.out.println(aPackage);
                    for(PlayerListEntry<ServerJet, ServerClient> e:game.getPlayerList().getList()){
                        if(e.getClient().getInetAddress().equals(connection.getInetAddress())){
                            if(e.getClient().getUDP_CONNECTION() == null) {
                                e.getClient().setUDP_CONNECTION(connection);
                            }
                            e.getController().process(((ControlStatusPackage) aPackage).getControlStatuses());
                        }
                    }
                }
            }
        };

    }

    public void killSwitch__DO_NOT_TOUCH() {
        try {
            UDP_SERVER.close();
        } catch (Exception e) {
            e.printStackTrace();
        }        try {
            TCP_SERVER.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



}
