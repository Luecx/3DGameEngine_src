package projects.vier_gewinnt.logic.server.server;

/**
 * Created by finne on 28.03.2018.
 */
public class Player {

    private String name;
    private boolean bot;

    public Player(String name, boolean bot) {
        this.name = name;
        this.bot = bot;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isBot() {
        return bot;
    }

    public void setBot(boolean bot) {
        this.bot = bot;
    }
}
