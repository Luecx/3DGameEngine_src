package projects.mediavle_game.map.entities.obs;

import projects.mediavle_game.map.entities.abs.InstancedGameEntity;
import projects.mediavle_game.map.entities.obs.sets.StreetSet;
import projects.mediavle_game.map.entities.obs.sets.TreeSet;

/**
 * Created by finne on 22.03.2018.
 */
public class Street extends InstancedGameEntity<Street> {

    public Street(int x, int y) {
        super(x, y, 2,2, false, StreetSet.STREET_SET);
    }

    @Override
    public Street clone() {
        Street t = new Street(this.getX(),this.getY());
        return t;
    }

}
