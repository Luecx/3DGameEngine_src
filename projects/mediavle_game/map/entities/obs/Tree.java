package projects.mediavle_game.map.entities.obs;

import projects.mediavle_game.map.entities.abs.InstancedGameEntity;
import projects.mediavle_game.map.entities.abs.InstancedGameEntitySet;

/**
 * Created by finne on 20.03.2018.
 */
public class Tree extends InstancedGameEntity {

    public Tree(int x, int y) {
        super(x, y, TreeSet.TREE_SET);
    }
}
