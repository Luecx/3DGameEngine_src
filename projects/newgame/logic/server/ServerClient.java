package projects.newgame.logic.server;

import projects.newgame.logic.client.Client;
import server.connection.Connection;
import server.server.Server;

import java.net.InetAddress;

/**
 * Created by finne on 25.09.2017.
 */
public class ServerClient extends Client {

    private Connection<Server> TCP_CONNECTION;
    private udpserver.Connection UDP_CONNECTION;
    private InetAddress inetAddress;

    public ServerClient(Connection<Server> TCP_CONNECTION) {
        this.TCP_CONNECTION = TCP_CONNECTION;
    }

    public Connection<Server> getTCP_CONNECTION() {
        return TCP_CONNECTION;
    }

    public void setTCP_CONNECTION(Connection<Server> TCP_CONNECTION) {
        this.TCP_CONNECTION = TCP_CONNECTION;
    }

    public udpserver.Connection getUDP_CONNECTION() {
        return UDP_CONNECTION;
    }

    public void setUDP_CONNECTION(udpserver.Connection UDP_CONNECTION) {
        this.UDP_CONNECTION = UDP_CONNECTION;
    }

    public InetAddress getInetAddress() {
        return inetAddress;
    }

    public void setInetAddress(InetAddress inetAddress) {
        this.inetAddress = inetAddress;
    }
}
