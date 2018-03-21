package projects.mediavle_game.map;

import engine.core.exceptions.CoreException;
import engine.core.sourceelements.RawModel;
import engine.core.system.Sys;
import engine.linear.entities.Entity;
import engine.linear.entities.TexturedModel;
import engine.linear.loading.Loader;
import engine.linear.loading.OBJLoader;
import engine.linear.material.EntityMaterial;
import projects.mediavle_game.map.entities.abs.UniqueGameEntity;
import projects.mediavle_game.map.entities.obs.Ground;
import projects.mediavle_game.map.entities.obs.Tree;

/**
 * Created by finne on 20.03.2018.
 */
public class GroundMap extends Ground {

    public static final int WIDTH = 1000;
    public static final int HEIGHT = 1000;

    Field[][] fields;

    public GroundMap() {
        super(0, 0, WIDTH, HEIGHT);
        fields = new Field[WIDTH][HEIGHT];

        for (int i = 0; i < WIDTH; i++) {
            for (int n = 0; n < HEIGHT; n++) {
                fields[i][n] = new Field();
            }
        }
        initGame();
    }

    public Field[][] getFields() {
        return fields;
    }

    public void setFields(Field[][] fields) {
        this.fields = fields;
    }

    public boolean isRigidBody(float x, float z) {
        if (x > 0 && z > 0 && x < this.width && z < this.height && fields[(int) x][(int) z].getGameEntity() != null) {
            if (x > 0 && z > 0 && x < this.width && z < this.height) {
                if (fields[(int) x][(int) z].getGameEntity().isRigidBody()) return true;
            }
        }
        return false;
    }

    public void initGame() {
        for(int i = 0; i < 15000; i++) {
            int x = (int) (Math.random() * WIDTH);
            int y = (int) (Math.random() * HEIGHT);
            Tree tree = new Tree(x,y);
            tree.generateEntity();
            fields[x][y].setUniqueGameEntity(tree);
        }
    }

}
