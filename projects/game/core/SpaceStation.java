package projects.game.core;

import projects.game.solarsystem.Celestial;

/**
 * Created by Luecx on 03.03.2017.
 */
public class SpaceStation extends Celestial {
    public SpaceStation(Celestial planet, float height) {
        super(1000, 0, planet.getRad() + height, 0);
    }
}
