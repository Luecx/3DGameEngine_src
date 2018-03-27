package projects.mediavle_game.map.entities.obs;

import projects.mediavle_game.map.entities.abs.InstancedGameEntity;
import projects.mediavle_game.map.entities.obs.sets.TreeSet;

/**
 * Created by finne on 20.03.2018.
 */
public class Tree extends InstancedGameEntity<Tree> {

    public Tree(int x, int y) {
        super(x, y, 1,1, true, TreeSet.TREE_SET);
    }

    @Override
    public Tree clone() {
        Tree t = new Tree(this.getX(),this.getY());
        return t;
    }

    @Override
    public void update(double time) {

    }
}
