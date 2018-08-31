package projects.ctf.server;

import projects.ctf.data.ClientData;
import projects.ctf.data.ClientDataPack;
import tcp.server.TCPServer;
import templates.gameserver_v1.Game.UDPTransmittedGame;
import templates.gameserver_v1.UDPGameServer;
import udp.server_side.UDPClientInformation;
import udp.server_side.UDPServer;

import java.io.IOException;

public class CTFServer {

    private TCPServer TCP_SERVER;
    private UDPGameServer<ClientData, ClientDataPack> UDP_SERVER;

    private UDPTransmittedGame<ClientData, ClientDataPack> game;

    public CTFServer(){
        setupServer();
    }

    private void setupServer(){
        try{
            TCP_SERVER = new TCPServer();
            TCP_SERVER.start(5555);

            game = new UDPTransmittedGame<ClientData, ClientDataPack>() {
                @Override
                public void new_player(UDPClientInformation udpClientInformation) {
                    System.out.println(udpClientInformation);
                }

                @Override
                public void remove_player(UDPClientInformation udpClientInformation) {
                    System.err.println(udpClientInformation);

                }

                @Override
                public void process_controls(double v) {
                }

                @Override
                public ClientDataPack getUpdatedPositions() {
                    return null;
                }
            };

            UDP_SERVER = new UDPGameServer<ClientData, ClientDataPack>(game, UDPGameServer.REFRESH_RATE_50, 5556);
        }catch (Exception e){
            e.printStackTrace();
            System.exit(-1);
        }
    }

    public void close(){
        try {
            this.TCP_SERVER.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        this.UDP_SERVER.close();
    }

    public static void main(String[] args){
        CTFServer ctfServer = new CTFServer();
        CTFClient ctfClient = new CTFClient();
        ctfClient.connect("localhost");

    }

}
