package projects.newgame.logic.game.playerlist;

import projects.newgame.logic.client.Client;
import projects.newgame.newcrafts.control.Controller;
import projects.newgame.newcrafts.jets.JetData;

import java.io.Serializable;

/**
 * Created by finne on 30.09.2017.
 */
public class PlayerListEntry<J extends JetData, C extends Client> implements Serializable {

    private J jet;
    private C client;

    private Controller controller;

    public PlayerListEntry(J jet, C client) {
        this.jet = jet;
        this.client = client;
        this.controller = new Controller();
    }

    public J getJet() {
        return jet;
    }

    public C getClient() {
        return client;
    }

    public Controller getController() {
        return controller;
    }
}
