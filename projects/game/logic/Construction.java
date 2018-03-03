package projects.game.logic;

/**
 * Created by Luecx on 25.02.2017.
 */
public enum Construction {
    X_12_MISSILE("X-12 Missile", "high impact, low cooldown");
    ;

    private final String name;
    private final String description;

    Construction(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }
}
