package projects.newgame.logic.game.playerlist;

import projects.newgame.logic.client.Client;
import projects.newgame.newcrafts.jets.JetData;

import java.util.ArrayList;

/**
 * Created by finne on 30.09.2017.
 */
public class PlayerList<J extends JetData, C extends Client> extends udpserver.Package{

    ArrayList<PlayerListEntry<J,C>> list = new ArrayList<>();

    public PlayerList() {
        super(2001200015041999l);
    }

    public ArrayList<PlayerListEntry<J, C>> getList() {
        return list;
    }
}
