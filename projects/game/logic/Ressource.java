package projects.game.logic;

/**
 * Created by Luecx on 26.01.2017.
 */
public enum Ressource {

    IRON_RESSOURCE("Iron", "Most common known element"),
    CARBON_RESSOURCE("Carbon", "Essential for nearly anything"),
    PLATIN_RESSOURCE("Platin", "Very expensive"),
    GOLD_RESSOURCE("Gold", "Quiet expensive"),
    SILVER_RESSOURCE("Silver", "Used for electronics"),
    DIAMOND_RESSOURCE("Diamond", "Shiny little stones"),
    URANIUM_RESSOUCE("Uranium", "Boom!!"),

    ;
    private final String name;
    private final String description;

    Ressource(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public String toString() {
        return "Ressource{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                "} " + super.toString();
    }
}
