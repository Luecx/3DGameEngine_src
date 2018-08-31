package projects.ctf.server;

import projects.ctf.data.ClientData;
import projects.ctf.data.ClientDataPack;
import tcp.client.TCPClient;
import templates.gameserver_v1.UDPGameClient;
import udp.client_side.UDPClient;

public class CTFClient {

    private TCPClient TCP_CLIENT;
    private UDPGameClient<ClientData, ClientDataPack> UDP_CLIENT;

    public CTFClient() {
    }

    public void connect(String ip){
        try{
            TCP_CLIENT = new TCPClient();
            TCP_CLIENT.connect(ip, 5555);

//            UDP_CLIENT = new UDPGameClient<ClientData, ClientDataPack>(ip, 5556) {
//                @Override
//                protected void package_received(ClientDataPack clientDataPack) {
//                    System.out.println(clientDataPack);
//                }
//            };
        }catch (Exception e){
            e.printStackTrace();
            System.exit(-1);
        }
    }
}
