package projects.vier_gewinnt_v2.communication.server;

import server.connection.Connection;

/**
 * Created by finne on 31.03.2018.
 */
public class Player {

    private Connection<?> connection;
    private String name;
    private boolean bot;

    private boolean gameLoaded = false;



    public Player(String name, boolean bot) {
        this.name = name;
        this.bot = bot;
        this.connection = null;
        this.gameLoaded = bot;
    }

    public Player(Connection<?> connection, String name, boolean bot) {
        this.connection = connection;
        this.name = name;
        this.bot = bot;
        this.gameLoaded = bot;
    }

    public boolean isGameLoaded() {
        if(this.bot) return true;
        return gameLoaded;
    }

    public void setGameLoaded(boolean gameLoaded) {
        this.gameLoaded = gameLoaded;
        if(this.bot) this.gameLoaded = true;
    }

    public Connection<?> getConnection() {
        return connection;
    }

    public void setConnection(Connection<?> connection) {
        this.connection = connection;
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
