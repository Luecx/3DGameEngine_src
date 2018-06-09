package projects.wot_mini;

import engine.core.components.ComplexGameObject;
import engine.core.components.Group;
import engine.core.components.GroupableGameObject;
import org.lwjgl.util.vector.Vector3f;
import templates.gameserver_v1.Game.UDPTransmittedGame;
import templates.gameserver_v1.UDPGameServer;
import udp.server_side.UDPClientInformation;

import java.util.ArrayList;

public class TestGame extends UDPTransmittedGame<ServerInput, ServerOutput> {

    class Player{
        public Player(long id) {
            this.id = id;
        }
        Group orientation = new Group(0,0,0);
        long id;
        @Override
        public String toString() {
            return "Player{" +
                    "orientation=" + orientation +
                    ", id=" + id +
                    '}';
        }
    }

    ArrayList<Player> players = new ArrayList<>();

    @Override
    public void new_player(UDPClientInformation udpClientInformation) {
        players.add(new Player(udpClientInformation.getId()));
    }

    @Override
    public void remove_player(UDPClientInformation udpClientInformation) {
        for(int i = 0; i < players.size(); i++){
            if(players.get(i).id == udpClientInformation.getId()){
                players.remove(i);
                break;
            }
        }
    }

    @Override
    public void process_controls(double v) {
        System.out.println(v);
        for(UDPClientInformation c:this.controller.keySet()){
            Player p = null;
            for(Player pl:players){
                if(pl.id == c.getId()){
                    p = pl;
                }
            }
            if(p != null){
                if(controller.get(c).getValues()[1]){
                    System.out.println(-(float) v);
                    p.orientation.increaseRotation(0,-(float)v * 20, 0);
                }if(controller.get(c).getValues()[2]){
                    p.orientation.increaseRotation(0,(float)v * 20, 0);
                }if(controller.get(c).getValues()[0]){
                    p.orientation.setPosition(
                            Vector3f.add(
                                    p.orientation.getPosition(),
                                    new Vector3f(
                                            -p.orientation.getZAxis().x * (float) v,
                                            -p.orientation.getZAxis().y * (float) v,
                                            -p.orientation.getZAxis().z * (float) v),
                                    null));

                }
            }

        }
    }

    @Override
    public ServerOutput getUpdatedPositions() {
        Vector3f[] pos = new Vector3f[players.size()];
        Vector3f[] rot = new Vector3f[players.size()];
        long[] id = new long[players.size()];
        for(int i = 0; i < players.size(); i++){
            pos[i] = players.get(i).orientation.getPosition();
            rot[i] = players.get(i).orientation.getRotation();
            id[i] = players.get(i).id;
        }
        return new ServerOutput(pos, rot, id);
    }
}
